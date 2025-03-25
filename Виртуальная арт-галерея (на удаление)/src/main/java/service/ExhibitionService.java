package service;

import beans.Exhibition;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExhibitionService {

    private static final List<Exhibition> exhibitions = new ArrayList<>();

    static {
        Exhibition exhibition1 = new Exhibition();
        exhibition1.setId(1);
        exhibition1.setTitle("Impressionist Wonders");
        exhibition1.setDescription("A showcase of famous impressionist paintings.");
        exhibition1.setArtistId(101);
        exhibition1.setCover("impressionist.jpg");
        exhibition1.setPublications(Arrays.asList(
                "ArtDaily - Monet's Masterpieces",
                "New York Times - Best Impressionist Works",
                "ArtBlog - Impressionist Highlights"
        ));

        Exhibition exhibition2 = new Exhibition();
        exhibition2.setId(2);
        exhibition2.setTitle("Renaissance Revival");
        exhibition2.setDescription("Experience the timeless beauty of Renaissance art.");
        exhibition2.setArtistId(102);
        exhibition2.setCover("renaissance.jpg");
        exhibition2.setPublications(Arrays.asList(
                "The Guardian - Top Renaissance Artists",
                "Art Critique - Da Vinci and Michelangelo",
                "Culture Times - A Return to Classics"
        ));

        exhibitions.add(exhibition1);
        exhibitions.add(exhibition2);
    }

    public List<Exhibition> getAllExhibitions() {
        return exhibitions;
    }

    public Exhibition getExhibitionById(int id) {
        return exhibitions.stream()
                .filter(exhibition -> exhibition.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
