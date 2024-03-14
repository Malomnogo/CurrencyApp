package com.malomnogo.presentation.dashboard

interface DashboardUiState {

    fun update(showList: ShowList)

    abstract class Abstract(private val dashboardUi: DashboardUi) : DashboardUiState {

        override fun update(showList: ShowList) {
            showList.show(listOf(dashboardUi))
        }
    }

    data class Base(private val currencies: List<DashboardUi>) : DashboardUiState {

        override fun update(showList: ShowList) {
            showList.show(currencies)
        }
    }

    data class Error(private val message: String) : Abstract(DashboardUi.Error(message))

    object Empty : Abstract(DashboardUi.Empty)

    object Nothing : DashboardUiState {

        override fun update(showList: ShowList) = Unit
    }

    object Progress : Abstract(DashboardUi.Progress)
}