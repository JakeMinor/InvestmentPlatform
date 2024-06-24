package investment.api.business;

import investment.api.dtos.*;
import investment.api.enums.AssetKindEnum;
import investment.api.repositories.AssetRepository;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.InvestorRepository;
import investment.api.repositories.PortfolioRespository;
import investment.api.repositories.entities.Broker;
import investment.api.repositories.entities.Portfolio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class PortfolioBusiness {

    private final PortfolioRespository portfolioRepository;
    private final BrokerRepository brokerRepository;
    private final InvestorRepository investorRepository;
    private final AssetRepository assetRepository;

    public PortfolioBusiness(PortfolioRespository portfolioRepository, BrokerRepository brokerRepository, InvestorRepository investorRepository, AssetRepository assetRepository) {
        this.portfolioRepository = portfolioRepository;
        this.brokerRepository = brokerRepository;
        this.investorRepository = investorRepository;
        this.assetRepository = assetRepository;
    }

    public Collection<PortfolioDto> getAllPortfolios(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        return portfolioRepository.findAllByInvestorId(
                user.getInvestor().getId()
        ).stream().map(portfolio ->
            new PortfolioDto(
                    portfolio.getId(),
                    portfolio.getCreation_date(),
                    portfolio.getBroker().getCompany(),
                    portfolio.getPortfolioAssets().stream().map(
                            asset -> new AssetDto(
                                    asset.getAsset_id(),
                                    asset.getBroker().getId(),
                                    AssetKindEnum.valueOf(asset.getKind().toUpperCase()),
                                    asset.getName()
                            )
                    ).toList()
                )
        ).toList();
    }

    public ResponseEntity createPortfolio(CreatePortfolioDto portfolio, Authentication authentication) {

        if(!brokerRepository.existsById(portfolio.getBrokerId())) {
            return new ResponseEntity("Broker doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        Broker broker = brokerRepository.findById(portfolio.getBrokerId());

        UserDto user = (UserDto) authentication.getPrincipal();

        portfolioRepository.save(new Portfolio(broker, user.getInvestor(), new ArrayList<>(), LocalDateTime.now()));

        return new ResponseEntity("Portfolio Created", HttpStatus.CREATED);
    }

    public ResponseEntity sellPortfolio(int id, Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        if(!investorRepository.existsById(user.getInvestor().getId())) {
            return new ResponseEntity<>("Investor doesn't exist.", HttpStatus.BAD_REQUEST);
        }
        else if(!portfolioRepository.existsById(id)) {
            return new ResponseEntity<>("Portfolio doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        portfolioRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity addAsset(AddAssetToPortfolioDto assetToAdd, Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        if(!portfolioRepository.existsById(assetToAdd.portfolioId)) {
            return new ResponseEntity<>("Portfolio doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        if(!assetRepository.existsById(assetToAdd.assetId)) {
            return new ResponseEntity<>("Asset doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        var asset = assetRepository.findById(assetToAdd.assetId);
        var portfolio = portfolioRepository.findById(assetToAdd.portfolioId);

        if(asset.get().getBroker().getId() != portfolio.get().getBroker().getId()) {
            return new ResponseEntity<>("Broker doesn't provide this Asset.", HttpStatus.BAD_REQUEST);
        }

        if(portfolio.get().getInvestor().getId() != user.getInvestor().getId()) {
            return new ResponseEntity<>("User doesn't have portfolio with the id " + assetToAdd.assetId, HttpStatus.BAD_REQUEST);
        }

        portfolio.get().getPortfolioAssets().add(asset.get());

        portfolioRepository.save(portfolio.get());

        return new ResponseEntity("Asset Added", HttpStatus.OK);
    }
}
