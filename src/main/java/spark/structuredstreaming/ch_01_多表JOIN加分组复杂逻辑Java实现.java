package spark.structuredstreaming;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.spark.sql.functions.expr;

public class ch_01_多表JOIN加分组复杂逻辑Java实现 {
    public static void main(String[] args) throws StreamingQueryException {
        Map<String, String> options = new HashMap<>();
        options.put("kafka.bootstrap.servers", "192.168.211.135:9092");
        options.put("subscribe", "market-data-input");

        SparkSession spark = SparkSession.builder().appName("demo-ss").master("local[*]").getOrCreate();

        Dataset<Row> df = spark.readStream().format("kafka")
                .options(options)
                .load();
        Dataset<Row> df_value = df.selectExpr("cast(value as string)");

        Dataset<Pojo> dsPojo = df_value.map(new MyMapFunction(), Encoders.bean(Pojo.class));

        Dataset<Pojo> timestampKafkaDS = dsPojo.withWatermark("timestamp1", "10 seconds");
        Dataset<Row> fileDF = createFileDF(spark);
        Dataset<Row> timestampFileDF = fileDF.withWatermark("timestamp2", "20 seconds");

        Dataset<Row> innerResDF = timestampKafkaDS.join(timestampFileDF, expr("stockCode1 = stockCode2 and timestamp1 >= timestamp2 and timestamp1 <= timestamp2 + interval 10 seconds"), "leftouter");

//        Dataset<Row> innerResDF2 = timestampKafkaDS.join(timestampFileDF, expr("stockCode1 = stockCode2 and timestamp1 <= timestamp2 and timestamp1 >= timestamp2 + interval 10 seconds"), "inner");
//
//        Dataset<Row> union = innerResDF.union(innerResDF2);

//        Dataset<Row> aggResult = union.withWatermark("timestamp1", "10 seconds").groupBy(
//                union.col("stockCode1").alias("Stock"),
//                functions.window(dsPojo.col("timestamp1"), "10 seconds").alias("Hour")
//        ).agg(
//                functions.max(dsPojo.col("price").alias("Max")).alias("Max"),
//                functions.min(dsPojo.col("price").alias("Min")).alias("Min"),
//                functions.sum(dsPojo.col("volume").alias("SUM_Volume")).alias("Volume"),
//                functions.avg(dsPojo.col("transId").alias("AVG_TransId")).alias("TransId"),
//                functions.count(dsPojo.col("seller").alias("COUNT_seller")).alias("seller_count ")
//        );

        Dataset<Row> count = innerResDF.withWatermark("timestamp1", "10 seconds").groupBy("stockCode1").count();

        StreamingQuery query = count.writeStream().format("console").outputMode(OutputMode.Update())
                .start();
        query.awaitTermination();
    }

    public static Dataset<Row> createFileDF(SparkSession spark) {
        List<StructField> fieldList = new ArrayList<>();
        fieldList.add(DataTypes.createStructField("stockCode2", DataTypes.StringType, false));
        fieldList.add(DataTypes.createStructField("otherCol1", DataTypes.StringType, true));
        fieldList.add(DataTypes.createStructField("timestamp2", DataTypes.TimestampType, true));
        StructType rowSchema = DataTypes.createStructType(fieldList);
        ExpressionEncoder<Row> rowEncoder = RowEncoder.apply(rowSchema);
        Dataset<String> ds = spark.readStream().textFile("dataPath").as(Encoders.STRING());
        Dataset<Row> domainDF = ds.map((MapFunction<String, Row>) line -> {
            String[] split = line.split(",");
            String stockCode = split[0];
            String otherCol1 = split[1];
            String otherCol2 = split[2];
            Timestamp timeStamp = Util.getTimeStamp(otherCol2);
            List<Object> objectList = new ArrayList<>();
            objectList.add(stockCode);
            objectList.add(otherCol1);
            objectList.add(timeStamp);
            return RowFactory.create(objectList.toArray());
        }, rowEncoder);
        return domainDF;
    }
}
