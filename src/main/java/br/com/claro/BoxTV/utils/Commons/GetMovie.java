package br.com.claro.BoxTV.utils.Commons;


import br.com.claro.BoxTV.DTO.GenreDTO;
import br.com.claro.BoxTV.DTO.MovieDTO;
import br.com.claro.BoxTV.DTO.StreamingPlatformDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetMovie {

    private final String BASE_URL = "https://api.themoviedb.org/3";
    private final String MOVIE_PATH = "/discover/movie?sort_by=popularity.desc&";

    public List<StreamingPlatformDTO> listAll(String apiKey) {
        int page = 1;
        List<String> responsesAPI = new ArrayList<>();

        //Get 10 Movie Pages
        do {
            String httpResponse = getHTTPResponse(apiKey, page);
            if(httpResponse != null) responsesAPI.add(httpResponse);
            page++;
        } while(page <= 10);

        if(!responsesAPI.isEmpty()) {

            List<StreamingPlatformDTO> streamingPlatformDTOList = new ArrayList<>();

            streamingPlatformDTOList.add(new StreamingPlatformDTO(0, "Netflix"));
            streamingPlatformDTOList.add(new StreamingPlatformDTO(1, "Amazon Prime Video"));
            streamingPlatformDTOList.add(new StreamingPlatformDTO(2, "Globoplay"));

            //Convert all Responses into JSON
            for (String response : responsesAPI) {

                if(response != null) {
                    JSONArray jsonResponse = parseToJson(response);

                    int idPlatform = 0;
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        //Convert JSONObject into MovieDTO
                        MovieDTO movie = convertJSONToMovieDTO(jsonResponse.getJSONObject(i));

                        if(movie != null) {
                            //Add MovieDTO to StreamingPlatform
                            addMovieDTOToStreamingPlatform(movie, streamingPlatformDTOList, idPlatform);
                            idPlatform = (idPlatform + 1) % 3;
                        }
                    }
                }
            }
            return streamingPlatformDTOList;
        }
        return null;
    }

    private String getHTTPResponse(String apiKey, int page) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String apiUrl = BASE_URL + MOVIE_PATH + "api_key=" + apiKey + "&page=" + page;

        //Creating HttpRequest and getting Response
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request Setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            //Check if status isn't ok
            if(status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            //Response success
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            //Return response
            return responseContent.toString();

        } catch (MalformedURLException e) {
            System.out.println("Error on GetMovie/listAll - URL: " + e);
        } catch(java.io.IOException e) {
            System.out.println("Error on GetMovie/listAll - Connection: " + e);
        }
        return null;
    }

    private JSONArray parseToJson(String responseContent) {
        JSONObject object = new JSONObject(responseContent);
        return new JSONArray(object.getJSONArray("results"));
    }

    private MovieDTO convertJSONToMovieDTO(JSONObject movieData) {

        try {
            int id = movieData.getInt("id");
            String title = movieData.getString("title");
            String overview = movieData.getString("overview");
            String imagePath = movieData.getString("poster_path");
            String imageUrl = "https://image.tmdb.org/t/p/w500" + imagePath;
            Double voteAvarage = movieData.getDouble("vote_average");

            List<GenreDTO> genresList = new ArrayList<>();
            JSONArray genres = movieData.getJSONArray("genre_ids");

            for (int j = 0; j < genres.length(); j++) {
                genresList.add(new GenreDTO(genres.getInt(j)));
            }
            return new MovieDTO(id, title, imageUrl, voteAvarage, overview, genresList);
        } catch (Exception e) {
            System.out.println("Error on convertJSONToMovieDTO: " + e);
        }
        return null;
    }

    private void addMovieDTOToStreamingPlatform(MovieDTO movie, List<StreamingPlatformDTO> streamingPlatformDTOList, int platformId) {
        streamingPlatformDTOList.get(platformId).getMovies().add(movie);
    }
}
