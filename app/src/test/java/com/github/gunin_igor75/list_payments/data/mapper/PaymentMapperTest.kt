package com.github.gunin_igor75.list_payments.data.mapper

import android.util.Log
import com.github.gunin_igor75.list_payments.data.network.dto.ResponsePaymentsDto
import com.github.gunin_igor75.list_payments.util.getErrorData
import com.github.gunin_igor75.list_payments.util.getErrorDto
import com.github.gunin_igor75.list_payments.util.getListPayment
import com.github.gunin_igor75.list_payments.util.getPaymentListDto
import com.github.gunin_igor75.list_payments.util.getResponsePaymentsDto
import com.github.gunin_igor75.list_payments.util.getResponsePaymentsDtoErrorDataNull
import com.github.gunin_igor75.list_payments.util.getResponseTokenDto
import com.github.gunin_igor75.list_payments.util.getResponseTokenDtoErrorDataNull
import io.mockk.every
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test

class PaymentMapperTest {

    private val mapper = PaymentMapper()

    @Before
    fun mockkLg() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }
    @Test
    fun mapResponsePaymentsDtoToPaymentsTestPositive() {
        val responsePaymentsDto = ResponsePaymentsDto(
            success = true,
            payments = getPaymentListDto(),
            error = null
        )
        val actual =
            mapper.mapResponsePaymentsDtoToPayments(responsePaymentsDto)
        val expected = getListPayment()
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
    }

    @Test
    fun mapResponsePaymentsDtoToPaymentsTestNegative() {
        val responsePaymentsDto = ResponsePaymentsDto(
            success = false,
            payments = null,
            error = getErrorDto()
        )
        val actual =
            mapper.mapResponsePaymentsDtoToPayments(responsePaymentsDto)
        println(actual)
        assertThat(actual).containsExactlyInAnyOrderElementsOf(emptyList())
    }

    @Test
    fun mapResponseTokenToErrorDataTestPositive() {
        val responseTokenDto = getResponseTokenDto()
        val expected = getErrorData()
        val actual = mapper.mapResponseTokenToErrorData(responseTokenDto)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun mapResponseTokenToErrorDataTestNegative() {
        val responseTokenDto = getResponseTokenDtoErrorDataNull()
        assertThatThrownBy{
            mapper.mapResponseTokenToErrorData(responseTokenDto)
        }.isInstanceOf( IllegalStateException::class.java)
    }

    @Test
    fun mapResponsePaymentsDtoToErrorDataTestPositive() {
        val responsePaymentsDto = getResponsePaymentsDto()
        val expected = getErrorData()
        val actual = mapper.mapResponsePaymentsDtoToErrorData(responsePaymentsDto)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun mapResponsePaymentsDtoToErrorDataTestNegative() {
        val responsePaymentsDto = getResponsePaymentsDtoErrorDataNull()
        assertThatThrownBy{
            mapper.mapResponsePaymentsDtoToErrorData(responsePaymentsDto)
        }.isInstanceOf(IllegalStateException::class.java)
    }
}

