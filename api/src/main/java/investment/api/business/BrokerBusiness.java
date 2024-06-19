package investment.api.business;

import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerBusiness {

    private final BrokerRepository brokerRepository;


    public BrokerBusiness(BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
    }

    public List<Broker> getAllBrokers() {
        return brokerRepository.findAll();
    }


}
