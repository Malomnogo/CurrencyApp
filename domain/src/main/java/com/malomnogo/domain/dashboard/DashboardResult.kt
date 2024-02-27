package com.malomnogo.domain.dashboard

interface DashboardResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(dashboardItems: List<DashboardItem>)

        fun mapError(message: String)

        fun mapEmpty()
    }

    data class Success(private val dashboardItems: List<DashboardItem>) : DashboardResult {

        override fun map(mapper: Mapper) {
            mapper.mapSuccess(dashboardItems)
        }
    }

    data class Error(private val message: String) : DashboardResult {

        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }

    object Empty : DashboardResult {

        override fun map(mapper: Mapper) {
            mapper.mapEmpty()
        }
    }
}