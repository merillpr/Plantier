package com.lubna.plantier.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lubna.plantier.databinding.ItemBenefitBinding
import com.lubna.plantier.model.Benefit

class BenefitAdapter(private val benefitList : List<Benefit>) :
    RecyclerView.Adapter<BenefitAdapter.BenefitViewHolder>() {

    class BenefitViewHolder(private val binding: ItemBenefitBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(benefit: Benefit) {
            binding.apply {
                Glide.with(itemView)
                    .load(benefit.benefitImage)
                    .into(benefitImageView)
                benefitNameTextView.text = benefit.benefitName
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenefitViewHolder {
        return BenefitViewHolder(
            ItemBenefitBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BenefitViewHolder, position: Int) {
        val benefit = benefitList[position]
        holder.bind(benefit)
    }

    override fun getItemCount(): Int {
        return benefitList.size
    }
}