//引用: 牛客noob85 好串
//题目链接：https://www.nowcoder.com/practice/9b072237ebdd4dd99562f01cbf594fac?tpId=383&tqId=306186&channelPut=tracker1
//这个题其实要以题目为切入点，他说要用一个空串，通过加若干个"ab"来得到，
//那么我们就可以反着看，就是说我有一个字符串，不断找到一对ab，进行删去，看能不能删到最后把字符串删空，如果能，那他就是一个好串，如果不能，那就不是呗~~~
//所以可以用栈来实现，代码如下：
import java.io.*;
import java.util.*;
public class Test05 {//提交的时候记得改为 class Main
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String s = br.readLine();
        Stack<Character> st = new Stack<>(); // 利用系统的容器栈
        for (char c : s.toCharArray()) {
            if (c == 'a') { // 我们遇到了a，那我压入栈
                st.push(c);
            } else if (c == 'b') { // 如果遇到了b，就先判断
                if (!st.empty() && st.peek() == 'a') { // 栈不为空而且栈里原有a
                    st.pop(); // 那就删除，相当于删了一个"ab"
                } else {
                    out.println("Bad"); // 如果是空的那肯定就不是好串
                    out.flush();
                    return;
                }
            }
        }
        // 最后删到空了，那就是好串
        out.println(st.empty() ? "Good" : "Bad");
        out.flush();
        br.close();
        out.close();
    }
}