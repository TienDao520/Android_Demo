package tdao.example.project2_memorygame_tiendao

//Create a model to store data
data class MemoryCard(val identifier: Int, var isFaceUp: Boolean = false, var isMatched: Boolean = false)
