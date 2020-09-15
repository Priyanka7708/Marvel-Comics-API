package com.example.marvel.comics.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataObject {
    private List<ComicCharacter> results;
    private int total;

    public void setResults(List<ComicCharacter> results) {
        this.results = results;
    }

    public List<ComicCharacter> getResults()
    {
        return results;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
