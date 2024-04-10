package com.phenix.go.other;

/**
 *
 * @author Edouard Jeanjean<edouard128@hotmail.com>
 */
public class Case {

    public String type;

    /**
     * 0 = pas dans un groupe.
     */
    public int groupe = 0;

    public Case(String type) {
        this.type = type;
    }
}
