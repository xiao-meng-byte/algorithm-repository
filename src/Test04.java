//引用: 牛客noob84 括号配对问题
//题目链接：https://www.nowcoder.com/practice/57260c08eaa44feababd05b328b897d7?tpId=383&tqId=296645&channelPut=tracker1
//这个题其实和弹栈和压栈有关，就是可以这么想:
//就是说我遍历字符串，发现了左扩号，那我就把他加到我们的数组里去（也就是栈里去），
//如果是发现了右扩号，那我就去匹配，如果说这个时候栈是空的，那就不可能是合法的，如果不是空的，那就要把栈里的元素弹出来，看他是不是左括号，如果是就是true的，否则就是false的
// (总结起来就是：遇到左括号压栈。遇到右括号弹栈),所以代码如下：
import java.io.*;
import java.util.*;
public class Test04 {//提交的时候记得改为 class Main
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String s = br.readLine();
        Stack<Character> st = new Stack<>(); // 利用系统的容器栈
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') { // 遇到左括号
                st.push(c); // 就压进去
            } else if (c == ')' || c == ']' || c == '}') { // 遇到右括号
                if (st.empty()) { // 如果是空就一定不合法
                    out.println("false");
                    out.flush();
                    return;
                }
                char top = st.peek(); // 取栈顶元素
                st.pop();
                if ((c == ')' && top != '(') || (c == ']' && top != '[') || (c == '}' &&
                        top != '{')) { // 进行判断
                    out.println("false");
                    out.flush();
                    return;
                }
            }
        }
        // 如果说我成双成对的取后，它空了，那就合法，否则就不合法。
        out.println(st.empty() ? "true" : "false");
        out.flush();
        br.close();
        out.close();
    }
}