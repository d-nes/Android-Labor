package hu.bme.aut.android.contacts.model

class Contact(
    val name: String,
    val number: String) {

    companion object{
        const val KEY_NAME = "KEY_NAME"
        const val KEY_NUMBER = "KEY_NUMBER"
    }
}