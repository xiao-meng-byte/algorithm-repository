//题目链接：https://www.luogu.com.cn/problem/P3370#ide
import java.io.*;
import java.util.*;
public class Main {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out=new PrintWriter(System.out);
    public static final int MAXN = 10001;//表示所需要存的哈希值的个数
    public static final int BASE = 131;//随机设置为一个质数，利于计算
    public static long[] nums = new long[MAXN];//用于存哈希值
    public static int n;//字符串的个数
    public static long value(char[] s) {//将字符串拆解为字符数组
        long hash = 0;
        for (char c : s) {
            hash = hash * BASE + c;
        }
        return hash;
    }
    public static int countDistinct() {
        if (n == 0) return 0;//如果字符串个数为0，就直接输出0
        Arrays.sort(nums, 0, n);//对算出的哈希值进行排序
        int ans = 1;//初始不重复的哈希值为1
        for (int i = 1; i < n; i++) {//遍历并记录不重复的哈希值的个数
            if (nums[i] != nums[i - 1]) {//如果不相同就加加
                ans++;
            }
        }
        return ans;
    }
    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(in.readLine());
        for (int i = 0; i < n; i++) {
            char[] str = in.readLine().toCharArray();//将字符串转为字符数组
            nums[i] = value(str);//将哈希值赋值给数组
        }
        out.println(countDistinct());
        out.flush();
        out.close();
        in.close();
    }
}
//对该方法的原理进行进一步的说明
//public static long value(char[] s) {//将字符串拆解为字符数组
//      long hash = 0;
//      for (char c : s) {
//          hash = hash * BASE + c;
//        }
//        return hash;
//    }
//对于公式hash=hash*BASE+c 它是一个滚动求哈希值的公式，即带入前一个字母的哈希值进行计算
//他的原理是来源于十进制数字的计算法：num=num*10+a(num是总数，a是循环数字的每一位数）
//即计算123：
//1.一开始num=1
//2.然后num=1*10+2=12
//3.最后num=12*10+3=123，这是很常见的求十进制数字的递推式
//所以有它的迁移，我们就有了求字符串哈希值的式子
//以字符串aba为例，查找ASCII码可知，a-97,b-98,则按照公式计算为：
//先第一个字符a进入，hash=0+131*97=12707
//然后第二个字符b进入，hash=12707+131*98=25545
//继续代入，hash=25545+131*97=38252
//实际上这样算出的哈希值会越来越大，所以在工业上我们会在算式中去取模，把数字变小，利于储存.
//基于这个算式在计算时依然会有哈希冲突的出现，举个例子：
//A="\x02\x05",B="\x010B",用以上公式算出来的267.
//所以这个式子在写题时可以使用但工业上就会有更进一步的计算了