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