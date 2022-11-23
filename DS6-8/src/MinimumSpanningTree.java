public class MinimumSpanningTree {
    private int edgeNum; // 边的数量
    private char[] Vertex; // 顶点集合
    private int[][] Matrix; // 邻接矩阵
    private static final int INF = Integer.MAX_VALUE; // 最大值

    public MinimumSpanningTree(char[] vexs, int[][] matrix) {
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

    private int getPosition(char ch) { // 返回ch的位置
        for(int i = 0; i < Vertex.length; i++)
            if(Vertex[i] == ch)
                return i;
        return -1;
    }

    private static class EData {
        char start; // 边的起点
        char end;   // 边的终点
        int weight; // 边的权重
        public EData(char start, char end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

    private EData[] getEdges() { // 获取图中的边
        int index = 0;
        EData[] edges;
        edges = new EData[edgeNum];
        for (int i = 0; i < Vertex.length; i++) {
            for (int j = i + 1; j < Vertex.length; j++) {
                if (Matrix[i][j] != INF) {
                    edges[index++] = new EData(Vertex[i], Vertex[j], Matrix[i][j]);
                }
            }
        }
        return edges;
    }

    private void sortEdges(EData[] edges, int elen) { // 对边按照权值大小进行排序(由小到大)
        for (int i = 0; i < elen; i++) {
            for (int j = i + 1; j < elen; j++) {
                if (edges[i].weight > edges[j].weight) {
                    EData tmp = edges[i];
                    edges[i] = edges[j];
                    edges[j] = tmp;
                }
            }
        }
    }

    /*
     * prim最小生成树
     *
     *   start -- 从图中的第start个元素开始，生成最小树
     */
    public void prim(int start) {
        int num = Vertex.length; // 顶点个数
        int index = 0; //prim最小树的索引，即prims数组的索引
        char[] prims  = new char[num]; // prim最小树的结果数组
        int[] weights = new int[num]; // 顶点间边的权值
        // prim最小生成树中第一个数是"图中第start个顶点"，因为是从start开始的。
        prims[index++] = Vertex[start];
        // 初始化"顶点的权值数组"，将每个顶点的权值初始化为"第start个顶点"到"该顶点"的权值。
        for (int i = 0; i < num; i++ )
            weights[i] = Matrix[start][i];
        for (int i = 0; i < num; i++) {
            // 由于从start开始的，因此不需要再对第start个顶点进行处理。
            if(start == i)
                continue;
            int k = 0;
            int min = INF;
            // 在未被加入到最小生成树的顶点中，找出权值最小的顶点。
            for (int j = 0; j < num; j++){
                // 若weights[j]=0，意味着"第j个节点已经被排序过"(或者说已经加入了最小生成树中)。
                if (weights[j] != 0 && weights[j] < min) {
                    min = weights[j];
                    k = j;
                }
            }
            // 经过上面的处理后，在未被加入到最小生成树的顶点中，权值最小的顶点是第k个顶点。
            // 将第k个顶点加入到最小生成树的结果数组中
            prims[index++] = Vertex[k];
            // 将"第k个顶点的权值"标记为0，意味着第k个顶点已经排序过了(或者说已经加入了最小树结果中)。
            weights[k] = 0;
            // 当第k个顶点被加入到最小生成树的结果数组中之后，更新其它顶点的权值。
            for (int j = 0; j < num; j++) {
                // 当第j个节点没有被处理，并且需要更新时才被更新。
                if (weights[j] != 0 && Matrix[k][j] < weights[j])
                    weights[j] = Matrix[k][j];
            }
        }
        // 计算最小生成树的权值
        int sum = 0;
        for (int i = 1; i < index; i++) {
            int min = INF;
            // 获取prims[i]在Matrix中的位置
            int n = getPosition(prims[i]);
            // 在Vertex[0-i]中，找出到j的权值最小的顶点。
            for (int j = 0; j < i; j++) {
                int m = getPosition(prims[j]);
                if (Matrix[m][n] < min)
                    min = Matrix[m][n];
            }
            sum += min;
        }
        // 打印最小生成树
        System.out.printf("PRIM(%c)= %d: ", Vertex[start], sum);
        for (int i = 0; i < index; i++)
            System.out.printf("%c ", prims[i]);
        System.out.printf("\n");
    }

    private int getEnd(int[] vends, int i) { // 获取i的终点
        while (vends[i] != 0)
            i = vends[i];
        return i;
    }

    public void kruskal() {
        int index = 0;                      // rets数组的索引
        int[] vends = new int[edgeNum];     // 用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
        EData[] rets = new EData[edgeNum];  // 结果数组，保存kruskal最小生成树的边
        EData[] edges;                      // 图对应的所有边
        // 获取"图中所有的边"
        edges = getEdges();
        // 将边按照"权"的大小进行排序(从小到大)
        sortEdges(edges, edgeNum);
        for (int i = 0; i < edgeNum; i++) {
            int p1 = getPosition(edges[i].start);      // 获取第i条边的"起点"的序号
            int p2 = getPosition(edges[i].end);        // 获取第i条边的"终点"的序号
            int m = getEnd(vends, p1);                 // 获取p1在"已有的最小生成树"中的终点
            int n = getEnd(vends, p2);                 // 获取p2在"已有的最小生成树"中的终点
            // 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
            if (m != n) {
                vends[m] = n;                       // 设置m在"已有的最小生成树"中的终点为n
                rets[index++] = edges[i];           // 保存结果
            }
        }
        // 统计并打印"kruskal最小生成树"的信息
        int length = 0;
        for (int i = 0; i < index; i++)
            length += rets[i].weight;
        System.out.printf("Kruskal= %d: ", length);
        for (int i = 0; i < index; i++)
            System.out.printf("(%c,%c) ", rets[i].start, rets[i].end);
        System.out.printf("\n");
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F'};
        int[][] matrix = {
                {0, 2, 5, 30, INF, INF}, {2, 0, 15, INF, 8, INF}, {5, 15, 0, INF, INF, 7},
                {30, INF, INF, 0, 4, 10}, {INF, 8, INF, 4, 0, 18}, {INF, INF, 7, 10, 18, 0}
        };
        MinimumSpanningTree graph = new MinimumSpanningTree(vexs, matrix);
        graph.prim(0);
        graph.kruskal();
    }
}
