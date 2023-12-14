package com.github.gunin_igor75.list_payments.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.databinding.FragmentHomeBinding
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw IllegalStateException("FragmentHomeBinding is null")

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var tokenSettings: TokenSettings

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