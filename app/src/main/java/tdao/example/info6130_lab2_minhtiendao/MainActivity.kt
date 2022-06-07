package tdao.example.info6130_lab2_minhtiendao

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var animationView: AnimationView
    var mediaPlayer: MediaPlayer? = null
    var txtRotate : TextView? = null
    var animRotate : Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animationView = findViewById(R.id.animationView)
        txtRotate = findViewById(R.id.textViewAuthor) as TextView?
        // Added the animation from the anim lib folder
        animRotate = AnimationUtils.loadAnimation (applicationContext, R.anim.rotate)
        mediaPlayer = MediaPlayer.create(this,R.raw.beat_it)
    }

    fun onBtnStop(view: View) {
        Log.d("Main","In plause Stop")
        stopMusic()
        animationView.pauseAnimation()
        txtRotate?.startAnimation(animRotate)

    }
    fun playMusic(){
        mediaPlayer?.start()
    }
    fun stopMusic(){
        if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        playMusic()
        txtRotate?.clearAnimation()
        return super.onTouchEvent(event)
    }
}