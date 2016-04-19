package com.bkredfox.minhhien.matrixcalc_bkredfox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class matrixDisplay extends Activity {

    static final int loader = 1;
    static final int calculator = 0;
    static final int no_func = 0;
    public ImageButton btnLoad;
    public ImageButton btnSave;
    public ImageButton btnCal;
    public ImageButton btnMenu;
    public Button btnAddCol;
    public Button btnRmCol;
    public Button btnAddRow;
    public Button btnRmRow;
    public LinearLayout llLoad;
    public LinearLayout llSave;
    public LinearLayout llCalculate;
    public LinearLayout llBack;
    public TableLayout matrixContainer;
    public EditText[][] matrix;
    public int columns;
    public int rows;




    boolean colChange;
    boolean rowChange;
    //1 for first input, 2 for second input, 3 for show reseult
    /*matDis(1) -> func         -> matDis(3)
                           v -> matDis(2) -> ^
                           */
    int stage;
    int func;
    boolean storage;

    Solver solver;
    Matrix mat1, mat2, matRes;
    NumberSolve numberParsingSolver;
    JsonUtil jsonUtil;

    String formatDouble(double dbl) {
        Configuration config = getResources().getConfiguration();
        DecimalFormatSymbols symbol = new DecimalFormatSymbols(config.locale);
        symbol.setDecimalSeparator('.');
        symbol.setGroupingSeparator(',');
        DecimalFormat precision = new DecimalFormat("0.00",symbol);
        return precision.format(dbl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix);

        solver = new Solver();
        numberParsingSolver = new NumberSolve();
        matrixContainer = (TableLayout) findViewById(R.id.matrixContainer);
        jsonUtil = new JsonUtil();

        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
        StringBuilder str = new StringBuilder();
        String theme=pre.getString("theme", "1");
        str.append("square_button_" + theme);
        int id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        btnRmRow = (Button) findViewById(R.id.btnRmRow);
        btnRmRow.setBackground(getResources().getDrawable(id));
        btnAddRow = (Button) findViewById(R.id.btnAddRow);
        btnAddRow.setBackground(getResources().getDrawable(id));
        btnAddCol = (Button) findViewById(R.id.btnAddCol);
        btnAddCol.setBackground(getResources().getDrawable(id));
        btnRmCol = (Button) findViewById(R.id.btnRmCol);
        btnRmCol.setBackground(getResources().getDrawable(id));

        str = new StringBuilder();
        str.append("matrix_bg_button_" + theme);
        id = getResources().getIdentifier(str.toString(), "drawable", getPackageName());

        llLoad = (LinearLayout) findViewById(R.id.llLoad);
        llLoad.setBackground(getResources().getDrawable(id));
        llSave = (LinearLayout) findViewById(R.id.llSave);
        llSave.setBackground(getResources().getDrawable(id));
        llCalculate = (LinearLayout) findViewById(R.id.llCalculate);
        llCalculate.setBackground(getResources().getDrawable(id));
        llBack = (LinearLayout) findViewById(R.id.llBack);
        llBack.setBackground(getResources().getDrawable(id));

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        str = new StringBuilder();
        str.append("bg_color_" + theme);
        id = getResources().getIdentifier(str.toString(), "color", getPackageName());
        sv.setBackgroundResource(id);

        btnLoad = (ImageButton) findViewById(R.id.btnLoad);
        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnCal = (ImageButton) findViewById(R.id.btnCal);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);

        matRes = null;
        setInitValue();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (stage != 2) {
            rowChange = true;
            colChange = true;
        }
        loadCurrentMatrix();
        switch (stage) {
            case 1: {
                if (!storage)
                    generateTable();
                else
                    storage = false;
                break;
            }
            case 2: {
                if (!storage)
                    generateTable();
                else
                    storage = false;
                break;
            }
            case 3: {
                btnCal.setBackground(getResources().getDrawable(R.drawable.btn_new_calculate));
                break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCurrentMatrix();
    }

    void saveCurrentMatrix() {
        SharedPreferences preferences = getSharedPreferences("my_matrix", MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            if (mat1 != null) editor.putString("mat1", jsonUtil.toJson(mat1));
            if (mat2 != null) editor.putString("mat2", jsonUtil.toJson(mat2));
            if (matRes != null) editor.putString("matRes", jsonUtil.toJson(matRes));
            editor.commit();

        }

    }

    void loadCurrentMatrix() {
        SharedPreferences preferences = getSharedPreferences("my_matrix", MODE_PRIVATE);
        if (preferences != null) {
            mat1 = JsonUtil.toMatrix(preferences.getString("mat1", null));
            mat2 = JsonUtil.toMatrix(preferences.getString("mat2", null));
            matRes = JsonUtil.toMatrix(preferences.getString("matRes", null));
        }

    }

    void setInitValue() {
        columns = 2;
        rows = 2;

        //matRes = null;
        mat1 = null;
        mat2 = null;


        func = 0;
        storage = false;

        if (matRes != null) {
            showMatrix(matRes);
            btnRmRow.setVisibility(View.VISIBLE);
            btnAddCol.setVisibility(View.VISIBLE);
            btnAddRow.setVisibility(View.VISIBLE);
            btnRmCol.setVisibility(View.VISIBLE);
            btnLoad.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);

            llLoad.setVisibility(View.VISIBLE);
            llSave.setVisibility(View.VISIBLE);
        } else
            generateTable();

        btnCal.setBackground(getResources().getDrawable(R.drawable.btn_calculate));
        rowChange = true;
        colChange = true;
        stage = 1;

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

    public Matrix buildMatrix() {
        Matrix res = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j].getText().length() == 0) matrix[i][j].setText("0");
                if (numberParsingSolver.SolveNumber(matrix[i][j].getText().toString())) {
                    res.data[i][j] = numberParsingSolver.result;
                } else {
                    return null;
                }
            }
        }
        return res;
    }

    public void generateTable() {
        if (stage == 2) {
            switch (func) {
                case R.id.add: {
                    colChange = false;
                    rowChange = false;
                    rows = mat1.rows;
                    columns = mat1.cols;
                    break;
                }
                case R.id.minus: {
                    colChange = false;
                    rowChange = false;
                    rows = mat1.rows;
                    columns = mat1.cols;
                    break;
                }
                case R.id.product: {
                    rowChange = false;
                    rows = mat1.cols;
                    break;
                }
                case R.id.numProduct: {
                    rowChange = false;
                    colChange = false;
                    rows = 1;
                    columns = 1;
                    break;
                }
            }

            if (!rowChange || !colChange) {
                btnLoad.setVisibility(View.INVISIBLE);
                llLoad.setVisibility(View.INVISIBLE);
                btnSave.setVisibility(View.INVISIBLE);
                llSave.setVisibility(View.INVISIBLE);
                if (!rowChange) {
                    btnRmRow.setVisibility(View.INVISIBLE);
                    btnAddRow.setVisibility(View.INVISIBLE);

                }
                if (!colChange) {
                    btnAddCol.setVisibility(View.INVISIBLE);
                    btnRmCol.setVisibility(View.INVISIBLE);

                }
            }
        } else {
            btnRmRow.setVisibility(View.VISIBLE);
            btnAddCol.setVisibility(View.VISIBLE);
            btnAddRow.setVisibility(View.VISIBLE);
            btnRmCol.setVisibility(View.VISIBLE);
            btnLoad.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);

            llLoad.setVisibility(View.VISIBLE);
            llSave.setVisibility(View.VISIBLE);
        }

        //showToast(Integer.toString(stage));
        matrixContainer.removeAllViews();
        matrix = new EditText[rows][columns];

        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);

            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new EditText(this);
                matrix[i][j].setHint("0");
                matrix[i][j].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                matrix[i][j].setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                matrix[i][j].setSingleLine(false);
                matrix[i][j].setLines(2);

                row.addView(matrix[i][j]);
            }
            matrixContainer.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }


    }

    public void showMatrix(Matrix mat) {
        matrixContainer.removeAllViews();
        rows = mat.rows;
        columns = mat.cols;
        matrix = new EditText[rows][columns];

        //showToast(Integer.toString(stage));

        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);

            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new EditText(this);
                matrix[i][j].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                matrix[i][j].setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                matrix[i][j].setSingleLine(false);
                matrix[i][j].setLines(2);
                matrix[i][j].setText(formatDouble(mat.data[i][j]));
                row.addView(matrix[i][j]);
            }
            matrixContainer.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    public void showResult(Matrix mat) {
        showMatrix(mat);
        btnCal.setBackground(getResources().getDrawable(R.drawable.btn_new_calculate));
        colChange = true;
        rowChange = true;
        if (func == R.id.orthogonic) {showToast(getString(R.string.under_contruction));}
        func = no_func;
        btnRmRow.setVisibility(View.INVISIBLE);
        btnAddCol.setVisibility(View.INVISIBLE);
        btnAddRow.setVisibility(View.INVISIBLE);
        btnRmCol.setVisibility(View.INVISIBLE);
        btnLoad.setVisibility(View.INVISIBLE);
        llLoad.setVisibility(View.INVISIBLE);
    }

    public void btnAddCol_clickListener(View v) {
        columns++;
        if (columns > 5) {
            columns = 5;
            showToast(getString(R.string.max_col));
        }
        generateTable();
    }

    public void btnAddRow_clickListener(View v) {
        rows++;
        if (rows > 5) {
            rows = 5;
            showToast(getString(R.string.max_row));
        }
        generateTable();
    }

    public void btnRmCol_clickListener(View v) {
        columns--;
        if (columns < 1) {
            columns = 1;
            showToast(getString(R.string.min_col));
        }
        generateTable();
    }

    public void btnRmRow_clickListener(View v) {
        rows--;
        if (rows < 1) {
            rows = 1;
            showToast(getString(R.string.min_row));
        }
        generateTable();
    }

    public void backToMenu(View view) {

        matRes = null;
        mat1 = null;
        mat2 = null;
        SharedPreferences preferences = getSharedPreferences("my_matrix", MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("matRes");
            editor.commit();
        }
        this.finish();

    }

    public void calculate(View view) {
        switch (stage) {
            case 1: {
                mat1 = buildMatrix();
                if (mat1 != null) {
                    Intent calculate = new Intent(this, functions.class);
                    saveCurrentMatrix();
                    startActivityForResult(calculate, calculator);
                } else {
                    showToast(getString(R.string.invalid_input));
                }
                break;
            }

            case 2: {

                mat2 = buildMatrix();
                switch (func) {
                    case R.id.add: {
                        stage = 3;
                        matRes = solver.Add(mat1, mat2);
                        showResult(matRes);
                        break;
                    }
                    case R.id.minus: {
                        stage = 3;
                        matRes = solver.Subtract(mat1, mat2);
                        showResult(matRes);
                        break;
                    }
                    case R.id.product: {
                        stage = 3;
                        matRes = solver.Multiply(mat1, mat2);
                        showResult(matRes);
                        break;
                    }
                    case R.id.numProduct: {
                        stage = 3;
                        matRes = solver.Multiply(mat1, mat2.data[0][0]);
                        showResult(matRes);
                        break;
                    }
                }
                break;
            }

            case 3: {

                setInitValue();

                /*
                Intent intent_calculator = new Intent(this,matrixDisplay.class);
                startActivity(intent_calculator);
                this.finish();
                */

                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case calculator: {
                if (resultCode == Activity.RESULT_OK) {

                    func = intent.getIntExtra("func", 0);
                    loadCurrentMatrix();
                    switch (func) {
                        case R.id.trace: {
                            stage = 3;
                            matRes = new Matrix(1, 1);
                            matRes.data[0][0] = solver.Trace(mat1);
                            showResult(matRes);
                            saveCurrentMatrix();
                            break;
                        }
                        case R.id.transpose: {
                            stage = 3;
                            matRes = solver.Transpose(mat1);
                            showResult(matRes);
                            saveCurrentMatrix();
                            break;
                        }
                        case R.id.invert: {
                            stage = 3;
                            matRes = solver.Reverse(mat1);
                            showResult(matRes);
                            saveCurrentMatrix();
                            break;
                        }
                        case R.id.determination: {
                            stage = 3;
                            matRes = new Matrix(1, 1);
                            matRes.data[0][0] = solver.Det(mat1);
                            showResult(matRes);
                            saveCurrentMatrix();
                            break;
                        }
                        case R.id.orthogonic: {
                            //for test
                            stage = 3;
                            matRes = mat1;
                            showResult(matRes);
                            break;
                        }
                        case R.id.add: {
                            stage = 2;
                            break;
                        }
                        case R.id.minus: {
                            stage = 2;
                            break;
                        }
                        case R.id.product: {
                            stage = 2;
                            break;
                        }
                        case R.id.numProduct: {
                            stage = 2;
                            break;
                        }
                    }
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    showToast(getString(R.string.action_canceled));
                }
                break;
            }
            case loader: {
                if (resultCode == Activity.RESULT_OK) {
                    String s = intent.getStringExtra("loadMat");
                    Matrix mat = jsonUtil.toMatrix(s);
                    showMatrix(mat);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    showToast(getString(R.string.load_canceled));
                }
                break;
            }
        }


    }

    public void load(View view) {
        storage = true;
        Intent load = new Intent(this, load.class);
        startActivityForResult(load, loader);
    }

    public void save(View view) {
        Intent save = new Intent(this, save.class);
        storage = true;
        switch (stage) {
            case 1: {
                mat1 = buildMatrix();
                String s = jsonUtil.toJson(mat1);
                save.putExtra("saveMat", s);
                break;
            }
            case 2: {
                mat2 = buildMatrix();
                String s = jsonUtil.toJson(mat2);
                save.putExtra("saveMat", s);
                break;
            }
            case 3: {
                matRes = buildMatrix();
                String s = jsonUtil.toJson(matRes);
                save.putExtra("saveMat", s);
                break;
            }
        }
        startActivity(save);

    }

}
