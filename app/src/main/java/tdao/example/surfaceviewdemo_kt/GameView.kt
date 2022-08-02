package tdao.example.surfaceviewdemo_kt

import android.content.Context
import android.graphics.*
import android.os.Build
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

    //Step2-5_2: Create mFlashlightCone
    private var mFlashlightCone: FlashlightCone? = null

    //Step2-5_4: Create mPath
    private val mPath: Path

    //Step2-6_1: Variables for View size
    private var mViewWidth = 0
    private var mViewHeight = 0


    //Step2-6_2: Calculate View size
    /**
     * We cannot get the correct dimensions of views in onCreate because
     * they have not been inflated yet. This method is called every time the
     * size of a view changes, including the first time after it has been
     * inflated.
     *
     * @param w Current width of view.
     * @param h Current height of view.
     * @param oldw Previous width of view.
     * @param oldh Previous height of view.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h
        mFlashlightCone = FlashlightCone(mViewWidth, mViewHeight)

        // Set font size proportional to view size.
        mPaint.textSize = (mViewHeight / 5).toFloat()
        mBitmap = BitmapFactory.decodeResource(
            mContext.resources, R.drawable.android
        )
    }


    /**
     * Runs in a separate thread.
     * All drawing happens here.
     */
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


                //Step2-5_3: Helper variables for performance.
                val x: Int? = mFlashlightCone?.getX()
                val y: Int? = mFlashlightCone?.getY()
                val radius: Int? = mFlashlightCone?.getRadius()

                //Step2-5_6: Add clipping region and fill rest of the canvas with black.
                //CCW: counter-clockwise
                mPath.addCircle(x!!.toFloat(), y!!.toFloat(), radius!!.toFloat(), Path.Direction.CCW)

                // The method clipPath(path, Region.Op.DIFFERENCE) was
                // deprecated in API level 26. The recommended alternative
                // method is clipOutPath(Path), which is currently available
                // in API level 26 and higher.
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    canvas.clipPath(mPath, Region.Op.DIFFERENCE)
                } else {
                    canvas.clipOutPath(mPath)
                }
                canvas.drawColor(Color.BLACK)

            }
        }

    }

    init {
//        mSurfaceHolder = getHolder() //from getHolder()
        mSurfaceHolder = holder //from getHolder()
        //Step2-4_2: Declare initial mPaint
        mPaint = Paint()
        mPaint.color = Color.DKGRAY
        //Step2-5_5: init value mPath
        mPath = Path()

    }



}