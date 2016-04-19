package com.bkredfox.minhhien.matrixcalc_bkredfox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);

        String lang=pre.getString("locale", "vi");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.menu);

        StringBuilder str = new StringBuilder();
        String theme=pre.getString("theme", "1");
        str.append("menu_button_" + theme);
        int id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        Button btnStartCalculator = (Button) findViewById(R.id.startCalculator);
        btnStartCalculator.setBackground(getResources().getDrawable(id));
        Button btnSetting = (Button) findViewById(R.id.setting);
        btnSetting.setBackground(getResources().getDrawable(id));
        Button btnExit = (Button) findViewById(R.id.exit);
        btnExit.setBackground(getResources().getDrawable(id));

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        str = new StringBuilder();
        str.append("bg_color_" + theme);
        id = getResources().getIdentifier(str.toString(), "color", getPackageName());
        sv.setBackgroundResource(id);

        TextView tv = (TextView) findViewById(R.id.appName);
        str = new StringBuilder();
        str.append("button_color_" + theme);
        id = getResources().getIdentifier(str.toString(), "color", getPackageName());
        tv.setTextColor(getResources().getColor(id));

        LinearLayout ll = (LinearLayout) findViewById(R.id.img);
        str = new StringBuilder();
        str.append("image_bg_" + theme);
        id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());
        ll.setBackground(getResources().getDrawable(id));
    }

    public void startCalculator(View view) {
        Intent intent_calculator = new Intent(this, matrixDisplay.class);
        startActivity(intent_calculator);
    }

    public void exit(View view) {
        finish();
        System.exit(0);
    }

    public void startSetting(View view) {
        Intent intent_setting = new Intent(this, setting.class);
        startActivity(intent_setting);
    }
}
