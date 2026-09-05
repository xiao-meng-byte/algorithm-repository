//力扣461题,题目链接：https://leetcode.cn/problems/hamming-distance/description/
//求两个数字对应二进制位不同的位置的数目，还是先看代码：
class Solution5 {//提交的时候记得改为Solution
    public int hammingDistance(int x, int y) {
        int n=x^y;
        int cnt = 0;
        while (n != 0) {
            n = n & (n - 1);
            cnt++;
        }
        return cnt;
    }
}
/*
其实在这里我们要两个数字的二进制的不同数字的个数，能想到啥？
其实是异或，因为异或相同为0，不同为1，我们可以对此进行标记，再对两个数异或后，其实就发现它不同的数字都被前面的异或运算变为了1，此时要看不同的数字个数，就是数异或后的二进制数字中的1的个数，那其实就是结论⑧了。
*/