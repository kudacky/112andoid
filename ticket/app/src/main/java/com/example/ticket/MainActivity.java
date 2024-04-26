package com.example.ticket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup genderRadioGroup, ticketTypeRadioGroup;
    private EditText quantityEditText;
    private Button confirmButton;
    private TextView resultTextView;

    // 定义票价
    private int childPrice = 100;
    private int adultPrice = 200;
    private int studentPrice = 150;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图控件
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        ticketTypeRadioGroup = findViewById(R.id.ticketTypeRadioGroup);
        quantityEditText = findViewById(R.id.quantityEditText);
        confirmButton = findViewById(R.id.confirmButton);
        resultTextView = findViewById(R.id.resultTextView);

        // 添加文本变化监听器
        quantityEditText.addTextChangedListener(quantityTextWatcher);

        // 添加单选按钮变化监听器
        genderRadioGroup.setOnCheckedChangeListener(radioGroupCheckedChangeListener);
        ticketTypeRadioGroup.setOnCheckedChangeListener(radioGroupCheckedChangeListener);

        // 确认按钮点击事件监听
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalPrice();
            }
        });
    }

    private TextWatcher quantityTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            calculateTotalPrice(); // 用户输入发生变化时，实时计算总价格
        }
    };

    // 单选按钮变化监听器
    private RadioGroup.OnCheckedChangeListener radioGroupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            calculateTotalPrice(); // 单选按钮选择发生变化时，实时计算总价格
        }
    };

    // 计算总价格并显示结果
    private void calculateTotalPrice() {
        // 获取用户输入的数量
        String quantityStr = quantityEditText.getText().toString();
        if (quantityStr.isEmpty()) {
            Toast.makeText(this, "请输入票卷数量", Toast.LENGTH_SHORT).show();
            return;
        }
        int quantity = Integer.parseInt(quantityStr);

        // 获取选择的性别
        RadioButton selectedGenderRadioButton = findViewById(genderRadioGroup.getCheckedRadioButtonId());
        String gender = selectedGenderRadioButton.getText().toString();

        // 获取选择的票种
        RadioButton selectedTicketTypeRadioButton = findViewById(ticketTypeRadioGroup.getCheckedRadioButtonId());
        String ticketType = selectedTicketTypeRadioButton.getText().toString();

        // 根据选择的票种确定票价
        int price = 0;
        switch (ticketType) {
            case "兒童票":
                price = childPrice;
                break;
            case "成人票":
                price = adultPrice;
                break;
            case "学生票":
                price = studentPrice;
                break;
        }

        // 计算总价格
        int totalPrice = quantity * price;

        // 显示结果
        String result = "性别：" + gender + "\n票种：" + ticketType + "\n总价格：" + totalPrice;
        resultTextView.setText(result);
    }
}
