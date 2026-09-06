# 算法仓库 (Algorithm Repository)

> **欢迎来到我的算法练习仓库！**
> 
> 本仓库打破常规，采用了 **“一算法一分支”** 的独特管理策略。每个分支代表一个独立的算法主题或数据结构，彼此互不影响，方便随时查阅和对比学习。
> 
> 当前页面（`guass` 分支）是该仓库的 **主入口**，你可以在这里找到通往所有算法分支的快捷链接。

---

## 🧭 快速导航（分支索引）

在 GitHub 网页端，点击上方的分支切换按钮，或者直接点击下方链接，即可跳转到对应算法分支的详细代码页面。

| 分支名称 | 核心算法 / 数据结构 | 实现语言 | 分支简述 | 快速直达 |
| :--- | :--- | :--- | :--- | :--- |
| **`guass`** | 高斯消元法 | C++ | 当前主分支。包含异或高斯消元模板、位图优化解法，以及相关算法题库的详细笔记。 | [🔗 前往 guass](https://github.com/xiao-meng-byte/gaussian-elimination/tree/guass) |
| **`stack`** | 栈 (Stack) | C++ / Java | 手写动态扩容栈、表达式求值（中缀/后缀）、括号匹配等经典栈应用。 | [🔗 前往 stack](https://github.com/xiao-meng-byte/gaussian-elimination/tree/stack) |
| **`SegmentTree`** | 线段树 (Segment Tree) | C++ / Java | 线段树的构建、区间查询、区间修改（加法/乘法/赋值），以及懒标记（Lazy Tag）的经典应用。 | [🔗 前往 SegmentTree](https://github.com/xiao-meng-byte/gaussian-elimination/tree/SegmentTree) |
| **`binary`** | 位运算 (Bit) | Java | 异或运算技巧、进制转换（二/八/十/十六）、以及利用位运算实现状态压缩。 | [🔗 前往 binary](https://github.com/xiao-meng-byte/gaussian-elimination/tree/binary) |
| **`bitset`** | 位图 (Bitset) | Java | 底层基于位（Bit）的数据结构实现，包含插入、删除、反转、统计等基础操作及原理解析。 | [🔗 前往 bitset](https://github.com/xiao-meng-byte/gaussian-elimination/tree/bitset) |
| **`hash`** | 哈希表 (Hash) | Java | 哈希表底层原理、哈希映射、冲突处理机制及典型算法应用。 | [🔗 前往 hash](https://github.com/xiao-meng-byte/gaussian-elimination/tree/hash) |
| **`fenwick`** | 树状数组 (Fenwick Tree) | C++ | 树状数组核心原理推导、单点修改/区间查询、区间修改/单点查询、双树状数组模拟区间修改与区间求和。 | [🔗 前往 fenwick](https://github.com/xiao-meng-byte/gaussian-elimination/tree/fenwick) |
| **`deque`** | 队列 / 双端队列 (Deque) | C++ / Java | 优先队列原理、手写完全二叉树（堆）、单调队列、滑动窗口、双端队列基础用法及其实现。 | [🔗 前往 deque](https://github.com/xiao-meng-byte/gaussian-elimination/tree/deque) |

---

## 📖 本地克隆与切换指南

如果你想在本地查看所有分支的代码，只需在终端执行以下命令：

### 1. 克隆整个仓库
git clone git@github.com:xiao-meng-byte/gaussian-elimination.git

### 2. 进入仓库目录
cd gaussian-elimination

### 3. 查看所有分支
git branch -a

### 4. 切换到你想查看的算法分支（例如看栈的代码）
git checkout stack
