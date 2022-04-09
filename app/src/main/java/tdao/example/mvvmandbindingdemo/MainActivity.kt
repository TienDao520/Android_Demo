package tdao.example.mvvmandbindingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tdao.example.mvvmandbindingdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // variable used to show databinding
    public lateinit var firstname:String

    private lateinit var viewModel: MainViewModel

    // setup the binding variable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // not using the old setContentView, need to use the binding version
//        setContentView(R.layout.activity_main)

        //DataBinding:Step3: add binding
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = MainViewModel()
        binding.executePendingBindings()
        binding.lifecycleOwner = this

        //Step2: Returns an existing ViewModel or
        // creates a new one in the scope (usually, a fragment or an activity), associated with this ViewModelProvider
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun onButtonUseName(view: View) {
        viewModel.name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        binding.firstname = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

    }
    fun onButtonCountPress(view: View) {
        var textViewCount: TextView = findViewById<TextView>(R.id.textViewPresses)
        //store all data in viewModel
//        viewModel.count++
        textViewCount.setText(viewModel.count.toString())
    }

    fun onButtonPerson(view: View) {
        //Step4: not only it update viewModel but also person instance in MainViewModel
        viewModel.person.name = viewModel.name
        //No need below since now viewModel update directly using binding data
//        viewModel.person.count = viewModel.count
        Log.i("MainActivity-ViewModel", viewModel.name)
        Log.i("MainActivity-ViewModel", viewModel.count.toString())
        Log.i("MainActivity-ViewModel", viewModel.person.toString())
    }
}