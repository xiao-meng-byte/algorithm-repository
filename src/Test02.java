//洛谷P4913 【深基16.例3】二叉树深度,题目链接：https://www.luogu.com.cn/problem/P4913#ide
import java.io.*;
import java.util.*;
public class Test02 {
    //创建一个二叉树
    static class Node {
        int left, right;
        Node(int l, int r) {
            left = l;
            right = r;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(br.readLine());
        Node[] tree = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            //将左右两个节点创建出来
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            tree[i] = new Node(l, r);
        }
        //用队列存放要遍历的节点
        Queue<Integer> q = new LinkedList<>();
        //1先入队列
        q.offer(1);
        //初始化深度为0
        int depth = 0;
        //只要队列不为空代表还有节点未遍历
        while (!q.isEmpty()) {
            // 首先获取当前这一层一共有多少个节点
            int size = q.size();
            //如果进入了新的一层，深度就++
            depth++;
            for (int i = 0; i < size; i++) {
                //将队首元素取出来
                int cur = q.poll();
                // 再拿出当前节点的左右孩子编号
                int l = tree[cur].left;
                int r = tree[cur].right;
                //如果左子节点存在，就加入队列，作为下一层节点
                if (l != 0) q.offer(l);
                //如果右子节点存在，就加入队列，作为下一层节点
                if (r != 0) q.offer(r);
            }
        }
        out.println(depth);//最后得出长度
        out.flush();
        br.close();
        out.close();
    }
}