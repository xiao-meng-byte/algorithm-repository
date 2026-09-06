//二维范围增加和范围查询
//引用洛谷P4514
//题目链接如下：https://www.luogu.com.cn/problem/P4514
//其实对于一维的范围增值来说，我们使用了一维差分，所以其实很自然的就可以想到能不能用二维差分来解决这个题呢？
//二维区间加、区间求和完整数学推导
//1. 基础定义
//设：原矩阵：a[i][j],二维差分数组：d[i][j]
//二维差分性质：对矩形 [a,b] ~ [c,d] 整体加 v，仅需4个单点修改 d：
//d[a][b] += v
//d[a][d+1] -= v
//d[c+1][b] -= v
//d[c+1][d+1] += v
//还原原矩阵：a[i][j] 是 d 的二维前缀和
//a[i][j] = sum(p=1 to i) sum(q=1 to j) d[p][q]
//2. 定义全局前缀和 S(x,y)
//S(x,y) 代表左上角 (1,1) 到 (x,y) 整个矩形所有数字之和：
//S(x,y) = sum(i=1 to x) sum(j=1 to y) a[i][j]
//把 a[i][j] = sum(p=1 to i) sum(q=1 to j) d[p][q] 代入：
//S(x,y) = sum(i=1 to x) sum(j=1 to y) [ sum(p=1 to i) sum(q=1 to j) d[p][q] ]
//3. 交换求和顺序
//固定差分点 (p,q)，统计 d[p][q] 会被累加多少次：
//i 取值范围 [p, x]，j 取值范围 [q, y]
//总累加次数 = 行数 × 列数 = (x - p + 1)(y - q + 1)
//因此改写求和式：
//S(x,y) = sum(p=1 to x) sum(q=1 to y) d[p][q] * (x-p+1)(y-q+1)
//4. 展开多项式 (x-p+1)(y-q+1)
//(x-p+1)(y-q+1) = [ (x+1) - p ] * [ (y+1) - q ]= (x+1)(y+1) - (x+1)q - (y+1)p + pq
//5. 代入拆分四项求和
//将展开式代入 S(x,y)：
//S(x,y) = sum(p=1 to x) sum(q=1 to y) d[p][q] * [(x+1)(y+1) - (y+1)p - (x+1)q + pq]
// = (x+1)(y+1) * sum(p=1 to x) sum(q=1 to y) d[p][q]- (y+1) * sum(p=1 to x) sum(q=1 to y) p * d[p][q]- (x+1) * sum(p=1 to x) sum(q=1 to y) q * d[p][q]+ sum(p=1 to x) sum(q=1 to y) pq * d[p][q]
//6. 定义四个二维前缀和（对应代码4棵树）
//令4个二维前缀和：
//T1(x,y) = sum(p=1 to x) sum(q=1 to y) d[p][q]
//T2(x,y) = sum(p=1 to x) sum(q=1 to y) p * d[p][q]
//T3(x,y) = sum(p=1 to x) sum(q=1 to y) q * d[p][q]
//T4(x,y) = sum(p=1 to x) sum(q=1 to y) pq * d[p][q]
//则 S(x,y) 最简公式：
//S(x,y) = (x+1)(y+1)T1 - (y+1)T2 - (x+1)T3 + T4
//和代码 sum(x,y) 内部计算公式完全对应：
//ans += (x + 1) * (y + 1) * Tree1[i][j] - (y + 1) * Tree2[i][j] - (x + 1) * Tree3[i][j] + Tree4[i][j];
//根据上面的公式，就可以写出代码如下：
import java.io.*;
import java.util.*;
public class Test04 {
    public static int MAXN = 2050;
    public static int MAXM = 2050;
    // 维护信息 : d[i][j]
    public static int[][] Tree1 = new int[MAXN][MAXM];
    // 维护信息 : d[i][j] * i
    public static int[][] Tree2 = new int[MAXN][MAXM];
    // 维护信息 : d[i][j] * j
    public static int[][] Tree3 = new int[MAXN][MAXM];
    // 维护信息 : d[i][j] * i * j
    public static int[][] Tree4 = new int[MAXN][MAXM];
    public static int n, m;
    public static int lowbit(int i) {
        return i & -i;
    }
    // 单点更新 (x,y) 增加v
    public static void add(int x, int y, int v) {
        int v1 = v;
        int v2 = x * v;
        int v3 = y * v;
        int v4 = x * y * v;
        for (int i = x; i <= n; i += lowbit(i)) {
            for (int j = y; j <= m; j += lowbit(j)) {
                Tree1[i][j] += v1;
                Tree2[i][j] += v2;
                Tree3[i][j] += v3;
                Tree4[i][j] += v4;
            }
        }
    }
    // 查询(1,1) ~ (x,y) 前缀和
    public static int sum(int x, int y) {
        int ans = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            for (int j = y; j > 0; j -= lowbit(j)) {
                // 套用数学推导拆分公式 S=(x+1)(y+1)T1 - (y+1)T2 - (x+1)T3 + T4
                ans += (x + 1) * (y + 1) * Tree1[i][j] - (y + 1) * Tree2[i][j] - (x + 1) * Tree3[i][j] + Tree4[i][j];
            }
        }
        return ans;
    }
    // 矩形区间 [a,b] ~ [c,d] 整体加v
    public static void add(int a, int b, int c, int d, int v) {
        add(a, b, v);
        add(a, d + 1, -v);
        add(c + 1, b, -v);
        add(c + 1, d + 1, v);
    }
    // 查询矩形 [a,b] ~ [c,d] 的总和
    public static int range(int a, int b, int c, int d) {
        return sum(c, d) - sum(a - 1, d) - sum(c, b - 1) + sum(a - 1, b - 1);
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String op;
        int a, b, c, d, v;
        //按照题目要求：循环读取所有输入，直到文件末尾
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            op = in.sval;
            if (op.equals("X")) {
                in.nextToken();
                n = (int) in.nval;
                in.nextToken();
                m = (int) in.nval;
            } else if (op.equals("L")) {
                in.nextToken();
                a = (int) in.nval;
                in.nextToken();
                b = (int) in.nval;
                in.nextToken();
                c = (int) in.nval;
                in.nextToken();
                d = (int) in.nval;
                in.nextToken();
                v = (int) in.nval;
                add(a, b, c, d, v);
            } else {
                in.nextToken();
                a = (int) in.nval;
                in.nextToken();
                b = (int) in.nval;
                in.nextToken();
                c = (int) in.nval;
                in.nextToken();
                d = (int) in.nval;
                out.println(range(a, b, c, d));
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}