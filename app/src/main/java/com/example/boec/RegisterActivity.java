package com.example.boec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.boec.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputUsername, InputPassword, InputAddress;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button)findViewById(R.id.register_btn);
        InputName = (EditText)findViewById(R.id.register_fullname_input);
        InputUsername = (EditText)findViewById(R.id.register_username_input);
        InputPassword = (EditText)findViewById(R.id.register_password_input);
        InputAddress = (EditText)findViewById(R.id.register_address_input);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount(){
        String name = InputName.getText().toString();
        String username = InputUsername.getText().toString();
        String password = InputPassword.getText().toString();
        String address = InputAddress.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else{
            //create new accout, save infor user
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("please wait, we are checking your information");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            User user = new User.UserBuilder(username, password).address(address).name(name).build();
            ValidInsert(user);

        }
    }
    private void ValidInsert(final User user){
        String name = user.getName();
        String username = user.getUsername();
        String password = user.getPassword();
        String address = user.getAddress();
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("User").child(user.getUsername()).exists())){
                    HashMap<String, Object> userdataMap =new HashMap<>();
                    userdataMap.put("username",user.getUsername());
                    userdataMap.put("password",user.getPassword());
                    userdataMap.put("name",user.getName());
                    userdataMap.put("address",user.getAddress());

                    RootRef.child("User").child(user.getUsername()).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Create account is success.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();;
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network error, try again in later...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(RegisterActivity.this, "This "+user.getUsername()+ "is existed", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Try other username", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void insertFB(User user){

    }
}
