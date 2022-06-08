package tdao.example.info6130_midterm_minhtiendao

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeedFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val checkedItem = 0
    var Levels = arrayOf("Level 1", "Level 2", "Level 3", "Level 4")
    private var checkedCurrentItem = 0

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
        return inflater.inflate(R.layout.fragment_speed, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpeedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        open fun newInstance(title: Int): SpeedFragment? {
//        open fun newInstance(title: String): ConfirmDialogFragment? {
            val fragment = SpeedFragment()
            val args = Bundle()
            args.putInt("title", title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = requireArguments().getInt("title")
        Log.d("DDD", "${title}")
        return AlertDialog.Builder(activity)
            .setTitle(title)
            .setSingleChoiceItems(Levels, checkedItem,
                DialogInterface.OnClickListener { dialog, which ->
                    // user checked an item
                    Log.d("BBB", "${dialog} _ ${which}")
                    checkedCurrentItem = which
                })
            .setPositiveButton(R.string.alert_dialog_ok,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    Log.d("BBBAAA", "${dialog} _ ${checkedCurrentItem}")
                    (activity as MainActivity?)
                        ?.doPositiveClick(checkedCurrentItem)

                })
            .setNegativeButton(R.string.alert_dialog_cancel,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    (activity as MainActivity?)
                        ?.doNegativeClick()
                }).create()
    }
}