package com.mstdemo.mst.vo;

public class SelectCode5 {
    /**
     * 选择排序:找到最小值所在的位置与其他值比较及交换
     * @param arr
     */
        public static void selectArray(int[] arr){
           //不需要排序
            if(arr==null || arr.length<2){
                return;
            }
            /**
             * 需要排序
             * 在一个范围上做一件事找到最小值及最小值所在的位置
             * 0---n-1
             * 1---n-1
             * 2---n-1
             * 3---n-1
             * 4---n-1
             * 5---n-1
             */
            int n = arr.length;
            for (int i=0;i < n;i++){
//                最小值所在的位置
                int minIndexValie=i;
                //获取i后面位置及值
                for (int j=i+1;j<n;j++){
//                    分别 j 位置的数与 i 位置的数取最小的位置
                    minIndexValie = arr[j]<arr[minIndexValie]? j:minIndexValie;
                }
                //将最小值的位置与i的位置交换
                swap(arr,i,minIndexValie);

            }
            }
    //数组临时变量交换
    public static void swap(int[] arr,int a, int b){
            int temp =arr[a];
            arr[a] = arr[b];
            arr[b]=temp;
    }

    public static void printArray(int[] arr){
        for (int i=0;i < arr.length;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


//   ------------------------------------------------------------------------

    /**
     * 冒泡排序：相邻的两个元素逆序：就交换
     * @param args
     */























    public static void main(String[] args) {
        int[] arr = {7,5,4,3,1,2,6};
        printArray(arr);
        selectArray(arr);
        printArray(arr);
    }
}
