import java.util.Scanner;

public class MaxSubseqSumList {
    public static void MaxSubseqSum(int List[], int N) {
        int i;
        int ThisSum, MaxSum, Head, Tail, TempHead;
        ThisSum = MaxSum = Head = Tail = TempHead = 0;
        for (i = 0; i < N; i++) {
            ThisSum += List[i];
            if(ThisSum > MaxSum) {
                MaxSum = ThisSum;
                Tail = i;
                Head = TempHead;
            }
            else if (ThisSum < 0) {
                ThisSum = 0;
                TempHead = i + 1;//注意这时不能改变真正的头，只有发现更大的和更新序列时才会改变头和尾
            }
        }
        System.out.println(MaxSum + " " + List[Head] + " " + List[Tail]);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] list = new int[n];
        for (int i = 0; i < n; i++) {
            list[i] = sc.nextInt();
        }
        MaxSubseqSum(list, n);
    }
}
