package tdao.example.layout_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private Spinner sp2,sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout2);

        sp2=findViewById(R.id.sp2);
        sp1=findViewById(R.id.sp1);
        String[] list = {"PTIT", "HUST", "NEU", "FTU"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.items,list);
        sp2.setAdapter(adapter);

        sp1.setAdapter(adapter);
        String[] list1 = getResources().getStringArray(R.array.country);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,R.layout.items, list1);
        sp1.setAdapter(adapter1);
    }
}