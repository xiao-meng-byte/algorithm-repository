import java.util.*;
public class Main {
    static final int N = 1000010;
    static int[] father = new int[N];
    static int[] size = new int[N];
    // 初始化：每个元素的父节点是自己，集合大小为 1
    public static void init(int n) {
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            size[i] = 1;
        }
    }
    // 查找：带路径压缩
    public static int find(int x) {
        if (father[x] != x) {
            father[x] = find(father[x]);
        }
        return father[x];
    }
    // 合并：按秩合并（小集合挂到大集合上）
    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            if (size[fx] >= size[fy]) {
                size[fx] += size[fy];
                father[fy] = fx;
            } else {
                size[fy] += size[fx];
                father[fx] = fy;
            }
        }
    }
    // 判断是否在同一个集合
    public static boolean is_same_set(int x, int y) {
        return find(x) == find(y);
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        int M = input.nextInt();
        init(N); // 注意：如果输入编号是 1~N，init 需要初始化到 N+1（包含 N）
        for (int i = 1; i <= M; i++) {
            int A = input.nextInt();
            int B = input.nextInt();
            union(A, B);
        }
        int count = 0;
        for (int i = 1; i <= N; i++) { // 注意这里是从 1 开始
            if (find(i) == i) {
                count++;
            }
        }
        System.out.println(count);
        input.close();
    }
}