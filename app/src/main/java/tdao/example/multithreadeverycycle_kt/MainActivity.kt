package tdao.example.multithreadeverycycle_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var titleTextView: TextView
    lateinit var mainHandler: Handler
    val delayTime = 1000
    private var secondsLeft = 60

    private val updateTextTask = object: Runnable {
        override fun run() {
            minusOneSecond()
            mainHandler.postDelayed(this, delayTime.toLong())
        }
    }

    private fun minusOneSecond() {
        if (secondsLeft > 0){
            secondsLeft -=1

            titleTextView.text= secondsLeft.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleTextView = findViewById(R.id.titleTextView)
        mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }
}