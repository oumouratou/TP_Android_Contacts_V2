package com.example.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class DetailActivity : AppCompatActivity() {

    private lateinit var imgDetail: ImageView
    private lateinit var edtNom: EditText
    private lateinit var edtPrenom: EditText
    private lateinit var edtTelephone: EditText
    private lateinit var edtEmail: EditText

    private var photoUri: String = ""
    private var tempPhotoUri: Uri? = null
    private var position: Int = -1

    // Sélectionner une photo dans la galerie
    private val galerieLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                try {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: Exception) {
                    // Ignore
                }

                photoUri = uri.toString()
                ContactAdapter.chargerPhoto(this, imgDetail, photoUri)
            }
        }

    // Prendre une photo haute résolution avec la caméra
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && tempPhotoUri != null) {
                photoUri = tempPhotoUri.toString()
                ContactAdapter.chargerPhoto(this, imgDetail, photoUri)
                Toast.makeText(this, "Photo enregistrée", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Aucune photo prise", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imgDetail = findViewById(R.id.imgDetail)
        edtNom = findViewById(R.id.edtNom)
        edtPrenom = findViewById(R.id.edtPrenom)
        edtTelephone = findViewById(R.id.edtTelephone)
        edtEmail = findViewById(R.id.edtEmail)

        val btnGalerie = findViewById<Button>(R.id.btnGalerie)
        val btnCamera = findViewById<Button>(R.id.btnCamera)
        val btnAppeler = findViewById<Button>(R.id.btnAppeler)
        val btnEmail = findViewById<Button>(R.id.btnEmail)
        val btnEnregistrer = findViewById<Button>(R.id.btnEnregistrer)
        val btnAnnuler = findViewById<Button>(R.id.btnAnnuler)

        position = intent.getIntExtra("position", -1)

        edtNom.setText(intent.getStringExtra("nom") ?: "")
        edtPrenom.setText(intent.getStringExtra("prenom") ?: "")
        edtTelephone.setText(intent.getStringExtra("telephone") ?: "")
        edtEmail.setText(intent.getStringExtra("email") ?: "")

        photoUri = intent.getStringExtra("photo") ?: ""

        ContactAdapter.chargerPhoto(this, imgDetail, photoUri)

        btnGalerie.setOnClickListener {
            galerieLauncher.launch(arrayOf("image/*"))
        }

        btnCamera.setOnClickListener {
            try {
                val fichier = File(filesDir, "contact_${position}_${System.currentTimeMillis()}.jpg")
                tempPhotoUri = Uri.fromFile(fichier)
                cameraLauncher.launch(tempPhotoUri!!)
            } catch (e: Exception) {
                Toast.makeText(this, "Caméra indisponible", Toast.LENGTH_SHORT).show()
            }
        }

        btnAppeler.setOnClickListener {
            val telephone = edtTelephone.text.toString().trim()
            if (telephone.isEmpty()) {
                edtTelephone.error = "Entrez un numéro"
                return@setOnClickListener
            }

            val intentAppel = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telephone"))
            ouvrirApplication(intentAppel)
        }

        btnEmail.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            if (email.isEmpty()) {
                edtEmail.error = "Entrez une adresse e-mail"
                return@setOnClickListener
            }

            val intentEmail = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            ouvrirApplication(intentEmail)
        }

        btnEnregistrer.setOnClickListener {
            enregistrerModifications()
        }

        btnAnnuler.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun ouvrirApplication(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Aucune application compatible trouvée", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enregistrerModifications() {
        val nom = edtNom.text.toString().trim()
        val prenom = edtPrenom.text.toString().trim()
        val telephone = edtTelephone.text.toString().trim()
        val email = edtEmail.text.toString().trim()

        if (nom.isEmpty()) {
            edtNom.error = "Le nom est obligatoire"
            edtNom.requestFocus()
            return
        }

        if (prenom.isEmpty()) {
            edtPrenom.error = "Le prénom est obligatoire"
            edtPrenom.requestFocus()
            return
        }

        if (telephone.isEmpty()) {
            edtTelephone.error = "Le téléphone est obligatoire"
            edtTelephone.requestFocus()
            return
        }

        if (email.isEmpty()) {
            edtEmail.error = "L'e-mail est obligatoire"
            edtEmail.requestFocus()
            return
        }

        if (position == -1) {
            Toast.makeText(this, "Contact invalide", Toast.LENGTH_SHORT).show()
            return
        }

        val resultat = Intent().apply {
            putExtra("position", position)
            putExtra("nom", nom)
            putExtra("prenom", prenom)
            putExtra("telephone", telephone)
            putExtra("email", email)
            putExtra("photo", photoUri)
        }

        setResult(RESULT_OK, resultat)
        Toast.makeText(this, "Modifications enregistrées", Toast.LENGTH_SHORT).show()
        finish()
    }
}
