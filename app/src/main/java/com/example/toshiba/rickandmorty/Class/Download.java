
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
    private List<CharacterAPI> characters;

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
    public Download(Info info, List<CharacterAPI> characters) {
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

    public List<CharacterAPI> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterAPI> characters) {
        this.characters = characters;
    }

}
