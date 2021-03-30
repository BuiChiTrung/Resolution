package Util;

import Component.*;

import java.util.ArrayList;

public class ExpressionUtils {
    //Tách tất cả các biểu thức chỉa toàn toán tử or khỏi 1 biểu  thứcchuẩn tắc hội tương đương từ 1 xâu
    public static ArrayList<OrExpression> extractAllOrExpressionFromString(String rule) {
        Expression expression = convertStringToExpression(rule);
        return extractAllOrExpression(expression);
    }

    //Chuyển 1 xâu thành 1 expression
    public static Expression convertStringToExpression(String rule) {
        //Example: ( ( v1 & v2 ) | ( v3 & v4 ) )
        String[] expression = rule.split(" ");
        ArrayList<Expression> expressionStack = new ArrayList<Expression>();
        ArrayList<Operator> operatorStack = new ArrayList<Operator>();
        for(String s: expression) {
            switch(s) {
                case "|":
                    operatorStack.add(Operator.OR);
                    break;
                case "&":
                    operatorStack.add(Operator.AND);
                    break;
                case "->":
                    getLastElement(expressionStack).reverse();
                    operatorStack.add(Operator.OR);
                    break;
                case "(":
                    break;
                case ")":
                    Expression lastExpression = getLastElement(expressionStack);
                    removeLastElement(expressionStack);
                    Expression secondLastExpression = getLastElement(expressionStack);
                    removeLastElement(expressionStack);
                    Expression newExpression = new BinaryExpression(lastExpression, secondLastExpression, getLastElement(operatorStack));
                    removeLastElement(operatorStack);
                    expressionStack.add(newExpression);
                    break;
                default:
                    if(s.charAt(0) == '!') {
                        expressionStack.add(new Variable(s.substring(1, s.length()), true));
                    } else {
                        expressionStack.add(new Variable(s, false));
                    }
                    break;
            }
        }
        Expression resultExpression = getLastElement(expressionStack);
        resultExpression.normalize();
        return resultExpression;
    }

    // Tách chuẩn tắc hội thành list các tuyển sơ cấp
    public static ArrayList<OrExpression> extractAllOrExpression(Expression expression) {
        expression.normalize();
        ArrayList<OrExpression> result = new ArrayList<OrExpression>();
        if (expression instanceof Variable) {
            result.add(new OrExpression(extractAllVariable(expression)));
            return result;
        }
        BinaryExpression binaryExpression = (BinaryExpression) expression;
        if(binaryExpression.getOperator().equals(Operator.OR)) {
            OrExpression newOrExpression = new OrExpression(extractAllVariable(expression));
            newOrExpression.normalize();
            result.add(newOrExpression);
            return result;
        }
        result.addAll(extractAllOrExpression(binaryExpression.getLeftExpression()));
        result.addAll(extractAllOrExpression(binaryExpression.getRightExpression()));
        return result;
    }

    //Tách tất cả cả các biến trong 1 biểu thức
    public static ArrayList<Variable> extractAllVariable(Expression expression) {
        ArrayList<Variable> resultList = new ArrayList<Variable>();
        if(expression instanceof Variable) {
            resultList.add( (Variable) expression );
            return resultList;
        }
        BinaryExpression binaryExpression = (BinaryExpression) expression;
        resultList.addAll(extractAllVariable(binaryExpression.getLeftExpression()));
        resultList.addAll(extractAllVariable(binaryExpression.getRightExpression()));
        return resultList;
    }

    public static <T> T getLastElement(ArrayList<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> void removeLastElement(ArrayList<T> list) {
        list.remove(list.size() - 1);
    }
}
