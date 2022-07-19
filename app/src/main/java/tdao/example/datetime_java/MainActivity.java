package tdao.example.datetime_java;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTime, editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextTime= findViewById(R.id.editTextTime);
        editTextDate= findViewById(R.id.editTextDate);
        editTextTime.setOnClickListener(this);
        editTextDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        if(view == editTextTime) {
            TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    editTextTime.setText(hour+":"+minute);
                }
            },hh,mm,false);
            timeDialog.show();
        }
        else if(view == editTextDate) {
            Calendar calendar1 = Calendar.getInstance();
            int y = calendar1.get(Calendar.YEAR);
            int m = calendar1.get(Calendar.MONTH);
            int d = calendar1.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    editTextDate.setText(yy+"/"+(mm+1)+"/"+dd);
                }
            },y,m,d);
            dialog.show();
        }
    }
}