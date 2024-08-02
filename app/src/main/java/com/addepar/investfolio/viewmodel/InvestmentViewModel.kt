package com.addepar.investfolio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.addepar.investfolio.data.mapper.InvestmentMapper
import com.addepar.investfolio.data.modal.Investment
import com.addepar.investfolio.data.modal.InvestmentsResponse
import com.addepar.investfolio.repository.InvestmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class InvestmentIntent {
    data class LoadInvestments(val filename: String) : InvestmentIntent()
    data class SelectInvestment(val investment: Investment) : InvestmentIntent()
}

data class InvestmentState(
    val investments: List<Investment> = emptyList(),
    val selectedInvestment: Investment? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class InvestmentViewModel @Inject constructor(
    private val repository: InvestmentRepository,
    private val mapper: InvestmentMapper
) : ViewModel() {

    private val _state = MutableStateFlow(InvestmentState())
    val state: StateFlow<InvestmentState> = _state.asStateFlow()

    private val intentChannel = Channel<InvestmentIntent>(Channel.UNLIMITED)

    init {
        processIntents()
    }

    fun sendIntent(intent: InvestmentIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is InvestmentIntent.LoadInvestments -> loadInvestments(intent.filename)
                    is InvestmentIntent.SelectInvestment -> selectInvestment(intent.investment)
                }
            }
        }
    }

    private fun loadInvestments(filename: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val investments = repository.getInvestments(filename)
                _state.value = _state.value.copy(
                    investments = mapper.map(InvestmentsResponse(investments)),
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    private fun selectInvestment(investment: Investment) {
        _state.value = _state.value.copy(selectedInvestment = investment)
    }
}
