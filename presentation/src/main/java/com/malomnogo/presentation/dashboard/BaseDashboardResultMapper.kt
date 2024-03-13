package com.malomnogo.presentation.dashboard

import com.malomnogo.domain.dashboard.DashboardItem
import com.malomnogo.domain.dashboard.DashboardResult
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

class BaseDashboardResultMapper @Inject constructor(
    private val dashboardItemMapper: DashboardItem.Mapper<DashboardUi>
) : DashboardResult.Mapper<DashboardUiState> {

    override fun mapSuccess(dashboardItems: List<DashboardItem>) =
        DashboardUiState.Base(
            dashboardItems.map {
                it.map(dashboardItemMapper)
            }
        )

    override fun mapError(message: String) = DashboardUiState.Error(message)


    override fun mapEmpty() = DashboardUiState.Empty
}

class BaseDashboardItemMapper @Inject constructor(
    private val rateFormat: RateFormat,
    private val delimiter: Delimiter.Create
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