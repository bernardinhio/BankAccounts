package bernardo.bernardinhio.kotlinclasstypesInbankingapp.data

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Account
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Owner

class AccountsOwners{

    companion object {
        var accountOwnerOnlyChecking: Account? = null
        var accountOwnerOnlySavings: Account? = null
        var accountOwnerBothCheckingAndSavings: Account? = null
        var ownerOnlyCheckingAccount: Owner? = null
        var ownerOnlySavingsAccount: Owner? = null
        var ownerBothCheckingAndSavingsAccount: Owner? = null
    }
}