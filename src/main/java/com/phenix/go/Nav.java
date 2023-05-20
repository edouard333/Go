package com.phenix.go;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Créé le fichier de navigation.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class Nav {

    private PrintWriter nav;

    /**
     *
     * @param fichier
     *
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public Nav(String fichier) throws UnsupportedEncodingException, FileNotFoundException {
        OutputStream os = new FileOutputStream(fichier);
        this.nav = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
        nav.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        nav.println("<!DOCTYPE html>");
        nav.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:epub=\"http://www.idpf.org/2007/ops\" lang=\"fr\" xml:lang=\"fr\">");
        nav.println("<head>");
        nav.println("  <title>ePub NAV</title>");
        nav.println("  <meta charset=\"utf-8\" />");
        //nav.println("  <link href=\"../Styles/sgc-nav.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        nav.println("</head>");
        nav.println("<body epub:type=\"frontmatter\">");
        nav.println("  <nav epub:type=\"toc\" id=\"toc\" role=\"doc-toc\">");
        nav.println("    <h1>Table des matières</h1>");
        nav.println("    <ol>");
    }

    public void addExo(String fichier, String titre) {
        nav.println("      <li>");
        nav.println("        <a href=\"" + fichier + "\">" + titre + "</a>");
        nav.println("      </li>");
    }

    public void close() {
        nav.println("    </ol>");
        nav.println("  </nav>");
        nav.println("  <nav epub:type=\"landmarks\" id=\"landmarks\" hidden=\"\">");
        nav.println("    <h2>Points de repère</h2>");
        nav.println("    <ol>");
        nav.println("      <li>");
        nav.println("        <a epub:type=\"toc\" href=\"#toc\">Table des matières</a>");
        nav.println("      </li>");
        nav.println("    </ol>");
        nav.println("  </nav>");
        nav.println("</body>");
        nav.println("</html>");
        nav.close();
    }
}
