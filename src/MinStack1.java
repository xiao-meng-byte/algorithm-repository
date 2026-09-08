//自己数组模拟手写栈
class MinStack1 {//提交的时候把1去掉
    public final int MAXN=20001;
    public int []data;
    public int []min;
    int size;
    public MinStack1(){
        data=new int[MAXN];
        min=new int[MAXN];
        size=0;
    }
    public void push(int val){
        data[size]=val;
        if(size==0||val<=min[size-1]){
            min[size]=val;
        }else {
            min[size]=min[size-1];
        }
        size++;
    }
    public void pop(){
        size--;
    }
    public int top(){
        return data[size-1];
    }
    public int getMin(){
        return min[size-1];
    }
}
