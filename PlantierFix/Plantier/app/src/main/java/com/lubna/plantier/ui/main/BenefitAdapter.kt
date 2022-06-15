package com.lubna.plantier.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lubna.plantier.databinding.ItemBenefitBinding
import com.lubna.plantier.data.model.Benefit

class BenefitAdapter (private val benefitList : ArrayList<Benefit>) : RecyclerView.Adapter<BenefitAdapter.BenefitViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class BenefitViewHolder(private val binding: ItemBenefitBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(benefit: Benefit) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(benefit)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(benefit.benefit_photo)
                    .into(benefitImageView)
                benefitNameTextView.text = benefit.benefit
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

    interface OnItemClickCallback {
        fun onItemClick(data: Benefit)
    }
}