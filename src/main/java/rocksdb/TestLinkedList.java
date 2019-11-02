package rocksdb;

import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import rocksdb.queue.RocksLinkedList;

/**
 * 1. fork from https://github.com/linushp/rocksdb-utils
 * 2. option 可以优化，参考快手实时项目负责人
 */
public class TestLinkedList {
    public static void main(String[] args) throws Exception {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true).setAllowMmapWrites(true);
        options.setInfoLogLevel(InfoLogLevel.ERROR_LEVEL);
        //RocksDB rocksDB = RocksDB.open(options, "./rocksDbData");
        RocksDB rocksDB = RocksDB.open(options, "/Users/mizhe/job/workspaces/zm666/tmp/rocksdb");

        byte[] mask = new byte[]{0x10, 0x20};
        RocksLinkedList linkedList = RocksLinkedList.getInstance(rocksDB, mask);

        linkedList.addLast("1".getBytes());
        linkedList.addLast("2".getBytes());
        linkedList.addLast("3".getBytes());
        linkedList.addLast("4".getBytes());

        RocksLinkedList linkedList2 = RocksLinkedList.getInstance(rocksDB, mask);
        byte[] x = linkedList2.pollFirst();

        System.out.println(linkedList == linkedList2);
        System.out.println(linkedList.size());
        System.out.println(new String(x));
    }
}
