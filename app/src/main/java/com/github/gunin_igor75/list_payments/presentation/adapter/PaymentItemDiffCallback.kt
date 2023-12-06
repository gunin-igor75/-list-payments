package com.github.gunin_igor75.list_payments.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.gunin_igor75.list_payments.domain.entity.Payment

object PaymentItemDiffCallback: DiffUtil.ItemCallback<Payment>() {
    override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem == newItem
    }
}