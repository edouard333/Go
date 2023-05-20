package com.phenix.go;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;

/**
 * Créé l'image de goban.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class ImageGoban {

    /**
     * Résolution des pierre et donc de l'image.
     */
    private static final int RESOLUTION = 40;

    /**
     * Couleur du fond.
     */
    private static final Color COULEUR_FOND = Color.GRAY;

    /**
     *
     */
    private final String dossier;

    /**
     *
     */
    private final String fichier_sortie;

    /**
     *
     */
    private final BufferedImage bufferedImage;

    /**
     *
     */
    private final Graphics2D g2d;

    /**
     *
     */
    private final int width;

    /**
     *
     */
    private final int height;

    /* Taille des boules de référence sur le Goban.*/
    private final int taille_reference = 8;

    /**
     *
     * @param dossier
     * @param fichier_sortie
     * @param array Goban initial.
     *
     * @throws IOException
     */
    public ImageGoban(String dossier, String fichier_sortie, JSONArray array) throws IOException {
        this.dossier = dossier;
        this.fichier_sortie = fichier_sortie;

        // Dimension du goban.
        int taille_goban = array.size() + 1;

        // Dimension de l'image :
        this.width = (RESOLUTION * taille_goban);
        this.height = (RESOLUTION * taille_goban);

        // Constructs a BufferedImage of one of the predefined image types.
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create a graphics which can be used to draw into the buffered image
        this.g2d = bufferedImage.createGraphics();

        // Définit le fond :
        g2d.setColor(COULEUR_FOND);
        g2d.fillRect(0, 0, width, height);

        // Grille du goban:
        // Lignes horizontales :
        for (int y = 1; y < taille_goban; y++) {
            int x = 1;
            g2d.setColor(Color.BLACK);
            g2d.fillRect((x * RESOLUTION), (y * RESOLUTION), width - (2 * RESOLUTION), 1);
        }

        // Ligne verticale :
        for (int x = 1; x < taille_goban; x++) {
            int y = 1;
            g2d.setColor(Color.BLACK);
            g2d.fillRect((x * RESOLUTION), (y * RESOLUTION), 1, height - (2 * RESOLUTION));
        }

        // Référence de Goban (pour 13x13) :
        if (taille_goban == (13 + 1)) {
            for (int y = 1; y <= 3; y++) {
                for (int x = 1; x <= 3; x++) {
                    addReference(((1 + (x * 3))), ((1 + (y * 3))));
                }
            }
        } // Référence de Goban (pour 19x19) :
        else if (taille_goban == (19 + 1)) {
            addReference(4, 4);
            addReference(10, 4);
            addReference(16, 4);

            addReference(4, 10);
            addReference(10, 10);
            addReference(16, 10);

            addReference(4, 16);
            addReference(10, 16);
            addReference(16, 16);
        }

        // Ajout les pierres sur le goban :
        for (int y = 0; y < array.size(); y++) {
            JSONArray ligne = (JSONArray) array.get(y);

            for (int x = 0; x < ligne.size(); x++) {
                this.addPierre((String) ligne.get(x), x, y, "");
            }
        }
    }

    /**
     *
     * @return
     */
    public String getFileName() {
        return this.fichier_sortie;
    }

    /**
     *
     * @param x
     * @param y
     */
    private void addReference(int x, int y) {
        g2d.setColor(Color.black);
        g2d.fillOval((x * RESOLUTION) - (taille_reference / 2), (y * RESOLUTION) - (taille_reference / 2), taille_reference, taille_reference);

    }

    /**
     *
     * @param pierre
     * @param x
     * @param y
     * @param texte
     */
    public void addPierre(String pierre, int x, int y, String texte) {
        int abs_x = (x * RESOLUTION) + (RESOLUTION / 2);
        int abs_y = (y * RESOLUTION) + (RESOLUTION / 2);
        switch ((String) pierre) {
            case "b":
                g2d.setColor(Color.white);
                g2d.fillOval(abs_x, abs_y, RESOLUTION, RESOLUTION);
                g2d.setColor(Color.black);
                break;
            case "n":
                g2d.setColor(Color.black);
                g2d.fillOval(abs_x, abs_y, RESOLUTION, RESOLUTION);
                g2d.setColor(Color.white);
                break;
        }

        // S'il y a du texte :
        if (!texte.equals("")) {
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 32));
            if (!texte.equals("t")) {
                g2d.drawString(texte, abs_x + (RESOLUTION / 2) - 8, abs_y + (RESOLUTION / 2) + 10);
            } else {
                g2d.drawString(Character.toString('\u25B2'), abs_x + (RESOLUTION / 2) - 12, abs_y + (RESOLUTION / 2) + 10);
            }
        }
    }

    /**
     *
     */
    public void addReponse() {
    }

    /**
     * Sauve le fichier final.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        // Disposes of this graphics context and releases any system resources that it is using. 
        this.g2d.dispose();

        // Créé le dossier où se trouve le résultat.
        File sous_dossier = new File(this.dossier + "Images/png/");
        sous_dossier.mkdirs();

        // Save as PNG
        File file = new File(sous_dossier + this.fichier_sortie);
        ImageIO.write(bufferedImage, "png", file);

        // Save as JPEG
        /* file = new File(sous_dossier + "Images/jpeg/" + fichier_sortie + ".jpg");
        ImageIO.write(bufferedImage, "jpg", file);*/
    }
}
