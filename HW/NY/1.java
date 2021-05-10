/**
my ided: double pointer
*/
class Solution{
	static char[] cs;
	public static void main(String[] args) {
		String str = "the Noah’s Ark Lab is located in Hong Kong and Shenzhen";
		cs = str.toCharArray();
		int n = str.length();
		int i = 0;
		int j = 0;
		while(i<n&&j<n){
			while(j<n && cs[j]!=' ') j++;
			reverse(cs,i,j-1);
			j++;
			i = j;
		}
		System.out.println(String.valueOf(cs));
		
	}
	public static void reverse(char[] cs,int i,int j){
		while(i<j){
			char c = cs[i];
			cs[i] = cs[j];
			cs[j] = c;
			i++;
			j--;
		}
	}
}