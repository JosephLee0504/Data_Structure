public class AvlTree<T extends Comparable<T>> {
    private static class AvlNode<T> {
        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        int height;
        public AvlNode(T element, AvlNode<T> left, AvlNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    private AvlNode<T> root;

    public AvlTree() {
        root = null;
    }

    public void makeEmpty() { // 创建空树
        root = null;
    }

    public boolean isEmpty() { // 判断是否为空树
        return root == null;
    }

    private boolean contains(T x, AvlNode<T> t) { // 递归查找
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

    private AvlNode<T> findMin(AvlNode<T> t) {
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

    private AvlNode<T> findMax(AvlNode<T> t) {
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

    private AvlNode<T> RRrotate(AvlNode<T> k1) { // RR旋转：更新k1和k2的高度，返回新的根结点k2
        AvlNode<T> k2 = k1.right;
        k1.right = k2.left; // 若k2原来存在左子树，则将它变为k1的右子树
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        return k2;
    }

    private AvlNode<T> LLrotate(AvlNode<T> k1) { // LL旋转：更新k1和k2的高度，返回新的根结点k2
        AvlNode<T> k2 = k1.left;
        k1.left = k2.right;
        k2.right = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        return k2;
    }

    private AvlNode<T> LRrotate(AvlNode<T> k1) {
        k1.left = RRrotate(k1.left); // k2与k3做RR旋转，返回k3
        return LLrotate(k1); // k1和k3做LL旋转，返回k3
    }

    private AvlNode<T> RLrotate(AvlNode<T> k1) {
        k1.right = LLrotate(k1.right); // k2与k3做LL旋转，返回k3
        return RRrotate(k1); // k1与k3做RR旋转，返回k3
    }

    private AvlNode<T> balance(AvlNode<T> t) {
        if (t == null)
            return t;
        if (height(t.left) - height(t.right) > 1) {
            if (height(t.left.left) >= height(t.left.right))
                t = LLrotate(t);
            else
                t = LRrotate(t);
        }
        else if (height(t.right) - height(t.left) > 1) {
            if (height(t.right.right) >= height(t.right.left))
                t = RRrotate(t);
            else
                t = RLrotate(t);
        }
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private AvlNode<T> insert(T x, AvlNode<T> t) {
        if (t == null)
            return new AvlNode<>(x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        return balance(t);
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    private AvlNode<T> remove(T x, AvlNode<T> t) {
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
        return balance(t);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    private void InorderTraversal(AvlNode<T> t) {
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

    private void PreorderTraversal(AvlNode<T> t) {
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

    private void PostorderTraversal(AvlNode<T> t) {
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

    private int height(AvlNode<T> t) {
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

    private void LeverorderTraversal(AvlNode<T> t, int level) { // 层序遍历
        if (t == null || level < 1)
            return;
        if (level == 1)
            System.out.print(t.element + " ");
        LeverorderTraversal(t.left, level - 1);
        LeverorderTraversal(t.right, level - 1);
    }

    public void LeverOrderTraversal(AvlNode<T> t) {
        if (t == null)
            return;
        int height = height(t);
        for (int i = 1; i <= height; i++) // 对树每层的结点进行遍历
            LeverorderTraversal(t, i);
    }

    private int TreeNode(AvlNode<T> t) { // 计算叶子结点数量
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

class TestAVL {
    public static void main(String[] args) {
        AvlTree<Integer> i = new AvlTree<>();
        i.insert(1);
        i.insert(2);
        i.insert(3);
        i.insert(4);
        i.insert(5);
        i.insert(6);
        i.insert(7);
        i.PreorderTraversal();
        System.out.println();
        i.InorderTraversal();
        System.out.println();
        i.PostorderTraversal();
        System.out.println();
        System.out.println(i.TreeNode());
    }
}
