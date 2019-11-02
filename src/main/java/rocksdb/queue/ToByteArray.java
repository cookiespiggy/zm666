package rocksdb.queue;

public interface ToByteArray {
    byte[] toByteArray();
    Object fromByteArray(byte[] bytes);
}