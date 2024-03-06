package com.malomnogo.domain.dashboard

interface DashboardRepository {

    suspend fun dashboardItems(): DashboardResult

    suspend fun removePair(from: String, to: String): DashboardResult
}