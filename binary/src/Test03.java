//力扣326题,题目链接：https://leetcode.cn/problems/power-of-three/description/
class Solution2 {//提交的时候记得改为Solution
    public boolean isPowerOfThree(int n) {
        // 1. n > 0：因为 3 的任何次方（包括 3^0 = 1）都是正数，负数或 0 直接排除。
        // 2. 1162261467 % n == 0：1162261467 是 int 类型范围内最大的 3 的幂（即 3^19）。
        //    如果 n 也是 3 的幂（比如 3, 9, 27 等），那么 3^19 一定能被 n 整除（余数为 0）。
        return n > 0 && 1162261467 % n == 0;
    }
}