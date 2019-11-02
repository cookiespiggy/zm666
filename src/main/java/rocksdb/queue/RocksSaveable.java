package rocksdb.queue;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.io.IOException;

public interface RocksSaveable {
    void save(RocksDB rocksDB) throws IOException, RocksDBException;

    void deleteFromRocks(RocksDB rocksDB) throws RocksDBException;
}