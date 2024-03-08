
package com.malomnogo.domain.premium

interface BuyPremiumResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(message: String)

        fun mapError(message: String)
    }

    data class Success(private val message: String) : BuyPremiumResult {

        override fun map(mapper: Mapper) {
            mapper.mapSuccess(message)
        }
    }

    data class Error(private val message: String) : BuyPremiumResult {

        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }
}