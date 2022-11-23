//合并有序链表
public class SortForLink {
    private static class Node {
        private int data;
        private Node next;
        public Node(int data) {
            this.data = data;
        }
    }

    public static Node mergeNode(Node head1, Node head2) {
        if (head1 == null && head2 == null)
            return null;
        else if (head1 == null)
            return head2;
        else if (head2 == null)
            return head1;
        //合并后的列表
        Node head = null;
        if (head1.data > head2.data) {
            head = head2;
            head.next = mergeNode(head1, head2.next);
        }
        else {
            head = head1;
            head.next = mergeNode(head1.next, head2);
        }
        return head;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node3;
        node3.next = node5;
        node2.next = node4;
        Node node = mergeNode(node1, node2);
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }
}

