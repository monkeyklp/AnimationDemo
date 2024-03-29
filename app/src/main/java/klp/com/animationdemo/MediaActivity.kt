package klp.com.animationdemo

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
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
    private var mSmartRefreshLayout: SmartRefreshLayout? = null
    private var mAdapter: MusicListAdapter? = null
    private var mHandler: Handler = Handler(Looper.getMainLooper())
    private var messageScroll: MessageScroller? = null
    private var autoScroll: Boolean = true

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
        mRecyclerView = findViewById(R.id.recyclerView)
        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout)
        mSmartRefreshLayout?.setRefreshHeader(ClassicsHeader(this))
        mSmartRefreshLayout?.setOnRefreshListener {
            it.finishRefresh()
        }
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mAdapter = MusicListAdapter(musicBeanList) { position ->
            MediaPlayerManager.getInstance().registerCallback(object : MediaPlayerManager.Callback {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun next(song: MusicBean?) {
                    MediaPlayerManager.getInstance().unRegisterCallback(this)
                    MediaQueueCenter.getInstance().reset(mAdapter?.mListData, position)
                }

                override fun start(song: MusicBean?) {

                }

                override fun paused(song: MusicBean?) {

                }

                override fun release(song: MusicBean?) {

                }

                override fun connectState(connected: Boolean) {

                }

            }).play(mAdapter?.mListData?.get(position))
        }
        mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    autoScroll = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() <= mAdapter?.mListData?.size?:0 -1
                }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    autoScroll = false
                }
            }
        })

        mRecyclerView?.adapter = mAdapter
        //滚到最底部
        mRecyclerView?.layoutManager?.scrollToPosition(musicBeanList.size - 1)
        //太快，connect 还没好
        for (musicBean in musicBeanList) {
            var item = QueueItem(musicBean)
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

            override fun release(song: MusicBean?) {
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

                    var item = QueueItem(musicBean)
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
        if (position == -1) return
        mAdapter?.mListData?.get(position)?.playState = state
        mAdapter?.notifyItemChanged(position)
    }

    private fun smoothScroll2Position(position: Int) {
        if (autoScroll) {
            messageScroll?.setRunnerStop()
            messageScroll?.targetPosition = position
            mRecyclerView?.layoutManager?.startSmoothScroll(messageScroll)
        }
    }

    override fun onStop() {
        super.onStop()

    }
}