package com.example.resolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expression.Expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Component.BinaryExpression;
import Util.ExpressionUtils;
import Util.Utils;
import a.Ok;

public class MainActivity extends AppCompatActivity {
    private final String RULE_PATH = "E:\\Android_Development\\Resolution\\app\\src\\main\\res\\raw\\rules.txt";
    private final String CLAUSE_PATH = "E:\\Android_Development\\Resolution\\resolution\\src\\main\\java\\Input\\clause.txt";

    private static EditText knowledgeBase;
    private static EditText prediction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIElement();
    }

    public void initUIElement() {
        knowledgeBase = findViewById(R.id.knowledgeBase);
        prediction = findViewById(R.id.prediction);
    }

    private void test() {
        String ruleString = "( ( !A | !B ) | C )\n( !A | B )\nA";
        String clauseString = "C";

        String result = Utils.predictUsingUserInput(ruleString, clauseString);
        Log.d("debug", result);
    }

    public void resolve(View view) {
        String rules = knowledgeBase.getText().toString();
        String clause = prediction.getText().toString();

        String result = Utils.predictUsingUserInput(rules, clause);
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}