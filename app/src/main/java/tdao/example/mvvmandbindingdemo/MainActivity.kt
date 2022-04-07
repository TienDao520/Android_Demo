package tdao.example.mvvmandbindingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Step2: Returns an existing ViewModel or
        // creates a new one in the scope (usually, a fragment or an activity), associated with this ViewModelProvider
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun onButtonUseName(view: View) {
        viewModel.name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
    }
    fun onButtonCountPress(view: View) {
        var textViewCount: TextView = findViewById<TextView>(R.id.textViewPresses)
        //store all data in viewModel
        viewModel.count++
        textViewCount.setText(viewModel.count.toString())
    }
}