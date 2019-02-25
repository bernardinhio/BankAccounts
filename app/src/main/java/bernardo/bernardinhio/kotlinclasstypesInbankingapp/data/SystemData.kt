package bernardo.bernardinhio.kotlinclasstypesInbankingapp.data

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Account
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.AccountType
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Owner

class SystemData{

    companion object {
        var yearlyInterestRate : Double = 3.32

        // used in the UI
        var accountOnlyChecking: Account? = null
        var accountOnlySavings: Account? = null
        var accountBothCheckingAndSavings: Account? = null
        var ownerOnlyCheckingAccount: Owner? = null
        var ownerOnlySavingsAccount: Owner? = null
        var ownerBothCheckingAndSavingsAccount: Owner? = null

        /**
         * since all activities of the App are using this adat provider
         * synchronously and one the update is affecting on Location that is
         * main activity, then we can set a variable indicator  that will
         * be checked every time we restart() the MainActivity.
         * we only have 3 type of accounts in the App that we use to simulate
         * the system
         */
        var accountModified : AccountType = AccountType.UNDEFINED
    }
}