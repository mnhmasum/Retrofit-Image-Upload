package com.hinext.software;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hinext.software.ModelClass.Datum;
import com.hinext.software.ModelClass.Example;
import com.hinext.software.ModelClass.ResponseModel;
import com.hinext.software.NetworkRelatedClass.NetworkCall;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static int officeNo = 0;
    public static int userId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.button_two_button);
        EditText username = findViewById(R.id.edtUserName);
        EditText password = findViewById(R.id.editPassword);
        TextView status = findViewById(R.id.textViewLoading);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setText("Logging in...");
                login.setVisibility(View.INVISIBLE);
                NetworkCall.login(username.getText().toString(), password.getText().toString());
                NetworkCall.setListener(new NetworkCall.CallBack() {
                    @Override
                    public void onSuccess(ResponseModel responseModel) {
                        if (responseModel.status == 201) {
                            officeNo = responseModel.data.office;
                            userId = responseModel.data.userId;
                            startActivity(new Intent(LoginActivity.this, ListActivity.class));
                            finish();
                        } else {
                            status.setText("Login Failed...");
                            login.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onSuccess(Example item) {

                    }

                    @Override
                    public void onFailed(String message) {
                        login.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }

}
