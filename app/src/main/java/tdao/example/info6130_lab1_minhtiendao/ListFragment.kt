package tdao.example.info6130_lab1_minhtiendao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : ListFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var Levels = arrayOf(R.string.lvl1, R.string.lvl2, R.string.lvl3, R.string.lvl4)
    var Levels_String = arrayOf("Level 1", "Level 2","Level 3","Level 4")
    var Texts = arrayOf(R.string.lvl1_string,R.string.lvl2_string,R.string.lvl3_string,R.string.lvl4_string)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_list_item_1, Levels_String
        )
        listAdapter = adapter

        return view
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val fragment_text: TextFragment? = activity?.supportFragmentManager?.findFragmentById(R.id.fragment_text) as TextFragment?
        fragment_text?.change(getString(Levels[position]), "Information : " + getString(Texts[position]))

//        val txt =requireFragmentManager().findFragmentById(R.id.fragment2) as TextFragment?
//        txt?.change(MonthL[position], "Month : " + MonthN[position])


        listView.setSelector(android.R.color.holo_blue_dark)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}