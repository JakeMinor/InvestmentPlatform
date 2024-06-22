package investment.api.controllers;

import investment.api.business.AssetBusiness;
import investment.api.business.BrokerBusiness;
import investment.api.dtos.AssetDto;
import investment.api.repositories.entities.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/broker")
public class BrokerController {

    @Autowired
    BrokerBusiness brokerBusiness;

    @Autowired
    AssetBusiness assetBusiness;

    @GetMapping("/")
    public ResponseEntity<List<Broker>> getAllBrokers() {
        return brokerBusiness.getAllBrokers();
    }

    @GetMapping("/assets")
    public ResponseEntity<List<AssetDto>> getAllAssets(Authentication authentication) {
        return assetBusiness.getAssets(authentication);
    }

    @PreAuthorize("hasRole('BROKER')")
    @PostMapping("/create-asset")
    public ResponseEntity createAsset(@RequestBody AssetDto asset, Authentication authentication) {
        return assetBusiness.createAsset(asset, authentication);
    }

    @PreAuthorize("hasRole('BROKER')")
    @DeleteMapping("/delete-asset/{id}")
    public ResponseEntity deleteAsset(@PathVariable int id) {
        return assetBusiness.deleteAsset(id);
    }

    @PreAuthorize("hasRole('BROKER')")
    @DeleteMapping("/delete-profile")
    public ResponseEntity<String> deleteBroker(Authentication authentication) {
        return brokerBusiness.deleteBroker(authentication);
    }
}
