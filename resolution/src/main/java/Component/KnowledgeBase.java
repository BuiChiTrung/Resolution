package Component;

import java.util.ArrayList;

public class KnowledgeBase {
    private ArrayList<OrExpression> orExpressionList =  new ArrayList<OrExpression>();

    public KnowledgeBase() {}

    public KnowledgeBase(ArrayList<OrExpression> OrExpressions) {
        this.orExpressionList = OrExpressions;
    }

    public void add(ArrayList<OrExpression> newRules) {
        orExpressionList.addAll(newRules);
    }

    public ArrayList<OrExpression> getOrExpressionList() {
        return orExpressionList;
    }

    public KnowledgeBase copy() {
        ArrayList<OrExpression> copyOfListExpression = new ArrayList<OrExpression>();
        for(OrExpression expression: orExpressionList) {
            copyOfListExpression.add(expression.copy());
        }
        return new KnowledgeBase(copyOfListExpression);
    }

    public boolean empty() {
        return orExpressionList.isEmpty();
    }

    public String toString() {
        String result = "KB: \n";
        for(OrExpression expression : orExpressionList) {
            result = result + expression.toString() + "\n";
        }
        return result;
    }
}
