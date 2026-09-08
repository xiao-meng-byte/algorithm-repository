import java.util.*;
import java.io.*;
public class Test {
    // 全局静态输入输出流，提速
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    static StreamTokenizer st = new StreamTokenizer(br);

    public static void main(String[] args) throws IOException {
        // 应对多组测试数据：只要还能读到下一个整数（不是文件结束符），就继续处理
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            // 【1. 建图】
            ArrayList<ArrayList<int[]>> graph = new ArrayList<>();
            int n = (int) st.nval; // 读入节点数 n
            // 因为节点编号从 1 开始，所以初始化 size 为 n+1，0 号节点废弃不用
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }
            st.nextToken();
            int m = (int) st.nval; // 读入边数 m
            // 读入 m 条边
            for (int i = 0; i < m; i++) {
                st.nextToken();
                int u = (int) st.nval;
                st.nextToken();
                int v = (int) st.nval;
                st.nextToken();
                int w = (int) st.nval;
                // 【无向图】必须双向加边（存的是 {目标点, 权值}）
                graph.get(u).add(new int[]{v, w});
                graph.get(v).add(new int[]{u, w});
            }
            // 【2. Prim 核心数据结构】
            // 小根堆：存放所有“已选中点伸出去”的边，按权值从小到大排序
            PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);

            // 【3. 初始化】
            // 随便选一个点开始（这里选 1），把它的所有边扔进小根堆
            for (int[] edge : graph.get(1)) {
                heap.add(edge);
            }
            boolean[] set = new boolean[n + 1]; // 记录节点是否已经加入生成树（visited数组）
            int cnt = 1; // 已经选中的节点数（因为从1开始，所以初始化为1）
            set[1] = true; // 标记起点 1 已加入
            int ans = 0; // 记录最小生成树的总权值
            // 【4. 贪心选边】
            while (!heap.isEmpty()) {
                int[] edge = heap.poll(); // 每次弹出权值最小的边（贪心策略）
                int next = edge[0]; // 这条边指向的目标点
                int cost = edge[1]; // 这条边的权重
                // 如果这个目标点还没被选过（不在 set 中）
                if (!set[next]) {
                    cnt++; // 选中节点数 +1
                    set[next] = true; // 标记已加入
                    ans += cost; // 累加权值
                    // 【核心步骤】：新点加入了，把它所有的邻边也扔进堆里，供以后挑选
                    for (int[] e : graph.get(next)) {
                        heap.add(e);
                    }
                }
                // 如果这个目标点已经在 set 中，说明这条边会构成环，直接舍弃（什么都不做）
            }
            // 【5. 判断连通性并输出】
            // 如果选中的节点数 cnt 等于总节点数 n，说明最小生成树构建成功，输出总权值
            out.println(cnt == n ? ans : "orz");
            out.flush();
        }
        out.flush();
        br.close();
        out.close();
    }
}