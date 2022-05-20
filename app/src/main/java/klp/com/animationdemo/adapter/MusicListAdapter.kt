package klp.com.animationdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import klp.com.animationdemo.R
import klp.com.animationdemo.media.MusicBean


/**
 * Created by klp13115 on 2016/4/5.
 */
class MusicListAdapter(var mListData: ArrayList<MusicBean>?, var callback: ((position: Int)->Unit)? = null) : RecyclerView.Adapter<MusicListAdapter.MyViewHolder>() {

    fun addData(data: MusicBean) {
        mListData?.add(data)
        notifyItemInserted(mListData?.size?:0)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.music_list_item,
                viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.title.text = mListData!![i].musicUrl
        myViewHolder.stateTv.text = getStateName(mListData!![i].playState)

        myViewHolder.itemView.setOnClickListener {
            callback?.invoke(i)
        }
    }

    override fun getItemCount(): Int {
        return if (mListData == null) 0 else mListData!!.size
    }

    private fun getStateName(state: Int): String {
        when(state) {
            MusicBean.MusicState.PLAY_DEFAULT -> return "默认"
            MusicBean.MusicState.PLAYING -> return "播放中"
            MusicBean.MusicState.PLAYED -> return "播放过"
            else -> return "默认"
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var stateTv: TextView

        init {
            title = itemView.findViewById<View>(R.id.listitem_name) as TextView
            stateTv = itemView.findViewById<View>(R.id.music_state) as TextView
        }
    }
}