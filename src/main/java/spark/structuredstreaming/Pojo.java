package spark.structuredstreaming;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
public class Pojo implements Serializable {

    private long transId;
    private Timestamp timestamp1;
    private String stockCode1;
    private String seller;
    private String buyer;
    private double price;
    private long volume;

    public Pojo(String[] row) {
        this.transId = Long.parseLong(row[0]);
        //2017-08-16 15:35:45
        this.timestamp1 = Util.getTimeStamp(row[1]);
        this.stockCode1 = row[2];
        this.seller = row[3];
        this.buyer = row[4];
        this.price = Double.parseDouble(row[5]);
        this.volume = Long.parseLong(row[6]);
    }
}
