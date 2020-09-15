Marvel Comics API

The Marvel Comics API allows developers to access information about Marvel's vast library of
comics.

Used the Marvel API (see http://developer.marvel.com/) to build a Characters API.

1) GET /characters – returns all Marvel character ids. Now the application when runs first time
takes time to load all the ids but then all the requests are served faster.

2) GET /characters/{characterId} – returns id, name , description, thumbnail information about
the character

3) GET /characters/ {characterId}?language={languageCode} – translates the description to
the language provided in the request parameter.

Now over here the better way to do will be using Google Translator Api but that didn’t have
free service ( only paid service to get the key), so had to use a free version of it and that did
not return a very clean result.

4) Created a swagger specification for this API.

Constraints :
Please make sure to put your PRIVATE_KEY and PUBLIC_KEY of marvel api in the environment
variable. Use the variable names as PRIVATE_KEY and PUBLIC_KEY.

Run: In order to run the application, just make sure to run the RestClient class.

