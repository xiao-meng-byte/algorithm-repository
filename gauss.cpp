//
// Created by Administrator on 2026/9/5.
//
#include "gauss.h"
#include <bits/stdc++.h>
using namespace std;
static vector<vector<int>> graph;
static void guass(int n) {
    for (int i=1;i<=n;i++) {
        for (int j=1;j<=n;j++) {
            if (j<i&&graph[j][j]==1) {
                continue;
            }
            if (graph[j][i]==1) {
                swap(graph[i],graph[j]);
                break;
            }
        }
        if (graph[i][i]==1) {
            for (int j=1;j<=n;j++) {
                if (i!=j&&graph[j][i]==1) {
                    for (int k=i;k<=n+1;k++) {
                        graph[j][k]^=graph[i][k];
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
    cin>>n;
    graph.assign(n+1,vector<int>(n+2,0));
    for (int i=1;i<=n;i++) {
        for (int j=1;j<=n+1;j++) {
            cin>>graph[i][j];
        }
    }
    guass(n);
    for (int i=1;i<=n;i++) {
        for (int j=1;j<=n+1;j++) {
            cout<<graph[i][j]<<" ";
        }
        cout<<endl;
    }
    return 0;
}