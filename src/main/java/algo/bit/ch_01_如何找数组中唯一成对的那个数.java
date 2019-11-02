package algo.bit;

import algo.util.ArrayUtils;

import java.util.Random;

/**
 * 精髓：88 ^ 88 ^ 88 = 88
 * https://www.cnblogs.com/jasonkoo/articles/2760411.html
 */
public class ch_01_如何找数组中唯一成对的那个数 {

    public static void main(String[] args) {
        int N = 11;
        int[] arr = new int[N]; // 长度为11的数组
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = i + 1;
        }
        // 最后一个数为随机数 # nextInt 不包含bound的值
        arr[arr.length - 1] = new Random().nextInt(N - 1) + 1;
        // 随机下标
        int index = new Random().nextInt(N);
        System.out.println(index);
        ArrayUtils.swap(arr, index, arr.length - 1);
        ArrayUtils.printArr(arr);
        solution(arr, 11);
    }

    public static void solution(int[] arr, int n) {
        int x = 0;
        for (int i = 1; i <= n - 1; i++) {
            x = x ^ i;
        }
        for (int i = 0; i < n; i++) {
            x = x ^ arr[i];
        }
        System.out.println(x);
    }
}

class 测试位运算 {
    /*
    结论：任何数异或自己为0
        任何数异或0为自己
     */
    public static void main(String[] args) {
        System.out.println(88 ^ 88); // 0
        System.out.println(88 ^ 0); // 88
        System.out.println(88 ^ 88 ^ 88); // 88
        // 与下面这些都没有关系，看看注释的那篇博客，总结一下异或的性质，交换律，结合律
        System.out.println(88 ^ 99); // 59
        System.out.println(1 ^ 2); // 3
        System.out.println(3 ^ 4); // 7
        System.out.println(6 ^ 7); // 1
        System.out.println(5 ^ 9); // 12
        System.out.println(0 ^ 88 ^ 88 ^ 99 ^ 100 ^ 88); // 95
    }
}
