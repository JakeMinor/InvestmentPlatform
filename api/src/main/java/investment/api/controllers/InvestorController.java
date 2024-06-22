package investment.api.controllers;

import investment.api.business.InvestorBusiness;
import investment.api.business.PortfolioBusiness;
import investment.api.dtos.CreatePortfolioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investor/")
public class InvestorController {

    @Autowired
    private InvestorBusiness investorBusiness;

    @Autowired
    private PortfolioBusiness portfolioBusiness;

    @GetMapping("/get-profile")
    public ResponseEntity getInvestor(Authentication authentication) {
        return investorBusiness.getInvestorProfile(authentication);
    }

    @PostMapping("/create-portfolio")
    public ResponseEntity createPortfolio(@RequestBody CreatePortfolioDto portfolio, Authentication authentication) {
        return portfolioBusiness.createPortfolio(portfolio, authentication);
    }

    @DeleteMapping("/delete-profile")
    public ResponseEntity deleteInvestor(Authentication authentication) {
        return investorBusiness.deleteInvestor(authentication);
    }

    @DeleteMapping("/sell-portfolio/{id}")
    public ResponseEntity sellPortfolio(@PathVariable int id, Authentication authentication) {
        return portfolioBusiness.sellPortfolio(id, authentication);
    }
}
