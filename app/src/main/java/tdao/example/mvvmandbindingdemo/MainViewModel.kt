package tdao.example.mvvmandbindingdemo

import android.util.Log
import androidx.lifecycle.ViewModel

//ViewModel can be reused in many activites
class MainViewModel: ViewModel() {

    //Step3: Create a person object
    var person: Person = Person("",0)

    // lifecycle
    init {
        Log.i("MainViewModel", "init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "onCleared")
    }
}