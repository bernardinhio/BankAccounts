package bernardo.bernardinhio.kotlinclasstypesInbankingapp.data

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.*

class SystemData{

    companion object {

        // used in the UI
        var accountOnlyChecking: CheckingAccount? = null
        var accountOnlySavings: SavingsAccount? = null
        var accountBothCheckingAndSavings: CheckingAccount? = null
        var ownerOnlyCheckingAccount: Owner? = null
        var ownerOnlySavingsAccount: Owner? = null
        var ownerBothCheckingAndSavingsAccount: Owner? = null

        fun resetData() {
            accountOnlyChecking = null
            accountOnlySavings = null
            accountBothCheckingAndSavings = null
            ownerOnlyCheckingAccount = null
            ownerOnlySavingsAccount = null
            ownerBothCheckingAndSavingsAccount = null
        }

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