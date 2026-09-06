//6-5栈实现十进制转任意进制
//题目描述
//输入一行两个非负数 m、n：
//m：需要转换的十进制数字
//n：目标进制（2 ≤ n ≤ 16）
//输入格式
//一行输入两个整数，用空格隔开
//输出格式
//输出转换后的 n 进制结果
//输入样例
//10 2
//输出样例
//1010
//输入样例
//255 16
//输出样例
//FF
//其实还是要明白栈能够实现进制转换的原理在哪里,
// 就是说我们对初始的十进制数字除以一个n，把余数纪录下来，如果商不为0，那就接着除n,知道商为0，
// 这时把余数按出现的逆序排例起来（先出现的余数在后面，后出现的就在前面），就得到了相应的n进制数字，举个例子，以进制转二进制为例：
//输入 35 2，运算过程如下：
//        35 ÷ 2 = 17 ···· 余 1
//        17 ÷ 2 = 8 ······余 1
//        8 ÷ 2 = 4 ······ 余 0
//        4 ÷ 2 = 2 ······ 余 0
//        2 ÷ 2 = 1 ······ 余 0
//        1 ÷ 2 = 0 ······ 余 1
//从下往上读余数：1, 0, 0, 0, 1, 1 → 二进制是 100011。
//所以说栈就最适合来实现它，代码如下：
import java.io.*;
import java.util.*;
class MyStack1{//手写一个栈类
    char[] arr;        // 底层用于存放字符数据的数组
    int top = -1;      // 栈顶指针，初始为-1表示栈是空的
    // 构造方法，初始化栈的容量
    public MyStack1(int len){
        arr = new char[len];
    }
    // 入栈方法
    public void push(char x) {
        // 如果栈满了（栈顶指针到达数组最后一位）
        if (top == arr.length - 1) {
            // 创建一个容量翻倍的新数组
            char[] newArr= new char[arr.length * 2];
            // 将旧数组的所有元素复制到新数组中
            for (int i = 0; i <= top; i++) {
                newArr[i] = arr[i];
            }
            // 将新数组赋给当前数组（数组扩容完成）
            arr = newArr;
        }
        // 栈顶指针向上移动一位，并将新元素填入该位置
        arr[++top] = x;
    }
    // 出栈方法：弹出栈顶元素
    char pop(){
        return arr[top--]; // 返回当前栈顶元素，并将指针下移一位
    }
    // 判断栈是否为空
    boolean empty(){
        return top==-1; // 如果指针回到-1，说明栈空了
    }
}
public class Test02{//提交的时候记得改为 class Main
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m=Integer.parseInt(st.nextToken()); // 读取要转换的十进制数
        int n=Integer.parseInt(st.nextToken()); // 读取目标进制
        // 进制字符映射表：用于将0-15的余数转换为对应的字符（如10变A，15变F）
        char[] table = "0123456789ABCDEF".toCharArray();
        // 创建一个初始容量为32的手写栈对象
        MyStack1 stack=new MyStack1(32);
        // 循环除法（短除法）：将 m 转换为 n 进制数
        while(m>0){
            int rem = m%n;         // 取余数，余数就是当前位的数值
            stack.push(table[rem]);// 将余数对应的字符压入栈中
            m /= n;                // 取商，准备计算下一位
        }
        // 栈非空时，不断弹出栈顶元素并输出（后进先出，正好得到高位到低位的顺序）
        while(!stack.empty()){
            out.print(stack.pop());
        }
        out.flush(); // 刷新输出流
        br.close();  // 关闭输入流
        out.close(); // 关闭输出流
    }
}