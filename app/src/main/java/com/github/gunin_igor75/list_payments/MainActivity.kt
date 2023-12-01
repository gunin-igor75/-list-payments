package com.github.gunin_igor75.list_payments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.gunin_igor75.list_payments.data.reposotory.PaymentRepositoryImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: PaymentRepositoryImp

    private val component by lazy {
        (application as PaymentApp).component
    }

    private val scope = CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch {
            val token = repository.signIn("demo", "12345")
            Log.d(TAG, token)
        }

        scope.launch {
            val payments = repository.loadPayments()
            Log.d(TAG, payments[0].amount.toString())
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}