package tdao.example.surfaceviewdemo_kt

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

//Step1: @JvmOverloads annotation for input parameters of contructors
class GameView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) : SurfaceView(mContext, attrs), Runnable {

    //Create a flag for run/pause progress
    private var mRunning = false
    private val mSurfaceHolder: SurfaceHolder

    //Step2-4_1: Create mPaint: Paint
    private val mPaint: Paint

    //Create bitmap variable
    private var mBitmap: Bitmap? = null
    private var mBitmapX = 0
    private var mBitmapY = 0

    override fun run() {
        //Step2-1: Declare canvas variable
        var canvas: Canvas
        while(mRunning){
            // If we can obtain a valid drawing surface...
            //Step2-2: Check whether a valid Surface is available
            if (mSurfaceHolder.surface.isValid) {

                //Step2-3: Lock the Canvas
                canvas = mSurfaceHolder.lockCanvas()

                //Step2-4_3: Fill the canvas with color and draw the bitmap.
                canvas.save()
                canvas.drawColor(Color.GREEN)
                canvas.drawBitmap(mBitmap!!, mBitmapX.toFloat(), mBitmapY.toFloat(), mPaint)
            }
        }

    }

    init {
//        mSurfaceHolder = getHolder() //from getHolder()
        mSurfaceHolder = holder //from getHolder()
        //Step2-4_2: Declare initial mPaint
        mPaint = Paint()
        mPaint.color = Color.DKGRAY

    }



}