package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

import org.junit.Assert.*

class AccountTest{

    // getFormattedTime

    @org.junit.Test
    fun test_getFormattedTime(){
        val dateGot = Account.getFormattedTime(System.currentTimeMillis())
        //assertEquals("18:36 27sec", dateGot)
    }


    // getDetailsOfYearlyInterestRate

    @org.junit.Test
    fun test_findDetailsYearlyInterestRate(){ // from enum
        val checkingAccount = CheckingAccount()

        // in real co,text we don't know which instance of the enum it is but we have just for ex: 3.23
        val aDoubleValueInterestRateExpected = YearlyInterestRatePerSalaryRange.INTEREST_3_POINT_23.yearlyRate
        val pairInfoGot : Pair<Double, Double> = YearlyInterestRatePerSalaryRange.findDetailsYearlyInterestRate(aDoubleValueInterestRateExpected)
        assertEquals(aDoubleValueInterestRateExpected.toString(), pairInfoGot.first.toString())

        val aDoubleValueMonthlySalaryExpected = YearlyInterestRatePerSalaryRange.INTEREST_3_POINT_23.monthlySalaryLessThan
        assertEquals(aDoubleValueMonthlySalaryExpected.toString(), pairInfoGot.second.toString())
    }
}