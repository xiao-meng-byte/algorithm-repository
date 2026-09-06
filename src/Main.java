import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        // 初始化高吞吐量的输入输出流
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        int n = (int) in.nval;
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            a[i] = (int) in.nval;
        }
        // 构建前缀和数组 b
        int[] b = new int[n + 1];
        b[0] = 0;
        for (int i = 1; i <= n; i++) {
            b[i] = b[i - 1] + a[i];
        }
        // 贪心算法求最大子数组和
        int Max = b[1];
        int Min = b[0];
        for (int i = 1; i <= n; i++) {
            Max = Math.max(Max, b[i] - Min);
            Min = Math.min(Min, b[i]);
        }
        out.println(Max);
        out.flush(); // 确保输出
        out.close();
    }
}