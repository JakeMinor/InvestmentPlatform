package investment.api.controllers;

import investment.api.business.AuthenticationBusiness;
import investment.api.dtos.LoginUserDto;
import investment.api.dtos.RegisterBrokerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin("http://localhost:8080")
public class AuthenticationController {

    private final AuthenticationBusiness authenticationBusiness;

    @Autowired
    public AuthenticationController(AuthenticationBusiness authenticationBusiness) {
        this.authenticationBusiness = authenticationBusiness;
    }

    @PostMapping("/register-broker")
    public ResponseEntity<String> registerBroker(@RequestBody RegisterBrokerDto broker) {
       return authenticationBusiness.registerBroker(broker);
    }

    @PostMapping("/login-broker")
    public ResponseEntity<String> loginBroker(@RequestBody LoginUserDto brokerLogin) {
        return authenticationBusiness.loginBroker(brokerLogin);
    }
}
