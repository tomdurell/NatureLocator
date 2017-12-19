package tomdurell.naturelocator;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Launch click event permissions - Changes value of text in this activity and also passes it to another activity through intent
    public void permissionsButton(View view) {
        // create an intent to start the activity called userPermissions
        Intent intent = new Intent(this, permissionsActivity.class);
        // run Permissions activity
        startActivity(intent);
    }

    public void recordIntent(View view) {
        // create an intent to start the activity called userPermissions
        Intent intent = new Intent(this, recordingActivity.class);
        // run Permissions activity
        startActivity(intent);
    }

    public void cameraIntent(View view) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            // Get Extra from the intent
            Bundle extras = data.getExtras();
            // Get the returned image from extra
            Bitmap bmp = (Bitmap) extras.get("data");

            ImageView naturePhoto = (ImageView) findViewById(R.id.ReturnedImageView);
            naturePhoto.setImageBitmap(bmp);

            String filename = "/storage/emulated/0/Android/data/naturePhoto.png";
            //File storageDirectory = Environment.getDataDirectory();
            //File fullPath = new File(storageDirectory, filename);
            File fullPath = new File(filename);
            //verifyStoragePermissions(this);


            try {
                FileOutputStream out = new FileOutputStream(fullPath);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
          final int REQUEST_EXTERNAL_STORAGE = 1;
          String[] PERMISSIONS_STORAGE = {
                  Manifest.permission.READ_EXTERNAL_STORAGE,
                  Manifest.permission.WRITE_EXTERNAL_STORAGE
          };
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
