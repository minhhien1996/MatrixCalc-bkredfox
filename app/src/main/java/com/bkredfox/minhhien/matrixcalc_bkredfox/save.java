package com.bkredfox.minhhien.matrixcalc_bkredfox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

public class save extends Activity {

    String saveMat;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);


        Intent intent = getIntent();
        if (intent != null)
            saveMat = intent.getStringExtra("saveMat");

        pref = getSharedPreferences("my_matrix", MODE_PRIVATE);
        editor = pref.edit();

        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        StringBuilder str = new StringBuilder();
        String theme=pre.getString("theme", "1");
        str.append("corners_button_" + theme);
        int id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        Button matA = (Button) findViewById(R.id.matA);
        matA.setBackground(getResources().getDrawable(id));
        Button matB = (Button) findViewById(R.id.matB);
        matB.setBackground(getResources().getDrawable(id));
        Button matC = (Button) findViewById(R.id.matC);
        matC.setBackground(getResources().getDrawable(id));
        Button matD = (Button) findViewById(R.id.matD);
        matD.setBackground(getResources().getDrawable(id));
        Button matE = (Button) findViewById(R.id.matE);
        matE.setBackground(getResources().getDrawable(id));
        Button matF = (Button) findViewById(R.id.matF);
        matF.setBackground(getResources().getDrawable(id));
        Button matG = (Button) findViewById(R.id.matG);
        matG.setBackground(getResources().getDrawable(id));
        Button matH = (Button) findViewById(R.id.matH);
        matH.setBackground(getResources().getDrawable(id));
        Button matI = (Button) findViewById(R.id.matI);
        matI.setBackground(getResources().getDrawable(id));

        TableLayout tbl = (TableLayout) findViewById(R.id.saveTbl);
        str = new StringBuilder();
        str.append("bg_color_" + theme);
        id = getResources().getIdentifier(str.toString(), "color", getPackageName());
        tbl.setBackgroundResource(id);
    }

    public void btnBack(View view) {
        this.finish();
    }

    public void showToast(String s) {
        Context context = getApplicationContext();
        CharSequence text = s;
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.cancel();
            }
        }.start();
        toast.show();

    }

    public void btnStorageSave_onClick(View view) {

        switch (view.getId()) {
            case R.id.matA: {

                String mat = pref.getString("matA", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matA", saveMat);
                break;
            }
            case R.id.matB: {
                String mat = pref.getString("matB", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matB", saveMat);
                break;
            }
            case R.id.matC: {
                String mat = pref.getString("matC", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matC", saveMat);
                break;
            }
            case R.id.matD: {
                String mat = pref.getString("matD", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matD", saveMat);
                break;
            }
            case R.id.matE: {
                String mat = pref.getString("matE", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matE", saveMat);
                break;
            }
            case R.id.matF: {
                String mat = pref.getString("matF", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matF", saveMat);
                break;
            }
            case R.id.matG: {
                String mat = pref.getString("matG", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matG", saveMat);
                break;
            }
            case R.id.matH: {
                String mat = pref.getString("matH", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matH", saveMat);
                break;
            }
            case R.id.matI: {
                String mat = pref.getString("matI", null);
                if (mat != null) showToast(getString(R.string.exist));
                editor.putString("matI", saveMat);
                break;
            }
        }
        editor.commit();
        this.finish();

    }
}
