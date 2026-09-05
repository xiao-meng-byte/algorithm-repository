//洛谷P1305 新二叉树,题目链接：https://www.luogu.com.cn/problem/P1305#ide
import java.io.*;
import java.util.*;
//和上面一样，手写一个二叉树
class Node1 {
    char value;
    Node1 left;
    Node1 right;
    public Node1(char c) {
        value = c;
        left = null;
        right = null;
    }
    //和题目意思：前序遍历的递归写法（题目的数据量比较小）
    public void preOrder(PrintWriter out) {
        out.print(value);
        if(left != null) left.preOrder(out);
        if(right != null) right.preOrder(out);
    }
}
public class Test01 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        Map<Character, Node1> map = new HashMap<>();
        int n = Integer.parseInt(br.readLine());
        Node1 root = null;
        for(int i = 0; i < n; i++) {
            String s = br.readLine();
            char father = s.charAt(0);
            char l = s.charAt(1);
            char r = s.charAt(2);
            if(!map.containsKey(father)) {
                map.put(father, new Node1(father));
            }
            Node1 fa = map.get(father);
            if(i == 0) root = fa;
            if(l != '*') {
                map.put(l, new Node1(l));
                fa.left = map.get(l);
            }
            if(r != '*') {
                map.put(r, new Node1(r));
                fa.right = map.get(r);
            }
        }
        root.preOrder(out);
        out.flush();
        br.close();
        out.close();
    }
}