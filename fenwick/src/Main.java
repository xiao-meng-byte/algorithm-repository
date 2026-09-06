//引用牛客PEEK73 【模板】动态区间和Ⅰ ‖ 单点修改 + 区间查询
//题目链接如下：
//https://www.nowcoder.com/practice/3d5796cd11d44b77bfb59e550beba3ee?tpId=388&tqId=11092602&channelPut=tracker1)
//同样的牛客这个题对应洛谷的P3374,链接如下：
//https://www.luogu.com.cn/problem/P3374
//没问题，直接给你 **Markdown 代码块（纯文本）** 的格式。为了排版不乱，直接放在 ` ```text ` 里面，你可以直接复制：
//树状数组完整原理推导
//我们用一段推导来表达它的原理：
//一.树状数组核心思想
//我们在这里对任意数组下标x，tree[x]专门维护一段连续区间：
//区间右端点固定为x，区间长度等于lowbit(x)，这样的区域为下标x所管辖的区域。
//假设有一个下标1~16的数组，我们每次通过对半开的形式，然后计算各下标单独管辖范围：
//- x=1，lowbit(1)=1，管辖区间[1]
//- x=2，lowbit(2)=2，管辖区间[1,2]
//- x=3，lowbit(3)=1，管辖区间[3]
//- x=4，lowbit(4)=4，管辖区间[1,2,3,4]
//- x=5，lowbit(5)=1，管辖区间[5]
//- x=6，lowbit(6)=2，管辖区间[5,6]
//- x=7，lowbit(7)=1，管辖区间[7]
//- x=8，lowbit(8)=8，管辖区间[1~8]
//- x=9，管辖[9]；
//- x=10，管辖[9,10]；
//- x=11，管辖[11]；
//- x=12，管辖[9,10,11,12]
//- x=13，管辖[13]；
//- x=14，管辖[13,14]；
//- x=15，管辖[15]；
//- x=16,管全部。
//也就是说在1~n范围内，任意下标x，以x作为子数组右边界、区间长度为lowbit(x)的连续区间，就是树状数组tree[x]独立管理的区域；所有以x为右边界的前缀区间，都可以拆分为多段上述标准区间累加，也就是前缀和查询的底层逻辑。
//二.前缀和查询原理
//求前缀和sum(1~x)流程：
//1. 累加当前x对应的管辖区间和tree[x]；
//2. x = x - lowbit(x)，跳到下一段靠左区间的右边界；
//3. 循环操作直至x=0。
//举例：求sum(1~6)
//1. x=6，lowbit=2，管辖[5,6]，累加tree[6]，x=6-2=4
//2. x=4，lowbit=4，管辖[1,4]，累加tree[4]，x=4-4=0
//最终总和：tree[4]+tree[6]，完整覆盖1~6所有元素。
//通俗点来说就是比如说我们要求1~12的累加和，就先看12的二进制（1100）,对应到图表中12管9 ~12下标的元素，然后我们找到它二进制最右边1，然后把它消去，变为（1000）就是8，然后在对应上面，它管1 ~8，那么我们按这样的变换把所有元素加起来就是1 ~
//三.单点更新原理
//给下标x数值增加v流程：
//1. tree[x] += v，更新当前x管辖区间；
//2. x = x + lowbit(x)，跳到所有覆盖x的更大右边界；
//3. 循环操作直至x>n。
//原理：所有右端点大于x、且管辖区间包含下标x的树状数组节点，都需要同步更新数值。
//这里也是一样，对一个值进行更行，比如给第3个元素加一个v，我们要看它的树状数组每个下标，看有哪几个管辖这下标3，它们的值都要加上一个v，那应该怎么做呢？其实还是位运算，3的二进制是0011，那我们减去最右边的1，然后再加上原二进制加上它最右侧的1，就是0010+0010=0100（就是4的二进制），和上面的推导相符，然后接着这样（直到越界）就可找到每一个管辖了3的下标，把它们都加上一个v，这样我们就修改成功了。
//重点注释：
//树状数组的核心是要取一个数字二进制的最右边的1，那为啥x&(-x)能实现呢，推导过程如下：
//11的二进制是00001011，对它进形结论中的操作：
//+11原码：00001011
//-11原码：10001011
//-11反码：11110100
//-11补码：11110101
//0 0 0 0 1 0 1 1
//1 1 1 1 0 1 0 1
//↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
//0 0 0 0 0 0 0 1
//可知11的二进制最低位的1恰好是原数最低位的1
//其实就是负数用补码（按位取反 + 1），使x与-x仅在最低位 1 处同为 1，按位与便只取出该位权值，得到它二进制最低位1的位置
//即证明
import java.io.*;
import java.util.*;
public class Main{
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out=new PrintWriter(System.out);
    static StringTokenizer st;
    static int N=500010;//按题目开出一个合适的空间
    static long []Tree;
    static int op;
    static int n;
    static int m;
    public static int lowbit(int x){//核心方法：取出二进制最右边的1
        return x&(-x);
    }
    public static void add(int x,int v){
        while(x<=N){//和上面的推导一样
            Tree[x]+=v;//把这些管辖了x的下标元素都加v
            x+=lowbit(x);//找到每位管辖x的下标
        }
    }
    public static long query(int x){
        long res=0;
        while(x>0){
            res+=Tree[x];//再把每个下标的元素相加
            x-=lowbit(x);//我们不断抹去x的二进制最右边的1
        }
        return res;
    }
    public static void main(String[] args) throws IOException{
        Tree = new long[N + 1];
        st=new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        st=new StringTokenizer(br.readLine());
        for(int i=1;i<=n;i++){
            int num=Integer.parseInt(st.nextToken());
            add(i,num);//这个一定不要忘记了，就是要初始化
        }
        while(m-->0){
            st=new StringTokenizer(br.readLine());
            op=Integer.parseInt(st.nextToken());
            if(op==1){
                int i=Integer.parseInt(st.nextToken());
                int x=Integer.parseInt(st.nextToken());
                add(i,x);
            }else{
                int l=Integer.parseInt(st.nextToken());
                int r=Integer.parseInt(st.nextToken());
                out.println(query(r)-query(l-1));
            }
        }
        out.flush();
        br.close();
        out.close();
    }
}