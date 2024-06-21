package investment.api.controllers;

import investment.api.business.AssetBusiness;
import investment.api.dtos.AssetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity createAsset(@RequestBody AssetDto asset, Principal principal) {
        return assetBusiness.createAsset(asset, principal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBroker(@PathVariable int id) {
        return assetBusiness.deleteAsset(id);
    }
}
