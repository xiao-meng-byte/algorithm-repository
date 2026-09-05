//力扣136题，题目链接：https://leetcode.cn/problems/single-number/submissions/747066741/
class Solution {
    public int singleNumber(int[] nums) {
        int eor = 0; // 初始化一个变量 eor，用来做异或累加，初始值为 0
        // 遍历数组中的所有数字
        for (int i = 0; i < nums.length; i++) {
            // 核心逻辑：异或运算
            // 1. 任何数与 0 异或，结果等于它本身 (x ^ 0 = x)
            // 2. 任何数与自身异或，结果等于 0 (x ^ x = 0)
            // 3. 异或运算满足交换律和结合律
            eor ^= nums[i];
        }
        // 因为题目保证只有一个数字出现一次，其他所有数字都恰好出现两次
        // 所以两两相同的数字异或后都会变成 0，最后剩下的 eor 就是那个只出现一次的数字
        return eor;
    }
}