package br.com.claro.BoxTV.DTO;

import java.io.Serializable;
import java.util.List;

public class MovieDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title, imageUrl, overview;
    private List<GenreDTO> genres;
    Double voteAverage;

    public MovieDTO() {}

    public MovieDTO(int id, String title, String imageUrl, Double voteAverage, String overview, List<GenreDTO> genres) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenre(List<GenreDTO> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                ", voteAverage=" + voteAverage +
                '}';
    }
}
