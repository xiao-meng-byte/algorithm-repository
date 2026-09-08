//直接调用库函数里的栈stack
import java.util.Stack;
class MinStack {
    private Stack<Integer> data;
    private Stack<Integer> min;
    public MinStack() {
        data = new Stack<>();
        min = new Stack<>();
    }
    public void push(int val) {
        data.push(val);
        // 最小栈为空，或者当前值小于等于最小栈栈顶，直接加入
        // 否则复制栈顶元素保持高度一致
        if (min.isEmpty() || val < min.peek()) {
            min.push(val);
        } else {
            min.push(min.peek());
        }
    }
    public void pop() {
        data.pop();
        min.pop();
    }
    public int top() {
        return data.peek();
    }
    public int getMin() {
        return min.peek();
    }
}