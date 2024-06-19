package investment.api.controllers;

import investment.api.business.AssetBusiness;
import investment.api.business.BrokerBusiness;
import investment.api.repositories.AssetRepository;
import investment.api.repositories.entities.Asset;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    private final AssetBusiness assetBusiness;

    public AssetController(AssetBusiness assetBusiness) {
        this.assetBusiness = assetBusiness;
    }

    @GetMapping("/")
    public ResponseEntity<List<Asset>> getAllAssets(Principal principal) {
        return assetBusiness.getAssets(principal);
    }


}
