package br.com.claro.BoxTV.DTO;

import java.util.ArrayList;
import java.util.List;

public class StreamingPlatformDTO {

    int id;
    private String name;
    private List<MovieDTO> movies = new ArrayList<>();

    public StreamingPlatformDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StreamingPlatformDTO(int id, String name, List<MovieDTO> movies) {
        this(id, name);
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}
