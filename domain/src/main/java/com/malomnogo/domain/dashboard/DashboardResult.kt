package com.malomnogo.domain.dashboard

interface DashboardResult {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(dashboardItems: List<DashboardItem>): T

        fun mapError(message: String): T

        fun mapEmpty(): T
    }

    data class Success(private val dashboardItems: List<DashboardItem>) : DashboardResult {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(dashboardItems)
    }

    data class Error(private val message: String) : DashboardResult {

        override fun <T : Any> map(mapper: Mapper<T>): T = mapper.mapError(message)
    }

    object Empty : DashboardResult {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapEmpty()
    }
}