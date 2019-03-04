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

        var standardYearlyInterestRate : Double = YearlyInterestRatePerSalaryRange.INTEREST_3_POINT_23.yearlyRate

        // format : Feb 28, 2019 at 18:36: 27sec
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
    abstract fun addMoney(money : Double, toTypeIfSecondAccount : AccountType) : Boolean
    abstract fun withdrawMoney(money : Double, fromTypeIfSecondAccount : AccountType) : Boolean
    abstract fun transferMoneyToSomeone(money : Double, fromTypeIfSecondAccount : AccountType, receiverAccount : Account) : Boolean
    abstract fun convertMoneyFromMyCheckingToMySavings(money : Double) : Boolean
    abstract fun convertMoneyFromMySavingsToMyChecking(money : Double) : Boolean

    /**
     * contrary to the one inside SavingsAccount class which will return
     * info about the specific interest-rate set on the creation
     * of the object SavingsAccount, here will return the standard one
     * this is inherited by all subclasses, for ex CheckingAccount, BUT
     * overridden in the SavingsAccount
     */
    open fun getDetailsOfYearlyInterestRate() : Pair<Double, Double> {
        return YearlyInterestRatePerSalaryRange.findDetailsYearlyInterestRate(Account.standardYearlyInterestRate)
    }

}

enum class AccountType(val type : String){
    CHECKING("checking"),
    SAVINGS("savings"),
    CHECKING_AND_SAVINGS("checking_and_savings"),
    UNDEFINED("")
}

// enum class allowing choosing overdraft limit according to salary range
enum class OverdraftLimitType(val limit : Double, val monthlySalaryLessThan : Double){
    LIMIT_1000(1000.0, 1000.0),
    LIMIT_1600(1600.0, 2350.0),
    LIMIT_2000(2000.0, 3500.0),
    LIMIT_3000(3000.0, 4800.0),
    LIMIT_4500(4500.0, 6300.0),
    LIMIT_6000(6000.0, 8000.0),
    UNDEFINED(0.0, 0.0)
}

enum class YearlyInterestRatePerSalaryRange(val yearlyRate : Double, val monthlySalaryLessThan : Double){
    INTEREST_3_POINT_23(3.23, 9000.0),
    INTEREST_4_POINT_02(4.02, 25000.0),
    INTEREST_5_POINT_57(5.57, 43000.0),
    INTEREST_6_POINT_15(6.15, 65000.0),
    UNDEFINED(0.0, 0.0)
    ; // only place in Kotlin we should use " ; "

    // call by individual enum objects
    fun findDetailsYearlyInterestRate() : Double{
        print("Monthly salary less than: $yearlyRate for Interest rate per year: $yearlyRate")
        return yearlyRate
    }

    // called when we just know the value of interest not the Object
    companion object { // static

        fun findDetailsYearlyInterestRate(yearlyInterestRateSearched : Double) : Pair<Double, Double>{
            val arrayAllInstances : Array<YearlyInterestRatePerSalaryRange> = YearlyInterestRatePerSalaryRange.values()
            var  monthlySalaryLessThanSearched : Double = 0.toDouble()
            arrayAllInstances.forEach {
                if (it.yearlyRate.equals(yearlyInterestRateSearched)){
                    monthlySalaryLessThanSearched = it.monthlySalaryLessThan
                }}
            print("Monthly salary less than: $monthlySalaryLessThanSearched for Interest rate per year: $yearlyInterestRateSearched")
            return Pair<Double, Double>(yearlyInterestRateSearched, monthlySalaryLessThanSearched)
        }
    }
}

enum class PeriodOfInterest(val period : String){
    MONTHS_12("12_months"),
    MONTHS_1("1_months"),
}