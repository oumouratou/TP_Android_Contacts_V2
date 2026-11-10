
package com.example.contacts

data class Contact(
    var nom: String,
    var prenom: String,
    var telephone: String,
    var email: String,
    var photo: String = ""
)
