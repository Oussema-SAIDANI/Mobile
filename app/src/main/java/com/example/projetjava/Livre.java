package com.example.projetjava;

public class Livre {
    private String Titre,Nom,Prenom,Resume,Image,Genre,Date,Editeur,ISBN;

    public Livre(String Titre, String Nom, String Prenom, String Resume, String Image, String Genre, String Date, String Editeur, String ISBN) {
        this.Titre = Titre;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Resume = Resume;
        this. Image = Image;
        this.Genre = Genre;
        this.Date = Date;
        this.Editeur = Editeur;
        this.ISBN = ISBN;
    }

    public Livre() {
    }

    public String getTitre() {
        return Titre;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getResume() {
        return Resume;
    }

    public String getImage() {
        return Image;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDate() {
        return Date;
    }

    public String getEditeur() {
        return Editeur;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setResume(String resume) {
        Resume = resume;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setEditeur(String editeur) {
        Editeur = editeur;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
