package investment.api.controllers;

import investment.api.business.BrokerBusiness;
import investment.api.dtos.LoginUserDto;
import investment.api.dtos.RegisterBrokerDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin("http://localhost:8080")
public class AuthenticationController {

    private final BrokerBusiness brokerBusiness;

    @Autowired
    public AuthenticationController(BrokerBusiness brokerBusiness) {
        this.brokerBusiness = brokerBusiness;
    }

    @PostMapping("/register-broker")
    public ResponseEntity<String> registerBroker(@RequestBody RegisterBrokerDto broker) {
       return brokerBusiness.registerBroker(broker);
    }

    @PostMapping("/login-broker")
    public ResponseEntity<String> loginBroker(@RequestBody LoginUserDto brokerLogin) {
        return brokerBusiness.loginBroker(brokerLogin);
    }
}
