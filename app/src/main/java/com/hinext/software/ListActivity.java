package com.hinext.software;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.software.ModelClass.Datum;
import com.hinext.software.ModelClass.Example;
import com.hinext.software.ModelClass.ResponseModel;
import com.hinext.software.NetworkRelatedClass.NetworkCall;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Button login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        callShowAPI();
    }

    private void callShowAPI() {
        NetworkCall.setListener(new NetworkCall.CallBack() {
            @Override
            public void onSuccess(ResponseModel responseModel) {

            }

            @Override
            public void onSuccess(Example responseModel) {
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                List<Datum> items = new ArrayList();
                ImageGalleryAdapter2 adapter2 = new ImageGalleryAdapter2(responseModel.getData(), ListActivity.this, item -> {

                });

                recyclerView.setAdapter(adapter2);
            }

            @Override
            public void onFailed(String message) {

            }
        });
        NetworkCall.show(LoginActivity.userId);
    }
}
