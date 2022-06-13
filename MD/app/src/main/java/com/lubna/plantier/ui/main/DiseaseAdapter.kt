package com.lubna.plantier.ui.main

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lubna.plantier.R
import com.lubna.plantier.databinding.ItemDiseaseBinding
import com.lubna.plantier.model.Disease

class DiseaseAdapter(private val diseaseList : List<Disease>) :
    RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>() {

    class DiseaseViewHolder(private val binding: ItemDiseaseBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(disease: Disease) {
            binding.apply {
                Glide.with(itemView)
                    .load(disease.diseaseImage)
                    .into(diseaseImageView)
                diseaseTextView.text = disease.diseaseName
                diseaseDescTextView.text = disease.diseaseDesc

                moreButton.setOnClickListener {
                    val dialog = Dialog(cardView.context)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(true)
                    dialog.setContentView(R.layout.dialog_detail_analysis)

                    val name = dialog.findViewById<TextView>(R.id.tv_name)
                    val detail = dialog.findViewById<TextView>(R.id.tv_detail_penyakit)
                    val treatment = dialog.findViewById<TextView>(R.id.tv_detail_treatment)

                    name.text = disease.diseaseName

                    dialog.show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        return DiseaseViewHolder(
            ItemDiseaseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = diseaseList[position]
        holder.bind(disease)
    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }
}