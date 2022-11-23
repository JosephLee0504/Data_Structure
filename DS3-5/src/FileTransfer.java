import java.util.Scanner;

public class FileTransfer {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] S = new int[n];
        for(int i = 0; i < n; i++) {
            S[i] = -1; // 默认集合元素全部初始化为-1
        }
        char ch;
        do {
            ch = in.next().charAt(0);
            switch (ch){
                case 'C':
                    check_connections(S,in);
                    break;
                case 'I':
                    input_connections(S,in);
                    break;
                case 'S':
                    check_network(S);
                    break;
            }
        }while (ch!='S');
    }

    private static void check_network(int[] s) {
        int count=0;
        for(int i = 0; i < s.length; i++) {
            if(s[i] < 0)
                count++;
        }
        if(count==1)
            System.out.println("The network is connected.");
        else
            System.out.println("There are "+count+" components.");
    }

    private static void input_connections(int[] s, Scanner in) {
        int num1 = in.nextInt();
        int num2 = in.nextInt();
        int root1 = find(s,num1);
        int root2 = find(s,num2);
        if(root1!=root2) {
            if(Math.abs(s[root1])<=Math.abs(s[root2])) { // 如果集合2大，则把集合1并入集合2
                s[root2] += s[root1];
                s[root1] = root2;
            }
            else {
                s[root1] += s[root2];
                s[root2] = s[root1];
            }
        }
    }

    private static void check_connections(int[] s,Scanner in) {
        int num1 = in.nextInt();
        int num2 = in.nextInt();
        int root1 = find(s,num1);
        int root2 = find(s,num2);
        if(root1==root2)
            System.out.println("yes");
        else
            System.out.println("no");
    }

    private static int find(int[] s, int num) { // 返回根节点在数组中的位置下标
        // 路径压缩: 递归把i的父节点的值s[i]设置为对其父结点s[x]执行find的结果，并且返回更新的父结点值；直到i本身时集合的根，被find返回上一层
        // find把当前结点的父结点设置成根结点，即把当前元素直接变成根结点的孩子，并一路返回根结点的值
		int i = num-1;
		if(s[i]<0)
			return i;
		else
			return s[i]=find(s,s[i] + 1);
    }
}
