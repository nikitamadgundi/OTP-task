package com.example.otpapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    private RadioButton rbFour;
    private RadioButton rbFive;
    private RadioButton rbSix;
    private RadioButton rbSeven;
    private LinearLayout MainLayout;
    private ArrayList<EditText> editTextArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setUpListeners();
        init();

    }

    private void init() {
        editTextArray = new ArrayList<EditText>();
    }

    private void initView() {
        rbFour = findViewById(R.id.rbFour);
        rbFive = findViewById(R.id.rbFive);
        rbSix = findViewById(R.id.rbSix);
        rbSeven = findViewById(R.id.rbSeven);
        MainLayout = findViewById(R.id.MainLayout);


    }

    private void setUpListeners() {
        BtnOnClickListeners btnOnClickListeners = new BtnOnClickListeners();
        rbFour.setOnClickListener(btnOnClickListeners);
        rbFive.setOnClickListener(btnOnClickListeners);
        rbSix.setOnClickListener(btnOnClickListeners);
        rbSeven.setOnClickListener(btnOnClickListeners);
    }

    class BtnOnClickListeners implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rbFour:
                    setInput(4);
                    break;
                case R.id.rbFive:
                    setInput(5);
                    break;
                case R.id.rbSix:
                    setInput(6);
                    break;
                case R.id.rbSeven:
                    setInput(7);
                    break;

            }

        }
    }

    private void setInput(int Count) {
        MainLayout.removeAllViews();
        editTextArray.clear();
        for (int i = 0; i < Count; i++) {

            EditText newEditText = new EditText(MainActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            );

            newEditText.setLayoutParams(layoutParams);
            newEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            newEditText.setTextSize(20);
            newEditText.setGravity(Gravity.CENTER);
            newEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            MainLayout.addView(newEditText);
            editTextArray.add(newEditText);
            int j = 0;
            for (j = 0; j < editTextArray.size(); j++) {


                int finalJ = j;
                editTextArray.get(j).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (count == 1) {
                            if (getNextEditTextIndexForFocus(editTextArray.get(finalJ)) == -1) {
                                editTextArray.get(finalJ).clearFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editTextArray.get(finalJ).getWindowToken(), 0);

                            } else {
                                editTextArray.get(getNextEditTextIndexForFocus(editTextArray.get(finalJ))).requestFocus();
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

        }


    }

    public int getNextEditTextIndexForFocus(EditText currentEditText) {

        for (int i = 0; i < editTextArray.size() - 1; i++) {
            if (currentEditText == editTextArray.get(editTextArray.size() - 1)) {
                Toast.makeText(MainActivity.this, "OTP Done", Toast.LENGTH_SHORT).show();
                return -1;

            }

            if (editTextArray.get(i) == currentEditText) return i + 1;
        }


        return 0;
    }
}
