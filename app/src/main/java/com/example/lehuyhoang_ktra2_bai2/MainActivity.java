package com.example.lehuyhoang_ktra2_bai2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView totalOrder;
    private CheckBox pack;
    private EditText name, start_date, search_text;
    private Spinner start_location;
    private String start_location_selected;
    private int year, month, day;
    private RecyclerView recyc;
    SQliteHelper db;
    OrderAdapter adapter;
    private Button add, datepicker, search;
    public static final String[] LIST_OF_LOCATION = {"HaNoi", "DaNang", "HoChiMinh", "Nha Trang"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
        initData();
        HandingEvent();
    }

    private void HandingEvent() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = String.valueOf(name.getText());
                String b = String.valueOf(start_date.getText());
                int c = 0;
                if (pack.isChecked()) {
                    c = 1;
                }
                db.AddOrder(new Order(c, a, start_location_selected, b));
                adapter.setData(db.GetAllOrder());
                totalOrder.setText("List of Data (Total: "+adapter.GetAllOrderHavePack()+" ticket with pack)");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setData(db.SearchData(String.valueOf(search_text.getText())));
            }
        });
    }

    private void initVar() {
        name = findViewById(R.id.name);
        start_location = findViewById(R.id.start_location);
        start_date = findViewById(R.id.start_date);
        pack = findViewById(R.id.pack);
        recyc = findViewById(R.id.recy);
        add = findViewById(R.id.add_btn);
        datepicker = findViewById(R.id.datepicker);
        totalOrder = findViewById(R.id.total);
        search_text = findViewById(R.id.search_text);
        search = findViewById(R.id.search_btn);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, LIST_OF_LOCATION);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_location.setAdapter(adapter);

        start_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start_location_selected = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        start_date.setText(dayOfMonth +"/"+(month+1)+"/"+year);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    private void initData() {
        db = new SQliteHelper(MainActivity.this);
        adapter = new OrderAdapter(MainActivity.this);
        adapter.setData(db.GetAllOrder());
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyc.setLayoutManager(manager);
        recyc.setAdapter(adapter);
        totalOrder.setText("List of Data (Total: "+adapter.GetAllOrderHavePack()+" ticket with pack)");
    }
}