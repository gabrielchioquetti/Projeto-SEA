package Projeto_SEA.IFSP.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String painelControle() {
        return "admin/dashboard";
    }
}
