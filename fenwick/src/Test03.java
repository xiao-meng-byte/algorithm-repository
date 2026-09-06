//二维单点增加和范围查询
//二维单点增加和范围查询，顾名思义，其实就是对一个二维矩阵单点进行加上一个值，和查询子矩阵的和，其实就是二维前缀和的思想了
//所以在这里我们基于以上的思想我们一样可以进行，而范围查询其实就是二维下的query范围相减即可，所以代码如下：
import java.io.*;
import java.util.*;
public class Test03 {//提交的时候记得改为class Main
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    static StringTokenizer st;
    static int n, m;
    final static int N = 2010;
    static int[][] Tree = new int[N][N];
    static int[][] matrix = new int[N][N];
    public static int lowbit(int x) {
        return x & (-x);
    }
    // 单点修改：在 (x, y) 位置加上 v
    public static void update(int x, int y, int v) {
        for (int i = x; i <= n; i += lowbit(i)) {
            for (int j = y; j <= m; j += lowbit(j)) {
                Tree[i][j] += v;
            }
        }
    }
    // 查询二维前缀和：从 (1, 1) 到 (x, y) 的和
    public static int query(int x, int y) {
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            for (int j = y; j > 0; j -= lowbit(j)) {
                res += Tree[i][j];
            }
        }
        return res;
    }
    // 利用二维前缀和的容斥原理，求子矩阵的和
    public static long getSum(int x1, int y1, int x2, int y2) {
        return (long) (query(x2, y2) - query(x1 - 1, y2) - query(x2, y1 - 1) + query(x1 - 1, y1 - 1));
    }
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        // 1. 读入初始矩阵，并构建二维树状数组
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
                // 将原矩阵的值作为增量，加入到树状数组中
                update(i, j, matrix[i][j]);
            }
        }
        int q = Integer.parseInt(br.readLine().trim());
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            if (op == 1) {
                //单点修改，将 (x, y) 的值加上 v
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                update(x, y, v);
                matrix[x][y] += v; // 需要同步更新原矩阵
            } else {
                //查询左上角 (x1, y1) 到 右下角 (x2, y2) 的子矩阵和
                int x1 = Integer.parseInt(st.nextToken());
                int y1 = Integer.parseInt(st.nextToken());
                int x2 = Integer.parseInt(st.nextToken());
                int y2 = Integer.parseInt(st.nextToken());
                out.println(getSum(x1, y1, x2, y2));//这样就可以得到我们想要的矩阵和了
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}