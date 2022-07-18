package tdao.example.vehicle_oop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvInfo1,tvInfo2,tvInfo3,tvInfo4,tvInfo5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvInfo1= (TextView) findViewById(R.id.tv_info1);
        tvInfo2= (TextView) findViewById(R.id.tv_info2);
        tvInfo3= (TextView) findViewById(R.id.tv_info3);
        tvInfo4= (TextView) findViewById(R.id.tv_info4);
        tvInfo5= (TextView) findViewById(R.id.tv_info5);
    }
}