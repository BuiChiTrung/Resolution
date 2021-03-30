package Component;

import Component.Expression;

//Biến logic
public class Variable extends Expression {
    private String name;
    private boolean isNegative;

    public Variable(String name, boolean isNegative) {
        this.name = name;
        this.isNegative = isNegative;
    }

    public String getName() {
        return name;
    }

    public boolean getIsNegative() {
        return isNegative;
    }

    @Override
    public void normalize() {}

    @Override
    public void convertOrOperatorToAnd() {
    }

    @Override
    public boolean ableToConvertToAndExpression() {
        return false;
    }

    @Override
    public void reverse() {
        isNegative = !isNegative;
    }

    @Override
    public String toString() {
        if(isNegative) {
            return '!' + name;
        }
        return name;
    }

    public Expression copy() {
        return new Variable(name, isNegative);
    }
}
