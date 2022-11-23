import java.util.Scanner;

public class MaxSubseqSum {
    public static int MaxSubseqSum(int List[], int N) {
        int i;
        int ThisSum, MaxSum;
        ThisSum = MaxSum = 0;
        for (i = 0; i < N; i++) {
            ThisSum += List[i];
            if(ThisSum > MaxSum)
                MaxSum = ThisSum;
            else if (ThisSum < 0) {
                ThisSum = 0;
            }
        }
        return MaxSum;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] list = new int[n];
        for (int i = 0; i < n; i++) {
            list[i] = sc.nextInt();
        }
        System.out.println(MaxSubseqSum(list, n));
    }
}
