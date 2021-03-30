package Component;

public abstract class Expression {

    public abstract void reverse();
    public abstract boolean ableToConvertToAndExpression();
    public abstract void convertOrOperatorToAnd();
    public abstract void normalize();
    public abstract String toString();
    public abstract Expression copy();
}
