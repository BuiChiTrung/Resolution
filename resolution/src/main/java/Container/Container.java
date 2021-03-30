package Container;

import java.util.ArrayList;

import Component.KnowledgeBase;

public class Container {
    public static KnowledgeBase KB;
    // Vị trí đang xét trong KB xem biểu thức có phân giải đc ko
    public static int KBIteratingPos = -1;
    public static boolean predictResult = false;
    // Vị trí các biểu thức phân giải đc với biểu thức tại vị trí KBIteratingPos
    public static ArrayList<Integer> resolvableIndex = new ArrayList<>();

    public static void reset() {
        KB = new KnowledgeBase();
        KBIteratingPos = -1;
        predictResult = false;
        resolvableIndex = new ArrayList<>();
    }
}
