package com.phenix.go.other;

/**
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class Case {

    /**
     *
     */
    public String type;

    /**
     * <em>0</em> = pas dans un groupe.
     */
    public int groupe = 0;

    /**
     *
     * @param type
     */
    public Case(String type) {
        this.type = type;
    }
}
