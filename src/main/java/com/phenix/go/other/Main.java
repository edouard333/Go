package com.phenix.go;

/**
 *
 * @author Edouard Jeanjean<edouard128@hotmail.com>
 */
public class Main {

    static int numero_groupe = 0;

    public static void main(String[] args) {

        // ' ' = vide, 'B' = blanc, 'N' = noir.
        String[][] terrain = {
            {" ", " ", " ", " ", " ", " "},
            {" ", " ", "N", "N", " ", " "},
            {" ", "B", " ", "N", "N", " "},
            {" ", "B", "B", " ", "N", " "},
            {" ", " ", " ", " ", "N", " "},
            {" ", " ", " ", " ", " ", " "}
        };

        Case[][] c = new Case[terrain.length][terrain[0].length];

        // String = case :
        for (int y = 0; y < c.length; y++) {
            for (int x = 0; x < c[0].length; x++) {
                c[y][x] = new Case(terrain[y][x]);
            }
        }
        afficher(c, false);
        afficher(c, true);
        // Groupe ? (A ou B...)
        for (int y = 0; y < c.length; y++) {
            for (int x = 0; x < c[0].length; x++) {

                // Si pas dans un groupe, on vérifie que le reste n'est pas un groupe :
                if (c[y][x].groupe == 0) {
                    if (c[y][x].type.equals("N")) {
                        Case[] a_cote = contour(c, x, y);
                        int numero = numeroGroupe(c[y][x], a_cote);
                        System.out.println("N à côté : " + a_cote.length + ", numero de groupe : " + numero);

                        if (numero == 0) {
                            numero_groupe++;
                            c[y][x].groupe = numero_groupe;
                        } else {
                            c[y][x].groupe = numero;
                        }

                        System.out.println("numero de groupe : " + c[y][x].groupe);
                    }
                    if (c[y][x].type.equals("B")) {
                        Case[] a_cote = contour(c, x, y);
                        int numero = numeroGroupe(c[y][x], a_cote);
                        System.out.println("N à côté : " + a_cote.length + ", numero de groupe : " + numero);

                        if (numero == 0) {
                            numero_groupe++;
                            c[y][x].groupe = numero_groupe;
                        } else {
                            c[y][x].groupe = numero;
                        }
                        System.out.println("numero de groupe : " + c[y][x].groupe);
                    }
                }
            }
        }

        afficher(c, true);
    }

    private static int numeroGroupe(Case c, Case[] a_cote) {

        for (int i = 0; i < a_cote.length; i++) {
            System.out.println(a_cote[i].type + " (" + a_cote[i].groupe + ") == " + c.type);
            if (a_cote[i].type.equals(c.type)) {
                if (a_cote[i].groupe != 0) {
                    return a_cote[i].groupe;
                }
            }
        }

        return 0;
    }

    private static void afficher(Case[][] terrain, boolean afficher_groupe) {
        for (int y = 0; y < terrain.length; y++) {
            for (int x = 0; x < terrain.length; x++) {

                String afficher = terrain[y][x].type;

                if (afficher_groupe && !terrain[y][x].type.equals(" ")) {
                    afficher = terrain[y][x].groupe + "";
                }
                System.out.print(afficher + " ");
            }
            System.out.println();
        }
    }

    /**
     * Juste avoir les case à côté...
     *
     * @param terrain_analyse
     * @param x
     * @param y
     * @return
     */
    private static Case[] contour(Case[][] terrain, int x, int y) {
        Case[] a_cote = new Case[4];

        a_cote[0] = terrain[y + 1][x];
        a_cote[1] = terrain[y - 1][x];
        a_cote[2] = terrain[y][x + 1];
        a_cote[3] = terrain[y][x - 1];

        System.out.print("Contour (" + x + "," + y + ") : ");
        for (int i = 0; i < a_cote.length; i++) {
            System.out.print(a_cote[i].type + ", ");
        }
        System.out.println();

        return a_cote;
    }
}
