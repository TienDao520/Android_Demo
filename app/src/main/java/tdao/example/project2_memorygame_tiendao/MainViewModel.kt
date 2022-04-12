package tdao.example.project2_memorygame_tiendao

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.internal.ContextUtils.getActivity

//ViewModel can be reused in many activites
class MainViewModel: ViewModel() {

    private val _cards = MutableLiveData<List<MemoryCard>>()
    val cards: LiveData<List<MemoryCard>> = _cards

    private val count_ = MutableLiveData(0)
    val count: LiveData<Int> = count_

    private var indexOfSingleSelectedCard: Int? = null
    var imgDefault =R.drawable.pokemon_flag


    // Duplicate the current images
    val images = mutableListOf(R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4)

    // lifecycle
    init {
        Log.i("MainViewModel", "init")
        images.addAll(images)
        // Mixed the order of images
        images.shuffle()

        _cards.value = images.indices.map { index ->
            MemoryCard(images[index])
        }
        Log.i("cards_.value", _cards.value.toString())
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "onCleared")
    }

    fun updateModels(position: Int) {
        val card = _cards.value?.get(position)

        //Error checking:
        if(card?.isFaceUp == true) {
//            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        // Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card?.isFaceUp  = !card?.isFaceUp!!

        Log.i("MainViewModel", card.toString())
        cards.value?.get(0)?.identifier.let { Log.i("MainViewModelcards", it.toString()) }
        cards.value?.get(0)?.isFaceUp.let { Log.i("MainViewModelcards_isFaceUp", it.toString()) }
        _cards.value = _cards.value

        count_.value = (count_.value ?: 0)+1
        Log.i("ViewModel", "count " +count.toString())
    }



    fun restoreCards() {
        for (card in _cards.value!!) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    fun checkForMatch(position1: Int, position2: Int) : Boolean {
        if (_cards.value?.get(position1)?.identifier  == _cards.value?.get(position2)?.identifier) {
//            Toast.makeText(getPranentActivity(MainActivity.class), "Match found!!", Toast.LENGTH_SHORT).show()
            _cards.value?.get(position1)?.isMatched = true
            _cards.value?.get(position2)?.isMatched = true
        }
        return true
    }

    fun resetCards() {

        images.shuffle()

        _cards.value = images.indices.map { index ->
            MemoryCard(images[index])
        }
        count_.value =0;

    }


}