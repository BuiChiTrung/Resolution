package Util;

import Component.Expression;
import Component.KnowledgeBase;
import Component.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    //hàm End-to-end dự đoán từ file các luật và file mệnh đề
    public static String predictUsingFile (String rulesPath, String clausePath) {
        KnowledgeBase KB = new KnowledgeBase();
        getRulesFromFile(rulesPath, KB);
        Expression clause = getPredictingClauseFromFile(clausePath);
        return RobinsonUtils.predict(KB, clause);
    }

    //Đọc các luật từ file và cho vào knowledge base
    public static void getRulesFromFile(String path, KnowledgeBase KB) {
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String rule = reader.nextLine();
                System.out.println(rule);
                KB.add(ExpressionUtils.extractAllOrExpressionFromString(rule));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot open file " + path);
        }
    }


    //Đọc mệnh đề muốn suy luận
    public static Expression getPredictingClauseFromFile(String path) {
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String rule = reader.nextLine();
                return ExpressionUtils.convertStringToExpression(rule);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot open file " + path);
        }
        return new Variable("Empty", false);
    }

    public static String predictUsingUserInput (String ruleString, String clauseString) {
        KnowledgeBase KB = new KnowledgeBase();

        addRuleToKB(ruleString, KB);
        Expression clause = ExpressionUtils.convertStringToExpression(clauseString);

        return RobinsonUtils.predict(KB, clause);
    }

    public static void addRuleToKB(String ruleString, KnowledgeBase KB) {
        String[] ruleList = ruleString.split("\n");
        for (String rule : ruleList) {
            KB.add(ExpressionUtils.extractAllOrExpressionFromString(rule));
        }
    }
    

}