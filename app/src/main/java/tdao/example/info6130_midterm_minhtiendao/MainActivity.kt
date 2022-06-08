package tdao.example.info6130_midterm_minhtiendao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //Rotate: Step1: declare variables
    var startSpin : Button? = null
    var stopSpin : Button? = null
    var speedSpin : Button? = null
    var animRotate : Animation? = null
    var imageWheel:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startSpin=findViewById(R.id.startBtn)
        stopSpin=findViewById(R.id.stopBtn)
        speedSpin=findViewById(R.id.speedBtn)

        imageWheel=findViewById(R.id.imageWheel)

        // Added the animation from the anim lib folder
        animRotate = AnimationUtils.loadAnimation (applicationContext, R.anim.rotate)


    }

    fun onStartBtn(view: View) {
        imageWheel?.startAnimation(animRotate)
    }
    fun onStopBtn(view: View) {
        imageWheel?.clearAnimation()
    }
    fun onSpeedBtn(view: View) {}
}