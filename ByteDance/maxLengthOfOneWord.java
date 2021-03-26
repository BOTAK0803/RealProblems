/**
有一个仅包含’a’和’b’两种字符的字符串s，长度为n，每次操作可以把一个字符做一次转换（把一个’a’设置为’b’，或者把一个’b’置成’a’)；
但是操作的次数有上限m，问在有限的操作数范围内，能够得到最大连续的相同字符的子串的长度是多少。
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Main{
    public static void main(String[] args){
        // 滑动窗口求解
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();
        String s = sc.nextLine();
        char[] c = s.toCharArray();
        // System.out.println(slideWindow(c,m));
        System.out.println(Math.max(arraySolve(c,'a',m),arraySolve(c,'b',m)));
    }
    /**
    ① 对字符串设置left和right两个指针，初始化left=right=0；而闭区间[left,right]就是窗口。
    ② 不断增加窗口的right指针，直到窗口中满足题目要求，本题是闭区间中已经更改某个字符（a或b）m次了（用an或bn来表示次数情况），此时相当于找到了解题的可行解。
    ③ 由于本题是寻找最长子串，所以left指针和right指针要一起往右增加，并且当窗口中改变了相应m次值时更新结果，在一系列可行解中找最优解。
    */
    public static int slideWindow(char[] c,int ops){
        int n = c.length;
        int left = 0;
        int right = 0;
        int res = Integer.MIN_VALUE;
        int numOfa = 0;
        int numOfb = 0;
        while(right < n){
            if(c[right] == 'a'){
                numOfa++;
            }else{
                numOfb++;
            }
            if(numOfa <= ops || numOfb <= ops){
                right++;
            }else{
                res = Math.max(res,right - left);
                if(c[left] == 'a'){
                    left++;
                    numOfa--;
                }else{
                    left++;
                    numOfb--;
                }
                right++;
            }
        }
        res = Math.max(res,right-left);
        return res;
    }
    /**
    利用字符下标计算间隔长度，遍历字符串s，以b换a举例：返回所有b的索引值保存在数组中，存为数组indexes=[idx1,idx2,…]，（a换b一样）。
    计算m个b的最大间隔区间，如果b的个数小于等于m，即字符串中不足m个b，全部将他们替换成a即可；
    否则取“indexes[i]- indexes[i-m-1]-1”的长度的最大值max即为最大连续的相同字符的子串长度，但要注意首位元素的处理。
    */
    public static int arraySolve(char[] cs,char c,int m){
        int res = 0;
        int n = cs.length;
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0;i<n;i++) if(cs[i] == c) indexes.add(i);
        if(indexes.size() < m) return n;
        indexes.add(cs.length);
        res = indexes.get(m);
        for(int i = m+1;i<indexes.size();i++){
            res = Math.max(res,indexes.get(i) - indexes.get(i-m-1)-1);
        }
        return res;
    }
}