package investment.api.controllers;

import investment.api.business.BrokerBusiness;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/broker")
public class BrokerController {

    @Autowired
    BrokerBusiness brokerBusiness;

    @GetMapping("/")
    public List<Broker> getAllBrokers() {
        return brokerBusiness.getAllBrokers();
    }
}
