import Util.Utils;

public class MainClass {
    private static final String RULE_PATH = "E:\\Android_Development\\Resolution\\resolution\\src\\main\\java\\Input\\rules.txt";
    private static final String CLAUSE_PATH = "E:\\Android_Development\\Resolution\\resolution\\src\\main\\java\\Input\\clause.txt";

    public static void main(String[] args) {
        System.out.println(Utils.predictUsingFile(RULE_PATH, CLAUSE_PATH));
    }
}
