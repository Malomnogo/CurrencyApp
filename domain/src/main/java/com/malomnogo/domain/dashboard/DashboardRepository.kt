package com.malomnogo.domain.dashboard

interface DashboardRepository {

    suspend fun dashboardItems(): DashboardResult
}