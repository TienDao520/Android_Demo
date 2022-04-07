package tdao.example.gesturedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        Log.i("onDown", "onDown: $p0")
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
        Log.i("onShowPress", "onShowPress: $p0")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        Log.i("onSingleTapUp", "onSingleTapUp: $p0")
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.i("onScroll", "onScroll: $p0 $p1")
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        Log.i("onLongPress", "onLongPress: $p0")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.i("onFling", "onFling: $p2 $p3")
        return true
    }

}