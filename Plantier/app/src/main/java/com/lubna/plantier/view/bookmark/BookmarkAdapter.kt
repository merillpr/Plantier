package com.lubna.plantier.view.bookmark

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lubna.plantier.R
import com.lubna.plantier.model.DataAnalysis

class BookmarkAdapter(private val data: List<DataAnalysis>): RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var container = itemView.findViewById<CardView>(R.id.container_bookmark)
        var name = itemView.findViewById<TextView>(R.id.tv_title_item_bookmark)

        fun bind(itemData: DataAnalysis){
            name.text = itemData.name
            container.setOnClickListener{
                val dialog = Dialog(container.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialog_detail_analysis)

                val name = dialog.findViewById<TextView>(R.id.tv_name)
                val detail = dialog.findViewById<TextView>(R.id.tv_detail_penyakit)
                val treatment = dialog.findViewById<TextView>(R.id.tv_detail_treatment)

                name.text = itemData.name

                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.count()
}