package com.example.contacts

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ContactAdapter(
    private val context: Context,
    private val contacts: MutableList<Contact>
) : BaseAdapter() {

    override fun getCount(): Int = contacts.size

    override fun getItem(position: Int): Contact = contacts[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contact, parent, false)

        val contact = getItem(position)

        val imgContact = view.findViewById<ImageView>(R.id.imgContact)
        val txtNom = view.findViewById<TextView>(R.id.txtNom)
        val txtPrenom = view.findViewById<TextView>(R.id.txtPrenom)
        val txtTelephone = view.findViewById<TextView>(R.id.txtTelephone)

        txtNom.text = contact.nom
        txtPrenom.text = contact.prenom
        txtTelephone.text = contact.telephone

        chargerPhoto(context, imgContact, contact.photo)

        return view
    }

    companion object {
        fun chargerPhoto(context: Context, imageView: ImageView, photoPath: String) {
            if (photoPath.isNotEmpty()) {
                val resId = context.resources.getIdentifier(photoPath, "drawable", context.packageName)
                if (resId != 0) {
                    imageView.setImageResource(resId)
                } else {
                    try {
                        imageView.setImageURI(Uri.parse(photoPath))
                    } catch (e: Exception) {
                        imageView.setImageResource(android.R.drawable.sym_def_app_icon)
                    }
                }
            } else {
                imageView.setImageResource(android.R.drawable.sym_def_app_icon)
            }
        }
    }
}
