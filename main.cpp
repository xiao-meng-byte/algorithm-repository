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
#include <bits/stdc++.h>
using namespace std;
static int MAXN = 101;
static vector<vector<double>> graph;
static double sml = 1e-7;
void gauss(int n) {
    for (int i = 1; i <= n; i++) {
        int max = i;
        for (int j = 1; j <= n; j++) {
            if (j < i && abs(graph[j][j]) >= sml) {
                continue;
            }
            if (abs(graph[j][i]) > abs(graph[max][i])) {
                max = j;
            }
        }
        swap(graph[i], graph[max]);
        if (abs(graph[i][i]) >= sml) {
            double tmp = graph[i][i];
            for (int j = i; j <= n + 1; j++) {
                graph[i][j] /= tmp;
            }
            for (int j = 1; j <= n; j++) {
                if (i != j) {
                    double rate = graph[j][i] / graph[i][i];
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
    int n;
    cin >> n;
    graph.assign(n + 1, vector<double>(n + 2));
    // 输入增广矩阵
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n + 1; j++) {
            cin >> graph[i][j];
        }
    }
    gauss(n);
    // 输出消元后的增广矩阵
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n + 1; j++) {
            if (abs(graph[i][j]) < sml) {
                graph[i][j] = 0;
            }
            cout << graph[i][j] << ' ';
        }
        cout << '\n';
    }
    return 0;
}