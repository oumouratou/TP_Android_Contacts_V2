package com.example.contacts

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listViewContacts: ListView
    private lateinit var adapter: ContactAdapter

    private val contacts = mutableListOf(
        Contact("Barry", "Oumouratou", "22123456", "oumouratou@gmail.com", "photo_barry"),
        Contact("Diallo", "Fatoumata", "22345678", "fatoumata@gmail.com", "photo_fatoumata"),
        Contact("Camara", "Mamadou", "23456789", "mamadou@gmail.com", "photo_mamadou"),
        Contact("Sow", "Aminata", "24567890", "aminata@gmail.com", "photo_aminata"),
        Contact("Bah", "Ibrahima", "25678901", "ibrahima@gmail.com", "photo_ibrahima")
    )

    private val detailLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                val data = result.data

                if (data != null) {

                    val position = data.getIntExtra("position", -1)

                    if (position in contacts.indices) {

                        contacts[position] = Contact(
                            nom = data.getStringExtra("nom") ?: "",
                            prenom = data.getStringExtra("prenom") ?: "",
                            telephone = data.getStringExtra("telephone") ?: "",
                            email = data.getStringExtra("email") ?: "",
                            photo = data.getStringExtra("photo") ?: contacts[position].photo
                        )

                        adapter.notifyDataSetChanged()

                        Toast.makeText(
                            this,
                            "Contact modifié avec succès",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        listViewContacts = findViewById(R.id.listViewContacts)

        adapter = ContactAdapter(this, contacts)

        listViewContacts.adapter = adapter

        listViewContacts.setOnItemClickListener { _, _, position, _ ->

            val contact = contacts[position]

            val intent = Intent(this, DetailActivity::class.java)

            intent.putExtra("position", position)
            intent.putExtra("nom", contact.nom)
            intent.putExtra("prenom", contact.prenom)
            intent.putExtra("telephone", contact.telephone)
            intent.putExtra("email", contact.email)
            intent.putExtra("photo", contact.photo)

            detailLauncher.launch(intent)
        }
    }
}
