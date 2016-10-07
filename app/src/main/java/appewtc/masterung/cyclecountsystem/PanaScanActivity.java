package appewtc.masterung.cyclecountsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class PanaScanActivity extends AppCompatActivity {

    //Explicit
    private EditText editText;
    private ListView listView;
    private Button button;
    private ArrayList<String> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pana_scan);

        //Bind Widget
        editText = (EditText) findViewById(R.id.editText8);
        listView = (ListView) findViewById(R.id.my_listview);
        button = (Button) findViewById(R.id.button6);

        stringArrayList = new ArrayList<String>();

        //Button Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strReadCode = editText.getText().toString().trim();

                Log.d("7octV4", "strReadCode ==> " + strReadCode);

                createListView(strReadCode);

            }   // onClick
        });


    }   // Main Method

    private void createListView(String strReadCode) {


        stringArrayList.add(strReadCode);
        Log.d("7octV4", "arrayList ==> " + stringArrayList.size());

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(PanaScanActivity.this,
                android.R.layout.simple_list_item_1, stringArrayList);
        listView.setAdapter(stringArrayAdapter);
    }   // createListView


}   // Main Class
