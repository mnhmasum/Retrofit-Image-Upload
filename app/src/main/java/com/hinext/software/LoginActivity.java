package com.hinext.software;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hinext.software.ModelClass.ResponseModel;
import com.hinext.software.NetworkRelatedClass.NetworkCall;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.button_two_button);
        EditText username = findViewById(R.id.edtUserName);
        EditText password = findViewById(R.id.editPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NetworkCall.login(username.getText().toString(), password.getText().toString());
                NetworkCall.setListener(new NetworkCall.CallBack() {
                    @Override
                    public void onSuccess(ResponseModel responseModel) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }
        });

    }

}
