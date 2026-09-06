//引用：牛客PEEK74 区间增量与单点求值
//题目链接如下：
//https://www.nowcoder.com/practice/addd700d4f4647a3b32ccaf42f5147fb?tpId=388&tqId=11298517&channelPut=tracker1
//同样的牛客这道题对应洛谷P3368,题目链接如下：
//https://www.luogu.com.cn/problem/P3368
//其实在这里这个题唯一的改变就是从只改单个值，变为了区间改值，那我们在区间改值下能想到啥？
// 就是差分，差分顾名思义，就是数组的前后两项相减，即：b[i]=a[i]-a[i-1],经典的模版如下：
import java.io.*;
import java.util.*;
public class Test01 {//提交的时候记得改为class Main
    static long[] Tree = new long[500010];
    static int n, m;
    static long lowbit(long x) {
        return x & (-x);
    }
    static void add(long x, long v) {
        while (x <= n) {
            Tree[(int) x] += v;
            x += lowbit(x);
        }
    }
    static long query(long x) {
        long res = 0;
        while (x > 0) {
            res += Tree[(int) x];
            x -= lowbit(x);
        }
        return res;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        long[] a = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            a[i] = Long.parseLong(st.nextToken());
            // 初始化的是原数组的差分形式
            add(i, a[i] - a[i - 1]);
        }
        while (m-- > 0) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            if (op == 1) {
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());
                // 和上面代码一样，以差分的方式对区间求值
                add(x, k);
                add(y + 1, -k);
            } else {
                int x = Integer.parseInt(st.nextToken());
                out.println(query(x)); // 差分求和就是原数组
            }
        }
        out.flush();
        br.close();
        out.close();
    }
}