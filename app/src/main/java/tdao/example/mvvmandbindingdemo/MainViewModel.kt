package tdao.example.mvvmandbindingdemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//ViewModel can be reused in many activites
class MainViewModel: ViewModel() {

    //Step3: Create a person object
    var person: Person = Person("",0)

    public var name:String = ""

    //DataBinding:Step4: Step up for live changed data
    private val count_ = MutableLiveData(0)
    val count: LiveData<Int> = count_
    // lifecycle
    init {
        Log.i("MainViewModel", "init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "onCleared")
    }

    //DataBinding:Step5: Declare lamda function
    fun onIncrementCount() {
        //Since it is multable data it need default value
        count_.value = (count_.value ?: 0)+1
        Log.i("ViewModel", "count " +count.toString())
    }
}