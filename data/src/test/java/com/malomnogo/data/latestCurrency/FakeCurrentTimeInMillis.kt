package com.malomnogo.data.latestCurrency

import java.util.concurrent.TimeUnit

class FakeCurrentTimeInMillis(
    private val value: Long = TimeUnit.HOURS.toMillis(24)
) : CurrentTimeInMillis {

    override fun time() = value
}