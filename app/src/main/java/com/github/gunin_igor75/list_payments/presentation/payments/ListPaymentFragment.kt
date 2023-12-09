package com.github.gunin_igor75.list_payments.presentation.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.databinding.FragmentListPaymentsBinding
import com.github.gunin_igor75.list_payments.domain.entity.PaymentsState
import com.github.gunin_igor75.list_payments.presentation.adapter.PaymentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListPaymentFragment : Fragment() {

    private var _binding: FragmentListPaymentsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentListPaymentsBinding is null")

    private val viewModel by viewModels<PaymentsViewModel>()

    @Inject
    lateinit var adapterPayment: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.loadPayments.collect {
                    when (it) {
                        is PaymentsState.Initialization -> {
                            binding.pbLoading.isVisible = false
                        }

                        is PaymentsState.Loading -> {
                            binding.pbLoading.isVisible = true
                        }

                        is PaymentsState.Content -> {
                            binding.pbLoading.isVisible = false
                            adapterPayment.submitList(it.payments)
                        }

                        is PaymentsState.ErrorLoading -> {
                            binding.pbLoading.isVisible = false
                            showToast(R.string.error_authorization)
                            goSignInFragment()
                        }

                        is PaymentsState.ErrorBackend -> {
                            showToast(R.string.connection_error)
                            goToHomeFragment()
                        }
                    }
                }
            }
        }
    }

    private fun goToHomeFragment() {
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    private fun goSignInFragment() {
        findNavController().popBackStack(R.id.signFragment, false)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvPayment
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.animation = null
        recyclerView.adapter = adapterPayment
    }

    private fun showToast(idString: Int) {
        Toast.makeText(
            requireContext(),
            getString(idString),
            Toast.LENGTH_LONG
        ).show()
    }
}