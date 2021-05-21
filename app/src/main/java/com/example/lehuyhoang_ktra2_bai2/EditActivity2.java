package com.example.lehuyhoang_ktra2_bai2;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity2 extends AppCompatActivity {
    private EditText name, start_date;
    private Spinner start_location;
    private Button edit_btn, del_btn, datepicker;
    private int year, month, day;
    private CheckBox pack;
    private int ID;
    private String start_location_selected;
    private SQliteHelper db = new SQliteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);
        initVar();
        initData();
        handlerEvent();
    }

    private void handlerEvent() {
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = String.valueOf(name.getText());
                String b = String.valueOf(start_date.getText());
                int c = 0;
                if (pack.isChecked()) {
                    c = 1;
                }
                int g = db.EditData(new Order(ID, c, a, start_location_selected, b));
                if (g>0) {
                    startActivity(new Intent(EditActivity2.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Save edit error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int g = db.DeleteData(ID);
                if (g>0) {
                    startActivity(new Intent(EditActivity2.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Delete error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//    String[] lo = new ArrayListay{"HaNoi", "DaNang", "HoChiMinh", "Nha Trang"};

    private void initData() {
        ArrayList<String> m = new ArrayList<>();
        m.add("HaNoi");
        m.add("DaNang");
        m.add("HoChiMinh");
        m.add("Nha Trang");

        Order x;
        Intent intent = getIntent();
        x = (Order) intent.getSerializableExtra("data");
        ID = x.getId();
        name.setText(x.getName());
        start_location.setSelection(m.indexOf(x.getStart_location()));
        start_date.setText(x.getStart_date());
        if (x.getPack() != 0) {
            pack.setChecked(true);
            del_btn.setVisibility(View.GONE);
        } else {
            pack.setChecked(false);
            del_btn.setVisibility(View.VISIBLE);
        }
    }

    private void initVar() {
        name = findViewById(R.id.edit_name);
        start_location = findViewById(R.id.edit_start_location);
        start_date = findViewById(R.id.edit_start_date);
        pack = findViewById(R.id.edit_pack);
        edit_btn = findViewById(R.id.edit_btn);
        del_btn = findViewById(R.id.edit_del);
        datepicker = findViewById(R.id.edit_datepicker);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, MainActivity.LIST_OF_LOCATION);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
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

                DatePickerDialog dialog = new DatePickerDialog(EditActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        start_date.setText(dayOfMonth +"/"+(month+1)+"/"+year);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }
}