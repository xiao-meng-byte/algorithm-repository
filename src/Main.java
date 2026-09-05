//该二叉树的结构
//                       A
//                    /     \
//                   C      D
//                 /  \    /  \
//                B   E   F   G
//              / \  / \ / \  / \
//             H  I J  K L M  N  O
//要组成上面的一个搜索二叉树，首先我们要插入元素，对于搜索二叉树的插入规则是：
//1.如果树不为空，那就从根节点开始对比；
//2.而但待插入值 < 当前节点值的时候：
//进行以下判断:
//①当前节点左孩子为空 → 新节点作为左孩子插入；
//②左孩子不为空 → 递归调用左子节点的 insert 继续往下找空位；
//3.而当待插入值 ≥ 当前节点值（相等、更大都走右边）：
//进行以下判断:
//①当前节点右孩子为空 → 新节点作为右孩子插入；
//②右孩子不为空 → 递归调用右子节点的 insert 继续往下找空位；
//新插入的节点一定是叶子节点；
//重复数值不会舍弃，全部插入到对应节点的右子树一侧。
//而二叉树的遍历分为：先序，中序，后序遍历
//就是说：
//           1
//         /  \
//        2    3
//       / \  / \
//      4  5 6  7
//对于以上这棵树：
//先序遍历：1 2 4 5 3 6 7
//而中序遍历：4 2 5 1 6 3 7
//后序遍历：4 5 2 6 7 3 1
//层序遍历：1 2 4 5 3 6 7
//所以搜索二叉树的手写代码如下：
import java.io.*;
import java.util.*;
//实现普通二叉搜索树
class Node {
    static PrintWriter out = new PrintWriter(System.out);
    int value;//节点的值
    Node left;//左子节点
    Node right;//右子节点
    public Node(int value) {
        this.value = value;
    }
    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
    public Node() {
        this.value = 0;
        this.left = null;
        this.right = null;
    }
    public void insert(int value) {//一个插入的操作
        if (value < this.value) {//插入的节点小于当前节点
            if (this.left == null) {//当前节点的左子节点为空，就是没有元素在占位置
                this.left = new Node(value);//创建一个节点，并插入
            } else {
                this.left.insert(value);//否则递归调用它，继续插入元素
            }
        } else {//插入的节点大于当前节点
            if (this.right == null) {//那就去看当前节点的右子节点是不是为空
                this.right = new Node(value);//创建一个节点，并插入元素
            } else {
                this.right.insert(value);//否则递归调用它，继续插入元素
            }
        }
    }
    //以下的遍历方式都是基于递归实现的
    //前序遍历,顾名思义，就是先访问根节点，再访问左子树，最后访问右子树
    //中序遍历，顾名思义，就是先访问左子树，再访问根节点，最后访问右子树
    //后序遍历，顾名思义，就是先访问左子树，再访问右子树，最后访问根节点
    //层序遍历，顾名思义，就是先访问根节点，再访问根节点的左子树，最后访问根节点的右子树
    //前序遍历(递归的形式)
    public void preOrder() {
        out.print(this.value + " ");
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
    //中序遍历(递归的形式)
    public void inOrder() {
        if (this.left != null) {
            this.left.inOrder();
        }
        out.print(this.value + " ");
        if (this.right != null) {
            this.right.inOrder();
        }
    }
    //后序遍历(递归的形式)
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        out.print(this.value + " ");
    }
    //层序遍历（就是按从左到右的顺序同时进行层级自上而下的顺序进行遍历）
    public void levelOrder() {
        Queue<Node> queue = new LinkedList<>();//开出一个队列，借助并借助它来实现BFS广度优先遍历
        //当现在的节点入队列的时候
        queue.offer(this);
        //只要队列不为空就持续遍历
        while (!queue.isEmpty()) {
            //先取出队首节点
            Node cur = queue.poll();
            //并输出当前节点值
            out.print(cur.value + " ");
            // 左子节点存在，先将它入队
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            //而右子节点存在，再将它入队
            if (cur.right != null) {
                queue.offer(cur.right);
            }
        }
    }
    //以下遍历都是非递归模式的
    //非递归遍历我们要借用栈，不过逻辑和递归版很相似
    //非递归 前序
    public void preOrderByStack() {
        Stack<Node> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            out.print(cur.value + " ");
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }
    //非递归 中序
    public void inOrderByStack() {
        Stack<Node> stack = new Stack<>();
        Node cur = this;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            out.print(cur.value + " ");
            cur = cur.right;
        }
    }
    //非递归 后序
    public void postOrderByStack() {
        Stack<Node> stack = new Stack<>();
        Node cur = this;
        Node lastVisited = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.peek();
            if (cur.right == null || cur.right == lastVisited) {
                out.print(cur.value + " ");
                stack.pop();
                lastVisited = cur;
                cur = null;
            } else {
                cur = cur.right;
            }
        }
    }
    //递归求深度
    public int getDepth() {
        int l = left == null ? 0 : left.getDepth();
        int r = right == null ? 0 : right.getDepth();
        return Math.max(l, r) + 1;
    }
    //BFS求深度(非递归求深度)
    public int getDepthByLevel() {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(this);
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
        }
        return depth;
    }
}
public class Main {
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n=Integer.parseInt(br.readLine());
        st=new StringTokenizer(br.readLine().trim());
        Node root = new Node(Integer.parseInt(st.nextToken()));
        for(int i=1;i<n;i++){
            int num=Integer.parseInt(st.nextToken());
            root.insert(num);
        }
        Node.out.print("递归前序：");
        root.preOrder();
        Node.out.println();
        Node.out.print("栈非递归前序：");
        root.preOrderByStack();
        Node.out.println();
        Node.out.print("递归中序：");
        root.inOrder();
        Node.out.println();
        Node.out.print("栈非递归中序：");
        root.inOrderByStack();
        Node.out.println();
        Node.out.print("递归后序：");
        root.postOrder();
        Node.out.println();
        Node.out.print("栈非递归后序：");
        root.postOrderByStack();
        Node.out.println();
        Node.out.print("层序遍历：");
        root.levelOrder();
        Node.out.println();
        Node.out.println("递归深度：" + root.getDepth());
        Node.out.println("BFS深度：" + root.getDepthByLevel());
        Node.out.flush();
    }
}
//测试结果如下：
//输入：
//7
//5 3 7 2 4 6 8
//递归前序：5 3 2 4 7 6 8
//栈非递归前序：5 3 2 4 7 6 8
//递归中序：2 3 4 5 6 7 8
//栈非递归中序：2 3 4 5 6 7 8
//递归后序：2 4 3 6 8 7 5
//栈非递归后序：2 4 3 6 8 7 5
//层序遍历：5 3 7 2 4 6 8
//递归深度：3
//BFS深度：3