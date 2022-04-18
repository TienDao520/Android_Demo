package tdao.example.finalexamminhtiendao

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    var userAddress:UserAddress = UserAddress("","")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onSaveBtn(view: View) {

        val prefsEditor = getSharedPreferences("pref", Context.MODE_PRIVATE)


        userAddress = UserAddress(prefsEditor?.getString("lat", ""),prefsEditor?.getString("lng", ""))
        saveDataToJson()
        Toast.makeText(this,"Data saved", Toast.LENGTH_LONG).show()
    }

    fun saveDataToJson () {
        try {
            // to save to JSON file "test.json" in data/data/packagename/File
            //osw stand for output stream writer
            val ofile: FileOutputStream = openFileOutput("address.json", MODE_PRIVATE)
            val osw = OutputStreamWriter(ofile)
            //Different with the test.txt file
            var jsonList = Gson().toJson(userAddress)
            for(address in jsonList) {
                osw.write(address.toString())
            }
            osw.flush()
            osw.close()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }



}