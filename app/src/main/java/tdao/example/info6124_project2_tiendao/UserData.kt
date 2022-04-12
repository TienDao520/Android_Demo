package tdao.example.info6124_project2_tiendao

/**No5-2: Create a data class whose main purpose is to hold data
 * It will be used to streaming the json file
 * Check the integrated steps in Module build.gradle files
 * Next step is create assets folder and put the json into it*/
data class UserData(var address:String?=null,
                    var phoneNumber:String?=null,
                    var smsMessage:String?=null,
                    var emailAddress:String?=null,
                    var emailSubject:String?=null,
                    var emailMessage:String?=null,
                    ){

}
