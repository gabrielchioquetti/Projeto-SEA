package Projeto_SEA.IFSP.Controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/login")
    public String paginaInicial(
            Authentication auth) {

        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {

            if (auth.getAuthorities()
                    .stream()
                    .anyMatch(a ->
                            a.getAuthority()
                                    .equals("ROLE_PROFESSOR"))) {

                return "redirect:/home/";
            }

            if (auth.getAuthorities()
                    .stream()
                    .anyMatch(a ->
                            a.getAuthority()
                                    .equals("ROLE_ALUNO"))) {

                return "redirect:/home/";
            }

            if (auth.getAuthorities()
                    .stream()
                    .anyMatch(a ->
                            a.getAuthority()
                                    .equals("ROLE_CAE"))) {

                return "redirect:/dashboard";
            }
        }

        return "index";
    }
}
