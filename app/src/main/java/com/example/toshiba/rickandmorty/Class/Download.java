
package com.example.toshiba.rickandmorty.Class;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Download {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("results")
    @Expose
    private List<Character> characters;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Download() {
    }

    /**
     * 
     * @param characters
     * @param info
     */
    public Download(Info info, List<Character> characters) {
        super();
        this.info = info;
        this.characters = characters;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

}
