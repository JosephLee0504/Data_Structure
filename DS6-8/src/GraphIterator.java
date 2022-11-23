import java.io.IOException;
import java.util.Scanner;

public class GraphIterator {
    private int edgeNum; // 边的数量
    private char[] Vertex; // 顶点集合
    private int[][] Matrix; // 邻接矩阵
    private static final int INF = Integer.MAX_VALUE; // 最大值

    /*
     * 创建图(自己输入数据)
     */
    public GraphIterator() {

        // 输入"顶点数"和"边数"
        System.out.print("input vertex number: ");
        int vlen = readInt();
        System.out.print("input edge number: ");
        int elen = readInt();
        if ( vlen < 1 || elen < 1 || (elen > (vlen * (vlen - 1)))) {
            System.out.println("input error: invalid parameters!");
            return ;
        }

        // 初始化"顶点"
        Vertex = new char[vlen];
        for (int i = 0; i < Vertex.length; i++) {
            System.out.printf("vertex(%d): ", i);
            Vertex[i] = readChar();
        }

        // 初始化"边"
        Matrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            System.out.printf("edge(%d):", i);
            char c1 = readChar();
            char c2 = readChar();
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);

            if (p1==-1 || p2==-1) {
                System.out.println("input error: invalid edge!");
                return ;
            }

            Matrix[p1][p2] = 1;
        }
    }

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     matrix -- 边数组
     */
    public GraphIterator(char[] vexs, int[][] edge) {
        int vlen = vexs.length; // 初始化”顶点数“和”边数“
        // 初始化顶点
        Vertex = new char[vlen];
        for (int i = 0; i< Vertex.length; i++)
            Vertex[i] = vexs[i];
        // 初始化边
        Matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++)
                Matrix[i][j] = edge[i][j];
        }
        // 统计边
        edgeNum = 0;
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (Matrix[i][j] != INF)
                    edgeNum++;
            }
        }
    }

    /*
     * 返回ch位置
     */
    private int getPosition(char ch) {
        for(int i = 0; i < Vertex.length; i++)
            if(Vertex[i] == ch)
                return i;
        return -1;
    }

    /*
     * 读取一个输入字符
     */
    private char readChar() {
        char ch='0';

        do {
            try {
                ch = (char)System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(!((ch>='a'&&ch<='z') || (ch>='A'&&ch<='Z')));

        return ch;
    }

    /*
     * 读取一个输入字符
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private int firstVertex(int v) { // 返回顶点v的第一个邻接顶点的索引，失败则返回-1
        if (v < 0 || v > Vertex.length - 1)
            return -1;
        for (int i = 0; i < Vertex.length; i++) {
            if (Matrix[v][i] != 0 && Matrix[v][i] != INF)
                return i;
        }
        return -1;
    }

    private int nextVertex(int v, int w) { // 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1
        if (v < 0 || v > Vertex.length - 1 || w < 0 || w > Vertex.length - 1)
            return -1;
        for (int i = w + 1; i < Vertex.length; i++) {
            if (Matrix[v][i] != 0 && Matrix[v][i] != INF)
                return i;
        }
        return -1;
    }

    private void DFS(int v, boolean[] visited) { // 深度优先遍历图的递归实现
        visited[v] = true; // 标记i已访问
        System.out.printf("%c ", Vertex[v]);
        // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走
        for (int w = firstVertex(v); w >= 0; w = nextVertex(v, w)) {
            if (!visited[w])
                DFS(w, visited);
        }
    }

    public void DFS(int v) {
        boolean[] visited = new boolean[Vertex.length];
        for (int i = 0; i < Vertex.length; i++)
            visited[i] = false;
        System.out.print("DFS: ");
        DFS(v, visited);
        System.out.println();
    }

    public void BFS(int v) {
        int head = 0;
        int rear = 0;
        int[] queue = new int[Vertex.length]; // 辅助队列，用于把访问过的顶点依次保存起来，以便下次依次访问它们的邻接点
        boolean[] visited = new boolean[Vertex.length];
        for (int i = 0; i < Vertex.length; i++)
            visited[i] = false;
        System.out.print("BFS: ");
        visited[v] = true;
        System.out.printf("%c ", Vertex[v]);
        queue[rear++] = v;
        while (head != rear) {
            int j = queue[head++];
            for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) {
                if (!visited[k]) {
                    visited[k] = true;
                    System.out.printf("%c ", Vertex[k]);
                    queue[rear++] = k;
                }
            }
        }
        System.out.println();
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.println("Martix Graph:");
        for (int i = 0; i < Vertex.length; i++) {
            for (int j = 0; j < Vertex.length; j++)
                System.out.printf("%d ", Matrix[i][j]);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F'};
        int[][] matrix = {
                {0, 2, 5, 30, INF, INF}, {2, 0, 15, INF, 8, INF}, {5, 15, 0, INF, INF, 7},
                {30, INF, INF, 0, 4, 10}, {INF, 8, INF, 4, 0, 18}, {INF, INF, 7, 10, 18, 0}
        };
        // 自定义"图"(输入矩阵队列)
        // GraphIterator graph = new GraphIterator();
        // 采用已有的"图"
        GraphIterator graph = new GraphIterator(vexs, matrix);
        graph.print();
        graph.BFS(0);
        graph.DFS(0);
    }
}
