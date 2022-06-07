package tdao.example.info6130_lab2_minhtiendao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var animationView: AnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animationView = findViewById(R.id.animationView)
    }

    fun onBtnStop(view: View) {
        Log.d("Main","In plause Stop")
        animationView.pauseAnimation()
    }

}