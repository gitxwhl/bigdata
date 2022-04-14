package com.mstdemo.mst.vo;

public class Code4 {



    /**
     * 阶乘
     * A（N）
     * 两种可能
     *1！  1
     *2！ 1*2
     *3！ 1*2*3
     *4！ 1*2*3*4
     *5   1*2*3*4*5
     * 这种方式设计多次相乘时间复杂度高
     *
     *A（N）
     * 1 1
     * 2 1*2
     * 3 2*3
     * 4 3*4
     * 5 4*5
     * 这种设计时间复杂度低
     *
     *
     * @param
     * @return
     */
   /* @Test
    public void test1(){




    }*/

    //二：
    public static long f2(int N){

        long ans= 0;
        long cur=1;
        for(int i=1;i <= N;i++){
            cur = cur*i;
            System.out.println(cur + "*" + i);
            ans+=cur;

        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(f2(5));

    }




}
