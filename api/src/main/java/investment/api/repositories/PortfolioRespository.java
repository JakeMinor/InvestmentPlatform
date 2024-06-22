package investment.api.repositories;

import investment.api.repositories.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRespository extends JpaRepository<Portfolio, Integer> {

}
