//力扣190题,题目链接：https://leetcode.cn/problems/reverse-bits/description/
//颠倒给定的 32 位有符号整数的二进制位，如何搞呢？先看代码：
class Solution7 {//提交的时候记得改为Solution
    public int reverseBits(int n) {
        int res = 0;
        for(int i = 0; i < 32; i++){
            res <<= 1;
            res += n & 1;
            n >>>= 1;
        }
        return res;
    }
}
/*
这个怎么想呢？就是这个题要我们反转的是它的二进制位的顺序，就是说 10（二进制是 1010，反转后变为 0101）。
那我们就从原数字的最右边一位一位地把数位取出来，然后依次放到结果数字的最左边，循环 32 次把所有位都处理完，最后就可以得到我们要的反转结果。
而从右边取的操作是n&1,将取到的数字左移则是res<<=1,n >>> 1则是删掉末尾已取的位，这样就可以实现了。
*/