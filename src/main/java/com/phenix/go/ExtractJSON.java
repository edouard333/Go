package com.phenix.go;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class ExtractJSON {

    private Object json;

    public ExtractJSON(File fichier) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(fichier), "UTF-8");
        String texte = "";

        while (sc.hasNextLine()) {
            texte += sc.nextLine();
        }

        sc.close();

        JSONParser parser = new JSONParser();
        Object o = null;
        try {
            this.json = parser.parse(texte);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Object getJSON() {
        return this.json;
    }
}
