package Likelion.Recruiting.controller;

import Likelion.Recruiting.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EntityManager;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseController {

    private final EntityManager em;

    @ModelAttribute("teams")
    public List<Team> teams(Model model){
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }


}
