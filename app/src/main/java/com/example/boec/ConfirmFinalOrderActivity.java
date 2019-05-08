package com.example.boec;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.boec.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {


    private EditText nameEditText, phoneEditText, addressEditText;
    private Button confirmButton;
    private String total= "";
    private RadioGroup payment_method;
    private RadioButton cash;
    private RadioButton credit_card;
    private String paymentmethod="cash";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        confirmButton = (Button)findViewById(R.id.confirm_btn);
        nameEditText = (EditText)findViewById(R.id.shipping_name);
        phoneEditText = (EditText)findViewById(R.id.shipping_phone_number);
        addressEditText = (EditText)findViewById(R.id.shipping_address);

        payment_method = (RadioGroup)findViewById(R.id.radioGroup_paymethod) ;
        cash = (RadioButton)findViewById(R.id.radioButton_cash);
        credit_card = (RadioButton)findViewById(R.id.radioButton_creditcard);

        // Đăng ký sự kiện khi RadioGroup_diffLevel có thay đổi.
        this.payment_method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnDifficultyLevelChanged(group, checkedId);
            }
        });

        total =getIntent().getStringExtra("Total");
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });


    }

    private void check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "Please enter your phone", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
        }
        else{
            confirmOrder();
        }
    }

    private void confirmOrder() {
        String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Order")
                .child(Prevalent.currentOnlineUser.getUsername());

        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("total",Long.valueOf(total));
        orderMap.put("full name",nameEditText.getText().toString());
        orderMap.put("phone", phoneEditText.getText().toString());
        orderMap.put("address", addressEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("payment",paymentmethod);
        orderMap.put("state","not shipped");


        orderRef.updateChildren(orderMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference().child("Cart")
                                    .child("User View")
                                    .child(Prevalent.currentOnlineUser.getUsername())
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(ConfirmFinalOrderActivity.this, "your order has been placed", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);

                                            }
                                        }
                                    });

                        }
                    }
                });
    }
    private void doOnDifficultyLevelChanged(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        if(checkedRadioId== R.id.radioButton_cash) {
           paymentmethod="cash";
        } else if(checkedRadioId== R.id.radioButton_creditcard ) {
            paymentmethod = "credit card";
        }
    }
}
