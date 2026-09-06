//引用：牛客noob88 牛牛与后缀表达式
//题目链接:https://www.nowcoder.com/practice/a1a4f178f6ff4188890e51da1cc8ce10?tpId=383&tqId=1087492&channelPut=tracker1
//很显然，我们发现它他的数字与数字间，数字与运算符间是有隔断的，就是用了一个#号来断开，
//那我们一样在用栈的时候，就是说如果我遇到了数字，那就可以把它拼接起来，
//比如我遇到了123这个数，那我要的是100乘1+2乘10+3，所以我们在遇到一个数字num的时候，那就要有num=num*10+(当前数字字符-'0')
//这样就可以把数字拼接好在后面进行运算，而一旦我们遇到了#号（也就是结束符）的时候我们要把先前拼出来的数字压入栈，把num变为0，重新吸取字符拼接数字
//后面如果遇到运算符的时候，就再弹出两个数字，运算得到结果再压入栈就行了，所以代码如下：
import java.util.*;
class Solution {
    long[] Mystack = new long[1000010];
    int size = 0;
    public long legalExp(String str) {
        long num = 0;
        for (char c : str.toCharArray()) {
            // 数字拼接
            if (c >= '0' && c <= '9') {
                num = num * 10 + (c - '0');
            }
            // # 表示数字结束，入栈
            else if (c == '#') {
                Mystack[size++] = num;
                num = 0;
            }
            // 进行运算的时候再弹出两个数字进行运算
            // 加法
            else if (c == '+') {
                long b = Mystack[--size];
                long a = Mystack[--size];
                Mystack[size++] = a + b; // 再把运算出的结果压入栈中
            }
            // 减法（顺序绝对不能错）
            else if (c == '-') {
                long b = Mystack[--size];
                long a = Mystack[--size];
                Mystack[size++] = a - b;
            }
            // 乘法
            else if (c == '*') {
                long b = Mystack[--size];
                long a = Mystack[--size];
                Mystack[size++] = a * b;
            }
        }
        return Mystack[size - 1]; // 最后就弹出我们的最终结果
    }
}