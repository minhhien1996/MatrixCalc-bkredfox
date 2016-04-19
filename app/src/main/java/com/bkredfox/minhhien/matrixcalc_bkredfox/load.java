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

public class load extends Activity {

    String loadMat;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);
        pref = getSharedPreferences("my_matrix", MODE_PRIVATE);

        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        StringBuilder str = new StringBuilder();
        String theme=pre.getString("theme", "1");
        str.append("corners_button_" + theme);
        int id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        Button matA_load = (Button) findViewById(R.id.matA_load);
        matA_load.setBackground(getResources().getDrawable(id));
        Button matB_load = (Button) findViewById(R.id.matB_load);
        matB_load.setBackground(getResources().getDrawable(id));
        Button matC_load = (Button) findViewById(R.id.matC_load);
        matC_load.setBackground(getResources().getDrawable(id));
        Button matD_load = (Button) findViewById(R.id.matD_load);
        matD_load.setBackground(getResources().getDrawable(id));
        Button matE_load = (Button) findViewById(R.id.matE_load);
        matE_load.setBackground(getResources().getDrawable(id));
        Button matF_load = (Button) findViewById(R.id.matF_load);
        matF_load.setBackground(getResources().getDrawable(id));
        Button matG_load = (Button) findViewById(R.id.matG_load);
        matG_load.setBackground(getResources().getDrawable(id));
        Button matH_load = (Button) findViewById(R.id.matH_load);
        matH_load.setBackground(getResources().getDrawable(id));
        Button matI_load = (Button) findViewById(R.id.matI_load);
        matI_load.setBackground(getResources().getDrawable(id));

        TableLayout tbl = (TableLayout) findViewById(R.id.loadTbl);
        str = new StringBuilder();
        str.append("bg_color_" + theme);
        id = getResources().getIdentifier(str.toString(), "color", getPackageName());
        tbl.setBackgroundResource(id);
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

    void setResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("loadMat", loadMat);
        setResult(RESULT_OK, resultIntent);
        this.finish();
    }

    public void btnStorageLoad_onClick(View view) {
        switch (view.getId()) {
            case R.id.matA_load: {
                loadMat = pref.getString("matA", null);
                if (loadMat != null) {

                    setResult();

                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matB_load: {
                loadMat = pref.getString("matB", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matC_load: {
                loadMat = pref.getString("matC", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matD_load: {
                loadMat = pref.getString("matD", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matE_load: {
                loadMat = pref.getString("matE", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matF_load: {

                loadMat = pref.getString("matF", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matG_load: {
                loadMat = pref.getString("matG", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matH_load: {
                loadMat = pref.getString("matH", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
            case R.id.matI_load: {
                loadMat = pref.getString("matI", null);
                if (loadMat != null) {
                    setResult();
                } else {
                    showToast(getString(R.string.not_exist));
                }
                break;
            }
        }
    }
}
