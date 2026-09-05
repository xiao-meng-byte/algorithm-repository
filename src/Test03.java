//洛谷P3373【模板】线段树 2,题目链接：https://www.luogu.com.cn/problem/P3373
import java.io.*;
import java.util.*;
class SegmentTree01 {
    int n, mod;
    long[] tree, lazyMul, lazyAdd;
    public SegmentTree01(int n, int mod) {
        this.n = n;
        this.mod = mod;
        //这里和前面一样开出4倍空间
        int size = 4 * n;
        tree = new long[size];
        //这里开出两个懒标记数组，一个管乘法，一个管加法
        lazyMul = new long[size];
        lazyAdd = new long[size];
        //初始化乘法懒标记值为1
        Arrays.fill(lazyMul, 1);
    }
    public void update(int node, int start, int end, int index, int value) {
        //但递归到叶子节点的时候，直接赋值并取模
        if (start == end) {
            tree[node] = value % mod;
        } else {
            // 要访问子节点，必须先把当前节点堆积的懒标记下传
            pushdown(node, start, end);
            int mid = (start + end) >> 1;
            // 如果修改下标落在左半区间的话，就递归左子节点node*2
            if (index <= mid) {
                update(node << 1, start, mid, index, value);
            } else {
                // 如果修改下标落在右半区间的话，递归右子节点 node*2+1，用为位运算来写就是(node<<1)|1
                update((node << 1) | 1, mid + 1, end, index, value);
            }
            // 但左右子树更新完成，向上合并区间总和再取模
            tree[node] = (tree[node << 1] + tree[(node << 1) | 1]) % mod;
        }
    }
    public int query(int node, int start, int end, int left, int right) {
        // 如果当前区间与查询区间完全无交集，就不用管它
        if (left > end || right < start) return 0;
        // 如果当前区间完全被查询区间包裹，那就直接直接返回预存的区间和
        if (left <= start && right >= end) return (int) tree[node];
        // 要访问子节点，先下传懒标记再说
        pushdown(node, start, end);
        int mid = (start + end) >> 1;
        // 最后分别查询左右子树，累加结果后取模
        long res = query(node << 1, start, mid, left, right) + query((node << 1) | 1, mid + 1, end, left, right);
        return (int) (res % mod);
    }
    private void pushdown(int node, int start, int end) {
        // 无任何待执行操作，直接跳过
        if (lazyMul[node] == 1 && lazyAdd[node] == 0) return;
        int mid = (start + end) >> 1;
        int lson = node << 1;       // 左子节点
        int rson = lson | 1;        // 右子节点
        long m = lazyMul[node];     // 当前节点乘法标记
        long a = lazyAdd[node];     // 当前节点加法标记
        // 更新左子树区间和、更新左子树懒标记
        tree[lson] = (tree[lson] * m + a * (mid - start + 1)) % mod;
        lazyMul[lson] = (lazyMul[lson] * m) % mod;
        lazyAdd[lson] = (lazyAdd[lson] * m + a) % mod;
        // 更新右子树区间和、更新右子树懒标记
        tree[rson] = (tree[rson] * m + a * (end - mid)) % mod;
        lazyMul[rson] = (lazyMul[rson] * m) % mod;
        lazyAdd[rson] = (lazyAdd[rson] * m + a) % mod;
        // 要记得清空当前节点懒标记，恢复初始状态
        lazyMul[node] = 1;
        lazyAdd[node] = 0;
    }
    public void rangeMul(int node, int start, int end, int l, int r, long val) {
        // 当前区间与修改区间无交集，直接返回就是了
        if (r < start || l > end) return;
        // 当前区间完全被修改区间覆盖，打懒标记，就不下传子节点（能偷懒就偷懒）
        if (l <= start && end <= r) {
            tree[node] = (tree[node] * val) % mod;
            lazyMul[node] = (lazyMul[node] * val) % mod;
            lazyAdd[node] = (lazyAdd[node] * val) % mod;
            return;
        }
        // 如果部分重叠，下传标记后递归处理子树
        pushdown(node, start, end);
        int mid = (start + end) >> 1;
        rangeMul(node << 1, start, mid, l, r, val);
        rangeMul((node << 1) | 1, mid + 1, end, l, r, val);
        // 最后合并更新后的左右子树和
        tree[node] = (tree[node << 1] + tree[(node << 1) | 1]) % mod;
    }
    public void rangeAdd(int node, int start, int end, int l, int r, long val) {
        // 当前区间与修改区间无交集，还是直接返回
        if (r < start || l > end) return;
        // 当前区间完全被修改区间覆盖，打加法懒标记
        if (l <= start && end <= r) {
            tree[node] = (tree[node] + val * (end - start + 1)) % mod;
            lazyAdd[node] = (lazyAdd[node] + val) % mod;
            return;
        }
        // 如果部分重叠，下传标记后递归处理子树
        pushdown(node, start, end);
        int mid = (start + end) >> 1;
        rangeAdd(node << 1, start, mid, l, r, val);
        rangeAdd((node << 1) | 1, mid + 1, end, l, r, val);
        // 最后合并更新后的左右子树和
        tree[node] = (tree[node << 1] + tree[(node << 1) | 1]) % mod;
    }
}
public class Test03 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        int mod = Integer.parseInt(st.nextToken());
        SegmentTree01 stree = new SegmentTree01(n, mod);
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            int num = Integer.parseInt(st.nextToken());
            stree.update(1, 1, n, i, num);//初始化我们的线段树
        }
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if (op == 1) {
                int k = Integer.parseInt(st.nextToken());
                stree.rangeMul(1, 1, n, x, y, k);
            } else if (op == 2) {
                int k = Integer.parseInt(st.nextToken());
                stree.rangeAdd(1, 1, n, x, y, k);
            } else if (op == 3) {
                int res = stree.query(1, 1, n, x, y);
                out.println(res);
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}