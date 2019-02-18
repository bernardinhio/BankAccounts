package bernardo.bernardinhio.kotlinclasstypesInbankingapp

import android.text.format.DateFormat
import java.util.*

abstract class Account{

    val id : Long = System.currentTimeMillis()
    val dateCreated : String = getFormattedTime(System.currentTimeMillis())

    var owner : Owner = Owner()
    var type : AccountType = AccountType.UNDEFINED

    abstract var totalBalance : Double
    abstract var savingsBalance : Double
    abstract var checkingBalance : Double

    abstract fun addCash(cash : Double)
    abstract fun withdrawCash(cash : Double)

    private fun getFormattedTime(timeStamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timeStamp
        return DateFormat.format("'Created:' EEE dd, MMM yyyy ' /  at:' HH:mm:ss", calendar).toString()
    }
}

enum class AccountType(type : String){
    SAVINGS("savings"),
    CHECKING("checking"),
    SAVINGS_AND_CHECKING("savings_and_checking"),
    UNDEFINED("")
}