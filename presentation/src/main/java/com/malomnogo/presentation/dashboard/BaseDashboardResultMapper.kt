package com.malomnogo.presentation.dashboard

import com.malomnogo.domain.dashboard.DashboardItem
import com.malomnogo.domain.dashboard.DashboardResult
import java.math.RoundingMode
import java.text.DecimalFormat

class BaseDashboardResultMapper(
    private val observable: DashboardUiObservable,
    private val dashboardItemMapper: DashboardItem.Mapper<DashboardUi> = BaseDashboardItemMapper()
) : DashboardResult.Mapper {

    override fun mapSuccess(dashboardItems: List<DashboardItem>) {
        observable.updateUi(
            DashboardUiState.Base(
                dashboardItems.map {
                    it.map(dashboardItemMapper)
                }
            )
        )
    }

    override fun mapError(message: String) {
        observable.updateUi(DashboardUiState.Error(message))
    }

    override fun mapEmpty() {
        observable.updateUi(DashboardUiState.Empty)
    }
}

class BaseDashboardItemMapper(
    private val rateFormat: RateFormat = RateFormat.Base(),
    private val delimiter: Delimiter.Create = Delimiter.Base()
) : DashboardItem.Mapper<DashboardUi> {

    override fun map(fromCurrency: String, toCurrency: String, rates: Double) =
        DashboardUi.Base(delimiter.create(fromCurrency, toCurrency), rateFormat.format(rates))
}

interface RateFormat {

    fun format(double: Double): String

    class Base(private val decimalFormat: DecimalFormat = DecimalFormat("#.##")) : RateFormat {

        override fun format(double: Double): String = decimalFormat.apply {
            roundingMode = RoundingMode.DOWN
        }.format(double)
    }
}