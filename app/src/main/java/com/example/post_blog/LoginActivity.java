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

public class LoginActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button btn_login,btn_reg;
    private FirebaseAuth mAuth;
    private ProgressBar login_pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.login_email);
        pass = (EditText)findViewById(R.id.login_password);
        btn_login = (Button)findViewById(R.id.login_button);
        btn_reg = (Button)findViewById(R.id.new_reg_button);
        login_pro = (ProgressBar)findViewById(R.id.login_progress);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login_emai = email.getText().toString();
                String reg_emai = pass.getText().toString();

                if(!TextUtils.isEmpty(login_emai)&&!TextUtils.isEmpty(reg_emai)){
                    login_pro.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(login_emai,reg_emai).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendToMain();
                            }else{
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"Error : "+errorMessage,Toast.LENGTH_LONG).show();
                            }
                            login_pro.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            sendToMain();
        }

    }

    private void sendToMain(){
        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

}
