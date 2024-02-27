package com.malomnogo.domain.dashboard

interface DashboardItem {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun map(fromCurrency: String, toCurrency: String, rates: Double): T
    }

    data class Base(
        private val from: String,
        private val to: String,
        private val rates: Double
    ) : DashboardItem {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.map(from, to, rates)
    }
}