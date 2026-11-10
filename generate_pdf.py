import os
from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Image, Table, TableStyle, PageBreak
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle

def generate_pdf():
    pdf_path = r'D:\ContactsListViewIntents\Compte_Rendu_TP_Contacts.pdf'
    doc = SimpleDocTemplate(pdf_path, pagesize=letter, rightMargin=40, leftMargin=40, topMargin=40, bottomMargin=40)
    story = []

    styles = getSampleStyleSheet()
    title_style = ParagraphStyle(
        'TitleStyle',
        parent=styles['Heading1'],
        fontSize=24,
        leading=28,
        textColor=colors.HexColor('#263B59'),
        alignment=1,
        spaceAfter=20
    )
    heading_style = ParagraphStyle(
        'HeadingStyle',
        parent=styles['Heading2'],
        fontSize=16,
        leading=20,
        textColor=colors.HexColor('#263B59'),
        spaceBefore=15,
        spaceAfter=10
    )
    body_style = ParagraphStyle(
        'BodyStyle',
        parent=styles['Normal'],
        fontSize=11,
        leading=15,
        textColor=colors.HexColor('#333333'),
        spaceAfter=8
    )

    # Title
    story.append(Paragraph("COMPTE RENDU DE TP ANDROID", title_style))
    story.append(Paragraph("Application : Gestion de Contacts (Version 2)", ParagraphStyle('SubTitle', parent=title_style, fontSize=16, textColor=colors.HexColor('#555555'))))
    story.append(Spacer(1, 15))

    # Section 1: Introduction
    story.append(Paragraph("1. Introduction et Objectifs", heading_style))
    story.append(Paragraph("Ce projet consiste à développer une application Android en Kotlin permettant de gérer une liste de contacts. L'application comporte deux activités principales (<b>MainActivity</b> et <b>DetailActivity</b>), utilise une liste personnalisée (<b>ListView</b> avec <b>ContactAdapter</b>) et met en œuvre des <b>Intents explicites</b> et <b>implicites</b> ainsi que le mécanisme de retour de résultat d'activité (<b>ActivityResultContracts</b>).", body_style))

    # Section 2: Architecture et Composants
    story.append(Paragraph("2. Architecture et Composants Principaux", heading_style))
    story.append(Paragraph("• <b>Contact.kt</b> : Modèle de données représentant un contact (nom, prénom, téléphone, e-mail, photo).", body_style))
    story.append(Paragraph("• <b>MainActivity.kt</b> : Activité principale affichant la liste des contacts à l'aide d'un adaptateur personnalisé et gérant le retour des modifications.", body_style))
    story.append(Paragraph("• <b>DetailActivity.kt</b> : Activité secondaire permettant de consulter et modifier les informations, d'appeler, d'envoyer un e-mail, ou de capturer une photo.", body_style))
    story.append(Paragraph("• <b>ContactAdapter.kt</b> : Adaptateur personnalisé gérant l'affichage de chaque ligne de contact (photo, nom, prénom, téléphone).", body_style))

    # Section 3: Fonctionnalités Réalisées
    story.append(Paragraph("3. Fonctionnalités Réalisées", heading_style))
    story.append(Paragraph("1. Affichage d'une liste de contacts personnalisée avec avatars et informations détaillées.", body_style))
    story.append(Paragraph("2. Transmission des données par Intent explicite vers <b>DetailActivity</b>.", body_style))
    story.append(Paragraph("3. Appels téléphoniques (Intent implicite <b>ACTION_DIAL</b>).", body_style))
    story.append(Paragraph("4. Envoi d'e-mails (Intent implicite <b>ACTION_SENDTO</b>).", body_style))
    story.append(Paragraph("5. Sélection de photos depuis la galerie ou prise de vue haute résolution via la caméra.", body_style))
    story.append(Paragraph("6. Validation des champs obligatoires et mise à jour dynamique des contacts en temps réel.", body_style))

    story.append(PageBreak())

    # Section 4: Captures d'écran
    story.append(Paragraph("4. Captures d'écran de l'Application", heading_style))

    img1_path = r'D:\ContactsListViewIntents\captures\MainActivity.png'
    img2_path = r'D:\ContactsListViewIntents\captures\DetailActivity.png'

    if os.path.exists(img1_path):
        story.append(Paragraph("<b>Écran Principal (MainActivity) :</b>", body_style))
        story.append(Image(img1_path, width=200, height=400))
        story.append(Spacer(1, 10))

    if os.path.exists(img2_path):
        story.append(Paragraph("<b>Écran de Détails et Modification (DetailActivity) :</b>", body_style))
        story.append(Image(img2_path, width=200, height=400))
        story.append(Spacer(1, 10))

    story.append(PageBreak())

    # Section 5: Tests et Difficultés
    story.append(Paragraph("5. Tests Effectués et Difficultés Rencontrées", heading_style))
    story.append(Paragraph("• <b>Tests effectués</b> : Validation de la modification des contacts avec mise à jour immédiate de la liste, test des boutons d'appel et d'e-mail, vérification des champs obligatoires vides, et test des boutons Enregistrer et Annuler.", body_style))
    story.append(Paragraph("• <b>Difficultés rencontrées</b> : Résolution des erreurs de parsing XML (en-têtes et BOM), configuration correcte du chargement des images à la fois depuis les ressources `drawable` et les URIs de fichiers, et gestion de la compatibilité des contrats Activity Result API.", body_style))
    story.append(Paragraph("• <b>Conclusion</b> : L'application est totalement fonctionnelle, robuste, élégante et respecte l'ensemble des consignes du cahier des charges du TP.", body_style))

    doc.build(story)
    print("PDF generated successfully.")

if __name__ == '__main__':
    generate_pdf()
