//力扣268题,题目链接：https://leetcode.cn/problems/missing-number/description/
//给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数，还是先看代码：
class Solution6 {//提交的时候记得改为Solution
    public int missingNumber(int[] nums) {
        int res=0;
        int n=nums.length;
        for(int i=0;i<=n;i++){
            res^=i;
        }
        for(int num:nums){
            res^=num;
        }
        return res;
    }
}
/*
其实这个题还是在找不同，一样的我们能想到异或，其实这里我们知道一个数异或自身为0，一个数异或0得到本身。
那么我们找缺失的数字其就是我们先用一个res来异或1~n，然后来异或数组里的数字，如果说前面异或1~n中数组中有那就相当于异或自身，得到0，最后我们都是成双成对得异或变为0了，留下一个单落下了的数字和前面的0异或得到本身，那就是这个缺失的数字了。
*/