//引用6-12全自动仓储系统的出库质检
//题目描述
//某大型电商物流中心引进了一套全自动单通道仓储系统。该系统的货架结构非常特殊，只有一个进/出口，且内部呈纵深排列（类似于一个深井）。
//货物按照订单生成的先后顺序，被赋予编号 1, 2, 3, ..., N，并依次尝试进入货架。当货物进入货架后，由于通道狭窄，后进入的货物会挡在先前的货物前面。
//在进行出库质检时，机械臂只能从货架的最外侧（即最后进入的位置）抓取货物。如果机械臂需要的货物被压在内部，它必须先将外面的货物全部取出，才能拿到里面的货物。
//需要注意的是，该货架的最大纵深容量为 M。也就是说，同一时间内，货架中堆放的货物数量不能超过 M 件。
//现在，物流中心收到了一份特殊的出库质检清单。请你编写程序，判断在满足货架容量限制的前提下，这套系统是否能够严格按照清单上的顺序完成所有货物的出库质检。
//输入格式
//输入第一行给出 3 个不超过 1000 的正整数：M（货架容量）、N（货物总数，编号从 1 到 N）、K（待检查的出库质检清单数量）。
//随后 K 行，每行给出 N 个数字的出库顺序。同行数字以空格间隔。
//输出格式
//对每一行出库顺序，如果系统能够按照该顺序完成出库，就在一行中输出 YES，否则输出 NO。
//输入样例
//    5 7 5
//    1 2 3 4 5 6 7
//    3 2 1 7 5 6 4
//    7 6 5 4 3 2 1
//    5 6 4 3 7 2 1
//    1 7 6 5 4 3 2
//输出样例
//   YES
//   NO
//   NO
//   YES
//   NO
//这个题他是啥意思呢，就是这句话：
//如果机械臂需要的货物被压在内部，它必须先将外面的货物全部取出，才能拿到里面的货物。
//这就符合栈的特性，就是后进先出，先进的压在下面，在取的时候只能取后进的，考虑我的M（货物总容量），
//其实就是栈的最大容量，其实就是检验栈的合法性，所以代码如下：
import java.io.*;
import java.util.*;
public class Test01 {//提交的时候记得改为 class Main
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
            int[] a = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }
            Stack<Integer> stack = new Stack<>();
            int index = 0;
            boolean flag = true;
            for (int i = 1; i <= n; i++) {
                stack.push(i);
                // 如果栈的大小超过了最大容量 M，标记为失败，直接终止当前检查
                if (stack.size() > m) {
                    flag = false;
                    break;
                }
                // 如果栈顶元素等于当前要出库的元素，就不断弹出
                while (!stack.isEmpty() && stack.peek() == a[index]) {
                    stack.pop();
                    index++;
                }
            }
            out.println(flag && stack.isEmpty() ? "YES" : "NO");
        }
        out.flush();
        br.close();
        out.close();
    }
}