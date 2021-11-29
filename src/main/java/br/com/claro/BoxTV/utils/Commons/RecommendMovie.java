package br.com.claro.BoxTV.utils.Commons;

import br.com.claro.BoxTV.DTO.MovieDTO;
import br.com.claro.BoxTV.DTO.StreamingPlatformDTO;

import java.util.List;

public class RecommendMovie {

    public MovieDTO recommendMovie(StreamingPlatformDTO platform, int movieId, List<Integer> genresId) {
        List<MovieDTO> allMovies = platform.getMovies();

        for (MovieDTO movie : allMovies) {
            for (int i = 0; i < genresId.size(); i++) {
                for (int j = 0; j < movie.getGenres().size(); j++) {
                    if(genresId.get(i) == movie.getGenres().get(j).getId() && movieId != movie.getId()) return movie;
                }
            }
        }
        return null;
    }

}
