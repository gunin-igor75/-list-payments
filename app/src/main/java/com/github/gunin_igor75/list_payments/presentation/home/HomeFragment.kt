package com.github.gunin_igor75.list_payments.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.gunin_igor75.list_payments.PaymentApp
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.databinding.FragmentHomeBinding
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import com.github.gunin_igor75.list_payments.presentation.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val  binding: FragmentHomeBinding
        get() = _binding ?: throw IllegalStateException("FragmentHomeBinding is null")

    private val component by lazy {
        (requireActivity().application as PaymentApp).component
    }

    @Inject
    lateinit var tokenSettings: TokenSettings

    @Inject
    lateinit var viewModelFactory: ViewModelFactory



    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickShowPayments()
        clickLogout()
    }

    private fun clickShowPayments() {
        binding.btPayments.setOnClickListener {
            val currentToken = tokenSettings.getCurrentToken()
            if (currentToken == null) {
                goToSignInFragment()
            } else {
                goToLustPaymentFragment()
            }
        }
    }

    private fun goToLustPaymentFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_listPaymentFragment)
    }

    private fun goToSignInFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_signFragment)
    }

    private fun clickLogout() {
        binding.btLogOut.setOnClickListener {
            goToSignInFragment()
            viewModel.logout()
        }
    }
}