//引用洛谷P3372 【模板】线段树 1
//题目链接如下：https://www.luogu.com.cn/problem/P3372
//双树状数组模拟解法
//在使用这个解法前，我们先思考一下：对于树状数组来说，要解这个题,还是前缀和和差分的结合，但我们要优化，推导过程如下：
//一、基础定义
//设原数组 a[1...n]，规定 a0 = 0
//差分数组 b[1...n]：
//bi = ai - a(i-1)
//设 c[k] 为差分数组 b 的前缀和：
//c[k] = sum(i=1 to k) bi = b1 + b2 + ... + bk
//二、步骤1：证明差分数组前缀和 c[k] = ak
//将 bi = ai - a(i-1) 代入展开：
//c[k] = b1 + b2 + b3 + ... + bk= (a1 - a0) + (a2 - a1) + (a3 - a2) + ... + (ak - a(k-1))
//裂项全部抵消，a0 = 0：
//c[k] = ak
//等价关系：
//sum(i=1 to k) bi = ak
//三、步骤2：推导原数组前 k 项总和 S(k) = a1 + a2 + ... + ak
//把 ai = sum(t=1 to i) bt 代入总和公式：
//S(k) = sum(i=1 to k) ai = sum(i=1 to k) [ sum(t=1 to i) bt ]
//交换二重求和顺序
//bt 在 i=t, i=t+1, ..., i=k 中一共出现 k-t+1 次：
//S(k) = sum(t=1 to k) bt * (k - t + 1)= sum(t=1 to k) bt * [ k - (t-1) ] = sum(t=1 to k) [ k*bt - (t-1)*bt ]
//拆分求和项
//S(k) = k * sum(t=1 to k) bt - sum(t=1 to k) (t-1)*bt
//四、最终结论
//原数组前 k 项累加和公式：
//a1 + a2 + ... + ak = k * sum(i=1 to k) bi - sum(i=1 to k) (i-1)*bi
//1. sum(i=1 to k) bi：Tree1 维护，存储差分数组 bi
//2. sum(i=1 to k) (i-1)bi：Tree2 维护，存储 (i-1)*bi
//所以基于以上推导过程，我们要添加几个方法：
//通俗一点来说我们要求l~r范围内的累加和就是先去维护一个方法query(求1 ~k的累加和），l ~r的累加和就是query（1-r）-query(1-(l-1)), 就是图中的range方法，而要区间增值，其实也是和差分是一样的，照着公式来说：
//a1 + a2 + ... + ak = k * sum(i=1 to k) bi - sum(i=1 to k) (i-1)*bi
//1. sum(i=1 to k) bi：Tree1 维护，存储差分数组 bi
//2. sum(i=1 to k) (i-1)bi：Tree2 维护，存储 (i-1)*bi
//差分我修改的话就要分两个部分来修改(就和上图一样)，正好对应了我们设出的两个树状数组，所以代码如下：
import java.io.*;
import java.util.*;
public class Test02{//提交的时候记得改为class Main
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    static StringTokenizer st;
    final static long MAX=1000010;
    // Tree1：维护差分数组b[i]的前缀和 Σb[i]
    static long []Tree1;
    // Tree2：维护 (i-1)*b[i] 的前缀和 Σ(i-1)*b[i]
    static long []Tree2;
    static int n,m;
    public static int lowbit(int x){
        return x&(-x);
    }
    // 单点更新两棵树：差分数组x位置增加v
    public static void update(int x,long v){
        // 循环向上更新所有包含x的区间节点
        for(int i=x;i<MAX;i+=lowbit(i)){
            Tree1[i]+=v;
            // Tree2对应存入 (x-1)*v，和前面的推导公式相同
            Tree2[i]+=(x-1)*v;
        }
    }
    // 区间 [l,r] 整体加上数值v，进行一个差分的操作
    public static void RangeAdd(int l,int r,long v){
        update(l,v);     // l位置增量v，l之后全部+ v
        update(r+1,-v);  // r+1位置减v，这样就可以抵消增量，而且仅l~r生效
    }
    // 查询原数组前x项总和，套用前面推出来的公式 S(x) = x*Σb[i] - Σ(i-1)*b[i]
    public static long getSum(int x){
        long res1=0; // 存储Tree1前缀和 Σb[i]
        long res2=0; // 存储Tree2前缀和 Σ(i-1)*b[i]
        // 从x往左拆分区间累加
        for(int i=x;i>0;i-=lowbit(i)){
            res1+=Tree1[i];
            res2+=Tree2[i];
        }
        return res1*x-res2;
    }
    // 查询任意区间 [l,r] 的累加和
    // 区间和 = 前r项总和 - 前l-1项总和
    public static long RangeQuery(int l,int r){
        return getSum(r)-getSum(l-1);
    }
    public static void main(String[] args) throws IOException{
        st=new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        //Math.toIntExact方法是用于将Long转为int,其实也可以(int)来强转的
        Tree1=new long[Math.toIntExact(MAX)];
        Tree2=new long[Math.toIntExact(MAX)];
        st=new StringTokenizer(br.readLine());
        for(int i=1;i<=n;i++){
            long num=Long.parseLong(st.nextToken());
            // 单点赋值就是相当于在区间[i,i]都增加num
            RangeAdd(i,i,num);
        }
        while(m-- > 0){
            st=new StringTokenizer(br.readLine());
            int op=Integer.parseInt(st.nextToken());
            if(op==1){
                int l=Integer.parseInt(st.nextToken());
                int r=Integer.parseInt(st.nextToken());
                long k=Long.parseLong(st.nextToken());
                RangeAdd(l,r,k);
            }else{
                int l=Integer.parseInt(st.nextToken());
                int r=Integer.parseInt(st.nextToken());
                out.println(RangeQuery(l,r));
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}