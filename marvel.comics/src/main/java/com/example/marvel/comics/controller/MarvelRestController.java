package com.example.marvel.comics.controller;

import com.example.marvel.comics.MarvelRestHelper;
import com.example.marvel.comics.models.ComicCharacter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import com.gtranslate.Translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MarvelRestController
{
    public static List<ComicCharacter> comicCharacters;
    public static Map<ComicCharacter, String> englishDescriptions = new HashMap<>();

    @GetMapping( "/characters" )
    public Long[] characters()
    {
        populateCharacters();
        Long[] idList = new Long[ comicCharacters.size() ];
        int idx = 0;
        for ( ComicCharacter character : comicCharacters )
        {
            idList[ idx++ ] = Long.valueOf( character.getId() );
        }
        return idList;
    }

    @GetMapping( "/characters/{characterId}" )
    public ComicCharacter getCharacterInLanguage( @PathVariable int characterId, @RequestParam( "languageCode" ) Optional<String> languageCode ) throws IOException
    {

        populateCharacters();
        for ( ComicCharacter character : comicCharacters )
        {
            if ( character.getId() == characterId )
            {
                character.setDescription( englishDescriptions.get( character ) );
                if ( languageCode.isPresent() )
                {
                    String res = translate( character.getDescription(), "en", (String)languageCode.get() );
                    character.setDescription( res );
                }
                return character;
            }
        }
        return null;
    }

    private String translate( String description, String from, String to ) throws IOException
    {
        URL nextUrl;
        description = description.replaceAll( "\\n", "" );
        description = URLEncoder.encode( description, StandardCharsets.UTF_8.name() );
        String urlString = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + from + "&tl=" + to + "&dt=t&q=" + description;
        nextUrl = new URL( urlString );
        HttpURLConnection conn = (HttpURLConnection)nextUrl.openConnection();
        conn.setRequestMethod( "GET" );
        conn.setRequestProperty( "Accept", "application/json" );
        conn.connect();
        BufferedReader reader = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
        String line;
        String output = "";
        int hackCounter = 0;
        while ( ( line = reader.readLine() ) != null )
        {
            int endOfText = line.indexOf( "\",\"" ) - 4;
            if ( endOfText >= 0 )
            {
                hackCounter++;
                if ( hackCounter % 2 == 1 )
                {
                    line = line.replace( "[[[\"", "" ).substring( 0, endOfText );
                    System.out.println( line );
                    output += line.replace( ",[\"", "" ) + "\n";
                }
            }
        }

        return output;
    }

    public void populateCharacters()
    {
        if ( comicCharacters == null )
        {
            comicCharacters = ( new MarvelRestHelper() ).getAllComicCharacters();
            for ( ComicCharacter character : comicCharacters )
            {
                englishDescriptions.put( character, character.getDescription() );
            }
        }

    }
}
