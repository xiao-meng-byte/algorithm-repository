import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        out.println("请输入整数 a：");
        out.flush();
        int a = Integer.parseInt(br.readLine());
        out.println("请输入整数 b：");
        out.flush();
        int b = Integer.parseInt(br.readLine());
        out.println("========================");
        out.flush();
        out.println("1. && 逻辑与 (短路)");
        boolean res1 = (b != 0) && (a / b > 1);//就这个表达式而言，如果b为0，则左边为false，它就不会看右边了
        // 如果b不为0，则右边才会执行，这时候看a/b是否大于1，如果大于1，则结果为true，否则为false，结果为false
        out.println("(b!=0) && (a/b>1) = " + res1);
        out.println("左边false → 右边不执行");
        out.println("========================");
        out.flush();
        out.println("2. & 按位与 (不短路)");
        try {
            boolean res2 = (b != 0) & (a / b > 1);//而这个表达式只有两边都满足才为true，只要有一边不为true，结果就会为false
            out.println("结果：" + res2);
        } catch (Exception e) {
            out.println("报错！因为 & 一定会执行右边，触发除0异常");
        }
        out.println("========================");
        out.flush();
        out.println("3. || 逻辑或 (短路)");
        boolean res3 = (a > 5) || (a / b > 1);//就这个表达式而言，如果a真的大于5，则右边不会执行，结果为true
        // 如果a小于5，则右边才会执行，这时候看a/b是否大于1，如果大于1，则结果为true，否则为false，结果为false
        out.println("(a>5) || (a/b>1) = " + res3);
        out.println("左边true → 右边不执行");
        out.println("========================");
        out.flush();
        out.println("4. | 按位或 (不短路)");
        try {
            boolean res4 = (a > 5) | (a / b > 1);//就这个表达式而言，只要有一边为true，结果就会为true，两边都为false，结果为false
            out.println("结果：" + res4);
        } catch (Exception e) {
            out.println("报错！因为 | 一定会执行右边，触发除0异常");
        }
        br.close();
        out.close();
    }
}
//不过这个逻辑运算符其实和位运算在表面上没有啥关系，但是它们的底层都是二进制来看的，比如说其实和上面的逻辑是相同的，计算机底层的false就是0，true就是1，对于发现了&&前一个条件是0，那后一个条件就不会进系统去判断了，只有左边是 1，才去算右边看是0还是1，而||：左边 为1直接返回 1，右边不执行的，而逻辑运算符的&和|则是不管左边是 0 还是 1，右边一定会全部执行完毕，然后按照纯位运算的规则去计算：
//        & 遵循 “按位与” 规则：两位都是 1，结果才是 1，只要有一个 0，结果就是 0；
//        | 遵循 “按位或” 规则：只要有一个 1，结果就是 1，两位都是 0，结果才是 0。
//所以说它和位运算其实有很大的关系，逻辑符|和&计算机底层的true和false其实还是按照位运算得来的。