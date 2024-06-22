package investment.api.business;

import investment.api.dtos.UserDto;
import investment.api.repositories.InvestorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class InvestorBusiness {

    private final InvestorRepository investorRepository;

    public InvestorBusiness(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
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
