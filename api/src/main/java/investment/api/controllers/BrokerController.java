package investment.api.controllers;

import investment.api.business.BrokerBusiness;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/broker")
public class BrokerController {

    @Autowired
    BrokerBusiness brokerBusiness;

    @GetMapping("/")
    public ResponseEntity<List<Broker>> getAllBrokers() {
        return brokerBusiness.getAllBrokers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBroker(@PathVariable int id) {
        return brokerBusiness.deleteBroker(id);
    }
}
