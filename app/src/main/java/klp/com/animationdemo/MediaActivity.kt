package klp.com.animationdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import klp.com.animationdemo.adapter.MessageScroller
import klp.com.animationdemo.adapter.MusicListAdapter
import klp.com.animationdemo.media.MediaPlayerManager
import klp.com.animationdemo.media.MediaQueueCenter
import klp.com.animationdemo.media.MusicBean
import klp.com.animationdemo.media.QueueItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediaActivity : AppCompatActivity() {
    val musicList = arrayListOf("/storage/emulated/0/Music/D-Push-3.wav",
            "/storage/emulated/0/12530/download/好久不见-周杰伦.mp3",
            "/storage/emulated/0/Music/D-Push-1.wav")
    var musicBeanList = arrayListOf<MusicBean>(MusicBean(1, "/storage/emulated/0/Music/D-Push-3.wav", MusicBean.MusicState.PLAY_DEFAULT),
            MusicBean(2, "/storage/emulated/0/Music/D-Push-1.wav", MusicBean.MusicState.PLAY_DEFAULT))

    //            + "typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: MusicListAdapter? = null
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
        MediaPlayerManager.getInstance().init()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        messageScroll = MessageScroller(baseContext)
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mAdapter = MusicListAdapter(musicBeanList) { position ->
            MediaPlayerManager.getInstance().play(mAdapter?.mListData?.get(position))
        }


        mRecyclerView?.adapter = mAdapter
        //滚到最底部
        mRecyclerView?.layoutManager?.scrollToPosition(musicBeanList.size - 1)
        //太快，connect 还没好
        for (musicBean in musicBeanList) {
            var item = QueueItem(baseContext, musicBean)
            MediaQueueCenter.getInstance().enqueue(item, !MediaPlayerManager.getInstance().isPlaying)
        }


        MediaPlayerManager.getInstance().registerCallback(object : MediaPlayerManager.Callback {
            override fun next(song: MusicBean?) {
                changeRecyclerView(song, MusicBean.MusicState.PLAYED)
                //重新设置队列
            }

            override fun start(song: MusicBean?) {
                changeRecyclerView(song, MusicBean.MusicState.PLAYING)
            }

            override fun paused(song: MusicBean?) {
                changeRecyclerView(song, MusicBean.MusicState.PLAYED)
            }

            override fun connectState(connected: Boolean) {

            }

        })


        findViewById<Button>(R.id.addTaskBtn).setOnClickListener {
            for (song in musicList) {
                MainScope().launch {
                    delay(200)
                var musicBean = MusicBean(System.currentTimeMillis(), song, MusicBean.MusicState.PLAY_DEFAULT)
                    mAdapter?.addData(musicBean)
                    smoothScroll2Position(mAdapter?.itemCount ?: 0 - 1)

                    var item = QueueItem(baseContext, musicBean)
                    //如果
                    MediaQueueCenter.getInstance().enqueue(item, !MediaPlayerManager.getInstance().isPlaying)
                }
            }

        }

        findViewById<Button>(R.id.endTaskBtn).setOnClickListener {
            MediaPlayerManager.getInstance().release()
            MediaQueueCenter.getInstance().quit()
        }

    }


    private fun changeRecyclerView(song: MusicBean?, state: Int) {
        var position = mAdapter?.mListData?.indexOf(song) ?: 0
        mAdapter?.mListData?.get(position)?.playState = state
        mAdapter?.notifyItemChanged(position)
    }

    private fun smoothScroll2Position(position: Int) {
        messageScroll?.setRunnerStop()
        messageScroll?.targetPosition = position
        mRecyclerView?.layoutManager?.startSmoothScroll(messageScroll)
    }

    override fun onStop() {
        super.onStop()

    }
}