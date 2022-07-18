package tdao.example.vehicle_oop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvInfo1, tvInfo2, tvInfo3, tvInfo4, tvInfo5;
    Vehicle v1;
    Car c1;
    Mercedes c200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvInfo1 = (TextView) findViewById(R.id.tv_info1);
        tvInfo2 = (TextView) findViewById(R.id.tv_info2);
        tvInfo3 = (TextView) findViewById(R.id.tv_info3);
        tvInfo4 = (TextView) findViewById(R.id.tv_info4);
        tvInfo5 = (TextView) findViewById(R.id.tv_info5);

        v1 = new Vehicle("Test", 25, 120, 100, 4, false);
        c1 = new Car("TestCar",30, 160, 120,4,false,"V2");
        c200 = new Mercedes("C200", 70,240,200,4,true, "v8", "Black & Red", "LED", "Black");

        tvInfo1.setText(v1.toString());
        tvInfo2.setText(c1.toString());
        tvInfo3.setText(c200.toString());
        tvInfo5.setText(v1.sound());

    }
}