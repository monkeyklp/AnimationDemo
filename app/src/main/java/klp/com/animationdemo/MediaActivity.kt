package klp.com.animationdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import klp.com.animationdemo.media.MediaPlayerManager
import klp.com.animationdemo.media.MediaQueueCenter
import klp.com.animationdemo.media.QueueItem

class MediaActivity : AppCompatActivity() {
    val  musicList = listOf<String>("/storage/emulated/0/Music/D-Push-3.wav",
            "/storage/emulated/0/Music/D-Push-1.wav","/storage/emulated/0/12530/download/好久不见-周杰伦.mp3")
    companion object {

        fun startActivity(fromActivity: AppCompatActivity) {
            val intent = Intent(fromActivity, MediaActivity::class.java)
            fromActivity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        val manager = MediaPlayerManager.getInstance(baseContext)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)

        findViewById<Button>(R.id.startBtn).setOnClickListener {

        }

        findViewById<Button>(R.id.addTaskBtn).setOnClickListener {
            Thread().run {
                for (song in musicList) {
                    Thread.sleep(1000)
                    var item = QueueItem(baseContext, song)
                    MediaQueueCenter.getInstance().enqueue(item, true)
                }
            }

//            var manger = MediaPlayerManager.getInstance(MainActivity@ this)
//            manger.setCallback(object : MediaPlayerManager.Callback {
//                override fun next() {
//
//                }
//
//                override fun connectState(connected: Boolean) {
//                    manger.play("/storage/emulated/0/Music/D-Push-3.wav")
//                }

//            }
//        )
        }
    }
}