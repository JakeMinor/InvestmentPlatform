package investment.api.repositories;

import investment.api.repositories.entities.Broker;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface BrokerRepository extends JpaRepository<Broker, Integer> {
}
