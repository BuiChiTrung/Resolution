package Util;

import Component.Expression;
import Component.KnowledgeBase;
import Component.Variable;
import Container.Container;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Container.Container.KB;

public class Utils {
    //hàm End-to-end dự đoán từ file các luật và file mệnh đề
    public static String predictUsingFile (String rulesPath, String clausePath) {
        Container.KB = new KnowledgeBase();
        getRulesFromFile(rulesPath);
        Expression clause = getPredictingClauseFromFile(clausePath);
        return RobinsonUtils.predict(clause);
    }

    //Đọc các luật từ file và cho vào knowledge base
    public static void getRulesFromFile(String path) {
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

    public static void parseUserInput (String ruleString, String predictString) {
        KB = new KnowledgeBase();
        addRulesToKB(ruleString);
        addPredictToKB(predictString);
        //        return RobinsonUtils.predict(Container.KB, clause);
    }

    private static void addRulesToKB(String ruleString) {
        String[] ruleList = ruleString.split("\n");
        for (String rule : ruleList) {
            KB.add(ExpressionUtils.extractAllOrExpressionFromString(rule));
        }
    }

    private static void addPredictToKB(String predictString) {
        Expression predict = ExpressionUtils.convertStringToExpression(predictString);
        predict.reverse();
        KB.add(ExpressionUtils.extractAllOrExpression(predict));
    }
    

}
