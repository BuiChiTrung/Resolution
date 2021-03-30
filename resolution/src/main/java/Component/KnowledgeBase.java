package Component;

import java.util.ArrayList;

public class KnowledgeBase {
    private ArrayList<OrExpression> orExpressionList =  new ArrayList<OrExpression>();

    public void add(ArrayList<OrExpression> newRules) {
        orExpressionList.addAll(newRules);
    }

    public ArrayList<OrExpression> getOrExpressionList() {
        return orExpressionList;
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
