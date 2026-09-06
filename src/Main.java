import java.io.*;
import java.util.*;
class MyStack {
    // 定义一个栈类
    int top;       // 表示栈顶指针（实际是当前栈顶元素的下标）
    int[] stack;   // 底层用来存放数据的整型数组
    // 构造方法：初始化栈
    public MyStack(int size) {
        stack = new int[size]; // 根据传入的 size 创建一个数组来作为栈的底层存储空间
        top = -1;              // 栈顶初始化为 -1，表示栈是空的（因为数组下标从0开始，-1代表还没有元素）
    }
    // 入栈方法
    public void push(int x) {
        // 判断当前栈是否已满：栈顶指针到达了数组的最后一个位置
        if (top == stack.length - 1) {
            // 如果已满，进行“动态扩容”操作：
            int[] newStack = new int[stack.length * 2]; // 创建一个新数组，容量扩展为原来的两倍
            for (int i = 0; i <= top; i++) {
                newStack[i] = stack[i]; // 使用 for 循环将旧数组中的所有元素原封不动地复制到新数组中
            }
            stack = newStack; // 将引用指向新数组，旧的数组会被 Java 垃圾回收机制自动回收
        }
        // 扩容完成后（或原本就没满），将栈顶指针先向上移动一位（++top），再把新元素存入该位置
        stack[++top] = x;
    }
    // 出栈方法：弹出栈顶元素
    public int pop() {
        // 返回当前栈顶元素，并将栈顶指针向下移动一位（top--），相当于把该元素“弹出”
        return stack[top--];
    }
    // 获取栈顶元素（只读取，不弹出）
    public int peek() {
        return stack[top]; // 直接返回栈顶指针指向的元素，不动 top 指针
    }
    // 判断栈是否为空
    public boolean empty() {
        return top == -1; // 如果栈顶指针回到了 -1，说明栈里没有元素了
    }
    // 获取栈中元素个数
    public int size() {
        return top + 1; // 因为 top 是下标（从0开始），所以元素个数就是下标加 1
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); // 获取指令的条数 n
        MyStack stack = new MyStack(n);
        // 循环处理 n 条指令
        for (int i = 0; i < n; i++) {
            // 读取每一行的指令
            st = new StringTokenizer(br.readLine());
            String op = st.nextToken(); // 读取指令的操作名（如 "push" 或 "pop"）
            if (op.equals("push")) { // 如果是入栈操作
                int x = Integer.parseInt(st.nextToken()); // 读取后面跟着的数字
                stack.push(x); // 执行入栈
            } else if (op.equals("pop")) { // 如果是出栈操作
                if (stack.empty()) { // 先检查栈是否为空
                    out.println("Error"); // 如果为空，输出错误信息
                } else {
                    out.println(stack.pop()); // 如果不为空，弹出栈顶元素并输出
                }
            }
        }
        out.flush(); // 刷新输出缓冲区，确保所有内容都打印出来
        br.close();  // 关闭输入流
        out.close(); // 关闭输出流
    }
}