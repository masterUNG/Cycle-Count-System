package appewtc.masterung.cyclecountsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

                editText.setText("");   // Clear Edit Text

                if(editText.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }

                if (strReadCode.equals("")) {
                    //Have Space
                    Toast.makeText(PanaScanActivity.this, "Scan ก่อนซิ", Toast.LENGTH_SHORT).show();
                } else {
                    createListView(strReadCode);
                }



            }   // onClick
        });


    }   // Main Method

    private void createListView(String strReadCode) {


        stringArrayList.add(strReadCode);
        Log.d("7octV4", "arrayList ==> " + stringArrayList.size());

        buildListView();

    }   // createListView

    private void buildListView() {

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(PanaScanActivity.this,
                android.R.layout.simple_list_item_1, stringArrayList);
        listView.setAdapter(stringArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                stringArrayList.remove(i);
                buildListView();

            }   // onItmeClick
        });

    }


}   // Main Class
