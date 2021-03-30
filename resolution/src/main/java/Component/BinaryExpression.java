package Component;

import Util.ExpressionUtils;

//Biểu thức 2 ngôi
public class BinaryExpression extends Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    private Operator operator;

    public BinaryExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public Operator getOperator() {
        return operator;
    }

    //Chuẩn hóa về dạng chuẩn tắc hội
    @Override
    public void normalize() {
        if(ableToConvertToAndExpression()) {
            convertOrOperatorToAnd();
        }
        leftExpression.normalize();
        rightExpression.normalize();
    }

    //Chuyển biểu thức sang biểu thức and tương đương
    @Override
    public void convertOrOperatorToAnd() {
        if(operator.equals(Operator.AND)) {
            return;
        }
        if(leftExpression.ableToConvertToAndExpression()) {
            leftExpression.convertOrOperatorToAnd();
            BinaryExpression currentLeftExpression = (BinaryExpression) leftExpression;
            Expression newLeftExpression = new BinaryExpression(currentLeftExpression.getLeftExpression().copy(), rightExpression.copy(), Operator.OR);
            Expression newRightExpression = new BinaryExpression(currentLeftExpression.getRightExpression().copy(), rightExpression.copy(), Operator.OR);
            this.leftExpression = newLeftExpression;
            this.rightExpression = newRightExpression;
            this.operator = Operator.AND;
            return;
        }
        if(rightExpression.ableToConvertToAndExpression()) {
            rightExpression.convertOrOperatorToAnd();
            BinaryExpression currentRightExpression = (BinaryExpression) rightExpression;
            Expression newLeftExpression = new BinaryExpression(currentRightExpression.getLeftExpression().copy(), leftExpression.copy(), Operator.OR);
            Expression newRightExpression = new BinaryExpression(currentRightExpression.getRightExpression().copy(), leftExpression.copy(), Operator.OR);
            this.leftExpression = newLeftExpression;
            this.rightExpression = newRightExpression;
            this.operator = Operator.AND;
        }
    }

    //Có thể chuyển về biểu thức and tương đương hay ko ?
    @Override
    public boolean ableToConvertToAndExpression() {
        if(this.operator.equals(Operator.AND)) {
            return true;
        }
        return leftExpression.ableToConvertToAndExpression() | rightExpression.ableToConvertToAndExpression();
    }

    //Nghich dao bieu thuc
    @Override
    public void reverse() {
        leftExpression.reverse();
        rightExpression.reverse();
        if(operator.equals(Operator.OR)) {
            operator = Operator.AND;
        }
        else {
            operator = Operator.OR;
        }
    }

    @Override
    public String toString() {
        if(operator.equals(Operator.AND)) {
            return "( " + leftExpression.toString() + " & " + rightExpression.toString() + " )";
        }
        return "( " + leftExpression.toString() + " | " + rightExpression.toString() + " )";
    }

    public Expression copy() {
        return new BinaryExpression(leftExpression.copy(), rightExpression.copy(), operator);
    }

    public static void main(String[] args) {
//        BinaryExpression binaryExpression = new BinaryExpression(new BinaryExpression(new BinaryExpression(new Variable("a", false), new Variable("b", false), Operator.AND), new Variable("c", false), Operator.OR), new Variable("d", false), Operator.AND);
//        binaryExpression.normalize();
//        System.out.println(binaryExpression);
        BinaryExpression binaryExpression2 = (BinaryExpression) ExpressionUtils.convertStringToExpression("( ( a & b ) | c ) & b )");
        System.out.println(binaryExpression2);
    }
}