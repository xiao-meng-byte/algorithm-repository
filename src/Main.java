class Bitset {
    // 静态数组，用来存储位状态。int 为 32 位，所以每个 int 能存 32 个标志位
    static int[] bits;

    public Bitset(int n) {
        // 向上取整：(n + 31) / 32
        // 如果 n=33，则需要开两个 int 来存。除32取整，多出的余数部分另开一个
        bits = new int[(n + 31) / 32];
    }

    // 添加元素（将指定位置设为 1）
    public void add(int x) {
        int index = x / 32; // 计算该数字落在第几个 int 里
        int bit = x % 32;   // 计算该数字在 int 内部的第几位
        bits[index] |= (1 << bit); // 将对应位置置 1（按位或运算）
    }

    // 删除元素（将指定位置设为 0）
    public void remove(int x) {
        int index = x / 32;
        int bit = x % 32;
        // ~(1 << bit) 表示除了目标位是 0，其他全是 1
        // 按位与运算后，原目标位的 1 会被清零，其他位保持不变
        bits[index] &= ~(1 << bit);
    }

    // 判断元素是否存在（判断指定位置是否为 1）
    public boolean contains(int x) {
        int index = x / 32;
        int bit = x % 32;
        // 按位与运算，如果结果为 0 说明该位原本就是 0，如果非 0 说明该位是 1
        return (bits[index] & (1 << bit)) != 0;
    }

    // 翻转元素（0 变 1，1 变 0）
    public void reverse(int x) {
        int index = x / 32;
        int bit = x % 32;
        // 按位异或运算（相同为0，不同为1），只要异或 1 就会发生翻转
        bits[index] ^= (1 << bit);
    }

    // 统计当前位集中一共有多少个 1（即存入了多少个元素）
    public int size() {
        int count = 0;
        for (int i = 0; i < bits.length; i++) {
            // Integer.bitCount 是 Java 内置方法，专门用来快速统计一个 int 里有多少个 1
            count += Integer.bitCount(bits[i]);
        }
        return count;
    }
}