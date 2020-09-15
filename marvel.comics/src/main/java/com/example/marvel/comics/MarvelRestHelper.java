package com.example.marvel.comics;

import com.example.marvel.comics.models.ComicCharacter;
import com.example.marvel.comics.models.ReturnObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MarvelRestHelper
{
    public List<ComicCharacter> getAllComicCharacters()
    {
        String publicKey = System.getenv().get( "PUBLIC_KEY" );
        String privateKey = System.getenv().get( "PRIVATE_KEY" );
        long timeStamp = System.currentTimeMillis();
        int limit = 100;

        String stringToHash = timeStamp + privateKey + publicKey;
        String hash = DigestUtils.md5DigestAsHex( stringToHash.getBytes() );

        List<ComicCharacter> comicCharacters = new ArrayList<>( 1000 );

        try
        {

            int offset = 0;
            int totalCharacters = 0;

            URL nextUrl;
            while ( offset == 0 || totalCharacters != comicCharacters.size() )
            {
                String urlString = offset == 0 ?
                                   String.format( "http://gateway.marvel.com/v1/public/characters?ts=%d&apikey=%s&hash=%s&limit=%d", timeStamp, publicKey, hash, limit )
                                               : String.format( "http://gateway.marvel.com/v1/public/characters?ts=%d&apikey=%s&hash=%s&limit=%d&offset=%d", timeStamp, publicKey, hash, limit, offset );
                nextUrl = new URL( urlString );
                HttpURLConnection conn = (HttpURLConnection)nextUrl.openConnection();
                conn.setRequestMethod( "GET" );
                conn.setRequestProperty( "Accept", "application/json" );
                conn.connect();
                ObjectMapper objectMapper = new ObjectMapper();
                BufferedReader reader = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
                String line;
                while ( ( line = reader.readLine() ) != null )
                {
                    ReturnObject test = objectMapper.readValue( line, ReturnObject.class );
                    comicCharacters.addAll( test.getData().getResults() );
                    if ( totalCharacters == 0 )
                        totalCharacters = test.getData().getTotal();
                }
                offset += limit;
            }
            for ( ComicCharacter comicCharacter : comicCharacters )
                System.out.println( comicCharacter.getId() + " " + comicCharacter.getName() + " " + comicCharacter.getThumbnail().getPath() + " " + comicCharacter.getThumbnail().getExtension() );

        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( ProtocolException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return comicCharacters;
    }
}
