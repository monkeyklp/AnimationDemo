package klp.com.animationdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import klp.com.animationdemo.adapter.ListAdapter
import klp.com.animationdemo.adapter.MessageScroller
import klp.com.animationdemo.media.MediaPlayerManager
import klp.com.animationdemo.media.MediaQueueCenter
import klp.com.animationdemo.media.QueueItem

class MediaActivity : AppCompatActivity() {
    val  musicList = arrayListOf("/storage/emulated/0/Music/D-Push-3.wav",
            "/storage/emulated/0/12530/download/好久不见-周杰伦.mp3",
            "/storage/emulated/0/Music/D-Push-1.wav",
            "/storage/emulated/0/Music/D-Push-3.wav",
    "/storage/emulated/0/12530/download/好久不见-周杰伦.mp3",
    "/storage/emulated/0/Music/D-Push-1.wav")

    //            + "typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: ListAdapter? =null
    private var mHandler: Handler = Handler(Looper.getMainLooper())
    private var messageScroll: MessageScroller? = null;
    companion object {
        fun startActivity(fromActivity: AppCompatActivity) {
            val intent = Intent(fromActivity, MediaActivity::class.java)
            fromActivity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        MediaPlayerManager.getInstance(baseContext).init()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        messageScroll = MessageScroller(baseContext)
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mAdapter = ListAdapter(musicList) {
            position ->

            MediaPlayerManager.getInstance(baseContext).setCallback(object: MediaPlayerManager.Callback{
            override fun next() {//播放结束，开始自动轮播
                MediaQueueCenter.getInstance().queryNext()
            }

            override fun connectState(connected: Boolean) {

            }
        }).play(musicList[position])
        }
        mRecyclerView?.adapter = mAdapter
        //滚到最底部
        mRecyclerView?.layoutManager?.scrollToPosition(musicList.size -1)

        findViewById<Button>(R.id.addTaskBtn).setOnClickListener {
            Thread().run {
                for (song in musicList) {
                    Thread.sleep(1000)
                    var item = QueueItem(baseContext, song)
                    //如果
                    MediaQueueCenter.getInstance().enqueue(item, !MediaPlayerManager.getInstance(baseContext).isPlaying)
                    mHandler.post {
                        mAdapter?.addData(song)
                        smoothScroll2Position(mAdapter?.itemCount?:0 -1)
                    }
                }
            }
        }

        findViewById<Button>(R.id.endTaskBtn).setOnClickListener {
            MediaPlayerManager.getInstance(baseContext).release()
        }

    }

    private fun smoothScroll2Position(position: Int) {
        messageScroll?.setRunnerStop()
        messageScroll?.targetPosition = position
        mRecyclerView?.layoutManager?.startSmoothScroll(messageScroll)
    }
}