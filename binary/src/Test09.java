//力扣476题,题目链接：https://leetcode.cn/problems/number-complement/description/
//多位翻转，即把数字二进制的有效位进行翻转，代码如下：
class Solution8 {//提交的时候记得改为Solution
    public int findComplement(int num) {
        int cnt = 0;
        int temp = num;
        while(temp>0){//通过不断地除2来看它二进制的数位
            cnt++;
            temp >>=1;
        }
        for(int i=0;i<cnt;i++){//因为下标从0开始的所以就是1<<i了
            num ^= (1<<i);
        }
        return num;
    }
}