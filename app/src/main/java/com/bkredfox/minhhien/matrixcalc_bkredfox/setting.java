package com.bkredfox.minhhien.matrixcalc_bkredfox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.RadioButton;

import java.util.Locale;

public class setting extends Activity {
    int langChecked, themeChecked;

    RadioButton viRBtn;
    RadioButton enRBtn;

    SharedPreferences pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pre=getSharedPreferences("my_data", MODE_PRIVATE);

        String lang=pre.getString("locale", "vi");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.setting);

        StringBuilder str = new StringBuilder();
        String theme=pre.getString("theme", "1");
        str.append("square_button_" + theme);
        int id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        str = new StringBuilder();
        str.append("matrix_bg_button_" + theme);
        id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        LinearLayout llDiscard = (LinearLayout) findViewById(R.id.llDiscard);
        llDiscard.setBackground(getResources().getDrawable(id));
        LinearLayout llApply = (LinearLayout) findViewById(R.id.llApply);
        llApply.setBackground(getResources().getDrawable(id));


        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        str = new StringBuilder();
        str.append("bg_color_" + theme);
        id = getResources().getIdentifier(str.toString(), "color", getPackageName());
        sv.setBackgroundResource(id);

        int langId = pre.getInt("langId",R.id.viRBtn);
        RadioButton langRb = (RadioButton) findViewById(langId);
        langRb.setChecked(true);

        int themeId = pre.getInt("themeId",R.id.theme1);
        RadioButton themeRb = (RadioButton) findViewById(themeId);
        themeRb.setChecked(true);

        viRBtn = (RadioButton) findViewById(R.id.viRBtn);
        enRBtn = (RadioButton) findViewById(R.id.enRBtn);
    }

    public void applySetting(View view) {
        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        switch(langChecked){
            case R.id.viRBtn:
                setLocale("vi");
                editor.putInt("langId", langChecked);
                break;
            case R.id.enRBtn:
                setLocale("");
                editor.putInt("langId", langChecked);
                break;
        }
        switch(themeChecked){
            case R.id.theme1:
                setTheme("1");
                editor.putInt("themeId", themeChecked);
                break;
            case R.id.theme2:
                setTheme("2");
                editor.putInt("themeId", themeChecked);
                break;
            case R.id.theme3:
                setTheme("3");
                editor.putInt("themeId", themeChecked);
                break;
            case R.id.theme4:
                setTheme("4");
                editor.putInt("themeId", themeChecked);
                break;
            case R.id.theme5:
                setTheme("5");
                editor.putInt("themeId", themeChecked);
                break;
        }
        editor.commit();
        Intent intent_menu = new Intent(this,menu.class);
        intent_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_menu);

        this.finish();
    }

    public void discardSetting(View view) {
        this.finish();
    }


    public void onRadioButtonClicked(View view){
        RadioGroup group = (RadioGroup) findViewById(R.id.languages);
        langChecked = group.getCheckedRadioButtonId();
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        editor.putString("locale", lang);
        editor.commit();
    }

    public void onThemeRadioButtonClicked(View view) {
        RadioGroup group = (RadioGroup) findViewById(R.id.themes);
        themeChecked = group.getCheckedRadioButtonId();
    }

    public void setTheme(String theme) {
        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        editor.putString("theme", theme);
        editor.commit();
    }



}
