package com.example.marvel.comics.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicCharacter
{
    private int id;
    private String name;
    private String description;
    private Thumbnail thumbnail;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Thumbnail getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail( Thumbnail thumbnail )
    {
        this.thumbnail = thumbnail;
    }
}
