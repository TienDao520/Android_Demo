package tdao.example.prepgesturesactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var mDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize the lateinits
        mDetector = GestureDetector(this,this)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.d("onFling", "onFling: $p2 $p3")
        if(p2 > 5000)  // flinging right
        {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        else if(p2 < -5000)  // flinging left
        {
            val intent = Intent(this, BackActivity::class.java)
            startActivity(intent)
        }
        return true
    }
    override fun onDown(p0: MotionEvent?): Boolean {
        Log.d("onDown", "onDown: $p0")
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
        Log.d("onShowPress", "onShowPress: $p0")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        Log.d("onSingleTapUp", "onSingleTapUp: $p0")
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.d("onScroll", "onScroll: $p0")
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        Log.d("onLongPress", "onLongPress: $p0")
    }

}