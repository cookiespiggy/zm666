package spark.structuredstreaming;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class MyMapFunction implements MapFunction<Row, Pojo> {

    @Override
    public Pojo call(Row row) throws Exception {
//        System.out.println(row.toString());
        String[] split = row.toString().replaceAll("\\[|\\]", "").split(",");
        return new Pojo(split);
    }
}
