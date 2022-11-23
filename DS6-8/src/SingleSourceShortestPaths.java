public class SingleSourceShortestPaths {
    private int EdgeNum; // 边的数量
    private char[] Vertex; // 顶点集合
    private int[][] Matrix; // 邻接矩阵
    private static final int INF = Integer.MAX_VALUE; // 最大值

    public SingleSourceShortestPaths(char[] vexs, int[][] matrix) {
        int vlen = vexs.length; // 初始化”顶点数“和”边数“
        // 初始化顶点
        Vertex = new char[vlen];
        for (int i = 0; i< Vertex.length; i++)
            Vertex[i] = vexs[i];
        // 初始化边
        Matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++)
                Matrix[i][j] = matrix[i][j];
        }
        // 统计边
        EdgeNum = 0;
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (Matrix[i][j] != INF)
                    EdgeNum++;
            }
        }
    }

    /*
     * Dijkstra算法求单源最短路径，即统计图中"顶点vs"到其它各个顶点的最短路径。
     *
     *       vs -- 起始顶点(start vertex)。即计算"顶点vs"到其它顶点的最短路径。
     *     prev -- 前驱顶点数组。即，prev[i]的值是"顶点vs"到"顶点i"的最短路径所经历的全部顶点中，位于"顶点i"之前的那个顶点。
     *     dist -- 长度数组。即，dist[i]是"顶点vs"到"顶点i"的最短路径的长度。
     */
    public void dijkstra(int vs, int[] prev, int[] dist) throws Exception {
        // flag[i]=true表示"顶点vs"到"顶点i"的最短路径已成功获取
        boolean[] flag = new boolean[Vertex.length];
        // 初始化
        for (int i = 0; i < Vertex.length; i++) {
            flag[i] = false;          // 顶点i的最短路径还没获取到。
            prev[i] = 0;              // 顶点i的前驱顶点为0。
            dist[i] = Matrix[vs][i];  // 顶点i的最短路径为"顶点vs"到"顶点i"的权。
        }
        // 对"顶点vs"自身进行初始化
        flag[vs] = true;
        dist[vs] = 0;
        // 遍历mVexs.length-1次；每次找出一个顶点的最短路径。
        int k = 0;
        for (int i = 1; i < Vertex.length; i++) {
            // 寻找当前最小的路径，即在未获取最短路径的顶点中，找到离vs最近的顶点k。
            int min = INF;
            for (int j = 0; j < Vertex.length; j++) {
                if (!flag[j] && dist[j]<min) {
                    min = dist[j];
                    k = j;
                }
            }
            // 标记"顶点k"为已经获取到最短路径
            flag[k] = true;
            // 修正当前最短路径和前驱顶点，即当已经获取"顶点k的最短路径"之后，更新"未获取最短路径的顶点的最短路径和前驱顶点"。
            for (int j = 0; j < Vertex.length; j++) {
                if (Matrix[k][j] < INF) {
                    if (Matrix[k][j] < 0) {
                        System.out.println("存在负边，不符合Dijkstra算法要求");
                        throw new Exception();
                    }
                    if (dist[k] + Matrix[k][j] < dist[j]) {
                        dist[j] = dist[k] + Matrix[k][j];
                        prev[j] = k;
                    }
                }
            }
        }
        // 打印dijkstra最短路径的结果
        System.out.printf("dijkstra(%c): \n", Vertex[vs]);
        for (int i=0; i < Vertex.length; i++)
            System.out.printf("shortest(%c, %c)=%d\n", Vertex[vs], Vertex[i], dist[i]);
    }

    public static void main(String[] args) throws Exception {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F'};
        int[][] matrix={
                {0,INF,5,30,INF,INF},{2,0,INF,INF,8,INF},{INF,15,0,INF,INF,7},
                {INF,INF,INF,0,INF,INF},{INF,INF,INF,4,0,INF},{INF,INF,INF,10,18,0}
        };
        SingleSourceShortestPaths graph = new SingleSourceShortestPaths(vexs, matrix);
        int[] prev = new int[graph.Vertex.length];
        int[] dist = new int[graph.Vertex.length];
        graph.dijkstra(0, prev, dist);
    }
}
