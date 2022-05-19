package klp.com.animationdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import klp.com.animationdemo.R
import klp.com.animationdemo.adapter.ListAdapter.MyViewHolder

/**
 * Created by klp13115 on 2016/4/5.
 */
class ListAdapter(var mListData: ArrayList<String>?, var callback: ((position: Int)->Unit)? = null) : RecyclerView.Adapter<MyViewHolder>() {

    fun addData(data: String) {
        mListData?.add(data)
        notifyItemInserted(mListData?.size?:0)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item,
                viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.title.text = mListData!![i]

        myViewHolder.itemView.setOnClickListener {
            callback?.invoke(i)
        }
    }

    override fun getItemCount(): Int {
        return if (mListData == null) 0 else mListData!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById<View>(R.id.listitem_name) as TextView
        }
    }
}