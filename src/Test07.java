//6-6中缀表达式计算器
//题目描述
//你需要为一个简易的图形计算器编写核心逻辑。该计算器支持加法（+），减法(-)，乘法（*）和除法(/)和括号（()）。
//请编写程序计算给定表达式的值。表达式保证合法，只包含非负整数、运算符和括号。
//输入格式
//一行字符串，表示数学表达式，长度不超过 1000。
//输出格式
//一个整数，表示计算结果。
//输入样例
//在这里给出一组输入。例如：
// (2+3)*4/2-5
//输出样例
//在这里给出相应的输出。例如：
//5
//这个题其实直接弹栈压栈就特别麻烦，括号后面又有其他字符，不同运算符插在数字之间，所以在弹栈压栈的时候步骤多，繁琐，那我们想，能不能把数字和运算法分别归类，类似于上面的后缀表达式，就是用两个栈分别存数字和运算符，要运算了再弹出运算，把运算的结果压入数字栈中，不过学过四则运算的都知道，加减乘除是有先后顺序的，就是优先级，先乘除，后加减，所以我们在运算的时候要优先乘除，然后再加减，总结下来就是：
//1.数字栈：存所有数字
//2.运算符栈：存 + - * / (
//3.遇到右括号就一直算到左括号
//4.运算符按优先级自动决定谁先算
//5.最后栈里剩下的就是答案
//那么代码如下：
import java.io.*;
public class Test07 {//提交的时候记得改为 class Main
    // 手写数组栈（存所有数字）
    static int[] nums = new int[100010];
    static int numsTop = -1;
    // 手写数组栈（存 + - * / ( )
    static char[] ops = new char[100010];
    static int opsTop = -1;
    // 获取运算符优先级
    public static int priority(char c) {
        if (c == '*' || c == '/') return 2;
        if (c == '+' || c == '-') return 1;
        return 0; // 左括号 (，表示括号的优先级最低
    }
    // 计算两个数 a op b
    public static int calculate(int a, int b, char op) {
        if (op == '+') return a + b;
        if (op == '-') return a - b;
        if (op == '*') return a * b;
        if (op == '/') return a / b;
        return 0;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String exp = br.readLine(); // 表达式字符串
        int len = exp.length();
        for (int i = 0; i < len; i++) { // 这里是转后缀，其实也可以转前缀，倒过来遍历就行了
            char c = exp.charAt(i);
            // 处理数字
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < len && Character.isDigit(exp.charAt(i))) {
                    num = num * 10 + (exp.charAt(i) - '0'); // 和前面一样，进行拼接数字
                    i++;
                }
                nums[++numsTop] = num; // 当没有再遇到数字的时候，就是说可能遇到运算符了，那就停下拼接的操作，把数字压入数字栈
                i--; // 抵消for循环的i++，因为这个时候它已经断了，就是前一段数字拼完了，可能遇到运算符了，就不要在移动下标了
            }
            // 左括号直接入栈
            else if (c == '(') {
                ops[++opsTop] = c;
            }
            // 右括号：一直算到左括号
            else if (c == ')') {
                while (ops[opsTop] != '(') {
                    // 弹出两个数字进行运算
                    int b = nums[numsTop--];
                    int a = nums[numsTop--];
                    char op = ops[opsTop--];
                    nums[++numsTop] = calculate(a, b, op); // 再把运算结果压入栈
                }
                opsTop--; // 弹出左括号
            }
            // 处理运算符 + - * /
            else {
                // 只要栈顶运算符的优先级 >= 当前运算符，就先算栈顶的
                while (opsTop != -1 && priority(ops[opsTop]) >= priority(c)) {
                    int b = nums[numsTop--];
                    int a = nums[numsTop--];
                    char op = ops[opsTop--];
                    nums[++numsTop] = calculate(a, b, op);
                }
                ops[++opsTop] = c;
            }
        }
        // 把剩下的所有运算做完
        while (opsTop != -1) {
            int b = nums[numsTop--];
            int a = nums[numsTop--];
            char op = ops[opsTop--];
            nums[++numsTop] = calculate(a, b, op);
        }
        out.println(nums[numsTop]);
        out.flush();
        br.close();
        out.close();
    }
}