//洛谷P1886 【模板】单调队列 / 滑动窗口
//题目链接:https://www.luogu.com.cn/problem/P1886
//所以我们在维护窗口的同时，我们可以思考在窗口遍历的时候我发现进数组的元素比前面的大（或者小）我可不可以就不用看前面的元素，
// 就是删掉它们，属于一个动态的过程，到窗口末尾的时候就停止呢，这样我们的效率就快了不少，
// 本质上双端队列就是有首尾都可出进的功能，所以就可以用他来思考，不一定用它，其实手动模拟也行，所以用java来模拟：
import java.io.*;
import java.util.*;
public class Test {//提交的时候记得改为class Main
    static final int MAXN = 1000005;
    static int[] a = new int[MAXN];  // 存储原始数组
    static int[] q = new int[MAXN];  // 用数组模拟队列，队列里存的是【下标】，而不是数组的值
    static int front, rear;          // 定义队列头、尾指针
    // 函数：求滑动窗口最小值（单调递增队列）
    public static void slidingWindowMin(int n, int k, PrintWriter out) {
        front = 0;
        rear = -1;  // 初始化队列：rear=-1表示空队列
        for (int i = 0; i < n; i++) {
            // 首先：维护单调递增
            // 队列不为空且当前数 <= 队尾下标对应的数
            // 说明队尾那个数不可能成为最小值了，所以直接删掉
            while (front <= rear && a[i] <= a[q[rear]])
                rear--;  // 队尾出队
            // 把当前下标加入队尾
            q[++rear] = i;
            // 然后：删掉窗口外的过期元素
            // 如果队头下标已经滑出窗口左边界，就删掉
            while (q[front] <= i - k)
                front++;
            // 最后：当窗口完全形成（i 到达 k-1）时，开始输出
            // 队列头就是当前窗口的最小值
            if (i >= k - 1)
                out.print(a[q[front]] + " "); // 用 PrintWriter 进行缓冲输出
        }
        out.println(); // 换行
    }
    // 函数：求滑动窗口最大值（单调递减队列）
    public static void slidingWindowMax(int n, int k, PrintWriter out) {
        front = 0;
        rear = -1;  // 重置队列
        for (int i = 0; i < n; i++) {
            // 首先：维护单调递减
            // 队列不为空且当前数 >= 队尾数 → 队尾那个数没用了，删掉
            while (front <= rear && a[i] >= a[q[rear]])
                rear--;
            // 当前下标入队
            q[++rear] = i;
            // 然后：删掉窗口外过期元素
            while (q[front] <= i - k)
                front++;
            // 最后：输出队头（最大值）
            if (i >= k - 1)
                out.print(a[q[front]] + " ");
        }
        out.println(); // 换行
    }
    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        st.nextToken();
        int n = (int) st.nval;
        st.nextToken();
        int k = (int) st.nval;
        for (int i = 0; i < n; i++) {
            st.nextToken();
            a[i] = (int) st.nval;
        }
        slidingWindowMin(n, k, out);
        slidingWindowMax(n, k, out);
        out.flush();
    }
}