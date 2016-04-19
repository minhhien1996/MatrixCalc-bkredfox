package com.bkredfox.minhhien.matrixcalc_bkredfox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import java.util.Locale;

public class functions extends Activity {

    Button btnTrace;
    Button btnTrans;
    Button btnDet;
    Button btnOrth;
    Button btnInv;

    Button btnAdd;
    Button btnProd;
    Button btnnumProd;
    Button btnMinus;

    Bundle bundle;


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

        setContentView(R.layout.functions);

        StringBuilder str = new StringBuilder();
        String theme=pre.getString("theme", "1");
        str.append("corners_button_" + theme);
        int id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        btnTrace = (Button) findViewById(R.id.trace);
        btnTrace.setBackground(getResources().getDrawable(id));
        btnTrans = (Button) findViewById(R.id.transpose);
        btnTrans.setBackground(getResources().getDrawable(id));
        btnDet = (Button) findViewById(R.id.determination);
        btnDet.setBackground(getResources().getDrawable(id));
        btnOrth = (Button) findViewById(R.id.orthogonic);
        btnOrth.setBackground(getResources().getDrawable(id));
        btnInv = (Button) findViewById(R.id.invert);
        btnInv.setBackground(getResources().getDrawable(id));
        btnAdd = (Button) findViewById(R.id.add);
        btnAdd.setBackground(getResources().getDrawable(id));
        btnProd = (Button) findViewById(R.id.product);
        btnProd.setBackground(getResources().getDrawable(id));
        btnnumProd = (Button) findViewById(R.id.numProduct);
        btnnumProd.setBackground(getResources().getDrawable(id));
        btnMinus = (Button) findViewById(R.id.minus);
        btnMinus.setBackground(getResources().getDrawable(id));

        ScrollView sv_func = (ScrollView) findViewById(R.id.sv_func);
        str = new StringBuilder();
        str.append("bg_color_" + theme);
        id = getResources().getIdentifier(str.toString(), "color", getPackageName());
        sv_func.setBackgroundResource(id);
    }

    public void buttonOnClick(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("func", view.getId());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
