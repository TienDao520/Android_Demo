package tdao.example.surfaceviewdemo_kt

import android.content.Context
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

    private val mSurfaceHolder: SurfaceHolder

    override fun run() {
        TODO("Not yet implemented")
    }

    init {
        mSurfaceHolder = holder //from getHolder()
    }



}