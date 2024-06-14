package investment.api.business;

import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerBusiness {

    @Autowired
    private BrokerRepository brokerRepository;

    public BrokerBusiness() { }

    public List<Broker> getAllBrokers() {
        return brokerRepository.findAll();
    }
}
