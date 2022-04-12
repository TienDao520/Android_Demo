package tdao.example.fragmentdemo_tiendao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var textViewStatus: TextView
    lateinit var textViewFragStatus: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewStatus =findViewById(R.id.textViewMainStatus)
        // program will crash because the widget hasn't memory at this point
//        textViewFragStatus =findViewById(R.id.textViewFragMessage)
    }

    override fun onResume() {
        super.onResume()
        // Lifecycle of a fragment
        textViewFragStatus =findViewById(R.id.textViewFragMessage)

    }
    fun onButtonClick(view: View) {
        when(view.id){
            R.id.imageButtonFragMsgToast-> {
                Toast.makeText(this,getString(R.string.toast), Toast.LENGTH_LONG).show()
                textViewStatus.setText(getString(R.string.toast))
                textViewFragStatus.setText(getString(R.string.toast))
            }
            R.id.imageButtonFragMsgSnackbar->{
                val snackbar = Snackbar
                    .make(view, getString(R.string.snackbar), Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        val snackbar1 = Snackbar.make(
                            view,
                            "Undo is selected!",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar1.show()
                        textViewStatus.setText(getString(R.string.undo))
                        textViewFragStatus.setText(getString(R.string.undo))
                    }
                snackbar.show()
                textViewStatus.setText(getString(R.string.snackbar))
                textViewFragStatus.setText(getString(R.string.snackbar))            }
        }

    }


    fun onButtonGo(view: View) {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    fun onButtonAdd(view: View) {
        try {
            val fragment: FragmentNavigate =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerViewNavigateMain) as FragmentNavigate
            Toast.makeText(this,getString(R.string.addfrag)+" Error", Toast.LENGTH_LONG).show()
        }
        catch(e:Exception){
            // create a new instance of the fragment
            val fragment = FragmentNavigate()
            //add the fragment to the fragment container, using inline function
            //supportFragmentManager.inTransaction { add(R.id.fragmentContainerViewNavigateMain, fragment) }
            //val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragmentTransaction = getSupportFragmentManager().beginTransaction()
            fragmentTransaction.add(R.id.fragmentContainerViewNavigateMain, fragment)
            fragmentTransaction.commit()
        }
    }
    fun onButtonRemove(view: View) {
        try {
            val fragment: FragmentNavigate =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerViewNavigateMain) as FragmentNavigate
            supportFragmentManager.inTransaction { remove(fragment) }
        }
        catch(e:Exception){
            Toast.makeText(this,getString(R.string.removefrag)+" Error",Toast.LENGTH_LONG).show()

        }
    }
}