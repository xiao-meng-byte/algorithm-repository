//题目链接：https://leetcode.cn/problems/design-bitset/
class BitSet {
    private int[] bits;
    private final int size;
    private int zeros; // 记录当前逻辑上 0 状态的个数
    private int ones;  // 记录当前逻辑上 1 状态的个数
    private boolean reverse; // 记录现在的规则有没有被翻转
    public BitSet(int n) {
        bits = new int[(n + 31) / 32]; // 向上取整，算出需要几个 int 来存
        size = n; // 初始总长度就是 n
        // 一开始按正常规则来，啥都没加，所以全是 0
        zeros = n;
        ones = 0;
        reverse = false; // 初始规则：1 代表存在，0 代表不存在
    }
    public void fix(int idx) {
        int index = idx / 32; // 算出在数组的哪个位置
        int bit = idx % 32;   // 算出在那个 int 里的第几位
        if (!reverse) { // 如果是正常规则（1=存在，0=不存在）
            // 题目要求：只有当该位不是 1 的时候，才去改它
            if ((bits[index] & (1 << bit)) == 0) {
                // 既然要把它变成 1，那 0 的个数就少一个，1 的个数多一个
                zeros--;
                ones++;
                bits[index] |= (1 << bit); // 用或运算把它强行变成 1
            }
            //翻转的模式下，逻辑 1 等价物理 0；若当前物理位为 1，代表逻辑是 0，需要翻转成物理 0，才能达到逻辑 1
        } else { // 如果规则反了（0=存在，1=不存在）
            // 所以我们要检查物理上是不是 1，如果是 1 就得把它变为0
            if ((bits[index] & (1 << bit)) != 0) {
                zeros--;
                ones++;
                bits[index] ^= (1 << bit); // 和上面的一样用异或把 1 变成 0
            }
        }
    }
    public void unfix(int idx) {
        int index = idx / 32;
        int bit = idx % 32;
        if (!reverse) { // 正常规则下，想让某位变成 0
            // 只有当它现在是 1 的时候，才去改
            if ((bits[index] & (1 << bit)) != 0) {
                ones--;
                zeros++;
                bits[index] ^= (1 << bit); // 用异或把 1 变成 0
            }
            //翻转模式下，逻辑 0 等价物理 1；当前物理位为 0 代表逻辑 1，需要改成物理 1，才能达到逻辑 0。
        } else { // 规则反了，如果要让逻辑上是 0（也就是物理上得是 1）
            // 检查物理上是不是 0，如果是 0 就得把它变成 1
            if ((bits[index] & (1 << bit)) == 0) {
                ones--;
                zeros++;
                bits[index] |= (1 << bit); // 用或运算把它强行变成 1
            }
        }
    }
    public void flip() {
        // 懒标记：我不去遍历数组，我只要把规则反过来就nice了
        reverse = !reverse;
        // 也要把 0 和 1 的个数也互换一下，保证计数器是对的
        int temp = zeros;
        zeros = ones;
        ones = temp;
    }
    public boolean all() {
        // 只要 1 的个数等于总长度，说明全都是 1
        return ones == size;
    }
    public boolean one() {
        // 只要有至少一个 1，就返回 true
        return ones > 0;
    }
    public int count() {
        // 直接返回我们维护好的计数器，O(1) 搞定
        return ones;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 外层循环遍历数组，内层循环遍历每个 int 的 32 个位
        for (int i = 0, k = 0, number, status; i < size; k++) {
            number = bits[k];
            for (int j = 0; j < 32 && i < size; j++, i++) {
                // 把每一位抠出来
                status = (number >> j) & 1;
                // 如果规则反了，就异或 1 把它翻过来；没反就保持原样
                status ^= reverse ? 1 : 0;
                sb.append(status);
            }
        }
        return sb.toString();
    }
}