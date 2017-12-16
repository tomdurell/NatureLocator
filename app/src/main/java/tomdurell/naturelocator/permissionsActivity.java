package tomdurell.naturelocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;


public class permissionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //create the TestActivity activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
    }

    /** Called when user touches the Permissions button */
    public void addLocation(View view) {
        boolean enabled = ((Switch) view).isChecked();

        if (enabled) {
            ActivityCompat.requestPermissions(permissionsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    public void addCamera(View view) {
        boolean enabled = ((Switch) view).isChecked();

        if (enabled) {
            ActivityCompat.requestPermissions(permissionsActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }
    public void addMic(View view) {
        boolean enabled = ((Switch) view).isChecked();

        if (enabled) {
            ActivityCompat.requestPermissions(permissionsActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the work!
                    // display short notification stating permission granted
                    Toast.makeText(permissionsActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(permissionsActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }






}
