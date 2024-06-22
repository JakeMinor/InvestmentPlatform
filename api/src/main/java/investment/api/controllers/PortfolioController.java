package investment.api.controllers;

import investment.api.business.PortfolioBusiness;
import investment.api.dtos.CreatePortfolioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioBusiness portfolioBusiness;

    @PostMapping("/create")
    public ResponseEntity createPortfolio(@RequestBody CreatePortfolioDto portfolio, Authentication authentication) {
        return portfolioBusiness.createPortfolio(portfolio, authentication);
    }
}
