package com.lubna.plantier.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lubna.plantier.databinding.ItemDiseaseBinding
import com.lubna.plantier.data.model.Disease
import com.lubna.plantier.ui.detail.disease.DiseaseActivity

class DiseaseAdapter(private val diseaseList : List<Disease>) :
    RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>() {

    class DiseaseViewHolder(private val binding: ItemDiseaseBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(disease: Disease) {
            binding.apply {
                Glide.with(itemView)
                    .load(disease.disease_photo)
                    .into(diseaseImageView)
                diseaseTextView.text = disease.disease_name
                diseaseDescTextView.text = disease.disease_desc

                moreButton.setOnClickListener {
                    val intent = Intent(itemView.context, DiseaseActivity::class.java)
                    intent.putExtra("Disease", disease)

                    itemView.context.startActivity(intent)
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