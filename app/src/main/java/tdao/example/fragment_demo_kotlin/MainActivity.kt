package tdao.example.fragment_demo_kotlin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var firstFragmentBtn: Button? = null
    var secondFragmentBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstFragmentBtn = findViewById(R.id.fragment1btn)
        secondFragmentBtn = findViewById(R.id.fragment2btn)
    }
}