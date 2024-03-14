package com.malomnogo

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.malomnogo.data.core.ForegroundWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BaseForegroundWrapper @Inject constructor(
    @ApplicationContext context: Context
) : ForegroundWrapper {

    private val workManager = WorkManager.getInstance(context)

    override fun start() {
        val request = OneTimeWorkRequestBuilder<Worker>().build()
        workManager.enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    companion object {
        private const val WORK_NAME = "worker"
    }
}