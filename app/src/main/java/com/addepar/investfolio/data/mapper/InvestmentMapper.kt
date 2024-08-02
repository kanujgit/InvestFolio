package com.addepar.investfolio.data.mapper

import com.addepar.investfolio.data.modal.Investment
import com.addepar.investfolio.data.modal.InvestmentsResponse
import javax.inject.Inject

class InvestmentMapper @Inject constructor() {
    fun map(response: InvestmentsResponse): List<Investment> {
        return response.investments.map {
            Investment(
                name = it.name,
                ticker = it.ticker,
                value = it.value,
                principal = it.principal,
                details = it.details
            )
        }
    }
}
