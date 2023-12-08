package com.github.gunin_igor75.list_payments.presentation.signin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.github.gunin_igor75.list_payments.PaymentApp
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.databinding.FragmentSignBinding
import com.github.gunin_igor75.list_payments.domain.entity.SignInState
import com.github.gunin_igor75.list_payments.presentation.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignFragment : Fragment() {

    private var _binding: FragmentSignBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentSignBinding is null")

    private val component by lazy {
        (requireActivity().application as PaymentApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SignInViewModel::class.java]
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
        _binding = FragmentSignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextClickListener()

        binding.btSignIn.setOnClickListener {
            val login = binding.tiEtLogin.text.toString()
            val password = binding.tiEtPassword.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.signIn(login, password).collect {
                        when (it) {
                            is SignInState.Authorization -> {
                                goToListPaymentFragment()
                            }

                            is SignInState.EmptyField -> {
                                binding.tiLogin.error =
                                    if (it.emptyLogin) getString(R.string.field_is_empty) else null
                                binding.tiPassword.error =
                                    if (it.emptyPassword) getString(R.string.field_is_empty) else null
                                binding.pbSignIn.isVisible = false
                            }

                            is SignInState.ErrorBackend -> {
                                showToast(R.string.connection_error)
                                binding.pbSignIn.isVisible = false
                            }

                            is SignInState.Unauthorized -> {
                                binding.tiEtPassword.text?.clear()
                                showToast(R.string.invalid_email_or_password)
                                binding.pbSignIn.isVisible = false
                            }

                            SignInState.ClearField -> {
                                clearField()
                            }

                            is SignInState.Initialization -> {
                                binding.pbSignIn.isVisible = false
                            }

                            SignInState.Loading -> {
                                binding.pbSignIn.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun editTextClickListener() {
        binding.tiEtLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiLogin.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.tiEtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tiPassword.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun clearField() {
        binding.tiEtPassword.text?.clear()
        binding.tiEtLogin.text?.clear()
    }

    private fun goToListPaymentFragment() {
        findNavController().navigate(R.id.action_signFragment_to_listPaymentFragment)
    }

    private fun showToast(idString: Int) {
        Toast.makeText(
            requireContext(),
            getString(idString),
            Toast.LENGTH_LONG
        ).show()
    }
}