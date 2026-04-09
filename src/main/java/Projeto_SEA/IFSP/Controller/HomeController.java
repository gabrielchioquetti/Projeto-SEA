package Projeto_SEA.IFSP.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping("/")
    public String homePage(){
        return "home.html";
    }

    @GetMapping("/horarios")
    public String horarios(){
        return "horarios";
    }

    @GetMapping("/salas")
    public String salas(){
        return "salas";
    }

    @GetMapping("/turmas")
    public String turmas(){
        return "turmas";
    }

}
