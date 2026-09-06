//栈和队列相互转化
//实现队列其实就是普通队列和双端队列queue,那这样我们就需要两个栈来模拟队列进出，实现栈其实可以一个队列就可以了，大概如下:
//普通队列（FIFO）
//用 两个栈 模拟 → 只能队尾进、队头出。
//双端队列（Deque）
//两端都能进、两端都能出 → 本质就是 2 个栈背对背。
//栈（LIFO）
//用 一个队列 / 双端队列 就能模拟。
//引用：牛客noob96 用两个栈实现队列
//题目链接：https://www.nowcoder.com/practice/54275ddae22f475981afa2244dd448c6?tpId=383&tqId=23281&channelPut=tracker1
//这个题我们就用一个栈来进元素，就是直接push，用一个栈来出元素，但我们的出队栈是空的，那我们就直接把入队栈的元素给他然后就输出就可，代码如下：
import java.util.*;
import java.util.Stack;
class Solution1 {//提交的时候记得改为Solution
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();
    public void push(int node) {
        // 直接压入栈1
        stack1.push(node);
    }
    public int pop() {
        // 如果栈2为空，则将栈1的所有元素倒入栈2（反转顺序）
        if (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }
        // 此时栈2的栈顶就是最早加入的元素，直接弹出即可
        return stack2.pop();
    }
}