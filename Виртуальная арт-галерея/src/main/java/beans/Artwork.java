package beans;

public class Artwork extends BaseBean {
    private String title;
    private String description;
    private String image;
    private int artistId;
    private Category category;
    private int likes;
    private int views;
    private java.sql.Date dateCreation;
    private boolean published;
    private boolean draft;
    private int exhibitionId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setDateCreation(java.sql.Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getArtistId() {
        return artistId;
    }

    public Category getCategory() {
        return category;
    }

    public int getViews() {
        return views;
    }

    public java.sql.Date getDateCreation() {
        return dateCreation;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean isDraft() {
        return draft;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void incrementLikes() {
        this.likes++;
    }

    public void decrementLikes() {
        this.likes--;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "od='" + super.getId() + '\'' +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", artistId=" + artistId +
                ", category=" + category +
                '}';
    }
}
