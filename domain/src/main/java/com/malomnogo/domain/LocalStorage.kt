package com.malomnogo.domain

interface LocalStorage {

    interface Save {

        fun save(key: String, value: Boolean)
    }

    interface Read {

        fun read(key: String, default: Boolean): Boolean
    }

    interface Mutable : Save, Read
}