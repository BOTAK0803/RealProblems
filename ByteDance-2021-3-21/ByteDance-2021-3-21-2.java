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