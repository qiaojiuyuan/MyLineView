package com.jeo.mylineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CustomLineView lineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineView = (CustomLineView) findViewById(R.id.line_view);
        lineView.setDate1("01/01");
        lineView.setDate2("01/10");
        lineView.setDate3("01/20");
        lineView.setDate4("01/30");
        lineView.setPoint1Value(100);
        lineView.setPoint2Value(400);
        lineView.setPoint3Value(200);
        lineView.setPoint4Value(300);
    }
}
