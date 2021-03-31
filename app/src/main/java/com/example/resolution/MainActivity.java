package com.example.resolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import Component.OrExpression;
import Container.Container;
import Util.RobinsonUtils;
import Util.Utils;

/**
 * App được chia làm 3 trạng thái
 * 1. Init: user điền vào KB, predict Clause
 * 2. Resolve: thuật toán robinson đang diễn ra
 * 3. Reset: thuật toán robinson hoàn thành
 */
public class MainActivity extends AppCompatActivity {
    private static ConstraintLayout constraintLayout;
    private static TextView explanation;
    private static EditText knowledgeBase;
    private static EditText prediction;
    private static Button resolve;
    private static Button getKB;
    private static Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIState();
    }

    // State 1
    public void initUIState() {
        constraintLayout = findViewById(R.id.constraintView);
//        constraintLayout.getBackground().setAlpha(220);

        explanation = findViewById(R.id.explanation);
        explanation.setVisibility(View.INVISIBLE);
        explanation.setText("");

        knowledgeBase = findViewById(R.id.knowledgeBase);
        knowledgeBase.setVisibility(View.VISIBLE);
        knowledgeBase.setText("");

        prediction = findViewById(R.id.prediction);
        prediction.setVisibility(View.VISIBLE);
        prediction.setText("");

        resolve = findViewById(R.id.resolve);
        resolve.setVisibility(View.INVISIBLE);

        getKB = findViewById(R.id.getKB);
        getKB.setVisibility(View.VISIBLE);

        reset = findViewById(R.id.reset);
        reset.setVisibility(View.INVISIBLE);
    }

    private void test() {
        String ruleString = "( ( !A | !B ) | C )\n( !A | B )\nA";
        String clauseString = "C";
    }

    // Nhận vào input, phân tích thành các tuyển sơ cấp để add vào KB
    public void addRulesAndPredictToKB(View view) {
        String rules = knowledgeBase.getText().toString();
        String predict = prediction.getText().toString();
        if (rules.equals("") || predict.equals("")) return;

        Utils.parseUserInput(rules, predict);
        displayKB();
        resolveUIState();
    }

    // Hiển thị KB tại mỗi bước trong thuật toán robinson
    private void displayKB() {
        StringBuilder KBString = new StringBuilder();
        ArrayList<OrExpression> orExpressions = Container.KB.getOrExpressionList();

        for (int i = 0; i < orExpressions.size(); ++i) {
            String orExpression = orExpressions.get(i).toString();
            String orExpressionWithoutBracket = orExpression.substring(1, orExpression.length() - 2);
            KBString.append(String.valueOf(i) + "." + orExpressionWithoutBracket + '\n');
        }
        knowledgeBase.setText(KBString.toString());
    }

    // State 2
    private void resolveUIState() {
        explanation.setVisibility(View.VISIBLE);
        resolve.setVisibility(View.VISIBLE);
        getKB.setVisibility(View.INVISIBLE);
        prediction.setVisibility(View.INVISIBLE);
    }

    // Thực hiện thuật toán robinson, mỗi lần gọi tương ứng với một vòng lặp trong tập KB
    public void resolve(View view) {
        boolean robinsonFinish = RobinsonUtils.robinsonStepByStep();
        displayKB();
        explainResolvedExpression();
        if (robinsonFinish) {
            notifyPredictResult();
            resetUIState();
        }
    }

    // Thông tin các biểu thức được phân giải với nhau trong từng bước
    private void explainResolvedExpression() {
        if (Container.resolvableIndex.size() == 0) return;

        StringBuilder explain = new StringBuilder(Container.KBIteratingPos + " resolves with ");
        for (int index : Container.resolvableIndex)
            explain.append(index + " ");

        explanation.setText(explain.toString());
    }

    // Hiển thị kết quả của robinson
    private void notifyPredictResult() {
        String KBString = knowledgeBase.getText().toString();
        if (Container.predictResult) {
            KBString = KBString  + "Clause is true";
        }
        else {
            KBString = KBString  + "Cannot predict";
        }
        knowledgeBase.setText(KBString);
    }

    // State 3
    private void resetUIState() {
        resolve.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.VISIBLE);
    }

    // Restart Application
    public void reset(View view) {
        Container.reset();
        initUIState();
    }
}