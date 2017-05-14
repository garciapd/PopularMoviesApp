# PopularMoviesApp
First project (MVP) for the Nano Degree of Android Development showing:

* Upon launch, present the user with an grid arrangement of movie posters.
* Allow your user to change sort order via a setting:
  * The sort order can be by most popular, or by top rated
* Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
  * original title
  * movie poster image thumbnail
  * A plot synopsis (called overview in the api)
  * user rating (called vote_average in the api)
  * release date

# Make it run
---------
`Add the themoviedb.org API KEY in the gradle.properties file in the field apiKey`


# Frameworks used
---------
- Picasso
- OkHTTP
- GSON

# Missing things, TODO
--------
There are some missing things like
- Testing
- Controlling some edge cases
- Refactor of the architecture