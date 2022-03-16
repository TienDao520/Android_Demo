package tdao.example.fragmentdemo_tiendao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {
    lateinit var textViewFragStatus:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
    override fun onResume() {
        super.onResume()
        // Lifecycle of a fragment
        textViewFragStatus =findViewById(R.id.textViewFragMessage)
        textViewFragStatus.setText(getString(R.string.secondactivity))
    }


    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.imageButtonFragMsgToast -> {
                Toast.makeText(this, getString(R.string.toast), Toast.LENGTH_LONG).show()
            }
            R.id.imageButtonFragMsgSnackbar -> {
                val snackbar = Snackbar
                    .make(view, getString(R.string.snackbar), Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        val snackbar1 = Snackbar.make(
                            view,
                            "Undo is selected!",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar1.show()
                    }
                snackbar.show()
            }
        }
    }

    fun onButtonGo(view: View) {
        finish()
    }

}