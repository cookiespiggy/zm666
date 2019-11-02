package rocksdb;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * https://www.jb51.net/article/131153.htm
 * RockDB 安装与使用 极其简单 注重你的业务去吧
 */
public class RocksDBDemo {

    private static RocksDB db;
    private static Options options;

    static {
        RocksDB.loadLibrary();
        options = new Options().setCreateIfMissing(true);
        try {
            /* should exist*/
            db = RocksDB.open(options, "/Users/mizhe/job/workspaces/zm666/tmp/rocksdb");
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RocksDBException {
        write();
    }

    public static void write() throws RocksDBException {
        byte[] key_1 = b();
        db.put(key_1, b());
        byte[] v_1 = db.get(key_1);

        byte[] key_2 = b();
        byte[] v_2 = db.get(key_2);
        if (v_2 == null) {
            db.put(key_2, v_1);
        }
        System.out.println(Arrays.equals(db.get(key_1), db.get(key_2)));
    }

    public static byte[] b() {
        byte[] key_1 = new byte[32];
        ThreadLocalRandom.current().nextBytes(key_1);
        return key_1;
    }

    public static void close() {
        if (db != null) db.close();
        options.dispose();
    }
}
