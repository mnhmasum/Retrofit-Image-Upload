package com.hinext.software;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.hinext.software.ModelClass.Datum;
import com.hinext.software.ModelClass.EventModel;
import com.hinext.software.ModelClass.Example;
import com.hinext.software.ModelClass.ResponseModel;
import com.hinext.software.NetworkRelatedClass.NetworkCall;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // if you want to upload only image, make it true. Otherwise to allow any file- false
    boolean isOnlyImageAllowed = true;

    private EditText nameEditText;
    private EditText ageEditText;
    private ImageView imageView;
    private Button uploadButton;
    private TextView responseTextView;

    private String filePath;
    private static final int PICK_PHOTO = 1958;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventModel event) throws ClassNotFoundException {
        /*if (event.isTagMatchWith("response")) {
            String responseMessage = "Response from Server:\n" + event.getMessage();
            responseTextView.setText(responseMessage);
        }*/
        responseTextView.setText(event.success);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        imageView = findViewById(R.id.imageView);
        uploadButton = findViewById(R.id.uploadButton);
        responseTextView = findViewById(R.id.responseTextView);

        verifyStoragePermissions(this);
    }

    public void addPhoto(View view) {

        Intent intent;

        if (isOnlyImageAllowed) {
            // only image can be selected
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            // any type of files including image can be selected
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
        }

        startActivityForResult(intent, PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            Uri imageUri = data.getData();
            filePath = getPath(imageUri);
            imageView.setImageURI(imageUri);
            uploadButton.setVisibility(View.VISIBLE);
        }
    }

    public void uploadButtonClicked(View view) {

        if (nameEditText.getText().toString().length() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please enter Vehicle Number");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }

        responseTextView.setText("Uploading. Please wait...");
        uploadButton.setVisibility(View.INVISIBLE);
        String vname = nameEditText.getText().toString();
        //int age = Integer.parseInt(ageEditText.getText().toString());
        NetworkCall.fileUpload(filePath, vname, LoginActivity.officeNo, LoginActivity.userId);
        NetworkCall.setListener(new NetworkCall.CallBack() {
            @Override
            public void onSuccess(Example responseModel) {

            }

            @Override
            public void onSuccess(ResponseModel responseModel) {
                responseTextView.setText(responseModel.messages.success);
                uploadButton.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                //finish();
            }

            @Override
            public void onFailed(String message) {
                responseTextView.setText("Upload failed");
                uploadButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
