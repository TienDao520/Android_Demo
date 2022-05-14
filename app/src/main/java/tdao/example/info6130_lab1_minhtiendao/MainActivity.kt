package tdao.example.info6130_lab1_minhtiendao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Watch for button clicks.
        val surveyBtn = findViewById<Button>(R.id.surveyBtn)
        surveyBtn.setOnClickListener { showDialog() }
        val introBtn = findViewById<Button>(R.id.introBtn)
        introBtn.setOnClickListener { showIntro() }
    }

    fun showDialog() {

    }

    fun showIntro() {

    }


}