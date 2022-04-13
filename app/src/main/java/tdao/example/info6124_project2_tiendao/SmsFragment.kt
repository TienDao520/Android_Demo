package tdao.example.info6124_project2_tiendao

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SmsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SmsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //For SMS
    private lateinit var textSmsNumber: TextView
    private lateinit var textSmsMessage: TextView
    private lateinit var textCurrentLocation: TextView

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
        return inflater.inflate(R.layout.fragment_sms, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SmsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SmsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        textSmsNumber= activity?.findViewById(R.id.textSmsNumber)!!
        textSmsMessage= activity?.findViewById(R.id.textSmsMessage)!!
        textCurrentLocation = activity?.findViewById(R.id.textCurrentLocation)!!

        val prefsEditor = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        textSmsNumber.text = prefsEditor?.getString("phoneNumber", "")
        textSmsMessage.text = prefsEditor?.getString("smsMessage", "")
        textCurrentLocation.text = prefsEditor?.getString("address", "")

        Log.i("AAA", textSmsNumber.text.toString())
    }


    override fun onPause() {
        Log.i("PPP", textSmsNumber.text.toString())
        // save the name in the EditText to the shared preference
        val prefsEditor = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)?.edit()
        prefsEditor?.putString("phoneNumber", textSmsNumber.text.toString())
        prefsEditor?.putString("smsMessage", textSmsMessage.text.toString())
        prefsEditor?.apply()
        super.onPause()
    }

    override fun onStop() {

        super.onStop()
    }



}