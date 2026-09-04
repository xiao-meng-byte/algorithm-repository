//前置知识---小学学的三元一次方程
//每次选绝对值最大的系数交换上来然后消元,联系线性代数
//时间复杂度O(n^3),那啥时候有唯一解，啥时候有多解，啥时候矛盾?
//假设方程的行列式如下；
//      1
//        1   ==>  系数对角线全为1的就有唯一解
//          1
//      1
//        0    0或不为0,只要有一个为0,那就看它后面的结果如果为0就是多解，不为0就是矛盾
//          1
//高斯消元顾名思义就是找主元，即对角线上为1
#include <bits/stdc++.h>
using namespace std;
// 矩阵最大大小
static int MAXN = 101;
// 增广矩阵，前 n 列为变量系数，第 n+1 列为等号右边的常数
static vector<vector<double>> graph;
// 浮点数精度，小于 sml 的数认为是 0
static double sml = 1e-7;
// 高斯-约旦消元，n 为变量数量和方程数量
void gauss(int n) {
    // 依次处理第 1 列到第 n 列
    for (int i = 1; i <= n; i++) {
        // 记录当前列绝对值最大的元素所在行
        int max = i;
        // 遍历所有尚未确定主元的行，寻找当前列绝对值最大的元素
        for (int j = 1; j <= n; j++) {
            // 前面的行如果已经有主元，就不再参与寻找
            if (j < i && abs(graph[j][j]) >= sml) {
                continue;
            }
            // 更新当前列绝对值最大的元素所在行
            if (abs(graph[j][i]) > abs(graph[max][i])) {
                max = j;
            }
        }
        // 将第 i 行和第 max 行交换，使最大元素成为当前主元
        swap(graph[i], graph[max]);
        // 如果当前主元不为 0
        if (abs(graph[i][i]) >= sml) {
            // 保存主元
            double tmp = graph[i][i];
            // 当前行除以主元，使主元变为 1
            for (int j = i; j <= n + 1; j++) {
                graph[i][j] /= tmp;
            }
            // 利用当前行消去其他所有行的第 i 列
            for (int j = 1; j <= n; j++) {
                // 不需要消除自己
                if (i != j) {
                    // 计算需要减去的倍数
                    double rate = graph[j][i] / graph[i][i];
                    // 第 j 行减去第 i 行乘以 rate
                    for (int k = i; k <= n + 1; k++) {
                        graph[j][k] -= graph[i][k] * rate;
                    }
                }
            }
        }
    }
}
int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    // 输入变量数量，同时也是方程数量
    int n;
    cin >> n;
    // 使用下标 1，所以额外开一行一列
    graph.assign(n + 1, vector<double>(n + 2));
    // 输入增广矩阵
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n + 1; j++) {
            cin >> graph[i][j];
        }
    }
    // 进行高斯-约旦消元
    gauss(n);
    // 输出消元后的增广矩阵
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n + 1; j++) {
            // 消除浮点数计算产生的微小误差
            if (abs(graph[i][j]) < sml) {
                graph[i][j] = 0;
            }
            cout << graph[i][j] << ' ';
        }
        cout << '\n';
    }
    return 0;
}