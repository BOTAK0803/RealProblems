# ByteDance 2017 - Algorithm

## 1 现在有一个字符串，你要对这个字符串进行 n 次操作，每次操作给出两个数字：(p, l) 表示当前字符串中从下标为 p 的字符开始的长度为 l 的一个子串。你要将这个子串左右翻转后插在这个子串原来位置的正后方，求最后得到的字符串是什么。

```java
import java.util.*;
public class Main{
    static StringBuilder sb;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        sb = new StringBuilder(str);
        int n = sc.nextInt();
        int[][] operations = new int[n][2];
        for(int i = 0;i<n;i++) for(int j = 0;j<2;j++) operations[i][j] = sc.nextInt();
        for(int i = 0;i<n;i++){
            appendStr(sb,operations[i][0],operations[i][1]);
        }
        System.out.println(sb.toString());
   
    }
    public static void appendStr(StringBuilder sb,int left,int len){
        String str = reverse(sb.substring(left,left+len));
        sb.insert(left+len,str);
    }
    public static String reverse(String str){
        int n = str.length();
        char[] cs = str.toCharArray();
        int left = 0;
        int right = n-1;
        while(left < right){
            char tmp = cs[left];
            cs[left] = cs[right];
            cs[right] = tmp;
            left++;
            right--;
        }
        return String.valueOf(cs);
    }
}
```

## 2 你作为一名出道的歌手终于要出自己的第一份专辑了，你计划收录 n 首歌而且每首歌的长度都是 s 秒，每首歌必须完整地收录于一张 CD 当中。每张 CD 的容量长度都是 L 秒，而且你至少得保证同一张 CD 内相邻两首歌中间至少要隔 1 秒。为了辟邪，你决定任意一张 CD 内的歌数不能被 13 这个数字整除，那么请问你出这张专辑至少需要多少张 CD ？

```java
import java.util.*;
public class Main{
    
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while(in.hasNext()){
            int n = in.nextInt();
            int s = in.nextInt();
            int l = in.nextInt();
            int count = (l+1)/(s+1);
            count = Math.min(n, count);
            if(count%13==0){
                count--;
            }
            int sum = n/count;
            int yu = n%count;
			if(yu!=0){
            	sum++;
            	if(yu%13==0&&(count-yu)==1){//查看最后最后一张专辑的情况
            		sum++;
            	}
            }
            System.out.println(sum);
        }
    }
}

/**
import java.util.Scanner;
public class Main{
    // n：计划收录n首歌
    static int n;
    // s：每一首歌s秒
    static int s;
    // L：每张CD的容量长度是L秒
    static int L;
    // 总共需要的CD数目
    static int res = 0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        s = sc.nextInt();
        L = sc.nextInt();
        numOfSongs(L,0,n);
        System.out.println(res);
    }
    /**
    paramters:
    int rest : 当前CD剩余的容量
    int num : 当前CD存储的歌曲数目
    int total : 总共的还需要写入磁盘的歌曲的数目
    */
    public static void numOfSongs(int rest,int num,int total){
        if(total == 0){
            res+=1;
            if(num%13 == 0) res+=1;
            return;
        }else{
            if(rest < s){
                if(num%13 == 0){
                    res+=1;
                    numOfSongs(L-s-1,1,total);
                }else{
                    res+=1;
                    numOfSongs(L,0,total);
                }
            }else{
                numOfSongs(rest - s - 1,num+1,total-1);
            }
        }
    }
}
*/
```

## 3给出 n 个字符串，对于每个 n 个排列 p，按排列给出的顺序(p[0] , p[1] … p[n-1])依次连接这 n 个字符串都能得到一个长度为这些字符串长度之和的字符串。所以按照这个方法一共可以生成 n! 个字符串。 一个字符串的权值等于把这个字符串循环左移 i 次后得到的字符串仍和原字符串全等的数量，i 的取值为 [1 , 字符串长度]。求这些字符串最后生成的 n! 个字符串中权值为 K 的有多少个。 注：定义把一个串循环左移 1 次等价于把这个串的第一个字符移动到最后一个字符的后面。

```java
import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int K = sc.nextInt();
        String[] strs = new String[n];
        for(int i = 0;i<n;i++) strs[i] = sc.nextLine();
        int res = 0;
        for(int i = 0;i<n;i++){
            for(int j = i;j<n;j++){
                String com = strs[i] + strs[j];
                if(com.length() == 0) continue;
                int k = K%com.length();
                if(com.equals(moveK(com,k))) res++;
            }
        }
        System.out.println(res);
    }
    public static String reverse(String str , int left , int right){
        char[] cs = str.toCharArray();
        int l = left;
        int r = right;
        while(l<r){
            char tmp = cs[l];
            cs[l] = cs[r];
            cs[r] = tmp;
            l++;
            r--;
        }
        return String.valueOf(cs);
    }
    public static String moveK(String str,int k){
        String str1 = reverse(str,0,k-1);
        String str2 = reverse(str1,k,str.length()-1);
        return reverse(str2,0,str.length()-1);
    }
}
```

## 4 给定 x, k ，求满足 x + y = x | y 的第 k 小的正整数 y 。 | 是二进制的或(or)运算，例如 3 | 5 = 7。 比如当 x=5，k=1时返回 2，因为5+1=6 不等于 5|1=5，而 5+2=7 等于 5 | 2 = 7。

```java
import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int k = sc.nextInt();
        int res = 0;
        for(int i = 1;i<1000000;i++){
            if((x+i) == (x|i)) {
                k--;
                if(k==0) res = i;
            }
        }
        System.out.println(res);
    }
}
```

