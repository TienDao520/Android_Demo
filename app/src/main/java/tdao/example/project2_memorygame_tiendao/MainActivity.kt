package tdao.example.project2_memorygame_tiendao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Returns an existing ViewModel or
        // creates a new one in the scope (usually, a fragment or an activity), associated with this ViewModelProvider
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }



}