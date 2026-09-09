//题目链接:https://leetcode.cn/problems/n-queens-ii/description/
class NQueens {//提交的时候要改为Solution
    public static int totalNqueens(int n){
        if(n<1) return 0;
        return fun(0,new int[n],n);
    }
    //i指代当前来到的行
    //path 是表示0~i-1行的皇后，都摆了在了哪些列
    //n表示问题的规模
    //0~i-1行已经摆完的情况下，在i~n-1行可以去尝试的情况下还能找到几种方法
    public static int fun(int i,int []path,int n){
        if(i==n){
            return 1;
        }
        int res=0;
        //j就是尝试的列数
        for(int j=0;j<n;j++){
            if(check(path,i,j)){
                path[i]=j;
                res+=fun(i+1,path,n);
            }
        }
        return res;
    }
    //当前在i行，j列的位置，摆了一个皇后
    //0~i-1行的皇后状况、
    //返回会不会冲突，不会冲突，就有效返回true
    //如果会冲突，无效，返回false
    public static boolean check(int []path,int i,int j){
         //当前行i
        //当前列j
         for(int k=0;k<i;k++){
             //0~i-1
             //之前行：k
             //之前 ：path[k]
             if(j==path[k]||Math.abs(i-k)==Math.abs(path[k]-j)){
                 return false;
             }
         }
         return true;
    }
}
