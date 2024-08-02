package com.addepar.investfolio.data.modal

data class Investment(
    val name: String,
    val ticker: String?,
    val value: String,
    val principal: String,
    val details: String
)

data class InvestmentsResponse(
    val investments: List<Investment>
)
