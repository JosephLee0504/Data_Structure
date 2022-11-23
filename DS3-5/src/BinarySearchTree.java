public class BinarySearchTree<T extends Comparable<T>> {
    private static class BinaryNode<T> {
        T element;
        BinaryNode<T> left;
        BinaryNode<T> right;
        public BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
        public BinaryNode(T element) {
            this(element, null, null);
        }
    }

    private BinaryNode<T> root;

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() { // 创建空树
        root = null;
    }

    public boolean isEmpty() { // 判断是否为空树
        return root == null;
    }

    private boolean contains(T x, BinaryNode<T> t) { // 递归查找
        if (t == null)
            return false;
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else
            return true;
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if (t != null)
            while (t.left != null)
                t = t.left;
        return t;
    }

    public T findMin() throws Exception {
        if (isEmpty())
            throw new Exception();
        return findMin(root).element;
    }

    private BinaryNode<T> findMax(BinaryNode<T> t) {
        if (t != null)
            while (t.right != null)
                t = t.right;
        return t;
    }

    public T findMax() throws Exception {
        if (isEmpty())
            throw new Exception();
        return findMax(root).element;
    }

    private BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if (t == null)
            return new BinaryNode<>(x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        return t;
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    private BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if (t == null)
            return t;
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) { // 删除的结点有左右子树
            t.element = findMin(t.right).element; // 从右子树中找最小的元素填充删除结点(也可以是从左子树中找最大的元素填充删除结点)
            t.right = remove(t.element, t.right); // 从右子树中删除最小元素
        }
        else { // 删除的结点只有左子树或右子树
            if (t.left != null)
                t = t.left;
            else
                t = t.right;
        }
        return t;
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    private void InorderTraversal(BinaryNode<T> t) {
        if (t != null) {
            InorderTraversal(t.left);
            System.out.print(t.element + " ");
            InorderTraversal(t.right);
        }
    }

    public void InorderTraversal() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            InorderTraversal(root);
    }

    private void PreorderTraversal(BinaryNode<T> t) {
        if (t != null) {
            System.out.print(t.element + " ");
            PreorderTraversal(t.left);
            PreorderTraversal(t.right);
        }
    }

    public void PreorderTraversal() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            PreorderTraversal(root);
    }

    private void PostorderTraversal(BinaryNode<T> t) {
        if (t != null) {
            PostorderTraversal(t.left);
            PostorderTraversal(t.right);
            System.out.print(t.element + " ");
        }
    }

    public void PostorderTraversal() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            PostorderTraversal(root);
    }

    private int height(BinaryNode<T> t) {
        if (t == null)
            return 0;
        else
            return 1 + Math.max(height(t.left), height(t.right));
    }

    public int height() throws Exception {
        if (isEmpty())
            throw new Exception();
        return height(root);
    }

    private void LeverorderTraversal(BinaryNode<T> t, int level) { // 层序遍历
        if (t == null || level < 1)
            return;
        if (level == 1)
            System.out.print(t.element + " ");
        LeverorderTraversal(t.left, level - 1);
        LeverorderTraversal(t.right, level - 1);
    }

    public void LeverOrderTraversal(BinaryNode<T> t) {
        if (t == null)
            return;
        int height = height(t);
        for (int i = 1; i <= height; i++) // 对树每层的结点进行遍历
            LeverorderTraversal(t, i);
    }

    private int TreeNode(BinaryNode<T> t) { // 计算叶子结点数量
        if(t == null)
            return 0;
        if(t.left == null && t.right == null)
            return 1;
        return TreeNode(t.left) + TreeNode(t.right);
    }

    public int TreeNode() {
        return TreeNode(root);
    }
}

class TestBST {
    public static void main(String[] args) {
        BinarySearchTree<Integer> i = new BinarySearchTree<>();
        i.insert(3);
        i.insert(1);
        i.insert(4);
        i.insert(7);
        i.insert(8);
        i.insert(5);
        i.insert(6);
        i.PreorderTraversal();
        System.out.println();
        i.InorderTraversal();
        System.out.println();
        i.PostorderTraversal();
        System.out.println();
        System.out.println(i.TreeNode());
    }
}