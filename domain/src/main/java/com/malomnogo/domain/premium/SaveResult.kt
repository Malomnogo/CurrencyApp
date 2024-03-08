package com.malomnogo.domain.premium

interface SaveResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess()

        fun mapNeedPremium()
    }

    object Success : SaveResult {

        override fun map(mapper: Mapper) {
            mapper.mapSuccess()
        }
    }

    object NeedPremium : SaveResult {

        override fun map(mapper: Mapper) {
            mapper.mapNeedPremium()
        }
    }
}