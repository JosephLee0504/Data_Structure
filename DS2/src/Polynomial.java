import java.util.Scanner;

//一元多项式乘法和除法
public class Polynomial {
    PolyNode head;
    PolyNode current;

    public Polynomial() {
        head = new PolyNode();
        current = head;
        head.next = null;
    }

    public boolean isEmpty() {
        return head.next == null;
    }

    public void insert(PolyNode node) {
        current.next = node;
        current = node;
    }

    public static int compare(int e1, int e2) {
        if(e1 > e2)
            return 1;
        else if(e1 < e2)
            return -1;
        else
            return 0;
    }

    public static Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        p1.current = p1.head.next;
        p2.current = p2.head.next;
        while (p1.current != null && p2.current != null) {
            switch (compare(p1.current.getExpo(), p2.current.getExpo())) {
                case 0:
                    result.insert(new PolyNode((int)(p1.current.getCoef() + p2.current.getCoef()), p1.current.getExpo()));
                    p1.current = p1.current.next;
                    p2.current = p2.current.next;
                    break;
                case 1:
                    result.insert(p2.current);
                    p2.current = p2.current.next;
                    break;
                case -1:
                    result.insert(p1.current);
                    p1.current = p1.current.next;
                    break;
            }
        }
        while (p1.current != null) {
            result.insert(p1.current);
            p1.current = p1.current.next;
        }
        while (p2.current != null) {
            result.insert(p2.current);
            p2.current = p2.current.next;
        }
        return result;
    }

    public static Polynomial mult(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        p1.current = p1.head.next;
        p2.current = p2.head.next;
        while (p1.current != null) {
            while (p2.current != null) {
                int coef = (int)(p1.current.getCoef() * p2.current.getCoef());
                int expo = p1.current.getExpo() + p2.current.getExpo();
                result.insert(new PolyNode(coef, expo));
                p2.current = p2.current.next;
            }
            p1.current = p1.current.next;
            p2.current = p2.head.next;
        }
        //合并同类项
        result.current = result.head.next;
        PolyNode tempPrevious = result.current;
        PolyNode temp = result.current.next;
        while (result.current.next != null) {
            while (temp != null) {
                if (temp.getExpo() != result.current.getExpo()) {
                    temp = temp.next;
                    tempPrevious = tempPrevious.next;
                }
                else {
                    result.current.setCoef((int)(result.current.getCoef() + temp.getCoef()));
                    tempPrevious.next = temp.next;
                    temp = temp.next;
                }
            }
            result.current = result.current.next;
            tempPrevious = result.current;
            temp = result.current.next;
        }
        return result;
    }

    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(); //多项式p1
        p1.insert(new PolyNode(10,1000));
        p1.insert(new PolyNode(5,14));
        p1.insert(new PolyNode(1,0));
        p1.current = p1.head.next;
        System.out.print("第一个多项式: ");
        while (p1.current != null) {
            System.out.print(p1.current.getCoef() + " " + p1.current.getExpo() + " ");
            p1.current = p1.current.next;
        }
        System.out.println();

        Polynomial p2 = new Polynomial();//多项式p2
        p2.insert(new PolyNode(3,1990));
        p2.insert(new PolyNode(-2,1492));
        p2.insert(new PolyNode(11,1));
        p2.insert(new PolyNode(5,0));
        p2.current = p2.head.next;
        System.out.print("第二个多项式: ");
        while (p2.current != null) {
            System.out.print(p2.current.getCoef() + " " + p2.current.getExpo() + " ");
            p2.current = p2.current.next;
        }
        System.out.println();

        Polynomial addResult = add(p1, p2);
        addResult.current = addResult.head.next;
        System.out.println("多项式相加:");
        while (addResult.current != null) {
            System.out.print(addResult.current.getCoef() + " " + addResult.current.getExpo() + " ");
            addResult.current = addResult.current.next;
        }
        System.out.println();

        Polynomial multResult = mult(p1, p2);
        multResult.current = multResult.head.next;
        System.out.println("多项式相乘");
        while (multResult.current != null) {
            System.out.print(multResult.current.getCoef() + " " + multResult.current.getExpo() + " ");
            multResult.current = multResult.current.next;
        }
        System.out.println();
    }
}

class PolyNode {
    private int coef;
    private int expo;
    PolyNode next;
    public PolyNode() {}
    public PolyNode(int coef, int expo) {
        this.coef = coef;
        this.expo = expo;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public int getCoef() {
        return coef;
    }

    public void setExpo(int expo) {
        this.expo = expo;
    }

    public int getExpo() {
        return expo;
    }
}


