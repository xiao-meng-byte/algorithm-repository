//kmp失配树
import java.util.*;
import java.io.*;
public class Test {
    static int[] head;
    static int[] nextEdge;
    static int[] to;
    static int cnt;
    static final int LOG = 20;
    static int[][] up;
    static int[] depth;
    static int[] size;
    public static int[] nextArray(char[] s, int m) {
        int[] next = new int[m + 1];
        next[0] = -1;
        if (m >= 1) next[1] = 0;
        int i = 2, cn = 0;
        while (i <= m) {
            if (s[i - 1] == s[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }
    public static void BuildFailTree(int[] next, int m) {
        head = new int[m + 1];
        Arrays.fill(head, -1);
        nextEdge = new int[m + 1];
        to = new int[m + 1];
        cnt = 0;
        for (int i = 1; i <= m; i++) {
            int num = next[i];
            to[cnt] = i;
            nextEdge[cnt] = head[num];
            head[num] = cnt++;
        }
    }
    public static void dfs(int u) {
        size[u] = 1;
        for (int e = head[u]; e != -1; e = nextEdge[e]) {
            int v = to[e];
            depth[v] = depth[u] + 1;
            up[0][v] = u;
            for (int k = 1; k < LOG; k++) {
                up[k][v] = up[k - 1][up[k - 1][v]];
            }
            dfs(v);
            size[u] += size[v];
        }
    }
    public static int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int tmp = u; u = v; v = tmp;
        }
        int diff = depth[u] - depth[v];
        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }
        if (u == v) return u;
        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }
        return up[0][u];
    }
    public static int getAncestor(int i, int k) {
        for (int j = 0; j < LOG; j++) {
            if ((k & (1 << j)) != 0) {
                i = up[j][i];
            }
        }
        return i;
    }
    public static int[] getAllBorders(int[] next, int i) {
        int count = 0;
        int cur = next[i];
        while (cur > 0) {
            count++;
            cur = next[cur];
        }
        int[] ans = new int[count];
        cur = next[i];
        int index = 0;
        while (cur > 0) {
            ans[index++] = cur;
            cur = next[cur];
        }
        return ans;
    }
    public static int longestCommonBorder(int i, int j) {
        return lca(i, j);
    }
    public static int countPrefixesWithBorder(int i) {
        return size[i] - 1;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        char[] s1 = br.readLine().trim().toCharArray();
        int m = s1.length;
        int[] next = nextArray(s1, m);
        BuildFailTree(next, m);
        up = new int[LOG][m + 1];
        depth = new int[m + 1];
        size = new int[m + 1];
        dfs(0);
        for (int i = 1; i <= m; i++) {
            int[] num = getAllBorders(next, i);
            out.print("prefix " + i + " borders: ");
            for (int n : num) out.print(n + " ");
            out.println();
        }
        // 读两个整数，兼容空白分隔
        String line = br.readLine();
        if (line != null && !line.trim().isEmpty()) {
            String[] str = line.trim().split("\\s+");
            int x = Integer.parseInt(str[0]);
            int y = Integer.parseInt(str[1]);
            if (m >= 2) {
                out.println("LCA(" + x + "," + y + ") = " + lca(x, y));
            }
        }
        for (int i = 1; i <= m; i++) {
            out.println("node " + i + " subtree size = " + size[i] + ", prefixes with border = " + (size[i] - 1));
        }
        out.flush();
        br.close();
        out.close();
    }
}