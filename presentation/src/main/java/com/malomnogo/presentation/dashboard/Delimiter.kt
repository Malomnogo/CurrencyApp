package com.malomnogo.presentation.dashboard

interface Delimiter {

    interface Create {

        fun create(from: String, to: String): String
    }

    interface Split {

        fun split(pair: String): Pair<String, String>
    }

    interface Mutable : Create, Split

    class Base(private val delimiter: String = "/") : Mutable {

        override fun create(from: String, to: String) = "$from$delimiter$to"

        override fun split(pair: String) = pair.split(delimiter).let { Pair(it[0], it[1]) }
    }
}