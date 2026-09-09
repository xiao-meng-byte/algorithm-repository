import java.io.*;
import java.util.*;
public class MonoStack {
    static final int N = 1000001;//根据题目来调大小避免RE和MLE
    static int[] arr = new int[N];      // 存储原始数组
    static int[] Mystack = new int[N];  // 单调栈：存储数组下标
    static int[][] ans = new int[N][2];
    static int r, n;                    // r：栈顶指针；n：数组长度
    static void compute() {
        r = 0;  // 栈初始化，栈为空
        int cur;
        // 从左到右遍历每个元素
        for (int i = 0; i < n; i++) {
            // 栈不为空 且 栈顶元素 < 当前元素
            // 说明当前元素 i 就是栈顶元素的「右侧第一个更大数」
            while (r > 0 && arr[Mystack[r - 1]] >= arr[i]) {
                cur = Mystack[--r];  // 弹出栈顶下标
                ans[cur][0] = r > 0 ? Mystack[r - 1] : -1;
                ans[cur][1] = i; // 记录答案：右侧更大数下标是 i
            }
            Mystack[r++] = i;  // 当前下标入栈
        }
        // 遍历结束后，栈中剩下的元素：没有更大的数
        while (r > 0) {
            cur = Mystack[--r];  // 弹出
            ans[cur][0] = r > 0 ? Mystack[r - 1] : -1;
            ans[cur][1] = -1; // 记录答案：右侧更大数下标是 i
        }
        for (int i = n - 2; i >= 0; i--) {
            //不是 -1 且右边的值等于本身
            if (ans[i][1] != -1 && arr[ans[i][1]] == arr[i]) {
                //右边的答案 该成 答案的右边
                ans[i][1] = ans[ans[i][1]][1];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(
                new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int)st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i] = (int) st.nval;
            }
            compute();
            for (int i = 0; i < n; i++) {
                out.println(ans[i][0] + " " + ans[i][1]);
            }
        }
        out.flush();
        out.close();
    }
}