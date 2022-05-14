package tdao.example.info6130_lab1_minhtiendao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {

    lateinit var surveyResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Watch for button clicks.
        val surveyBtn = findViewById<Button>(R.id.surveyBtn)
        surveyBtn.setOnClickListener { showDialog() }
        val introBtn = findViewById<Button>(R.id.introBtn)
        introBtn.setOnClickListener { showIntro() }
        surveyResult = findViewById(R.id.resultTextView)
    }

    fun showDialog() {
        val newFragment: DialogFragment? = SurveyFragment
            .newInstance(R.string.survey_title)
        newFragment!!.show(supportFragmentManager, "dialog")
    }

    fun showIntro() {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }

    fun doPositiveClick(whichButton:Int) {
        Log.i("DialogFragment", "Positive click!")
        surveyResult.text = "Survey result: ${whichButton +1}"
    }

    fun doNegativeClick() {
        Log.i("DialogFragment", "Negative click!")
    }


}