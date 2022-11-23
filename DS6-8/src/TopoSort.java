import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopoSort {
    // 邻接点的定义
    private class ENode {
        int AdjV;       // 邻接点的数据
        ENode Next; // 指向下一个邻接点的指针
    }

    // 顶点表头结点的定义
    private class VNode {
        char data;          // 存顶点的数据
        ENode firstEdge;    // 边表头指针
    };

    private List<VNode> mVexs;  // 顶点数组

    public TopoSort(char[] vexs, char[][] edges) { // 创建图：vexs--顶点数组，edges--边数组
        // 初始化"顶点数"和"边数"
        int vlen = vexs.length;
        int elen = edges.length;
        // 初始化"顶点"
        mVexs = new ArrayList<VNode>();
        for (int i = 0; i < vlen; i++) {
            // 新建VNode
            VNode vnode = new VNode();
            vnode.data = vexs[i];
            vnode.firstEdge = null;
            // 将vnode添加到数组mVexs中
            mVexs.add(vnode);
        }

        // 初始化"边"
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            char c1 = edges[i][0];
            char c2 = edges[i][1];
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(edges[i][0]);
            int p2 = getPosition(edges[i][1]);
            // 初始化邻接点node1
            ENode node1 = new ENode();
            node1.AdjV = p2;
            // 将node1链接到"p1所在链表的末尾"
            if(mVexs.get(p1).firstEdge == null)
                mVexs.get(p1).firstEdge = node1;
            else
                linkLast(mVexs.get(p1).firstEdge, node1);
        }
    }

    private void linkLast(ENode list, ENode node) { // 将node结点链接到list的最后
        ENode p = list;
        while(p.Next != null)
            p = p.Next;
        p.Next = node;
    }

    private int getPosition(char ch) { // 返回ch的位置
        for(int i = 0; i < mVexs.size(); i++)
            if(mVexs.get(i).data == ch)
                return i;
        return -1;
    }

    public void topologicalSort() {
        int index = 0;
        int num = mVexs.size();
        int[] ins = new int[num];               // 入度数组
        char[] tops = new char[num];             // 拓扑排序结果数组，记录每个节点的排序后的序号。
        Queue<Integer> queue = new LinkedList<>(); // 辅助队列，将入度为0的顶点插入队列；当队列不为空时，取出一个顶点v并输出；
                                                   // 然后将与v相邻的顶点入度减1；当产生新的入度为0的顶点时，将其插入队列
        // 统计每个顶点的入度数
        for(int i = 0; i < num; i++) {
            ENode node = mVexs.get(i).firstEdge;
            while (node != null) {
                ins[node.AdjV]++;
                node = node.Next;
            }
        }
        // 将所有入度为0的顶点入队列
        for(int i = 0; i < num; i ++)
            if(ins[i] == 0)
                queue.offer(i);                 // 入队列
        while (!queue.isEmpty()) {              // 队列非空
            int j = queue.poll(); // 出队列。j是顶点的序号
            tops[index++] = mVexs.get(j).data;  // 将该顶点添加到tops中，tops是排序结果
            ENode node = mVexs.get(j).firstEdge;// 获取以该顶点为起点的出边队列
            // 将与"node"关联的节点的入度减1；
            // 若减1之后，该节点的入度为0；则将该节点添加到队列中。
            while(node != null) {
                // 将节点(序号为node.AdjV)的入度减1。
                ins[node.AdjV]--;
                // 若节点的入度为0，则将其"入队列"
                if( ins[node.AdjV] == 0)
                    queue.offer(node.AdjV);    // 入队列
                node = node.Next;
            }
        }
        if(index != num) {
            System.out.println("Graph has a cycle");
        }
        // 打印拓扑排序结果
        System.out.print("TopSort: ");
        for(int i = 0; i < num; i++)
            System.out.printf("%c ", tops[i]);
        System.out.println();
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F'};
        char[][] edges = {{'A', 'C'}, {'A', 'D'}, {'B', 'E'}, {'C', 'B'},
                {'C', 'F'}, {'E', 'D'}, {'F', 'D'}, {'F', 'E'}};
        TopoSort graph = new TopoSort(vexs, edges);
        graph.topologicalSort();
    }
}
