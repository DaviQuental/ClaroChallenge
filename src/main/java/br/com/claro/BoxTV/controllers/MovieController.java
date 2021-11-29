package br.com.claro.BoxTV.controllers;

import br.com.claro.BoxTV.DTO.StatusDTO;
import br.com.claro.BoxTV.DTO.StreamingPlatformDTO;
import br.com.claro.BoxTV.utils.Commons.GetMovie;
import br.com.claro.BoxTV.utils.Commons.RecommendMovie;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private static List<StreamingPlatformDTO> allMovies;
    private static String apiKey;

    @GetMapping("init/{apiKeyInput}")
    @Cacheable(value = "initController", key = "#apiKeyInput")
    public ResponseEntity initController(@PathVariable String apiKeyInput) {
        StatusDTO status = new StatusDTO();

        apiKey = apiKeyInput;
        allMovies = new GetMovie().listAll(apiKey);

        if(allMovies != null) {
            status.setStatus(true);
            status.setMessage("API has been initialized!");
        } else {
            status.setStatus(false);
            status.setMessage("Error on start API, check your APIKEY!");
        }

        return ResponseEntity.ok(status);
    }

    @GetMapping("listAll")
    public ResponseEntity listAllMovies() {
        return ResponseEntity.ok(Objects.requireNonNullElse(allMovies, "Error: the API must be initialized first"));
    }

    @GetMapping("recommendMovie/{platformId}/{movieId}/{genresId}")
    public ResponseEntity recommendMovie(
            @PathVariable int platformId,
            @PathVariable int movieId,
            @PathVariable String genresId
    ) {
        String[] splittedGenresId = genresId.split(",");
        List<Integer> intGenresId = new ArrayList<>();

        for (String genreId : splittedGenresId) {
            intGenresId.add(Integer.parseInt(genreId));
        }

        return ResponseEntity.ok(new RecommendMovie().recommendMovie(allMovies.get(platformId), movieId, intGenresId));
    }

}
