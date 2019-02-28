package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

import org.junit.Assert.*
import java.sql.Timestamp

class AccountTest{

    // getFormattedTime

    @org.junit.Test
    fun test_getFormattedTime(){
        val dateGot = Account.getFormattedTime(System.currentTimeMillis())
        //assertEquals("18:36 27sec", dateGot)
    }


    // getInterestRatePerYear

    @org.junit.Test
    fun test_getInterestRatePerYear(){
        val checkingAccount = CheckingAccount()

        val interestGot = checkingAccount.getInterestRatePerYear()
        val interestExpected = Account.yearlyInterestRate

        assertEquals(interestExpected.toString(), interestGot.toString())

        val categoryGot = InterestRatePerYearForAmount.values().forEach { if (it.interestRate.equals(interestGot)) it.amountMore }
        val categoryExpected = InterestRatePerYearForAmount.values().forEach { if (it.interestRate.equals(interestExpected)) it.amountMore }
        assertEquals(categoryExpected.toString(), categoryGot.toString())
    }
}