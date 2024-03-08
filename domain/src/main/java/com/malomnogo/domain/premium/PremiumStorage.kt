package com.malomnogo.domain.premium

interface PremiumStorage {

    interface Read {

        fun read(): Boolean
    }

    interface Save {

        fun save()
    }

    interface Mutable : Read, Save
}