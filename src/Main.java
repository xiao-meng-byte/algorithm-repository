// 大根堆二叉树的规则：
// ① 插入的时候，插入的元素应该放在堆的末尾，然后向上调整，使堆的规则满足。
// 就是从左到右顺序，从上到下顺序，把大的值放在堆的顶部。
// 插入[1, 2, 5, 3, 4]
//【① 插入1的时候】
//        1
//【② 插入2的时候】
//       1            ==>          2
//      / \                       / \
//     2                         1
//(发现 2 比 1 大，所以交换位置)
//【③ 继续插入5】
//    2                         5
//   / \        ==>            / \
//  1   5                     1   2
//(发现 5 比 2 大，所以交换位置)
//【④ 继续插入3】
//        5                          5
//       / \                        / \
//      1   2         ==>          3   2
//     /                          /
//    3                          1
//(发现 3 比 1 大，所以交换位置)
//【⑤ 继续插入4】
//        5                          5
//       / \                        / \
//      3   2         ==>          4   2
//     / \                        / \
//    1   4                      1   3
//(发现 4 比 3 大，所以交换位置，此时大根堆构建完成)
//============================================================
// ② 删除的时候，删除的是堆顶元素，然后向下调整，使堆的规则满足。
// 对插入的内容 [5, 4, 2, 3, 1] 进行取出并删除。
//【① 先取5，交换堆顶和末尾】
//       3                         3
//      / \                       / \
//    4   2        ==>          4   2
//   / \                       /
//  1   5                     1
//(取出 5 后)
//【② 发现 4 比 3 大，向下交换位置】
//      3                          4
//     / \                        / \
//    4   2         ==>          3   2
//   /                          /
//  1                          1
//【③ 取4，交换堆顶和末尾】
//      1                          1
//     / \                        / \
//    3   2         ==>          3   2
//   /                          /
//  4                          4
//(删去 4 后)
//【④ 发现 3 比 1 大，向下交换位置】
//      3                          3
//     / \                        / \
//    1   2         ==>          1   2
//【⑤ 取3，交换堆顶和末尾】
//      2                          2
//     / \                        /
//    1   3                      1
//(删去 3 后)
//【⑥ 再交换再取出 2，最后取出 1】
//1
import java.util.*;
import java.io.*;
class PriorityQueue {
    static PrintWriter out = new PrintWriter(System.out);
    // 定义一个堆数组
    private int[] heap;
    // 堆的容量
    private int size;
    public PriorityQueue(int capacity) {
        // 初始化堆数字的容量
        heap = new int[capacity];
        // 堆的有效长度初始化为0
        size = 0;
    }
    // 插入 → 向上调整
    public void swap(int[] heap, int index, int parent) { // 交换元素,用异或来实现
        heap[index] = heap[index] ^ heap[parent];
        heap[parent] = heap[index] ^ heap[parent];
        heap[index] = heap[index] ^ heap[parent];
    }
    public void insert(int value) {
        // 判断堆数组是否已满
        if (size >= heap.length) {
            return;
        }
        // 将新元素放在堆数组末尾
        heap[size] = value;
        // 向上调整,长度加1
        size++;
        // 定义索引等于长度-1，父节点索引为(index-1)/2
        int index = size - 1;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[parent] < heap[index]) { // 小根堆的话，判断条件变为>
                swap(heap, index, parent);
                index = parent;
            } else {
                // 不交换，跳出循环
                break;
            }
        }
    }

    public int poll() {
        if (isEmpty()) { // 判断是否为空
            throw new RuntimeException("队列为空!");
        }
        int result = heap[0]; // 堆顶是最大值
        heap[0] = heap[size - 1]; // 最后一个元素放到堆顶
        size--; // 长度减1
        int index = 0; // 记录父节点，就是从顶节点开始
        while (true) {
            // 左子节点索引
            int left = index * 2 + 1;
            // 右子节点索引
            int right = index * 2 + 2;
            int maxIdx = index;
            // 左子节点存在且左子节点值更大，更新最大下标
            if (left < size && heap[left] > heap[maxIdx]) // 小根堆的话，判断条件变为<
                maxIdx = left;
            // 右子节点存在且右子节点值更大，更新最大下标
            if (right < size && heap[right] > heap[maxIdx]) // 小根堆的话，判断条件变为<
                maxIdx = right;
            // 最大值还是自己，无需调整，退出循环
            if (maxIdx == index) break;
            // 和最大的孩子交换
            swap(heap, index, maxIdx);
            // 下标移动到交换后的位置，继续向下调整
            index = maxIdx;
        }
        return result;
    }
    public boolean isEmpty() { // 判断是否为空
        return size == 0;
    }
    public int peek() { // 返回堆顶元素,但不删除
        return heap[0];
    }
    public int size() { // 返回堆中元素个数
        return size;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            out.print(heap[i] + " ");
        }
        out.println();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine().trim());
        PriorityQueue pq = new PriorityQueue(n);
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt(st.nextToken());
            pq.insert(x);
        }
        while (!pq.isEmpty()) {
            out.print(pq.poll() + " ");
        }
        out.println();
        out.flush();
        br.close();
        out.close();
    }
}