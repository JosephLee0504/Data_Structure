import java.util.Scanner;
import java.util.Stack;

public class PopSequence {
    /**
     popsequence要求（以7为例）
     1、给定一组数为 1 2 3 4 5 6 7
     2、给定5行数，判断每一行能否按照规定的顺序出栈
     3、栈的大小为5

     解题思路：
     1、设定一变量为beginNum，从1~7循环；设置一个栈为st，将beginNum进行入栈、出栈操作
     2、取5行数中的第一行，做数组A，则A[i],i=1~7；此数组仅与beginNum进行比较，不会进出栈
     3、判断A[i]与beginNum的大小，以及栈st是否为空
     若栈st为空，或者st栈顶元素比A[i]小，并且st栈未满，则继续往st栈中添加beginNum
     若栈顶元素与A[i]相等，将栈顶元素抛出
     若栈顶元素比A[i]小，且此时栈满了，或者栈顶元素大于A[i]，则此行元素出栈顺序不会符合给定数组A，输出No
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] line = sc.nextLine().split(" ");
        int stackSize = Integer.parseInt(line[0]); // 栈的大小
        int sumOfNum = Integer.parseInt(line[1]); // 每行输入的数的个数
        int sequence = Integer.parseInt(line[2]); // 输入队列数
        boolean[] judge = new boolean[sequence]; // 队列判断结果
        Stack<Integer> st;
        int beginNum; // 进行对比的数组
        A: for (int i = 0; i < sequence; i++) {
            st = new Stack<>();
            beginNum = 1;
            line = sc.nextLine().split(" "); // 开始接收每行的数据
            for (int j = 0; j < sumOfNum; j++) {
                int temp = Integer.parseInt(line[j]);
                while (st.empty() || st.peek() < temp && (st.size() < stackSize))
                    st.push(beginNum++);
                if (st.peek() == temp) {
                    st.pop();
                    continue;
                }
                if (st.peek() < temp && (st.size() == stackSize) || st.peek() > temp) {
                    judge[i] = false;
                    continue A;
                }
            }
            judge[i] = true;
        }
        for (int i = 0; i < sequence; i++) {
            if (judge[i])
                System.out.println("Yes");
            else
                System.out.println("No");
        }
    }
}
