package com.malomnogo.presentation.settings

import android.os.Bundle

interface BundleWrapper {

    interface Save {
        fun save(from: String, to: String)
    }

    interface Restore {
        fun restore(): Pair<String, String>
    }

    interface Mutable : Save, Restore {
        fun isEmpty(): Boolean
    }

    class Base(private val bundle: Bundle?) : Mutable {

        override fun isEmpty() = bundle == null

        override fun save(from: String, to: String) {
            bundle?.let {
                it.putString(FROM_KEY, from)
                it.putString(TO_KEY, to)
            }
        }

        override fun restore() = bundle!!.let {
            Pair(it.getString(FROM_KEY) ?: "", it.getString(TO_KEY) ?: "")
        }

        private companion object {
            private const val FROM_KEY = "FROM_KEY"
            private const val TO_KEY = "TO_KEY"
        }
    }
}