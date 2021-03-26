/**
有一个推箱子的游戏, 一开始的情况如下图:


上图中, '.' 表示可到达的位置, '#' 表示不可到达的位置，其中 S 表示你起始的位置, 0表示初始箱子的位置, E表示预期箱子的位置，你可以走到箱子的上下左右任意一侧, 将箱子向另一侧推动。如下图将箱子向右推动一格;
..S0.. -> ...S0.
注意不能将箱子推动到'#'上, 也不能将箱子推出边界;
现在, 给你游戏的初始样子, 你需要输出最少几步能够完成游戏, 如果不能完成, 则输出-1。
输入描述:
第一行为2个数字,n, m, 表示游戏盘面大小有n 行m 列(5< n, m < 50);
后面为n行字符串,每行字符串有m字符, 表示游戏盘面;
输出描述:
一个数字,表示最少几步能完成游戏,如果不能,输出-1;
*/
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
public class Main{
    public static void main(String[] args){
        solve();
        
    }
    public static void solve(){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();
        String[] input = new String[n];
        for(int i = 0;i<n;i++) input[i] = sc.nextLine();
        char[][] graph = new char[n][m];
        for(int i = 0;i<n;i++) for(int j = 0;j<m;j++) graph[i][j] = input[i].charAt(j);
        bfs(n,m,graph);
    }
    public static void bfs(int n,int m,char[][] graph){
        int[][][][] arr = new int[n][m][n][m];
        Queue<int[]> queue = new LinkedList<>();
        int sX = 0,sY = 0,bX = 0,bY = 0,eX = 0,eY = 0;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(graph[i][j] == 'S'){
                    sX = i;
                    sY = j;
                }
                if(graph[i][j] == '0'){
                    bX = i;
                    bY = j;
                }
                if(graph[i][j] == 'E'){
                    eX = i;
                    eY = j;
                }
            }
        }
        queue.offer(new int[]{sX,sY,bX,bY});
        while(queue.peek()!=null){
            int[] poll = queue.poll();
            int sNowX = poll[0];
            int sNowY = poll[1];
            int bNowX = poll[2];
            int bNowY = poll[3];
            int[] theX = new int[]{-1,0,1,0};
            int[] theY = new int[]{0,1,0,-1};
            for(int i = 0;i<4;i++){
                int snx = sNowX + theX[i];
                int sny = sNowY + theY[i];
                int bnx = snx + theX[i];
                int bny = sny + theY[i];
                if(snx >=0&&sny>=0&&snx<n&&sny<m&&graph[snx][sny]!='#'&&(snx!=bNowX || sny!=bNowY)&&(arr[snx][sny][bNowX][bNowY]==0)){
                    arr[snx][sny][bNowX][bNowY] = arr[sNowX][sNowY][bNowX][bNowY] + 1;
                    queue.offer(new int[]{snx,sny,bNowX,bNowY});
                }else if(snx == bNowX && sny == bNowY && bnx >= 0 && bny >= 0 && bnx < n && bny < m && graph[bnx][bny]!='#' && arr[snx][sny][bnx][bny] == 0){
                    arr[snx][sny][bnx][bny] = arr[sNowX][sNowY][snx][sny] + 1;
                    if(bnx == eX && bny == eY){
                        System.out.println(arr[snx][sny][bnx][bny]);
                        return;
                    }
                    queue.offer(new int[]{snx,sny,bnx,bny});
                }
            }
        }
        System.out.println(-1);
    }
}