# ByteDance-2021-3-21

## Question 1

> 题目：动物园有猴山，每天都需要给猴子分发香蕉，猴子会排队依次拿去香蕉,猴子们铺张浪费，会多拿食物，但是最多不会拿超过自己食量两倍且不会超过当前香蕉一半数量的香蕉，最后一个猴子除外，即最后一个猴子可以拿走剩余的所有香蕉。
> 那么，需要准备多少香蕉，能保证所有的猴子都能吃饱。

> 输入： 一个数组，每一项表示每一个猴子的食量
> 4 3 

> 输出：香蕉数量
> 8

```java
/**
题目：动物园有猴山，每天都需要给猴子分发香蕉，猴子会排队依次拿去香蕉
猴子们铺张浪费，会多拿食物，但是最多不会拿超过自己食量两倍且不会超过当前香蕉一半数量的香蕉，最后一个猴子除外，即
最后一个猴子可以拿走剩余的所有香蕉。
那么，需要准备多少香蕉，能保证所有的猴子都能吃饱。

输入： 一个数组，每一项表示每一个猴子的食量
4 3 

输出：香蕉数量
8
*/
import java.util.Scanner;
class Solution{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String[] strs = sc.nextLine().split(" ");
		int n = strs.length;
		long[] arr = new long[n];
		for(int i = 0;i<n;i++) arr[i]=Long.parseLong(strs[i]);
		long l = 1;
		long r = Long.MAX_VALUE;
		while(l<r){
			long mid = l+(r-l)/2;
			if(check(arr,mid)){
				r = mid;
			}
			else{
				l = mid+1;
			}
			
		} 
		System.out.println(r);
		

	}
	public static boolean check(long[] nums,long mid){
		long temp;
		for(int i = 0;i<nums.length - 1;i++){
			temp  = Math.min(nums[i] *2,mid/2);
			if(nums[i] > temp) return false;
			mid -= temp;
		}
		return mid>=nums[nums.length - 2];
	} 
}
```



## Question 2

> 题目：
> 为了回馈老顾客李华，蛋糕店老板准备了n块蛋糕，排成一排，并向李华赠送其中的若干块蛋糕，但是李华在平衡饮食，决定最多接受m块蛋糕，用美味值cakes[i]来衡量第i块蛋糕的美味程度，求解李华能够收获蛋糕的美味值的最大值。

> 输入：
> 4 2
> 4 -3 9 2 

> 输出：
> 11



```java
/**
题目：
为了回馈老顾客李华，蛋糕店老板准备了n块蛋糕，排成一排，并向李华赠送其中的若干块蛋糕，
但是李华在平衡饮食，决定最多接受m块蛋糕，用美味值cakes[i]来衡量第i块蛋糕的美味程度，求解李华能够收获蛋糕的美味值的最大值。
输入：
4 2
4 -3 9 2 
输出：
11
*/
import java.util.Scanner;
import java.util.Deque;
import java.util.LinkedList;
class Solution{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		sc.nextLine();
		int[] cakes = new int[n];
		for(int i = 0;i<n;i++) cakes[i] = sc.nextInt();
		/**
		采用滑动窗口思想是对的，但是时间复杂度是O(m*n)，时间复杂度很高
		*/
		/**
		可以采用单调栈的思想，会将时间复杂度降到O(m)
		首先想一下上面的暴力的解法，也就是滑动窗口的思想,我们考虑前缀和数组sums[i]
		最大的子段和可以表示为：
		p[i] = max{sum[i] - sum[j] , for i in [i - m,i - 1]};
		ans = max{p[i] for i in [0,n]}
		将上面的p[i]的计算修改称为
		p[i] =  sum[i] - min{sum[j]} for j in [i-m,i-1];
		显然，sum[i] 是一个定值，所以p[i] 由sum[j]的最小值确定，
		于是，我们要想办法在优于O(m)时间内找到最小的sum[j],达到最优的时候sum[j]满足
		- sum[j] <= sum[x] for x in [i-m,i-1] and x != j
		- j in [i-m,i-1]
		所以思想就是创建一个单调递增队列，队列的队首元素永远是满足条件j in [i-m,i-1]区间中sum[j]的最小值
		*/
		int[] sums = new int[n+1];
		// 首先构造前缀和数组
		for(int i = 0;i<n;i++){
			sums[i+1] = cakes[i];
			sums[i+1] = sums[i] + sums[i+1];
		}
		Deque<Integer> deque = new LinkedList<>();
		int ans = 0;
		// 维护一个单调队列,单调递增队列
		for(int i = 0;i<=n;i++){
			// 如果此时单调队列中有元素，并且单调队列队首的元素不在i-m范围之内
			while(!deque.isEmpty() && deque.getFirst() + m < i){
				deque.removeFirst();
			}
			// 如果此时单调队列中有元素，计算
			if(!deque.isEmpty()){
				ans = Math.max(ans,sums[i] - sums[deque.getFirst()]);
			} 
			// 如果此时单调队列中有元素，并且单调队列中最后一个index对应的sum[index]不小于当前的sum[i]则从单调队列中删除该索引
			// 思考：如果sums[j] > sums[j+1] 	for j+1 in [i-m,i-1] , 则sums[j]是没有意义的，因为不会用它了
			while(!deque.isEmpty() && sums[deque.getLast()] >= sums[i]){
				deque.removeLast();
			}
			deque.addLast(i);
		}
		System.out.println(ans);
		
	}
}
```

## Question 3

>  题目：有一个文本编辑器，初始状态为空，请你设计将编辑器中的内容变为N个相同的字符串需要多少时间，只能通过一下操作编辑文本：
>
> - 把文本翻倍，用a时间
>
> - 在末尾删除一个字符，用b时间
>
> - 在末尾添加一个字符，用b时间

> 输入： 
>
> 10 3 1

> 输出：
> 8

```java
/**
题目：有一个文本编辑器，初始状态为空，请你设计将编辑器中的内容变为N个相同的字符串需要多少时间，只能通过一下操作编辑文本：
- 把文本翻倍，用a时间
- 在末尾删除一个字符，用b时间
- 在末尾添加一个字符，用b时间
输入： 
10 3 1
输出：
8
*/
import java.util.Scanner;
import java.util.HashMap;
class Solution{
	static long n;
	static long a;
	static long b;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		a = sc.nextInt();
		b = sc.nextInt();
		System.out.println(dfs(n));
		System.out.println(memory(n));

	}
	/**
	递归
	解题思路：就是简单的数学思想，倒着求
	目标数字，要么减到0，否则就分情况
	要是目标数字是偶数的话，就直接除以2
	要是目标数字是奇数，求一个+1，-1的最小值
	*/
	public static long dfs(long n){
		if(n == 0) return 0;
		if(n == 1) return b;
		/**
		b*n 表示直接从目标值减小到0
		dfs(n/2) + a + b * (n%2) 表示目标值是奇数，进行-1的操作 然后再减半
		dfs(n/2) + b * (n-n/2) 表示目标值是偶数，将目标值直接一个一个减到一半
		dfs((n+1)/2) + a + b * (n%2) 表示目标值是奇数，进行了+1的操作，然后再进行减半
		*/
		long res = Math.min(Math.min(dfs(n/2) + a + b * (n%2),b*n), dfs(n/2) + b*(n-n/2));
		return Math.min(res,dfs((n+1)/2) + a + b*(n%2));
	} 
	/**
	记忆化搜索
	*/
	static HashMap<Long,Long> record  = new HashMap<>();
	public static long memory(long n){
		if(n == 0) return 0;
		if(n == 1) return b;
		if(n == 2) return Math.min(a+b,b+b);
		if(record.containsKey(n)) return record.get(n);
		if(n%2 == 0){
			long res = Long.MAX_VALUE;
			if((1.0*a)/b > n/2){
				res = Math.min(res,memory(n/2) + b * n/2);
			}else{
				res = Math.min(res,memory(n/2) + a);
			}
			record.put(n,res);
			return record.get(n);
		}else{
			long res = Long.MAX_VALUE;
			res = Math.min(res,memory(n-1) + b);
			res = Math.min(res,memory(n+1) + b);
			// res = Math.min(res,memory((n+1)/2) + b + a);
			record.put(n,res);
			return record.get(n);
		}
	}
}
```



## Question 4



给你一个字符串S跟一个长度为2的字符串T,一次操作可以更改S里的一个字母，问在不超过k次操作的前提下，S中与T相同的子序列最多。 

1<=strlen(S)<=200,1<=k<=strlen(S) 

题解：dp(i,j,l）表示S的前i个字母改了j个，且有l个T[1]的最大子序列数 状态转移即可

![截屏2021-03-23 15.07.02](/Users/botak/Desktop/截屏2021-03-23 15.07.02.png)