package com.example.post_blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_emai,reg_pass,reg_confirmp;
    private Button btn_reg_login,btn_reg;
    private FirebaseAuth mAuth;
    private ProgressBar reg_pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        reg_emai = (EditText)findViewById(R.id.reg_email);
        reg_pass = (EditText)findViewById(R.id.reg_password);
        reg_confirmp = (EditText)findViewById(R.id.confirm_password);
        btn_reg_login = (Button)findViewById(R.id.reg_login_button);
        btn_reg = (Button)findViewById(R.id.reg_button);
        reg_pro = (ProgressBar)findViewById(R.id.reg_progress);

        btn_reg_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login_emai = reg_emai.getText().toString();
                String reg_pas = reg_pass.getText().toString();
                String confirm_p = reg_confirmp.getText().toString();

                if(!TextUtils.isEmpty(login_emai)&&!TextUtils.isEmpty(reg_pas)&&!TextUtils.isEmpty(confirm_p)){
                    if(reg_pas.equals(confirm_p)){
                        reg_pro.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(login_emai, reg_pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
                                    startActivity(setupIntent);
                                    finish();
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                                }
                                reg_pro.setVisibility(View.INVISIBLE);

                            }
                        });
                    }else {
                        Toast.makeText(RegisterActivity.this,"Password & Confirm Password should be same",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            sendToMain();
        }
    }
    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
