package appewtc.masterung.cyclecountsystem;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ServiceCountActivity extends AppCompatActivity {

    //Explicit
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_count);

        //Bind Widget
        button = (Button) findViewById(R.id.button5);

        //Button Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "BAR_CODE_MODE");
                    startActivityForResult(intent, 0);

                } catch (ActivityNotFoundException e) {
                    Log.d("7octV3", "e ==> " + e.toString());
                }

            }   // onClick
        });

    }   // Main Method

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == RESULT_OK)) {

            String dataReaded = data.getStringExtra("SCAN_RESULT");
            Log.d("7octV3", "dataReaded ==> " + dataReaded);

        } else {
            Log.d("7octV3", "False");
        }

    }   // onActivity

}   // Main Class
