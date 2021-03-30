package Util;

import Component.*;

import java.util.ArrayList;

public class RobinsonUtils {
//    public static String predict(KnowledgeBase knowledgeBase, Expression expression) {
//        if(robinson(knowledgeBase, expression)) {
//            return "Clause is true";
//        }
//        Expression reversedExpression = expression.copy();
//        reversedExpression.reverse();
//        if(robinson(knowledgeBase, reversedExpression)) {
//            return "Clause is false";
//        }
//        return "Cannot predict";
//    }
//
//    //Áp dụng thuật toán robinson vs initKB và mệnh đề muốn dự đoán đúng hay không ?
//    public static boolean robinson(KnowledgeBase initKnowledgeBase, Expression predictingClause) {
//        KnowledgeBase knowledgeBase = initKnowledgeBase.copy();
//        predictingClause.reverse();
//        knowledgeBase.add(ExpressionUtils.extractAllOrExpression(predictingClause));
//        ArrayList<OrExpression> listOfExpression = knowledgeBase.getOrExpressionList();
//
//        int stepCounter = 1;
//        while(!knowledgeBase.empty()) {
////            System.out.println("Step " + Integer.toString(stepCounter) + "\n");
//            stepCounter++;
//            for(int i = 1; i < listOfExpression.size(); i++) {
//                if(ableToResolve(listOfExpression.get(0), listOfExpression.get(i))) {
//                    OrExpression newRule = resolve(listOfExpression.get(0), listOfExpression.get(i));
//                    if(newRule.isEmpty()) {
//                        return true;
//                    }
//                    listOfExpression.add(newRule);
//                }
//            }
////            System.out.println(knowledgeBase.toString());
//            listOfExpression.remove(0);
//        }
//        return false;
//    }

    public static String predict(KnowledgeBase knowledgeBase, Expression expression) {
        if(robinson(knowledgeBase, expression)) {
            return "Clause is true";
        }
        Expression reversedExpression = expression.copy();
        reversedExpression.reverse();
        if(robinson(knowledgeBase, reversedExpression)) {
            return "Clause is false";
        }
        return "Cannot predict";
    }

    //Áp dụng thuật toán robinson vs initKB và mệnh đề muốn dự đoán đúng hay không ?
    public static boolean robinson(KnowledgeBase initKnowledgeBase, Expression predictingClause) {
        KnowledgeBase knowledgeBase = initKnowledgeBase.copy();
        predictingClause.reverse();
        knowledgeBase.add(ExpressionUtils.extractAllOrExpression(predictingClause));
        ArrayList<OrExpression> listOfExpression = knowledgeBase.getOrExpressionList();

        int stepCounter = 1;
        while(!knowledgeBase.empty()) {
//            System.out.println("Step " + Integer.toString(stepCounter) + "\n");
            stepCounter++;
            for(int i = 1; i < listOfExpression.size(); i++) {
                if(ableToResolve(listOfExpression.get(0), listOfExpression.get(i))) {
                    OrExpression newRule = resolve(listOfExpression.get(0), listOfExpression.get(i));
                    if(newRule.isEmpty()) {
                        return true;
                    }
                    listOfExpression.add(newRule);
                }
            }
//            System.out.println(knowledgeBase.toString());
            listOfExpression.remove(0);
        }
        return false;
    }

    //Phân giải 2 biểu thức A và B
    public static OrExpression resolve(OrExpression A, OrExpression B) {
        if(!ableToResolve(A, B)) {
            return new OrExpression();
        }
        OrExpression copyOfA = A.copy();
        OrExpression copyOfB = B.copy();
        ArrayList<Variable> listA = copyOfA.getListOfVariables();
        ArrayList<Variable> listB = copyOfB.getListOfVariables();
        boolean found = false;
        for (int i = 0; i < listA.size(); i++) {
            for (int j = 0; j < listB.size(); j++) {
                if (listA.get(i).getName().equals(listB.get(j).getName())
                        & listA.get(i).getIsNegative() != listB.get(j).getIsNegative()) {
                    listA.remove(i);
                    listB.remove(j);
                    i--;
                    found = true;
                    break;
                }
            }
            if(found) {
                break;
            }
        }
        ArrayList<Variable> resultList = new ArrayList<Variable>();
        resultList.addAll(listA);
        resultList.addAll(listB);
        OrExpression resultExpression = new OrExpression(resultList);
        resultExpression.normalize();
        return resultExpression;
    }

    //check 2 expression có phân giải được không ?
    public static boolean ableToResolve(OrExpression A, OrExpression B) {
        OrExpression copyOfA = A.copy();
        OrExpression copyOfB = B.copy();
        ArrayList<Variable> listA = copyOfA.getListOfVariables();
        ArrayList<Variable> listB = copyOfB.getListOfVariables();
        int counter = 0;
        for (int i = 0; i < listA.size(); i++) {
            for (int j = 0; j < listB.size(); j++) {
                if (listA.get(i).getName().equals(listB.get(j).getName())
                        & listA.get(i).getIsNegative() != listB.get(j).getIsNegative()) {
                    counter++;
                    break;
                }
            }
        }
        return counter == 1;
    }
}
