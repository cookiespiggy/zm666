package algo.util;

public class ArrayUtils {

    public static void printArr(final int[] arr) {
        final StringBuilder _sb = new StringBuilder("arr: (");
        for (int i = 0; i <= arr.length - 1; i++) {
            _sb.append(arr[i]);
            if (i != arr.length - 1) {
                _sb.append(", ");
            }
        }
        _sb.append(")");
        System.out.println(_sb.toString());
    }

    // 交换数组下标为x和y的位置
    public static void swap(final int[] arr, final int x, int y) {
        int tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }

    public static void swapByBit(final int[] arr, final int x, int y) {
        //https://blog.csdn.net/qq_34072526/article/details/88992578
        // a = a ^ b    b = a ^ b  a = a ^ b  交换律
        arr[x] = arr[x] ^ arr[y];
        arr[y] = arr[x] ^ arr[y];
        arr[x] = arr[x] ^ arr[y];
    }

    public static void main(String[] args) {
        int[] arr = new int[3];
        arr[0] = 1;
        arr[1] = 3;
        arr[2] = 9;
        printArr(arr);
        swap(arr, 0, 2);
        printArr(arr);
        swapByBit(arr, 0, 2);
        printArr(arr);
    }
}
