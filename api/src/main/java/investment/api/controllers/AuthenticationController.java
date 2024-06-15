package investment.api.controllers;

import investment.api.business.BrokerBusiness;
import investment.api.dtos.RegisterBrokerDto;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin("http://localhost:8080")
public class AuthenticationController {

    private BrokerBusiness brokerBusiness;

    @Autowired
    public AuthenticationController(BrokerBusiness brokerBusiness) {
        this.brokerBusiness = brokerBusiness;
    }

    @PostMapping("/register-broker")
    public ResponseEntity<String> registerBroker(@RequestBody RegisterBrokerDto broker) {
       return brokerBusiness.registerBroker(broker);
    }
}
