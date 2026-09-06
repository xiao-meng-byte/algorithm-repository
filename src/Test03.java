//引用：洛谷B3620 x 进制转 10 进制
//题目链接：https://www.luogu.com.cn/problem/B3620
//题目描述
//给一个小整数 x 和一个 x 进制的数 S。将 S 转为 10 进制数。对于超过十进制的数码，用 A，B，... 表示。
//输入格式
//第一行一个整数 x;
//第二行一个字符串 S。
//输出格式
//输出仅包含一个整数，表示答案。
//输入输出样例 #1
//输入 #1
// 16
// 7B
//输出 #1
// 123
//说明/提示
//【数据规模和约定】
//保证目标数在十进制下不超过 10^9，2 <= x <= 36。
//前面提到了我要把十进制转为一个n进制的数字，就是把这个数字num一直除以n取余数知道余数为0为止，然后倒这输出就可，在这里要把其它进制转为十进制，那我们就可以倒过来运算，就是说我们要把一个n进制的数转为10进制，那就用当前位乘n累加，正着算进行了。
//比如说我把二进制转为十进制:
//将二进制数1010转为十进制的步骤如下：
//第一位 1
//结果 = 0 × 2 + 1 = 1
//第二位 0
//结果 = 1 × 2 + 0 = 2
//第三位 1
//结果 = 2 × 2 + 1 = 5
//第四位 0
//结果 = 5 × 2 + 0 = 10
//最终得出的答案就是10
//那么可以用栈写吗，答案是肯定的，就是说利用栈先进后出的特性，先把 X 进制数每一位字符依次入栈，原本从左到右的高位到低位序列，入栈后变为栈底高位、栈顶低位；后续再逐个出栈，配合不断累乘进制基数的位权，从最低位向高位按权展开求和，就能实现 X 进制向十进制的换算。
//所以代码如下：
//提交的时候记得改为 class Main
import java.io.*;
import java.util.*;
public class Test03{//提交的时候记得改为 class Main
    public static void main(String[] args)throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out=new PrintWriter(System.out);
        int x=Integer.parseInt(br.readLine().trim());
        String s=br.readLine().trim();
        Stack<Integer> st=new Stack<>();
        for(char ch:s.toCharArray()){
            int num=0;
            if(ch>='0'&&ch<='9'){//如果读到的是数字字符就直接转为数字
                num = ch - '0';
            }else{
                num = ch - 'A' + 10;//如果是字母，那就计算除字母ASCII值离'A'的距离值，再加10，因为是要转为10进制数字
            }
            st.push(num);//把数字压入栈中
        }
        long ans = 0;
        long pow = 1; //初始化为1，就是我从低位到高位要乘位数的x次方
        while(!st.isEmpty()){
            ans += st.pop() * pow;//对算出来的每一位进行加和
            pow *= x;//每升一位x就要自乘一次
        }
        out.println(ans);
        out.flush();
        out.close();
    }
}
//import java.io.*;
//import java.util.*;
//public class Test03 {
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter out = new PrintWriter(System.out);
//        int x = Integer.parseInt(br.readLine().trim());
//        String s = br.readLine().trim();
//        long ans = 0;
//        for (int i = 0; i < s.length(); i++) {
//            char ch = s.charAt(i);
//            int num;
//            if (ch >= '0' && ch <= '9') {
//                num = ch - '0';
//            } else {
//                num = ch - 'A' + 10;
//            }
//            // 从左到右：当前结果乘进制，再加新数
//            ans = ans * x + num;
//        }
//        out.println(ans);
//        out.flush();
//        br.close();
//        out.close();
//    }
//}
