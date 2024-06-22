package investment.api.controllers;

import investment.api.business.InvestorBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/investor/")
public class InvestorController {

    @Autowired
    private InvestorBusiness investorBusiness;

    @DeleteMapping("/delete-profile")
    public ResponseEntity deleteInvestor(Authentication authentication) {
        return investorBusiness.deleteInvestor(authentication);
    }
}
