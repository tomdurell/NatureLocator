package tomdurell.naturelocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

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
}
