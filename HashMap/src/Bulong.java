//双哈希映射布隆过滤器
import java.io.*;
import java.util.*;
public class Bulong {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    static StringTokenizer st;
    static class BloomFilter {
        long[] bits;
        int bitSize;
        // 两组不同底数、不同大模数的多项式滚动哈希
        final long BASE1 = 131, BASE2 = 13331;
        final long MOD1 = (long)1e9 + 7, MOD2 = (long)1e9 + 9;
        public BloomFilter(int size) {
            bitSize = size;
            bits = new long[(bitSize + 63) / 64];//这里和位图一样，这里在取空间时用到了向上取整。
        }
        //这里很上面的取字符串的哈希值逻辑一样，在最后取模，防止数据量过大
        private long hash1(String s) {
            long h = 0;
            for (int i = 0; i < s.length(); i++) {
                h = (h * BASE1 + s.charAt(i)) % MOD1;
            }
            return h % bitSize;
        }
        //计算两个情况下的哈希值，有利于互相映射，减小哈希冲突
        private long hash2(String s) {
            long h = 0;
            for (int i = 0; i < s.length(); i++) {
                h = (h * BASE2 + s.charAt(i)) % MOD2;
            }
            return h % bitSize;
        }
        public void add(String s) {
            setBit(hash1(s));
            setBit(hash2(s));
        }
        //在判断元素是否存在的时候，同时看两个模式下的状态是否为1
        public boolean contains(String s) {
            return getBit(hash1(s)) && getBit(hash2(s));
        }
        //这里和位图的逻辑是一样的，只不过用了是64位long的空间下
        private void setBit(long idx) {
            int i = (int) idx;
            bits[i >>> 6] |= (1L << (i & 63));
            //(1L << (i & 63)就是把需要的那一位二进制变为1，其余都变为0
            //i >>> 6就是对i除以 64，定位对应 long 数组下标
        }
        private boolean getBit(long idx) {//同时校验两个哈希下标对应的比特，全部为 1 才返回 true
            int i = (int) idx;
            return (bits[i >>> 6] & (1L << (i & 63))) != 0;
            //1L << (i & 63)就是把对应比特置 1；按位与判断比特是否为 1
        }
    }
    public static void main(String[] args) throws IOException {//主函数测试部分
        BloomFilter bf = new BloomFilter(100000);
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String op = st.nextToken();
            String s = st.nextToken();
            if (op.equals("add")) {
                bf.add(s);
            } else if (op.equals("query")) {
                out.println(bf.contains(s));
            }
        }
        out.flush();
        out.close();
        br.close();
    }
}