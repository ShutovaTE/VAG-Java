package beans;

import java.util.List;

public class Exhibition extends BaseBean{
    private String title;
    private String description;
    private int artistId;
//    private int[] artworkId;
    private String cover;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getArtistId() {
        return artistId;
    }
    private List<String> publications;

    public List<String> getPublications() {
        return publications;
    }

    public void setPublications(List<String> publications) {
        this.publications = publications;
    }


//    public int[] getArtworkId() {
//        return artworkId;
//    }

    public String getCover() {
        return cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

//    public void setArtworkId(int[] artworkId) {
//        this.artworkId = artworkId;
//    }

    public void setCover(String cover) {
        this.cover = cover;
    }

        @Override
    public String toString() {
        return "Exhibition{" +
                "id='" + super.getId() + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", artistId=" + artistId +
                ", publications=" + publications +
                '}';
    }

}
