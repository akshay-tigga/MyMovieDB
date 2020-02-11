# MyMovieDB #
Restful APIs written in java using Spring Boot.   APIs to find similar movies and get movie details.      
Movie titles are downloaded from www.themoviedb.org      
Movie details are downloaded from wikipedia    
Similarity in movies is based upon number of matching actors and categories.      


## API 0 ##
Return details of the below APIs.     
Url : /

## API 1 ##
Return sorted list of movie titles.             
Url : /movies              
Additional parameters : 'format' and 'sort'                 
'format' - defines return format, json or xml. Default is json. (Example: format=xml)            
'sort' - defines the sorting method used. Default is 'title.asc'. (Example: title.desc)          
       - Valid values are 'title.asc', 'title.desc', 'date.asc', 'date.desc', 'category.asc', 'category.desc'         

Examples of API -            
/movies?format=xml&sort=date.desc  : Return movie titles sorted by release date in descending order             
/movies?format=json&sort=category.asc :  Return movie titles sorted by category count(total number of category tags for the movie) in ascending order                       
 
---              

## API 2 ##            
Return all the information about a given movie            
Url : /movies/{title}                
    : Where {title} is the name of the movie you want information for                 
Additional parameters : 'format'                  
'format' - defines return format, json or xml. Default is json. (Example: format=xml)                  

Examples of API -                   
/movies/Free%20Solo  : Return all information for the movie 'Free Solo'                  
/movies/Free%20Solo?format=xml  : Return all information for the movie 'Free Solo' in xml format                  

---

## API 3 ##
Return a list of similar movie titles ordered by the most similar movie first.                  
Url : /similarmovies?title={movie_name}                  
    : Where {movie_name} is the name of the movie for which you want to find similar movies                  
Additional parameters : 'format' and 'limit'                  
'format' - defines return format, json or xml. Default is json. (Example: format=xml)                  
'limit' - Use this if you want to limit the number of movie titles you get back as result. Default value is '0'. A value of '0' will return all the similar movie titles.                  

Examples of API -                  
/similarmovies?title=Free%20Solo&limit=5  : Return top 5 movies similar to 'Free Solo'                  
similarmovies?title=Free%20Solo&format=xml  : Return all the movies similar to 'Free Solo' in xml format.                  
