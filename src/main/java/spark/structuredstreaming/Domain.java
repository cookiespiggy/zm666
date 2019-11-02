package spark.structuredstreaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Domain {
    private String stockCode2;
    private String otherCol1;
    private Timestamp timestamp2;
}
