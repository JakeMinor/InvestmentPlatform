package investment.api.business;

import investment.api.dtos.CreatePortfolioDto;
import investment.api.dtos.UserDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.PortfolioRespository;
import investment.api.repositories.entities.Broker;
import investment.api.repositories.entities.Portfolio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PortfolioBusiness {

    private final PortfolioRespository portfolioRepository;
    private final BrokerRepository brokerRepository;

    public PortfolioBusiness(PortfolioRespository portfolioRepository, BrokerRepository brokerRepository) {
        this.portfolioRepository = portfolioRepository;
        this.brokerRepository = brokerRepository;
    }

    public ResponseEntity createPortfolio(CreatePortfolioDto portfolio, Authentication authentication) {

        if(!brokerRepository.existsById(portfolio.getBrokerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Broker doesn't exist.");
        }

        Broker broker = brokerRepository.findById(portfolio.getBrokerId());

        UserDto user = (UserDto) authentication.getPrincipal();

        portfolioRepository.save(new Portfolio(broker, user.getInvestor(), new ArrayList<>(), LocalDateTime.now()));

        return new ResponseEntity("Portfolio Created", HttpStatus.CREATED);
    }
}
