package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

class Owner(
        var firstName : String = "",
        var lastName : String = "",
        var dateOfBirth : String = "",
        var nationality : String = "",
        var address : String = "",
        var occupation : String = "",
        var account : Account? = null,
        var accountType : AccountType? = account?.type,
        val id : Long = System.currentTimeMillis() - "owner".length
){

}