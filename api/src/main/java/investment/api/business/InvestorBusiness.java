package investment.api.business;

import investment.api.dtos.InvestorDto;
import investment.api.dtos.UserDto;
import investment.api.repositories.InvestorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class InvestorBusiness {

    private final InvestorRepository investorRepository;
    private final PortfolioBusiness portfolioBusiness;

    public InvestorBusiness(InvestorRepository investorRepository, PortfolioBusiness portfolioBusiness) {
        this.investorRepository = investorRepository;
        this.portfolioBusiness = portfolioBusiness;
    }

    public ResponseEntity getInvestorProfile(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        if((user.getInvestor() == null)){
            return new ResponseEntity<>("Investor doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        var portfolios = portfolioBusiness.getAllPortfolios(authentication);


        return new ResponseEntity<>(
                new InvestorDto(
                        user.getInvestor().getFirstname(),
                        user.getInvestor().getLastname(),
                        user.getInvestor().getUsername(),
                        portfolios
                ),
                HttpStatus.OK);
    }

    public ResponseEntity<String> deleteInvestor(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        if(!investorRepository.existsById(user.getInvestor().getId())) {
            return new ResponseEntity<>("Broker doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        investorRepository.deleteById(user.getInvestor().getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
