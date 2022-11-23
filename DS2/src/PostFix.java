import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class PostFix {
    public static List<String> expressionToList(String expression) { // 将表达式转换为中缀表达式并加入list
        int index = 0;
        List<String> list = new ArrayList<>();
        while (index < expression.length()) {
            char ch = expression.charAt(index);
            if (ch < 47 || ch > 58) {
                //是操作符，则直接加入list中
                index++;
                list.add(ch + "");
            }
            else if (ch >= 47 && ch <= 58) {
                //是数字，则需判断多位数情况
                String str = "";
                while (index < expression.length() && expression.charAt(index) >= 47
                        && expression.charAt(index) <= 58) {
                    str += expression.charAt(index);
                    index++;
                }
                list.add(str);
            }
        }
        return list;
    }

    public static List<String> toPostFix(List<String> expressionList) throws Exception { // 将中缀表达式转换为后缀表达式
        Stack<String> st = new Stack<>(); // 栈用于储存操作符
        List<String> postfix = new ArrayList<>(); // list用于储存后缀表达式
        for (String item:
             expressionList) {
            if (isOperator(item)) {
                // 栈为空或者栈顶元素为左括号或者当前操作符大于栈顶操作符，则直接进栈
                if (st.isEmpty() || "(".equals(st.peek()) || priority(item) > priority(st.peek()))
                    st.push(item);
                else {
                    // 否则将栈中元素出栈入list，直到遇到大于当前操作符或者遇到左括号时
                    while (!st.isEmpty() && !"(".equals(st.peek())) {
                        if (priority(item) < priority(st.peek()))
                            postfix.add(st.pop());
                    }
                    // 当前操作符进栈
                    st.push(item);
                }
            }
            else if (isNumber(item)) // 是数字则直接进栈
                postfix.add(item);
            else if ("(".equals(item)) // 是左括号则进栈
                st.push(item);
            else if (")".equals(item)) { // 是右括号，则将栈中元素弹出进入list，直到遇到左括号，左括号出栈，但不入list
                while (!st.isEmpty()) {
                    if ("(".equals(st.peek())) {
                        st.pop();
                        break;
                    }
                    else
                        postfix.add(st.pop());
                }
            }
            else
                throw new Exception("存在非法字符!");
        }
        // 循环完毕，如果操作符栈中元素不为空，将栈中元素出栈入list
        while (!st.isEmpty())
            postfix.add(st.pop());
        return postfix;
    }

    public static boolean isOperator(String op) { // 判断是否为数字符号
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    public static boolean isNumber(String num) { // 判断是否为数字
        return num.matches("\\d+");
    }

    public static int priority(String op) { // 判断优先级
        if (op.equals("*") || op.equals("/"))
            return 1;
        else if (op.equals("+") || op.equals("-"))
            return 0;
        else
            return -1;
    }

    public static int calculate(List<String> list) throws Exception { // 根据后缀表达式list计算结果
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            if (item.matches("\\d+"))
                st.push(Integer.parseInt(item));
            else {
                // 是操作符，则从栈顶取两个元素
                int num1 = st.pop();
                int num2 = st.pop();
                int result = 0;
                if (item.equals("+"))
                    result = num1 + num2;
                else if (item.equals("-"))
                    result = num1 - num2;
                else if (item.equals("*"))
                    result = num1 * num2;
                else if (item.equals("/"))
                    result = num1 / num2;
                else
                    throw new Exception("运算符错误");
                st.push(result);
            }
        }
        return st.pop();
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("请输入一个表达式:");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        List<String> expressionList = expressionToList(s);
        List<String> postfix = toPostFix(expressionList);
        System.out.println("后缀表达式:" + postfix);
        int calculateResult = calculate(postfix);
        System.out.println("表达式的值:" + calculateResult);
    }
}
