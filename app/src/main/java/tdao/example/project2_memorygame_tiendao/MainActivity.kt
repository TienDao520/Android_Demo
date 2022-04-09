package tdao.example.project2_memorygame_tiendao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var buttons: List<ImageButton>
    private lateinit var imageButton1: ImageButton
    private lateinit var imageButton2: ImageButton
    private lateinit var imageButton3: ImageButton
    private lateinit var imageButton4: ImageButton
    private lateinit var imageButton5: ImageButton
    private lateinit var imageButton6: ImageButton
    private lateinit var imageButton7: ImageButton
    private lateinit var imageButton8: ImageButton

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Returns an existing ViewModel or
        // creates a new one in the scope (usually, a fragment or an activity), associated with this ViewModelProvider
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        imageButton1=findViewById(R.id.imageButton1)
        imageButton2=findViewById(R.id.imageButton2)
        imageButton3=findViewById(R.id.imageButton3)
        imageButton4=findViewById(R.id.imageButton4)
        imageButton5=findViewById(R.id.imageButton5)
        imageButton6=findViewById(R.id.imageButton6)
        imageButton7=findViewById(R.id.imageButton7)
        imageButton8=findViewById(R.id.imageButton8)

        buttons = listOf(imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8)

        val images = mutableListOf(R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4)
        // Duplicate the current images
        images.addAll(images)
        // Mixed the order of images
        images.shuffle()

        viewModel.cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "button clicked!!${index.toString()}")

                // Update Memory Card models
                viewModel.updateModels(index)
                // Update the view
                updateViews()
            }
        }
    }

    private fun updateViews() {
        viewModel.cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.pokemon_ball)
        }
    }

}