package com.phenix.go;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Lance le programme.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class Main {

    /**
     * Où commence le programme.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // On lit un Json.
        String json;

        // TODO : les fichiers sont dans le dossier "resources". Et le résultat doit aller dans "target" (ou build).
        String dossier = "C://XML/go/";

        // Menu :
        Nav nav = new Nav(dossier + "html/nav.xhtml");

        // Que les problemes :
        File d = new File(dossier + "json/probleme/");

        File[] liste_probleme = d.listFiles();

        // Parcoure chaque fichier JSON :
        for (int i = 0; i < liste_probleme.length; i++) {
            System.out.println("Fichier : " + liste_probleme[i].getName());

            // Récupère les informations du JSON :
            JSONObject probleme = (JSONObject) new ExtractJSON(liste_probleme[i]).getJSON();
            JSONObject en_tete = (JSONObject) probleme.get("entete");
            String numero = (String) en_tete.get("numero");
            System.out.println("numero : " + numero);
            String titre = (String) en_tete.get("titre");
            System.out.println("Titre : " + titre);
            String enonce = (String) en_tete.get("enonce");
            System.out.println("Enonce : " + enonce);

            JSONArray array = (JSONArray) probleme.get("goban");

            ImageGoban img_probleme = new ImageGoban(dossier, numero + "-probleme.png", array);

            JSONArray liste_pierre = (JSONArray) probleme.get("ajouter");

            if (liste_pierre != null) {
                for (int j = 0; j < liste_pierre.size(); j++) {
                    JSONObject pierre = (JSONObject) liste_pierre.get(j);
                    img_probleme.addPierre(
                            (String) pierre.get("pierre"),
                            Integer.parseInt((String) pierre.get("x")) - 1,
                            Integer.parseInt((String) pierre.get("y")) - 1,
                            (String) pierre.get("texte")
                    );
                }
            }
            img_probleme.close();

            ImageGoban image_reponse;

            String nom_fichier_reponse = "";

            String nom_fichier_exo = numero + "-probleme.xhtml";

            // Les réponses :
            JSONArray liste_reponse = (JSONArray) probleme.get("reponse");
            if (liste_reponse != null) {
                for (int j = 0; j < liste_reponse.size(); j++) {

                    String fichier_reponse = (String) ((JSONObject) liste_reponse.get(j)).get("fichier");

                    // Récupère le JSON de la réponse :
                    File f = new File(liste_probleme[i].getParent().replace("probleme", "reponse") + "/" + fichier_reponse);

                    try {
                        JSONObject reponse = (JSONObject) new ExtractJSON(f).getJSON();

                        System.out.println("Réponse : " + f.getAbsolutePath());

                        JSONObject en_tete_reponse = (JSONObject) reponse.get("entete");
                        String titre_reponse = (String) en_tete_reponse.get("titre");
                        System.out.println("Titre : " + titre_reponse);
                        String texte = (String) en_tete_reponse.get("texte");
                        System.out.println("texte : " + texte);

                        // Liste des pierres :
                        image_reponse = new ImageGoban(
                                dossier,
                                numero + "-reponse-" + f.getName().replace(".json", "").split("-")[2] + ".png",
                                array);

                        JSONArray reponse_pierre = (JSONArray) ((JSONObject) reponse.get("reponse")).get("ajout");

                        for (int k = 0; k < reponse_pierre.size(); k++) {
                            JSONObject pierre = (JSONObject) reponse_pierre.get(k);
                            image_reponse.addPierre(
                                    (String) pierre.get("pierre"),
                                    Integer.parseInt((String) pierre.get("x")) - 1,
                                    Integer.parseInt((String) pierre.get("y")) - 1,
                                    (String) pierre.get("texte")
                            );
                        }
                        image_reponse.close();

                        String nom_fichier = numero + "-reponse-" + f.getName().replace(".json", "").split("-")[2] + ".xhtml";

                        if (j == 0) {
                            nom_fichier_reponse = nom_fichier;
                        }

                        new Reponse(
                                dossier + "html/" + nom_fichier,
                                titre_reponse,
                                texte,
                                image_reponse.getFileName(),
                                nom_fichier_exo
                        );
                    } catch (FileNotFoundException ex) {
                        System.out.println("La réponse n'existe pas.");
                    }
                }
            }

            new Exo(
                    dossier + "html/" + nom_fichier_exo,
                    ((numero != null) ? "Problème " + numero + ". " : "") + titre,
                    enonce,
                    img_probleme.getFileName(),
                    nom_fichier_reponse
            );

            nav.addExo(
                    nom_fichier_exo,
                    ((numero != null) ? "Problème " + numero + " : " : "") + ((!titre.equals("")) ? titre : "Exo")
            );

        }

        nav.close();
    }
}
