//洛谷p1226,题目链接:https://www.luogu.com.cn/problem/P1226
//对于这个题目我们可以先看看我们如果要求10^75次方难道要乘75次10吗？，很显然有比这更快的
//我们可以先看75的二进制为1001011.那我们可不可以每次更新的是幂数呢？
//当然可以，我们先从幂数为1开始，即10^1开始，那么我们我们看着一位的的二进制是否为1，为一就乘它，同时幂数也要自乘
//依次变为10^2,10^4,10^8......看到二进制为1就乘上这一位对应的该幂数次方，相乘的步骤就大大的减少了。
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
ll quickpow(ll a, ll b, ll mod) {
    ll res = 1;
    a %= mod;
    while (b > 0) {
        if (b & 1) {
            res = (res * a) % mod;
        }
        a = (a * a) % mod;
        b >>= 1;
    }
    return res;
}
int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    ll a, b, mod;
    cin >> a >> b >> mod;
    cout << a << "^" << b << " mod " << mod << "=" << quickpow(a, b, mod) << endl;
    return 0;
}