package com.github.gunin_igor75.list_payments.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.gunin_igor75.list_payments.databinding.FragmentItemPaymentsBinding
import com.github.gunin_igor75.list_payments.domain.entity.Payment
import javax.inject.Inject

class PaymentAdapter @Inject constructor(
) : ListAdapter<Payment, PaymentViewHolder>(PaymentItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = FragmentItemPaymentsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = getItem(position)
        with(holder.binding) {
            tvCreated.text = payment.created
            tvTitlePayment.text = payment.title
            val amount = payment.amount.toString()
            tvAmountPayment.text = amount
        }
    }
}