package com.phenix.go;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Créé le fichier de réponse.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class Reponse {

    /**
     *
     * @param output
     * @param titre
     * @param enonce
     * @param fichier_image
     * @param fichier_exo
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public Reponse(String output, String titre, String enonce, String fichier_image, String fichier_exo) throws FileNotFoundException, UnsupportedEncodingException {
        OutputStream os2 = new FileOutputStream(output);
        PrintWriter w = new PrintWriter(new OutputStreamWriter(os2, "UTF-8"));

        w.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        w.println("<!DOCTYPE html>");
        w.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:epub=\"http://www.idpf.org/2007/ops\">");
        w.println("<head>");
        w.println("  <title></title>");
        w.println("</head>");
        w.println("<body>");
        w.println("<center><img src= \"../Images/png/" + fichier_image + "\"/></center><br/>");
        w.println("<center><strong><i>" + titre + "</i></strong></center><br/>");
        w.println(enonce + "<br/>");
        w.println("<a href= \"" + fichier_exo + "\">Exo</a><br/>");
        w.println("<a href= \"nav.xhtml\">Retour au menu</a>");
        w.println("</body>");
        w.println("</html>");
        w.close();
    }
}
