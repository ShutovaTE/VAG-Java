package controller;

import beans.Exhibition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import service.ExhibitionService;

@Controller
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @Autowired
    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping("/exhibitions")
    public String getAllExhibitions(Model model) {
        model.addAttribute("exhibitions", exhibitionService.getAllExhibitions());
        return "exhibitions";
    }

    @GetMapping("/exhibitions/{id}")
    public String getExhibitionById(@PathVariable("id") int id, Model model) {
        Exhibition exhibition = exhibitionService.getExhibitionById(id);
        if (exhibition != null) {
            model.addAttribute("exhibition", exhibition);
        } else {
            model.addAttribute("error", "Выставка не найдена");
        }
        return "exhibition_detail";
    }
}
