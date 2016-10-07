package appewtc.masterung.cyclecountsystem;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private Button signInButton, signUpButton, synButton;
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] loginStrings;
    private boolean userABoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        synButton = (Button) findViewById(R.id.button3);
        userEditText = (EditText) findViewById(R.id.editText6);
        passwordEditText = (EditText) findViewById(R.id.editText7);

        //Request Database
        myManage = new MyManage(MainActivity.this);

        //Tester Add Value to SQLite
        //testAddValueToSQLite();


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

            //Delete userTABLE
            deleteValueSQLite(MyManage.table_userTABLE);

            //Synchronize userTABLE
            SynUser synUser = new SynUser(MainActivity.this);
            MyConstant myConstant = new MyConstant();
            synUser.execute(myConstant.getUrlGetUser());

        } else {
            // Internet False
            MyAlert myAlert = new MyAlert(MainActivity.this, R.drawable.kon48,
                    "Internet False", "Cannot Connected Internet");
            myAlert.myDialog();
        }

        //Sign In Controller
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Value from Edit Text
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("")) {
                    // Have Space
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            R.drawable.bird48, "Have Space", "Please Fill All EveryBlank");
                    myAlert.myDialog();
                } else {
                    // No Space
                    checkUserAnPass();
                }

            }   // onClick
        });


    }   // Main Method

    private void checkUserAnPass() {

        //Read All SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);
        cursor.moveToFirst();
        Log.d("7octV2", "cursor.getCont ==> " + cursor.getCount());

        if (cursor.getCount() == 0) {
            //User False
            MyAlert myAlert = new MyAlert(MainActivity.this, R.drawable.rat48,
                    "User False", "No " + userString + " in my Database");
            myAlert.myDialog();
        } else {
            // User True
            loginStrings = new String[cursor.getColumnCount()];
            for (int i=0;i<loginStrings.length;i+=1) {

                loginStrings[i] = cursor.getString(i);
                Log.d("7octV2", "loginStrings(" + i + ") = " + loginStrings[i]);

            }   // for

            if (!passwordString.equals(loginStrings[4])) {
                MyAlert myAlert = new MyAlert(MainActivity.this, R.drawable.rat48,
                        "Password False", "Please Try again Password False");
                myAlert.myDialog();
            } else {
                //Pass True
                Toast.makeText(MainActivity.this,
                        "Welcome " + loginStrings[1],
                        Toast.LENGTH_SHORT).show();


            }


        }   // if








    }   // checkUserAnPass

    private class SynUser extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;

        public SynUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("7octV1", "e doInBack ==> " + e.toString());
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("7octV1", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i += 1) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String strName = jsonObject.getString(MyManage.column_Name);
                    String strID_card = jsonObject.getString(MyManage.column_ID_card);
                    String strUser = jsonObject.getString(MyManage.column_User);
                    String strPassword = jsonObject.getString(MyManage.column_Password);
                    String strPosition = jsonObject.getString(MyManage.column_Position);
                    String strStatus = jsonObject.getString(MyManage.column_Status);

                    myManage.addUserTABLE(strName, strID_card, strUser,
                            strPassword, strPosition, strStatus);

                }   // for

            } catch (Exception e) {
                Log.d("7octV1", "e onPost ==> " + e.toString());
            }

        }   // onPost

    }   // SynUser Class


    private void deleteValueSQLite(String strTable) {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(strTable, null, null);

    }

    private void testAddValueToSQLite() {

        myManage.addUserTABLE("Name", "idCard", "user", "pass", "position", "status");
        myManage.addTb_CountTxns("124", "lotID", "Quan", "Price", "UOM", "Date", "status");
        myManage.addTb_CountMst("111", "SheIl", "date", "Count", "Check", "status");

    }

    private boolean checkInternet() {

        try {

            boolean bolInternet = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            Log.d("6octV2", "networkInfo ==> " + (networkInfo != null));
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
