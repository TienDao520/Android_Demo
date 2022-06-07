package tdao.example.viewanimationexample_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //FadeIn: Step1: declare variables
    var btnFadeIn : Button? = null
    var txtFadeIn : TextView? = null
    var animFadeIn : Animation? = null

    //FadeOut: Step1: declare variables
    var btnFadeOut : Button? = null
    var txtFadeOut : TextView? = null
    var animFadeOut : Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Fade in
        btnFadeIn = findViewById(R.id.btnFadeIn) as Button
        txtFadeIn = findViewById(R.id.txtFadeIn) as TextView
        txtFadeIn!!.alpha = 0f
        //FadeIn: Step2: Added the animation from the anim lib folder
        animFadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        btnFadeIn!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?)
            {
                txtFadeIn!!.visibility = View.VISIBLE
                txtFadeIn!!.alpha = 1f
                txtFadeIn!!.startAnimation(animFadeIn)
            }
        })

        //Fade out
        btnFadeOut = findViewById(R.id.btnFadeOut) as Button
        txtFadeOut = findViewById(R.id.txtFadeOut) as TextView
        animFadeOut = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
        btnFadeOut!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?)
            {
                txtFadeOut!!.visibility = View.VISIBLE
                txtFadeOut!!.startAnimation(animFadeOut)
            }
        })
    }
}