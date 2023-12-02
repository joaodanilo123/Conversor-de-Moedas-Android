package uri.pm.conversordemoedas

class ConversionService {
    val USDtoEUR = 0.92
    val USDtoBRL = 4.88
    val USDtoARS = 362.17
    val USDtoBTC = 0.000026

    fun convert(c1: Currencies, c2: Currencies, amount: Double): Double {
        return when (c1) {
            Currencies.USD -> {
                when (c2) {
                    Currencies.EUR -> amount * USDtoEUR
                    Currencies.BRL -> amount * USDtoBRL
                    Currencies.ARS -> amount * USDtoARS
                    Currencies.BTC -> amount * USDtoBTC
                    else -> amount // Same currency, no conversion needed
                }
            }
            else -> {
                val amountInUSD = amount / when (c1) {
                    Currencies.EUR -> USDtoEUR
                    Currencies.BRL -> USDtoBRL
                    Currencies.ARS -> USDtoARS
                    Currencies.BTC -> USDtoBTC
                    else -> 1.0
                }

                when (c2) {
                    Currencies.EUR -> amountInUSD * USDtoEUR
                    Currencies.BRL -> amountInUSD * USDtoBRL
                    Currencies.ARS -> amountInUSD * USDtoARS
                    Currencies.BTC -> amountInUSD * USDtoBTC
                    else -> amountInUSD
                }
            }
        }
    }
}