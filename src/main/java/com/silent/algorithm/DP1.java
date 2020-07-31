package com.silent.algorithm;

import java.util.Arrays;

/**
 * @author zhaochangren
 * @Title: DP1
 * @ProjectName CODE-X
 * @Description: TODO
 * @date 2020/6/1 17:52
 */
public class DP1 {

    public static void main(String[] args) {

    }


    /** 最长递增子序列 */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        // dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

}
