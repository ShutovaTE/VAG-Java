package beans;

import java.util.List;

public class Exhibitions extends BaseBean{
    private String title;
    private String description;
    private int userId;

    private Boolean IsPrivate;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }
    private List<String> publications;

    public List<String> getPublications() {
        return publications;
    }

    public void setPublications(List<String> publications) {
        this.publications = publications;
    }




    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int artistId) {
        this.userId = userId;
    }


        @Override
    public String toString() {
        return "Exhibition{" +
                "id='" + super.getId() + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", artistId=" + userId +
                ", publications=" + publications +
                '}';
    }

}
