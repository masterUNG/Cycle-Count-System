package appewtc.masterung.cyclecountsystem;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private Button signInButton, signUpButton, synButton;
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        synButton = (Button) findViewById(R.id.button3);

        //Request Database
        myManage = new MyManage(MainActivity.this);


        //Sign Up Controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }   // onClick
        });

        //Check Internet
        if (checkInternet()) {
            //Internet OK
            Toast.makeText(this, getResources().getString(R.string.connected_ok), Toast.LENGTH_SHORT).show();

        } else {
            // Internet False
            MyAlert myAlert = new MyAlert(MainActivity.this, R.drawable.kon48,
                    "Internet False", "Cannot Connected Internet");
            myAlert.myDialog();
        }


    }   // Main Method

    private boolean checkInternet() {

        try {

            boolean bolInternet = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            Log.d("6octV2", "networkInfo ==> " +(networkInfo != null));
            Log.d("6octV2", "Connectd ==> " + networkInfo.isConnected());

            if ((networkInfo != null) && (networkInfo.isConnected())) {
                bolInternet = true;
            }

            Log.d("6octV2", "bolInternet  ==> " + bolInternet);

            return bolInternet;

        } catch (Exception e) {
            return false;
        }

    }

}   // Main Class
