package tdao.example.mvvmandbindingdemo

import android.util.Log
import androidx.lifecycle.ViewModel

//ViewModel can be reused in many activites
class MainViewModel: ViewModel() {
    // lifecycle
    init {
        Log.i("MainViewModel", "init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "onCleared")
    }
}