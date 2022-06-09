package tdao.example.info6130_midterm_minhtiendao

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {

    //Rotate: Step1: declare variables
    var startSpin : Button? = null
    var stopSpin : Button? = null
    var speedSpin : Button? = null
    var animRotate1 : Animation? = null
    var animRotate2 : Animation? = null
    var animRotate3: Animation? = null
    var animRotate4 : Animation? = null
    var animFlipRepeat : Animation? = null
    var animFadeOutRepeat : Animation? = null
    var imageWheel:ImageView? = null
    var nameTextView:TextView? = null
    var stateTextView:TextView? = null

    var mediaPlayer: MediaPlayer? = null
    var stateFlag:Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startSpin=findViewById(R.id.startBtn)
        stopSpin=findViewById(R.id.stopBtn)
        speedSpin=findViewById(R.id.speedBtn)

        imageWheel=findViewById(R.id.imageWheel)
        nameTextView = findViewById(R.id.nameTextView)
        stateTextView = findViewById(R.id.stateTextView)
        // Added the animation from the anim lib folder
//        animRotate = AnimationUtils.loadAnimation (applicationContext, R.anim.rotate)
//        animRotate = AnimationUtils.loadAnimation (applicationContext, R.anim.flip)
        animRotate1 = AnimationUtils.loadAnimation (applicationContext, R.anim.rotate_1)
        animRotate2 = AnimationUtils.loadAnimation (applicationContext, R.anim.rotate_2)
        animRotate3 = AnimationUtils.loadAnimation (applicationContext, R.anim.rotate_3)
        animRotate4 = AnimationUtils.loadAnimation (applicationContext, R.anim.rotate_4)
//        var aimTest = ObjectAnimator.ofFloat(imageWheel, "rotation", 0, 360);

        animFlipRepeat = AnimationUtils.loadAnimation (applicationContext, R.anim.flip_repeat)
        animFadeOutRepeat = AnimationUtils.loadAnimation (applicationContext, R.anim.fade_out_repeat)


        mediaPlayer = MediaPlayer.create(this,R.raw.spinning_wheel)
    }

    fun onStartBtn(view: View) {
        imageWheel?.startAnimation(animRotate1)
        nameTextView?.clearAnimation()
        stopSpin?.clearAnimation()
        playMusic()
        mediaPlayer?.playbackParams = mediaPlayer!!.playbackParams.setSpeed(0.75f)
        stateFlag =1
        stateTextView?.text = "Current State: Speed 1"
    }
    fun onStopBtn(view: View) {
        imageWheel?.clearAnimation()
        nameTextView?.startAnimation(animFlipRepeat)
        stopSpin?.startAnimation(animFadeOutRepeat)
        stopMusic()
        stateFlag =0
        stateTextView?.text = "Current State: Stop"

    }
    fun onSpeedBtn(view: View) {
        if (stateFlag == 1) {
            val newFragment: DialogFragment? = SpeedFragment
                .newInstance(R.string.speed_title)
            newFragment!!.show(supportFragmentManager, "dialog")
        }
    }


    fun doPositiveClick(whichButton:Int) {
        Log.i("DialogFragment", "Positive click!")
//        surveyResult.text = "Survey result: ${whichButton +1}"
        when (whichButton) {
            0 -> {
                imageWheel?.startAnimation(animRotate1)
                stateTextView?.text = "Current State: Speed ${whichButton +1}"
                mediaPlayer?.playbackParams = mediaPlayer!!.playbackParams.setSpeed(0.75f)
            }

            1 -> {
                imageWheel?.startAnimation(animRotate2)
                stateTextView?.text  = "Current State: Speed ${whichButton +1}"
                mediaPlayer?.playbackParams = mediaPlayer!!.playbackParams.setSpeed(1.25f)
            }
            2 -> {
                imageWheel?.startAnimation(animRotate3)
                stateTextView?.text = "Current State: Speed ${whichButton +1}"
                mediaPlayer?.playbackParams = mediaPlayer!!.playbackParams.setSpeed(1.5f)
            }
            3 -> {
                imageWheel?.startAnimation(animRotate4)
                stateTextView?.text = "Current State: Speed ${whichButton +1}"
                mediaPlayer?.playbackParams = mediaPlayer!!.playbackParams.setSpeed(2f)
            }
            else -> {
                print("Just for test")
            }
        }

    }

    fun doNegativeClick() {
        Log.i("DialogFragment", "Negative click!")
    }


    private fun playMusic(){
        mediaPlayer?.start()
    }
    private fun stopMusic(){
        if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
    }
}