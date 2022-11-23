/*-- 递归求链表反转
public class ReverseLink {
    private static class Node {
        private int data;
        private Node next;
        public Node(int data) {
            this.data = data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }
    }

    public static Node reverseLink(Node head) {
        if (head == null || head.next == null)
            return head;
        Node next = head.next;
        Node new_head = reverseLink(next);
        next.next = head;
        head.next = null;
        return new_head;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node1.next.next = node3;
        node1.next.next.next = node4;
        node1.next.next.next.next = node5;
        Node node = reverseLink(node1);
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }
}*/

//非递归方法求链表反转
public class ReverseLink {
    private static class Node {
        private int data;
        private Node next;
        public Node(int data) {
            this.data = data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }
    }

    public static Node reverseLink(Node head) {
        if (head == null && head.getNext() == null)
            return head;
        Node prevNode = null;
        Node curNode = head;
        Node nextNode = null;
        while (curNode != null) {
            nextNode = curNode.getNext(); // nextNode指向下一个节点
            curNode.setNext(prevNode); // 将当前节点next域指向前一个节点
            prevNode = curNode; // preNode指针向后移动
            curNode = nextNode; // curNode指针向后移动
        }
        return prevNode;
    }
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node1.next.next = node3;
        node1.next.next.next = node4;
        node1.next.next.next.next = node5;
        Node node = reverseLink(node1);
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }
}