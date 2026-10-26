# KMP算法的学习
## 引入背景
$S1$字符串是否包含$S2$字符串，如果包含返回$S1$中包含$S2$的最左开头位置，不包含返回-1，暴力解法我们可以用$S1$的每个位置都做开头，然后去匹配S2整体，这样的话时间复杂度为**O(n*m)**
，实际上，我们有更快的方法，就是我们可以试着想一个字符串$S1$,判断另一个字符串$S2$是否是他的字串，示意图如下:
```text
一个字符串S1:abcdabaed
另一个字符串S2:cdab,然后我们判断S2是否是S1的子串，BF的暴力解法就是:
int BF(string S1,string S2){
   int n=S1.size();
   int m=S2.size();
   if(m>n) return -1;
   for(int i=0;i<=n-m;i++){
       int j=0;
       for(int j=0;j<m;j++){
         if(S1[i+j]!=S2[j]){
            break;  
         }
       }
       if(j==m) return i;
   }
   return -1;
}
在这个解法中，我们以 S1 的每一个字符为起点，往后遍历 S2 长度个字符去比较。
如果当前结果比较失败，主串指针 i 和子串指针 j 都要全盘回退。
比如，假设 S1 的前四个字符是 abcd，匹配到一半失败了，那么 i 要从当前位置回退到下一个位置（比如从下标 3 退回到 1），变成从 bcda 继续比较；而 S2 的指针 j 也要直接清零，重新从头遍历。这种暴力匹配的最坏时间复杂度达到了 O(n * m)。
那么，我们能不能让主串不回退，或者至少减少子串指针的回退次数呢？
答案是肯定的。我们可以提前对 S2 创建一个前缀数组（即 next 数组）。这个数组记录了 S2 自身的“最长相等前后缀”信息。这样一来，当某一次比较失配时，主串指针 i 保持不动，我们只需要根据 next 数组，把子串指针 j 回退到“已经成功匹配的最长前缀的下一个位置”即可，无需每次都退回起点。
这样不仅避免了主串的重复扫描，也大幅减少了子串的无用比对。这就是大名鼎鼎的 KMP 算法（时间复杂度优化到了 O(n + m)）。
```
## 原理解说和模版代码
* 在**KMP**算法中，我们需要定义一个next数组来进行辅助，而它的含义是**不含当前，前面字符串，前后缀最大区间匹配长度，且不能是整体**,比如说字符串$aabaabsaabaaa$
对于这个字符串来说:
```text
字符    a  a  b  a  a  b  s  a  a  b  a  a  a
下标    0  1  2  3  4  5  6  7  8  9  10 11 12
next值 -1  0  1  0  1  2  3  0  1  2  3  4  5
```
* 再比如说:
  `next` 数组是专门针对子串 `S2` 计算出来的一个表。
  它记录的是：**子串 `S2` 的每一个前缀，它最长相等的“真前缀”和“真后缀”的长度。**

*   **前缀**：包含第一个字符，不包含最后一个字符的连续子串。
*   **后缀**：包含最后一个字符，不包含第一个字符的连续子串。

拿 `S2 = "ababc"` 举例，我们来手算它的 `next` 数组：

| 当前子串 | 真前缀 | 真后缀 | 最长相等长度 | next值 |
| :--- | :--- | :--- | :--- | :--- |
| `a` | 无 | 无 | 0 | 0 |
| `ab` | `a` | `b` | 0 | 0 |
| `aba` | `a`, `ab` | `a`, `ba` | `a` 和 `a` 相等，长度1 | 1 |
| `abab` | `a`, `ab`, `aba` | `b`, `ab`, `bab` | `ab` 和 `ab` 相等，长度2 | 2 |
| `ababc` | ... | ... | 前缀和后缀根本不搭边，长度0 | 0 |

所以 `S2 = "ababc"` 的 `next` 数组是：`[0, 0, 1, 2, 0]`。
* 在这里next数组的第i项的意义在于: 当子串在第i位置匹配失败时，子串应该瞬移到哪个位置进行比较，而主串指针则永远不回退:
  * 及没匹配上，主串不动，子串按next数组所标滑过去，比如说:
```text
S1: [a b a b] a b c
S2: [a b a b] c
前四个字符已经比较了abab相同了，那我们发现第五个字符不相同，那我们在要回退的时候就可以参考next数组，下一次比较再回退时就移到位置2处即:
S1: [a b a b] a b c
S2:     [a b  a b c] 
```
* 模板代码如下:
```cpp
#include <bits/stdc++.h>
using namespace std;
// 构建 next 数组（前缀表右移一位，首位补 -1）
vector<int> nextArray(const vector<char>& s2, int m) {
    // 边界情况：如果模式串长度为 1，直接返回 [-1]
    if (m == 1) {
        return vector<int>{-1};
    }
    vector<int> next(m);
    next[0] = -1; // 第 0 位失配，回退到 -1（实际代码中靠 y==0 分支拦截，避免越界）
    next[1] = 0;  // 第 1 位失配，只能回退到第 0 位重新开始
    int i = 2;    // i 代表当前正在计算 next 值的后缀下标
    int cn = 0;   // cn 代表当前匹配到的最长相等前缀的下一个位置（也代表前缀长度）
    while (i < m) {
        if (s2[i - 1] == s2[cn]) {
            // 情况 1：当前字符与前缀下一个字符匹配，前缀长度 +1，继续计算下一个 next
            next[i++] = ++cn;
        } else if (cn > 0) {
            // 情况 2：不匹配，利用已经求出的 next 数组，让前缀指针快速往回跳
            cn = next[cn];
        } else {
            // 情况 3：cn 已经退到 0 了，实在找不到公共前后缀了，记为 0
            next[i++] = 0;
        }
    }
    return next;
}
// KMP 匹配主函数
int kmp(const vector<char>& s1, const vector<char>& s2) {
    int n = s1.size(); // 主串长度
    int m = s2.size(); // 模式串长度
    // 如果主串比模式串短，或者模式串为空，直接返回 -1
    if (n < m || m < 1) {
        return -1;
    }
    int x = 0; // 主串指针
    int y = 0; // 模式串指针
    // 获取模式串的 next 数组
    vector<int> next = nextArray(s2, m);
    // 当主串和模式串都没走到尽头时，继续匹配
    while (x < n && y < m) {//如果x>n的时候那么就是比对主串越界了，就说明没找到，如果y>m的是查询串越界了，说明已经找到了
        if (s1[x] == s2[y]) {
            // 分支 1：匹配成功，主串和模式串指针同时后移
            x++;
            y++;
        } else if (y == 0) {
            // 分支 2：模式串首字符就失配了，模式串无法再回退，只能让主串往前挪一步
            x++;
        } else {
            // 分支 3：模式串中途失配，主串指针 x 不动，模式串指针 y 根据 next 数组滑移
            y = next[y];
        }
    }
    // 如果 y 走到了 m，说明模式串全部匹配完，返回在主串中的起始下标；否则返回 -1
    return y == m ? x - y : -1;
}
int main(){
	// 优化输入输出速度（在算法竞赛中常用）
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	string str1;
	cin >> str1; // 读取主串
	string str2;
	cin >> str2; // 读取模式串
	// 将 std::string 转换为 vector<char> 以匹配 kmp 函数的参数类型
	vector<char> s1(str1.begin(), str1.end());
	vector<char> s2(str2.begin(), str2.end());
	// 调用 KMP 算法获取匹配的起始位置
	int pos = kmp(s1, s2);
	// 输出结果
	cout << pos << endl;
	return 0;\n
}
```
* 以下几种情况的跳转进行讲解:

  第一种:不用跳，字符串:$abatabasabatabas$
```text
1. 匹配成功，长度自然增加
- 字符串：a b a t a b a s a b a t a b a s
- 下标：0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
- 解说：
  abata（下标 0~4），后接字符 b（下标 5）。
  abata（下标 9~13），后接字符 b（下标 14）。
- 原理：模式串的前缀 abata 和它自己的后缀 abata 已经相等（长度 5）。现在，前缀紧跟着的字符 b（下标 5）和后缀紧跟着的字符 b（下标 14）也相等。
- 结论：因为新加入的字符也相等，所以最长相等前后缀的长度直接 +1，从 5 变成了 6。

2. “不用跳”，实际上，不用跳的意思是:
当前字符相等，cn指针(前缀长度)和i指针(后缀末尾)就需要同时向前走无需回溯
在 KMP 的推导代码中：
  if (P[i - 1] == P[cn]) {
      next[i++] = ++cn;
  }
```
  第二种:需要回溯的情况，对于字符串$abatabasabatabatX$,$X$表示下一个的任意字符
```text
我们已知主串为abatabasabatabatX，我们假设在与子串进行匹配的时候发生了失配:
第一跳：找整个串的最长相等前后缀
1. 观察已匹配的部分：失配前，主串和模式串的 abatabasabatabat 是完全匹配的。
2. 找前后缀：
   - 字符串开头是：abat（下标 0~3）
   - 字符串结尾是：abat（下标 12~15）
   - 它们是完全相等的，最长相等前后缀长度是 4。
3. 执行跳转：既然模式串的后 4 个字符 abat 和开头的 4 个字符 abat 是一样的，我们就不需要把模式串完全退回起点。
   - 跳法：把模式串直接平移，让开头的 abat 对齐刚才主串匹配成功的后缀 abat。
   - 结果：模式串的指针从末尾跳到下标 4（即前缀 abat 的后面）。这就是“跳一次”。

第二跳：前缀内部找更短的相等前后缀
1. 新场景：指针跳到了下标 4，准备用模式串的字符 a（下标 4）继续和主串的 X 匹配。结果发现还是失配（a 不等于 X）。
2. 找子串的前后缀：现在我们不能看整个串了，只能看刚才被利用的前缀 abat（下标 0~3）。
   - 在 abat 里面，开头是 a，结尾是 t。
   - 找它的最长相等前后缀：只能找到 a（长度为 1）。
3. 执行跳转：既然 abat 的后缀 a 和前缀 a 是一样的，我们再次平移模式串。
   - 结果：模式串的指针从下标 4 跳到下标 1（前缀 a 的后面）。这就是“跳两次”。

第三跳：退无可退，回到起点
1. 新场景：指针跳到了下标 1，用模式串的字符 b（下标 1）去和主串的 X 匹配。结果还是失配。
2. 找子串的前后缀：看刚才利用的前缀 a。长度为 1 的字符串，没有真前缀和真后缀。
3. 执行跳转：这意味着连最短的公共前后缀都找不到了。
   - 结果：指针只能回到下标 0（或者 -1），也就是“跳到头了”。由于模式串的第一个字符都接不上，主串的指针只能往后移动一位，换下一个字符重新开始。

总结：“跳”的递推逻辑
对于 abatabasabatabat 这个串：
1. 当你需要计算/失配时，先看最长的公共前后缀（abat，长度 4），指针跳到下标 4。
2. 如果在下标 4 依然接不上，去查 abat 内部的公共前后缀（a，长度 1），指针跳到下标 1。
3. 如果在下标 1 还是接不上，查 a 内部（无），指针跳到下标 0。
```
## 经典好题
### 题目一---BF 与 KMP 比较次数对比
### 题目描述

给定主串 \(S\) 和模式串 \(P\)，分别用 **BF 算法**和 **KMP 算法**进行匹配（找到第一次出现即停止，允许重叠但本题只找首次）。统计两种算法中的字符比较次数（每次比较 \(S[i]\) 与 \(P[j]\) 计 1 次，无论成功失败）。输出 BF 比较次数和 KMP 比较次数。若不存在匹配，输出 `-1 -1`。

### 输入格式

一行两个字符串 \(S, P\)。

### 输出格式

一行两个整数：BF 比较次数、KMP 比较次数；若不存在匹配，输出 `-1 -1`。

### 输入样例
```in
ababcabcacbab abcac
```
### 输出样例
```out
16 12
```
代码如下：
```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//BF暴力匹配算法，S1为主串，S2为模式串，cnt记录比较次数
int BF(char* S1,char* S2,int *cnt){
    int n=strlen(S1);
    int m=strlen(S2);
    *cnt=0;//初始化比较次数
    if(m>n) return -1;//模式串比主串长，不可能匹配
    for(int i=0;i<=n-m;i++){//主串起点最多到n-m，保证剩余长度足够
        int j=0;
        for(j=0;j<m;j++){//逐个比较模式串的字符
            (*cnt)++;//无论成功失败，只要比较了就+1
            if(S1[i+j]!=S2[j]){//发现不匹配，直接跳出当前起点的比较
                break;  
            }
        }
        if(j==m) return i;//j走到了m，说明全部匹配成功，返回起始下标
    }
    return -1;//遍历完都没找到，返回-1
}
//获取KMP的next数组，S1实际传入的是模式串，m是模式串长度
int* getnext(char *S1,int m){
    int *next=(int*)malloc(m*sizeof(int));//动态分配next数组
    next[0]=-1;//规定第0位失配跳到-1
    if(m==1) return next;//长度为1特判
    next[1]=0;//第1位失配跳回0
    int i=2;//从下标2开始计算next
    int index=0;//index代表当前匹配的最长公共前后缀长度
    while(i<m){
        if(S1[i-1]==S1[index]){//后缀末尾和前缀末尾相等
            next[i++]=++index;//长度+1并记录
        }else if(index>0){//不相等且可以回溯
            index=next[index];//往前找更短的公共前后缀
        }else{//退到0了还不相等
            next[i++]=0;//没有公共前后缀，记为0
        }
    }
    return next;
}
//KMP算法匹配主串S1和模式串S2，count记录比较次数
int kmp(char *S1,char *S2,int *count){
    int n=strlen(S1);
    int m=strlen(S2);
    *count=0;//初始化比较次数
    if(m>n) return -1;//模式串比主串长，直接返回
    int *next=getnext(S2,m);//获取模式串的next数组
    int i=0,j=0;//i遍历主串，j遍历模式串
    while(i<n&&j<m){
        if(j==-1){//j为-1说明模式串第一个字符就失配了
            i++;//主串指针后移，模式串重新从头开始
            j=0;
        }else{
            (*count)++;//只要发生比较，计数+1
            if(S1[i]==S2[j]){//匹配成功，两个指针同时后移
                i++;
                j++;
            }else{//匹配失败
                j=next[j];//主串不动，模式串根据next数组回溯
            }
        }
    }
    free(next);//释放动态分配的内存
    if(j==m) return i-j;//匹配成功，返回起始下标
    return -1;//未找到返回-1
}
int main(){
    char str[20000];//主串
    char arr[20000];//模式串
    if(scanf("%s %s",str,arr)!=2) return 0;//读取输入
    int BFcnt=0,kmpcnt=0;//分别记录两种算法的比较次数
    int pos1=BF(str,arr,&BFcnt);//执行BF算法
    int pos2=kmp(str,arr,&kmpcnt);//执行KMP算法
    if(pos1==-1){//如果没有找到匹配
        printf("-1 -1\n");
    }else{//如果找到了，输出两种算法的比较次数
        printf("%d %d\n",BFcnt,kmpcnt);
    }
    return 0;
}
```
### 题目二--- KMP 的 next 与 nextval 数组
### 题目描述

给定模式串 `P`（下标从 1 开始，长度 `m`），按数据结构教材定义计算并输出 `next[1..m]` 和 `nextval[1..m]`。

#### 约定

- `next[1] = 0`；
- 对 `j ≥ 2`，`next[j]` 为 `P[1..j−1]` 的最长相等真前缀与真后缀的长度加 1；
- `nextval[1] = 0`；对 `j ≥ 2`，若 `P[j] = P[next[j]]`，则 `nextval[j] = nextval[next[j]]`，否则 `nextval[j] = next[j]`。

#### 数据范围

`1 ≤ m ≤ 10^5`。

#### 输入格式

一行字符串 `P`，不含空格。

#### 输出格式

共两行：

- 第一行：`next[1..m]`，空格分隔；
- 第二行：`nextval[1..m]`，空格分隔。

#### 输入样例

```in
abaabcac
```

#### 输出样例

```out
0 1 1 2 2 3 1 2
0 1 0 2 1 3 0 2
```
* 其实对于这个题目，我们可以了解一下这个`nextval`数组的真实作用:
```text
我们在kmp算法中，我们知道next数组的作用是在字符失配是，回溯到next数组的在该下标位置的值。
那么我们就要多考虑一个情况，对于字符串str[i]=str[next[i]]时，就会出现:
一个字符串aaaab,他的next数组为next[0,1,2,3,4],如果我们在str[4]='a'时，
主串就和他出现了失配的现象，那么我们肯定想着回溯，按照next,i就会回退到next[4]=3
那我们就又要比较一次'a'字符了，所以很大概率又会失配，这样如此往复，面对这样的问题
我们的nextval数组就诞生了，对于这个数组，他有:
nextval[1] = 0
对 j ≥ 2：
    若 P[j] == P[next[j]]，则 nextval[j] = nextval[next[j]]
    否则 nextval[j] = next[j]
而他的含义是如果在失配的情况下，我们进行回溯是发现目标位置的字符和失配的字符相同
即当前的字符和回溯后的字符相同，那么我们就直接继承回退目标的nextval，一步跳到更前面去
就是说我们回溯到回溯后的字符和当前字符不一样了，就可以停止了，这样就要进行多次回退，对于str这个数组来说:
i      str[i]     next[i]    str[next[i]]    比较     nextval[i]
1       'a'         0           无            无         0
2       'a'         1           'a'           相同    nextval[1]=0
3       'a'         2           'a'           相同    nextval[1]=0
4       'a'         3           'a'           相同    nextval[2]=0
5       'b'         4           'a'           不同    nextval[5]=next[5]=4
对于这样的设置来说，我们其实进行了优化，即:
用 next：str[4] 失配 → 退到 3 → 退到 2 → 退到 1 → 退到 0，4 次回退。
用 nextval：str[4] 失配 → 直接退到 0，1 次回退。
这样以空间换时间的写法，可以使得算法更快
```
所以说，这个题目代码如下:
```c
#include <stdio.h>
#include <string.h>
#define MAXN 100010
char s2[MAXN];
int m;
int next[MAXN];        
int next2[MAXN];       
int nextval[MAXN];
void nextArray() {
    next[0] = -1;
    next[1] = 0;
    int i = 2, cn = 0;
    while (i <= m) {
        if (s2[i-1] == s2[cn]) {
            next[i++] = ++cn;
        } else if (cn > 0) {
            cn = next[cn];
        } else {
            next[i++] = 0;
        }
    }
}
//以上的模版下标是从0开始，在这里我们需要进行转化
void getNext2() {
    next2[1] = 0;
    for (int j = 2; j <= m; j++) {
        int len = next[j-1];
        next2[j] = len + 1;
    }
}
void getNextval() {
    nextval[1] = 0;
    for (int j = 2; j <= m; j++) {
        int k = next2[j];
        if (s2[j-1] == s2[k-1]) {
            nextval[j] = nextval[k];
        } else {
            nextval[j] = next2[j];
        }
    }
}
int main() {
    scanf("%s", s2);
    m = (int)strlen(s2);
    if (m == 0) return 0;
    nextArray();
    getNext2();
    getNextval();
    for (int i = 1; i <= m; i++) {
        if (i > 1) printf(" ");
        printf("%d", next2[i]);
    }
    printf("\n");
    for (int i = 1; i <= m; i++) {
        if (i > 1) printf(" ");
        printf("%d", nextval[i]);
    }
    printf("\n");
    return 0;
}
```
### 题目三---最小循环节、循环次数与所有 border
### 题目描述

给定非空字符串 \( S \)，计算：

1. **最小循环节长度** `len`。若 \( S \) 不能由某个更短串重复多次得到，则 `len = |S|`，循环次数为 1。
2. **循环次数** `cnt = |S| / len`（当 `|S| % len == 0` 时有效；否则 `cnt = 1`）。
3. 输出 \( S \) 的**所有 border 长度**（border 指既是真前缀又是真后缀的子串），从大到小排列。若没有 border，输出 `-1`。

利用 KMP 的 `next` 数组完成，要求复杂度 \( O(|S|) \)。

#### 输入格式

一行字符串 \( S \)。

#### 输出格式

共三行：

- 第一行：最小循环节长度；
- 第二行：循环次数；
- 第三行：所有 border 长度（从大到小，空格分隔），或 `-1`。

#### 样例

#### 输入：
```in
ababab
```
#### 输出：
```out
2
3
4 2
```
#### 数据范围
$
( 1 \le |S| \le 10^5)。
$

* 对于这个题目，三个要求的，为什么就都可以用**kmp**来写呢？
比如说我们有一个字符串`abcabcabca`:
```text
对于这个字符串，我们以肉眼可以看出它的最小循环节为abc,长度为3，循环次数为3
那么我们先引入next数组，对于这个字符串来说它的相等的真前缀和真后缀为abcabca，长度为7
那我们用字符串总长度减去这个next的值，就等于循环节的长度3，那么我们可以进行证明:
证明: 最小循环节长度=|S|-next(|S|):
设字符串 S 长度为 n。
next[n] 表示 S 的最长相等真前缀真后缀（border）的长度。
记 L = next[n]。
要证明：
    最小周期 p = n - L
并进一步说明：当 p | n 时，p 是最小循环节；否则循环节为 n。
============================================================
一、证明 p = n - L 是 S 的一个周期
============================================================
周期的定义：
    若对任意 i 满足 p+1 <= i <= n，都有 S[i] = S[i-p]，
    则称 p 是 S 的一个周期。
已知 L = next[n] 是 S 的最长 border，即：
    S[1..L] = S[n-L+1..n]
令 p = n - L，则 n-L+1 = p+1，所以：
    S[1..L] = S[p+1..n]
逐位展开：对 k = 1, 2, ..., L，有
    S[k] = S[p+k]
令 i = p + k，则 k = i - p，且 i 的范围是 p+1 <= i <= p+L = n。
于是对任意 i = p+1, ..., n，都有：
    S[i] = S[i-p]
这正是周期的定义。
所以 p = n - L 是 S 的一个周期。
============================================================
二、证明 p = n - L 是最小周期
============================================================
反证法：
假设存在更小的周期 q < p，即 q < n - L。
由周期定义：对 i = q+1, ..., n，有
    S[i] = S[i-q]
这意味着：
    S[q+1..n] = S[1..n-q]
即长度为 n-q 的前缀等于后缀：
    S[1..n-q] = S[q+1..n]
所以 n-q 是 S 的一个 border 长度。
但 q < n - L，所以：
    n - q > n - (n - L) = L
即存在一个比 L 更长的 border，这与 L = next[n] 是【最长】border 矛盾。
因此不存在比 p = n - L 更小的周期。
p = n - L 是最小周期。
============================================================
三、周期与循环节的区别
============================================================
周期 p：
    只要求 S[i] = S[i-p] 对 i > p 成立，
    不要求 p 能整除 n。
循环节：
    要求 S 能写成某个长度为 p 的串重复 n/p 次，
    即必须满足 p | n。
关键结论：
    (1) 若 p | n，则 p 是最小循环节长度，循环次数 cnt = n/p。
    (2) 若 p 不整除 n，则 S 不能由更短串重复构成，
        最小循环节长度为 n，循环次数为 1。
============================================================
四、证明 p | n 时 p 是循环节
============================================================
设 n = k * p（k 为正整数）。
由周期性质，对任意 i = p+1, ..., n，有
    S[i] = S[i-p]
把 S 分成 k 块，每块长度 p：
    第 0 块：S[1..p]
    第 1 块：S[p+1..2p]
    第 2 块：S[2p+1..3p]
    ...
    第 j 块：S[jp+1..(j+1)p]
对第 j 块（j >= 1）的任意位置 jp + t（1 <= t <= p），
由周期性质：
    S[jp+t] = S[(j-1)p+t]
即第 j 块的每个字符都等于第 j-1 块对应位置的字符。
逐块归纳：
    第 j 块 = 第 j-1 块 = ... = 第 0 块

所以：
    S = (S[1..p])^k
即 S 由长度为 p 的串重复 k 次构成。
又 p 是最小周期，故 p 是最小循环节。
循环次数 cnt = n/p = k。
============================================================
五、例子验证：S = "abcabcabca"
============================================================
n = 10
求 next[10]（最长 border）：
    真前缀候选：abcabcabc, abcabcab, ...
    真后缀候选：bcabcabca, cabcabca, ...
    最长相等：abcabca，长度 L = 7
验证：
    S[1..7] = "abcabca"
    S[4..10] = "abcabca"
    相等，确认 L = 7
最小周期：
    p = n - L = 10 - 7 = 3
判断循环节：
    10 mod 3 = 1 != 0
所以：
    p = 3 是周期（可验证 S[i] = S[i-3] 对 i >= 4 成立），
    但 3 不整除 10，p = 3 不是循环节。
按题目规则：
    10 % 3 != 0
    => 最小循环节长度 len = n = 10
    => 循环次数 cnt = 1
注意：
    肉眼说"循环节为 abc，长度 3，次数 3"是不准确的。
    "abcabcabca" = "abc" 重复 3 次 + "a"，最后多一个 'a'，
    并不是 "abc" 的整数次重复。
    严格按循环节定义（必须整除），其循环节是自身，长度 10，次数 1。
    若按周期定义，最小周期才是 3。
============================================================
六、总结
============================================================
+----------+--------------------------------------------------+
| 结论     | 内容                                             |
+----------+--------------------------------------------------+
| 周期     | p = n - next[n] 是 S 的最小周期                  |
| 循环节   | 当且仅当 p | n 时，p 是最小循环节，次数 n/p        |
| 否则     | 最小循环节 = n，次数 = 1                         |
+----------+--------------------------------------------------+
核心证明链：
    border L
        ==> S[1..L] = S[n-L+1..n]
        ==> S[i] = S[i-(n-L)]
        ==> n-L 是周期
    若存在更小周期 q
        ==> n-q > L 是更长 border
        ==> 与 L 是最长 border 矛盾
        ==> n-L 是最小周期
    p | n
        ==> 各块逐位相等
        ==> S = (S[1..p])^(n/p)
        ==> p 是最小循环节
这就是为什么一个 next[n] 就能同时给出
最小循环节长度和循环次数——
因为 border 和周期是同一件事的两种表述。
```
* 因此，这道题的代码如下:
```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//标准的next数组写法
int *getnext(char *s1,int m){
    if(m==1){
        int *next=(int*)malloc(1*sizeof(int));
        next[0]=-1;
        return next;
    }
    int *next=(int*)malloc((m+1)*sizeof(int));
    next[0]=-1;
    next[1]=0;
    int i=2;
    int index=0;
    while(i<=m){
        if(s1[i-1]==s1[index]){
            next[i++]=++index;
        }else if(index>0){
            index=next[index];
        }else{
            next[i++]=0;
        }
    }
    return next;
}
int main(){
    char str[100005];
    if(scanf("%s",str)!=1){
        return 0;
    }
    int len=strlen(str);
    int *next=getnext(str,len);
    /* ---------- 1. 最小循环节长度 ---------- */
	/*
	   * 经典结论：最小周期 p = len - next[len]
	   * 若 len % p == 0，则 p 是最小循环节长度，循环次数 = len / p
	   * 否则 S 不能由更短串重复构成，循环节 = len，次数 = 1
	*/
    int num=len-next[len];
    int ans=0,cnt=0;
    if(len%num==0){
		ans=num;
		cnt=len/ans;
	}else{
		ans=len;
		cnt=1;
	}
	printf("%d\n",ans);
	printf("%d\n",cnt);
    int res=next[len];
    /* ---------- 2. 所有 border 长度（从大到小） ---------- */
    /*
	    * border 链：next[len] -> next[next[len]] -> ... -> 0
	    * 每个非 0 值都是一个 border 长度，天然从大到小
    */
    if(res==0){
		printf("-1");
	}else{
		int hasback=1;
		while(res>0){
			if(!hasback) printf(" ");
			printf("%d",res);
			hasback=0;
			res=next[res];
		}
		printf("\n");
	}
	free(next);
    return 0;
}
```
### 题目四---KMP 匹配过程完整轨迹
### 题目描述

给定主串 \( S \) 和模式串 \( P \)（下标均从 1 开始），使用 KMP 算法进行匹配（允许重叠）。要求输出完整的比较轨迹：每次将 \( S[i] \) 与 \( P[j] \) 比较时，输出一行 `i j 结果`，其中结果为 `=` 表示相等，`!` 表示不等；当 \( j = m+1 \) 时表示匹配成功，输出 `i m+1 OK`，然后令:
$
(j = \text{next}[m])（若使用 nextval 则用 nextval，本题统一使用 next）继续。
$
### 约定

- 初始 \( i = 1, j = 1 \)；
- 当 \( j = 0 \) 时，不输出比较，直接:
$
  (i \leftarrow i+1, j \leftarrow 1 )；
$
- 当 \( i > |S| \) 时结束。

### 数据范围
$
(1 \le |S|, |P| \le 10^5)，轨迹行数不超过 (2|S|)。
$
### 输入格式

一行两个字符串 \( S, P \)。

### 输出格式

若干行，每行三个部分：`i j 结果`，结果用 `=`、`!` 或 `OK`。按下标从小到大输出。

### 样例

### 输入：
```in
ababcabcacbab abcac
```

### 输出：
```out
1 1 =
2 2 =
3 3 !
3 1 =
4 2 =
5 3 =
6 4 =
7 5 !
7 2 =
8 3 =
9 4 =
10 5 =
11 6 OK
11 1 !
12 1 =
13 2 =
```
* 根据题意，代码如下:
```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
// 标准的next数组的写法
int *getnext(char *s1,int m){
    if(m==1){
        int *next=(int*)malloc(sizeof(int)*1);
        next[0]=-1;
        return next;
    }
    int *next=(int*)malloc((m+1)*sizeof(int));
    next[0]=-1;
    next[1]=0;
    int i=2;
    int index=0;
    while(i<=m){//这里要加上=的原因是题目要求需要j=m+1是匹配成功，意思就是需要m个，加上等于号
        if(s1[i-1]==s1[index]){
            next[i++]=++index;
        }else if(index>0){
            index=next[index];
        }else{
            next[i++]=0;
        }
    }
    return next;
}
void kmp(char *s1,char *s2){
    int n=strlen(s1);
    int m=strlen(s2);
    if(m>n) return;
    int *next=getnext(s2,m);
    int i=0,j=0;
    while(i<n||j==m){//加上j==m可以进行的在i==n是不会直接退出循环，这样就可以保证匹配成功后还能输出 OK，并且继续找重叠匹配。
    //比如说:s1="abc",s2="abc",此时n=m=3,我们可以模拟一次0-based的过程
    //步骤      i     j     操作
    // 1        0     0   s1[0]==s1[0],需要输出1 1 =
    // 2        1     1   s1[1]==s2[1],需要输出2 2 =
    // 3        2     2   s1[2]==s2[2],需要输出3 3=
    //此时i=3=n,j=3=m,那么如果我们不加||j==m的话，这样的话我们i=3,不满足i<n,循环会直接退出，那么我们就会发现明明比较成功了，就会缺了一次4 4 OK
    //而如果加上这一条件，就可以避免这种情况
    	if(j==m){//这里根据题目要求，有当 (j = m+1) 时表示匹配成功，输出 i m+1 OK，然后令(j=next[m])，而我们输出i+1,j+1,符合题意
			printf("%d %d OK\n",i+1,j+1);
			j=next[j];
		}else if(s1[i]==s2[j]){
            printf("%d %d =\n",i+1,j+1);
            i++;
            j++;
        }else if(j==0){
            printf("%d %d !\n",i+1,j+1);
            i++;
        }else{
            printf("%d %d !\n",i+1,j+1);
            j=next[j];
        }
    }
    //如果不想在循环里写j==m,那么我们可以在循环外面再特判一次，即:
    //if(j==m){
	//	printf("%d %d OK\n",i+1,j+1);
	//}
    free(next);
}
int main(){
    char str[100005];
    char arr[100005];
    if(scanf("%s %s",str,arr)!=2){
        return 0;
    }
    kmp(str,arr);
    return 0;
}
```

### 题目五---KMP 匹配中模式串各位置的比较次数
### 题目描述

给定主串 $S$ 和模式串 $P$，使用 KMP 算法进行匹配（允许重叠）。统计在匹配过程中，模式串 $P$ 的每个字符 $P[j]$（$1 \le j \le m$）被与主串字符比较的次数。输出 $m$ 个整数。

#### 约定

每次将 $S[i]$ 与 $P[j]$（$1 \le j \le m$）比较时，`cnt[j]++`；当 $j=0$ 时不比较，直接 $i \leftarrow i+1,\ j \leftarrow 1$；当 $j=m+1$ 时匹配成功，令 $j = \text{next}[m]$，不增加比较次数。

#### 数据范围

$1 \le |S|, |P| \le 10^5$。

#### 输入格式

一行两个字符串 $S, P$。

#### 输出格式

一行 $m$ 个整数，空格分隔。

#### 输入样例

```in
abababa aba
```

#### 输出样例

```out
3 2 2
```
* 对于这个题目，我们可以从样例入手，对于这个样例:
```text
首先，对于模式串来说，它的next数组为[0,0,1](根据0-bases规则，下标从1开始),下面我们进行匹配
初始化cnt数组[0,0,0]
[a b a] b a b a
 a b a
初始i=1,j=1;
比较的时候，s1[1]==s2[1]
cnt[1]++ ==> [1,0,0]
此时i=2,j=2
s1[2]==s2[2]
cnt[2]++ ==> [1,1,0]
此时i=3,j=3
s1[3]==s1[3]
cnt[3]++ ==> [1,1,1]
此时i=4,j=4
此时我们发现已经匹配成功了(j=4=m+1)，那么根据题目所给的规则，就要j=next[m],不再增加比较次数,令j=next[m]=1
此时i=4,j=1;
s1[4]和s2[1]不相等，就直接cnt[1]++ ==> [2,1,1]
此时i=4,j=next[1]=0，按照规则，那么i就要++，j也要加一,所以i=5,j=1
s1[5]==s2[1]
cnt[1]++ ==>[3,1,1]
此时i=6,j=2
s1[6]==s2[2]
cnt[2]++ ==> [3,2,1]
此时i=7,j=3
s1[7]==s2[3]
cnt[3]++ ==> [3,2,2]
此时i=8,j=4，且i>n就要退出循环了，因此
cnt数组为[3,2,2]
```
* 对于这个题目，我们还可以继续套用上面下标从0开始的模版，就可以这样改动:
```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//标准的找next值的函数
int *getnext(const char *s1,int m){
    if(m==1){
        int *next=(int*)malloc(1*sizeof(int));
        next[0]=-1;
        return next;
    }
    int *next=(int*)malloc(m*sizeof(int));
    next[0]=-1;
    next[1]=0;
    int i=2;
    int index=0;
    while(i<m){
        if(s1[i-1]==s1[index]){
            next[i++]=++index;
        }else if(index>0){
            index=next[index];
        }else{
            next[i++]=0;
        }
    }
    return next;
}
int *getCount(char *s1,char *s2){
    int n=strlen(s1);
    int m=strlen(s2);
    //calloc可以初始化每一个元素为0
    int *count=(int*)calloc(m,sizeof(int));
    //初始化下标并获得next数组值
    int i=0,j=0;
    int *next=getnext(s2,m);
    while(i<n){
        //由于题目下标是从1开始的，而我们的模板是从0开始的
        //所以我们要进行改动，这里的j=-1是对应题目j=0的情况
        if(j==-1){
            //此时我们不比较，i前进，j则回到0
            i++;
            j=0;
        }else if(s1[i]==s2[j]){
            //只要相等过，就要count[j]++
            count[j]++;
            if(j==m-1){
                //已经比较到模式串最后一个字符且相等 → 匹配成功
                //此时 P[0..m-1] 全部匹配，最后一次比较已计入 count[m-1]
                //令 j = next[m-1]（跳回最长相等前后缀位置），继续找重叠匹配
                //i++ 是因为 S[i] 已经与 P[m-1] 匹配，下一轮从 S[i+1] 开始
                j=next[m-1];
                i++;
            }else{
                i++;
                j++;
            }
        }else{
            //比较不等，count[j]++，然后 j 回退到 next[j]
            count[j]++;
            j=next[j];
        }
    }
    return count;
}
int main(){
	char *str=(char*)malloc(100005*sizeof(char));
	if(str==NULL) return 0;
	char *arr=(char*)malloc(100005*sizeof(char));
	if(arr==NULL) return 0;
	if(scanf("%s %s",str,arr)!=2){
		return 0;
	}
    int *count=getCount(str,arr);
    for(int i=0;i<strlen(arr);i++){
        printf("%d",count[i]);
        if(i!=strlen(arr)-1) printf(" ");
    }
    printf("\n");
    return 0;
}
```



