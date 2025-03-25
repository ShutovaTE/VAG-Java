package beans;

public class Artworks extends BaseBean {
    private String title;
    private String description;
    private String imagePath;
    private int userId;
    private Categories category;
    private int likes;
    private int views;
    private java.sql.Date dateCreation;
    private boolean published;
    private String status;

    private boolean draft;
    private int exhibitionId;
    private int categoryId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCategory(Categories category) {
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

    public String getImagePath() {
        return imagePath;
    }

    public int getUserId() {
        return userId;
    }

    public Categories getCategory() {
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
                ", image='" + imagePath + '\'' +
                ", artistId=" + userId +
                ", category=" + category +
                '}';
    }
}
