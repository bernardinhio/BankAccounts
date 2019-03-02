package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

import java.text.SimpleDateFormat
import java.util.*

abstract class Account(
        val id : Long = System.currentTimeMillis() - "account".length,
        val dateCreated : String = Account.getFormattedTime(System.currentTimeMillis()),
        open var type : AccountType = AccountType.UNDEFINED,
        var owner : Owner = Owner()
) {

    companion object {

        var yearlyInterestRate : Double = InterestRatePerYearForAmount.INTEREST_3_POINT_23.interestRate

        // format  Feb 28, 2019 at 18:36: 27sec
        fun getFormattedTime(timeStamp: Long): String {
            try {
                val simpleDateFormat = SimpleDateFormat("HH:mm ss'sec'", Locale.GERMANY)
                val netDate = Date(timeStamp)
                return simpleDateFormat.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }
    }

    // must have for: CHECKING, SAVINGS and CHECKING_AND_SAVINGS
    abstract fun getBalance() : Double
    abstract fun addMoney(money : Double, toTypeIfSecondAccount : AccountType)
    abstract fun withdrawMoney(money : Double, fromTypeIfSecondAccount : AccountType) : Boolean
    abstract fun transferMoneyToSomeone(money : Double, fromTypeIfSecondAccount : AccountType, receiverAccount : Account) : Boolean
    abstract fun convertMoneyFromMyCheckingToMySavings(money : Double) : Boolean
    abstract fun convertMoneyFromMySavingsToMyChecking(money : Double) : Boolean

    // inherited by all, having implementation
    fun getInterestRatePerYear() : Double{
        val bankStandardInterest = Account.yearlyInterestRate
        val category = InterestRatePerYearForAmount.values().forEach { if (it.interestRate.equals(bankStandardInterest)) it.amountMore }

        print("Standard Interest per year: $bankStandardInterest for amount more than: $category")
        return bankStandardInterest
    }
}

enum class AccountType(val type : String){
    CHECKING("checking"),
    SAVINGS("savings"),
    CHECKING_AND_SAVINGS("checking_and_savings"),
    UNDEFINED("")
}

// enum class allowing choosing overdraft limit according to salary range
enum class OverdraftLimitType(val limit : Double, val salaryLess : Double){
    LIMIT_1000(1000.0, 1000.0),
    LIMIT_1600(1600.0, 2350.0),
    LIMIT_2000(2000.0, 3500.0),
    LIMIT_3000(3000.0, 4800.0),
    LIMIT_4500(4500.0, 6300.0),
    LIMIT_6000(6000.0, 8000.0),
    UNDEFINED(0.0, 0.0)
}

enum class InterestRatePerYearForAmount(val interestRate : Double, val amountMore : Double){
    INTEREST_3_POINT_23(3.23, 9000.0),
    INTEREST_4_POINT_02(4.02, 25000.0),
    INTEREST_5_POINT_57(5.57, 43000.0),
    INTEREST_6_POINT_15(6.15, 65000.0),
    UNDEFINED(0.0, 0.0)
}

enum class PeriodOfInterest(val period : String){
    MONTHS_12("12_months"),
    MONTHS_1("1_months"),
}