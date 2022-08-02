package tdao.example.surfaceviewdemo_kt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) : SurfaceView(mContext, attrs), Runnable {

    //Create a flag for run/pause progress
    private var mRunning = false
    private val mSurfaceHolder: SurfaceHolder

    override fun run() {
        //Declare canvas variable
        var canvas: Canvas
        while(mRunning){
            // If we can obtain a valid drawing surface...
            //Check whether a valid Surface is available
            if (mSurfaceHolder.surface.isValid) {

            }
        }

    }

    init {
//        mSurfaceHolder = getHolder() //from getHolder()
        mSurfaceHolder = holder //from getHolder()
    }



}