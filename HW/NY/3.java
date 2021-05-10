import java.util.HashMap;
/**
idea use a hashmap to record character <key,value> 
key : character
value : the max right position occured
*/
class Solution{
	public static void main(String[] args) {
		String[] strs = new String[]{"abcabcbb","bbbbb","pwwkew"};
		for(String str:strs) System.out.println(maxLength(str));
		
	}
	public static int maxLength(String str){
		HashMap<Character,Integer> map = new HashMap<>();
		int max = 1;
		int start = 0;
		for(int end = 0;end<str.length();end++){
			if(map.containsKey(str.charAt(end))){
				start = Math.max(start,map.get(str.charAt(end))+1);
			}
			max = Math.max(max,end-start+1);
			map.put(str.charAt(end),end);
		}
		return max;
	}
}