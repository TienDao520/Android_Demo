package tdao.example.notificationandsms_tiendao

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // The id and name of the channel.
    // Android 8.0 introduced the concept of channels in notifications.
    val CHANNEL_ID = "channel_01"
    val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channnel_desc)
            // The default of priority
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun onButtonClick(view: View) {
        when(view.id) {
            R.id.buttonSMS->{
                requestPermission()
            }

            R.id.buttonDirectSMS->{
                requestPermissionDirect()
            }

            R.id.buttonBasicNotification-> {
                sendNotification(view)
            }

        }
    }

    /**
     * Send a sample notification using the NotificationCompat API.
     */
    fun sendNotification(view: View?) {

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // BEGIN_INCLUDE(build_action)
        // Notification Anatomy
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a [android.app.PendingIntent] so that the
         * notification service can fire it on our behalf.
         */
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:")  // This ensures only SMS apps respond
            putExtra("address", "5554")
            putExtra("sms_body", "message from notification")
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.android_12)

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent)

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true)
        /**
         * Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.android))
        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         * versions of Android prior to 4.2 will ignore this field, so don't use it for
         * anything vital!
         */
        builder.setContentTitle("BasicNotifications Sample")
        builder.setContentText("Time to learn about notifications!")
        builder.setSubText("Tap to view documentation about notifications.")

        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
        // END_INCLUDE(send_notification)
    }

    // Check the permission status
    private fun requestPermissionDirect() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED -> {
                // permission is granted
                Toast.makeText(this,getString(R.string.per_grant),Toast.LENGTH_SHORT).show()
                var smsManager:SmsManager
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
                    // Do something for S (12) and above versions
                    smsManager = baseContext.getSystemService(SmsManager::class.java)
                } else{
                    // do something for phones running an SDK before S (12)
                    smsManager = SmsManager.getDefault()
                }
                Toast.makeText(this, android.os.Build.VERSION.SDK_INT.toString(),Toast.LENGTH_LONG).show()

                smsManager.sendTextMessage("5554", "Test", "Direct SMS Message", null, null)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)-> {
                // additional rationale is displayed
                Toast.makeText(this,getString(R.string.per_not_grant),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
            else-> {
                // permission has not been asked yet
                Toast.makeText(this,getString(R.string.per_not_ask),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
        }
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED -> {
                // permission is granted
                Toast.makeText(this,getString(R.string.per_grant),Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:")  // This ensures only SMS apps respond
                    putExtra("address", "5554")
                    putExtra("sms_body", getString(R.string.sms))
                }
                // use default sms
//               startActivity(intent)
                // chooser for sms app
                startActivity(Intent.createChooser(intent,"SMS"))
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)-> {
                // additional rationale is displayed
                // This case will occur when the user change the setting permission
                Toast.makeText(this,getString(R.string.per_not_grant),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
            else-> {
                // permission has not been asked yet
                // This case will occur when the user first run application
                Toast.makeText(this,getString(R.string.per_not_ask),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result:Boolean ->
        // checking the result of permission
        if (result) {
            Toast.makeText(this,getString(R.string.per_grant), Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,getString(R.string.per_not_grant), Toast.LENGTH_SHORT).show()
        }
    }


}