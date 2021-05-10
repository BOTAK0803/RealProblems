/**
my idea : dfs + pruning
*/
class Solution{
	public static class TreeNode{
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(){};
		TreeNode(int val){this.val = val;};
		TreeNode(int val,TreeNode left,TreeNode right){
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
	static boolean res = false;
	public static void main(String[] args) {
		
		TreeNode g = new TreeNode(7);
		TreeNode h = new TreeNode(2);
		TreeNode i = new TreeNode(1);
		TreeNode d = new TreeNode(11,g,h);
		TreeNode e = new TreeNode(13);
		TreeNode f = new TreeNode(4,null,i);
		TreeNode b = new TreeNode(4,d,null);
		TreeNode c = new TreeNode(8,e,f);
		TreeNode a = new TreeNode(5,b,c);
		dfs(a,0,22);
		System.out.println(res);
		
	}
	public static void dfs(TreeNode root,int sum,int target){
		if(res) return;
		if(root == null) return;
		if(root.left == null && root.right == null){
			if(sum+root.val == target) res = true;
			return;
		}
		if(root.left != null) dfs(root.left,sum+root.val,target);
		if(root.right!= null) dfs(root.right,sum+root.val,target);
	}
}