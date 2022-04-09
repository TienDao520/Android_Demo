package tdao.example.project2_memorygame_tiendao

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

//ViewModel can be reused in many activites
class MainViewModel: ViewModel() {

    lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    // lifecycle
    init {
        Log.i("MainViewModel", "init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "onCleared")
    }

    fun updateModels(position: Int) {
        val card = cards[position]

        //Error checking:
        if( card.isFaceUp) {
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
        card.isFaceUp = !card.isFaceUp
    }



    public fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    public fun checkForMatch(position1: Int, position2: Int) : Boolean {
        if (cards[position1].identifier == cards[position2].identifier) {
//            Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
        return true
    }
}