public class AllPairsShortestPaths {
    private int edgeNum; // 边的数量
    private char[] Vertex; // 顶点集合
    private int[][] Matrix; // 邻接矩阵
    private static final int INF = Integer.MAX_VALUE; // 最大值

    public AllPairsShortestPaths(char[] vexs, int[][] matrix) {
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
        edgeNum = 0;
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (Matrix[i][j] != INF)
                    edgeNum++;
            }
        }
    }

    /*
     * floyd算法求多源最短路径，即统计图中各个顶点间的最短路径。
     *
     *     path -- 路径。path[i][j]=k表示，"顶点i"到"顶点j"的最短路径会经过顶点k。
     *     dist -- 长度数组。即，dist[i][j]=sum表示，"顶点i"到"顶点j"的最短路径的长度是sum。
     */
    public void floyd(int[][] path, int[][] dist) throws Exception {
        // 初始化
        for (int i = 0; i < Vertex.length; i++) {
            for (int j = 0; j < Vertex.length; j++) {
                dist[i][j] = Matrix[i][j];    // "顶点i"到"顶点j"的路径长度为"i到j的权值"。
                path[i][j] = j;                // "顶点i"到"顶点j"的最短路径是经过顶点j。
            }
        }
        // 计算最短路径
        for (int k = 0; k < Vertex.length; k++) {
            for (int i = 0; i < Vertex.length; i++) {
                for (int j = 0; j < Vertex.length; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            if (i == j && dist[i][j] < 0) {
                                System.out.print("发现负值圈，不符合floyd算法");
                                throw new Exception();
                            }
                            path[i][j] = k;
                        }
                    }
                }
            }
        }
        // 打印floyd最短路径的结果
        System.out.println("floyd:");
        for (int i = 0; i < Vertex.length; i++) {
            for (int j = 0; j < Vertex.length; j++)
                System.out.printf("%2d  ", dist[i][j]);
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F'};
        int[][] matrix = {
                {0, 2, 5, 30, INF, INF}, {2, 0, 15, INF, 8, INF}, {5, 15, 0, INF, INF, 7},
                {30, INF, INF, 0, 4, 10}, {INF, 8, INF, 4, 0, 18}, {INF, INF, 7, 10, 18, 0}
        };
        AllPairsShortestPaths graph = new AllPairsShortestPaths(vexs, matrix);
        int[][] path = new int[graph.Vertex.length][graph.Vertex.length];
        int[][] floy = new int[graph.Vertex.length][graph.Vertex.length];
        graph.floyd(path, floy);
    }
}
