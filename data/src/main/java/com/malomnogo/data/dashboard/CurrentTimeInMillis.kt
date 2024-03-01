package com.malomnogo.data.dashboard

interface CurrentTimeInMillis {

    fun time(): Long

    class Base : CurrentTimeInMillis {

        override fun time() = System.currentTimeMillis()
    }
}