package com.malomnogo.data.dashboard

import java.util.concurrent.TimeUnit

class FakeCurrentTimeInMillis(
    private val value: Long = TimeUnit.HOURS.toMillis(24)
) : CurrentTimeInMillis {

    override fun time() = value
}