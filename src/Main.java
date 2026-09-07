//kruskal 算法
import java.io.*;
import java.util.*;
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    static StreamTokenizer st = new StreamTokenizer(br);
    static int MAXN = 5001;
    static int MAXM = 200001;
    static int n, m;          // n = 节点数（村庄数），m = 边数（可修建的公路数）
    static int[] father = new int[MAXN];           // 并查集：father[i] 表示节点 i 的父节点
    static int[][] edges = new int[MAXM][3];       // 边集：edges[i] = {起点, 终点, 权值}
    static void init() {
        for (int i = 1; i <= n; i++) {
            father[i] = i;   // 每个节点都是自己的根节点
        }
    }
    static int find(int x) {
        if (x != father[x]) {               // 如果 x 不是根节点
            father[x] = find(father[x]);    // 递归查找根节点，并压缩路径
        }
        return father[x];                   // 返回根节点
    }
    static boolean Union(int x, int y) {
        int px = find(x);   // 找到 x 的根节点
        int py = find(y);   // 找到 y 的根节点
        if (px != py) {          // 如果根节点不同，说明不在同一集合
            father[px] = py;     // 将 x 的集合挂到 y 的集合下（简单合并）
            return true;         // 合并成功，这条边可以加入生成树
        }
        return false;            // 根相同，合并失败（加入这条边会形成环）
    }
    public static void main(String[] args) throws IOException {
        // ---- 读取 n 和 m ----
        st.nextToken();          // 读取第一个 token
        n = (int) st.nval;       // 村庄数量
        st.nextToken();          // 读取第二个 token
        m = (int) st.nval;       // 公路数量
        init();
        // ---- 读取 m 条边 ----
        for (int i = 0; i < m; i++) {
            st.nextToken();                    // 读取起点
            edges[i][0] = (int) st.nval;
            st.nextToken();                    // 读取终点
            edges[i][1] = (int) st.nval;
            st.nextToken();                    // 读取权值（修建成本）
            edges[i][2] = (int) st.nval;
        }
        // ---- Kruskal 算法第一步：按权值升序排序 ----
        // 贪心思想：优先选择成本最小的边
        Arrays.sort(edges, 0, m, (a, b) -> a[2] - b[2]);
        // ---- Kruskal 算法第二步：从小到大遍历边 ----
        int ans = 0;    // 记录最小生成树的总权值（最低成本）
        int cnt = 0;    // 记录已选入生成树的边数
        for (int i = 0; i < m; i++) {
            int from = edges[i][0];   // 起点
            int to = edges[i][1];     // 终点
            int w = edges[i][2];      // 权值
            // 如果这条边连接的两个节点不在同一集合（不会形成环）
            if (Union(from, to)) {
                cnt++;            // 选中边数 +1
                ans += w;         // 累加成本
                // 优化：提前退出
                // 最小生成树只需要 n-1 条边，选够了就不用再遍历了
                if (cnt == n - 1) {
                    break;
                }
            }
        }
        // ---- 输出结果 ----
        // 如果选中的边数 == n-1，说明所有节点连通，输出最低成本
        // 否则输出 "orz"（表示图不连通，无法实现村村通）
        out.println(cnt == n - 1 ? ans : "orz");
        out.flush();
        br.close();
        out.close();
    }
}