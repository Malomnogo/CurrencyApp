package com.malomnogo

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult
import com.malomnogo.presentation.dashboard.DashboardUiObservable
import com.malomnogo.presentation.dashboard.DashboardUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class Worker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val observable: DashboardUiObservable,
    private val repository: DashboardRepository,
    private val mapper: DashboardResult.Mapper<DashboardUiState>
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = try {
        val result = repository.loadDashboardItems()
        withContext(Dispatchers.Main) {
            observable.updateUi(result.map(mapper))
        }
        Result.success()
    } catch (e: Exception) {
        Result.retry()
    }
}