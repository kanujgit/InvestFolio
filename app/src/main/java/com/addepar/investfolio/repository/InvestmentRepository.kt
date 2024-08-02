package com.addepar.investfolio.repository

import android.content.Context
import com.addepar.investfolio.data.modal.Investment
import com.addepar.investfolio.data.modal.InvestmentsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class InvestmentRepository @Inject constructor(private val context: Context) {

    suspend fun getInvestments(filename: String): List<Investment> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
                val type = object : TypeToken<InvestmentsResponse>() {}.type
                val response: InvestmentsResponse = Gson().fromJson(jsonString, type)
                response.investments
            } catch (e: IOException) {
                emptyList()
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
