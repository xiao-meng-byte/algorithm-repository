import java.io.*;
import java.util.*;
public class Hash {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    static StringTokenizer st;
    static class HashMap {
        // 0-空无数据，1-占用，2-已删除状态
        int[] hash;//存状态
        int[] keys;//存键值
        int[] values;//存value值
        int size;//存储的元素个数
        int capacity;//总容量
        final double LOAD_FACTOR = 0.75;//负载因子，和工业级的一样为0.75
        public HashMap() {
            this.capacity = 31;//初始容量为31，一个质数
            hash = new int[capacity];
            keys = new int[capacity];
            values = new int[capacity];
            size = 0;
        }
        public void put(int key, int value) {//插入元素
            if (size >= capacity * LOAD_FACTOR) {//如果容量已满，则扩容
                resize(capacity * 2);
            }
            int index = find(key);//寻找插入的位置下标
            if (hash[index] == 0 || hash[index] == 2) {//如果位置为空或者已删除状态，则插入
                hash[index] = 1;//将哈希状态设为以占用
                keys[index] = key;//赋入键值
                values[index] = value;//赋入value值
                size++;//有效长度加加
            } else {//如果已经状态为被占用了，就覆盖
                values[index] = value;
            }
        }
        public int get(int key) {//查询元素
            int index = find(key);//查找位置下标
            if (hash[index] == 1) return values[index];//如果状态为占用，则返回value值
            return -1;//如果状态为空或者已删除状态，则返回-1
        }
        public boolean containsKey(int key) {//判断是否包含某个键值
            int index = find(key);//查找位置下标
            return hash[index] == 1;//如果状态为占用，则返回true，否则返回false
        }
        public boolean containsValue(int value) {//判断是否包含某个value值
            for (int i = 0; i < capacity; i++) {//遍历哈希表
                if (hash[i] == 1 && values[i] == value) {//如果状态为占用且value值等于给定值，则返回true
                    return true;
                }
            }
            return false;
        }
        public int remove(int key) {//删除元素
            int index = find(key);//查找位置下标
            if (hash[index] != 1) return -1;//如果不存在该元素，则返回-1
            int res = values[index];//存储要删除的value值
            hash[index] = 2; //将状态设为已删除
            size--;//长度减减
            return res;//返回删除的value值
        }
        public void print() {//打印哈希表
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            boolean first = true;
            for (int i = 0; i < capacity; i++) {
                if (hash[i] == 1) {//如果状态为占用，即有有效元素才输出打印
                    if (!first) sb.append(", ");
                    sb.append(keys[i]).append("=").append(values[i]);
                    first = false;
                }
            }
            sb.append("}");
            out.println(sb.toString());
        }
        private int find(int key) {//查找元素位置下标
            int index = (key % capacity + capacity) % capacity;//利用同余原理计算哈希值（处理负数key，确保下标非负）
            int count = 0;//记录探测次数，防止哈希表满时发生死循环
            while (hash[index] != 0 && keys[index] != key && count < capacity) {//当前位置被占用，且不是目标key，且未遍历完整个数组
                index = (index + 1) % capacity;//发生哈希冲突，线性探测到下一个位置
                count++;//探测次数加1
            }
            return index;//返回最终找到的下标（若找到key则返回key所在下标，若未找到则返回空位或伪删除位的下标）
        }
        private int nextPrime(int n) {//在扩容的时候每次保证容量为质数
            if (n <= 2) return 2;
            if (n % 2 == 0) n++;//如果n为偶数，则加1
            while (true) {
                boolean isPrime = true;
                for (int i = 3; i * i <= n; i += 2) {//判断是否为质数
                    if (n % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) return n;//如果是质数，则返回该数
                n += 2; //否则，加2，把它变为质数
            }
        }
        private void resize(int newCapacity) {//扩容操作
            int newCap = nextPrime(newCapacity);//扩容为一个质数
            //将原哈希素表复制过来
            int oldCap = capacity;
            int[] oldHash = hash;
            int[] oldKeys = keys;
            int[] oldVals = values;
            //创建新的哈希表
            capacity = newCap;
            hash = new int[capacity];
            keys = new int[capacity];
            values = new int[capacity];
            size = 0;
            for (int i = 0; i < oldCap; i++) {
                if (oldHash[i] == 1) {//将原有的有效元素插入扩容后的哈希表
                    put(oldKeys[i], oldVals[i]);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {//主函数测试
        int n = Integer.parseInt(br.readLine());
        HashMap hm = new HashMap();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String op = st.nextToken();
            if (op.equals("put")) {
                int num1 = Integer.parseInt(st.nextToken());
                int num2 = Integer.parseInt(st.nextToken());
                hm.put(num1, num2);
            } else if (op.equals("get")) {
                int num1 = Integer.parseInt(st.nextToken());
                out.println(hm.get(num1));
            } else if (op.equals("containsKey")) {
                int num1 = Integer.parseInt(st.nextToken());
                out.println(hm.containsKey(num1));
            } else if (op.equals("containsValue")) {
                int num1 = Integer.parseInt(st.nextToken());
                out.println(hm.containsValue(num1));
            } else if (op.equals("remove")) {
                int num1 = Integer.parseInt(st.nextToken());
                out.println(hm.remove(num1));
            } else if (op.equals("size")) {
                out.println(hm.size);
            } else if (op.equals("print")) {
                hm.print();
            }
        }
        out.flush();
        br.close();
        out.close();
    }
}