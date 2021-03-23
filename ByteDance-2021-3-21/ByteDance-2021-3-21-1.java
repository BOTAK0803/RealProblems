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