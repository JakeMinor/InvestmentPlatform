package investment.api.controllers;

import investment.api.business.AssetBusiness;
import investment.api.dtos.AssetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<AssetDto>> getAllAssets(Authentication authentication) {
        return assetBusiness.getAssets(authentication);
    }

    @PostMapping("/create")
    public ResponseEntity createAsset(@RequestBody AssetDto asset, Authentication authentication) {
        return assetBusiness.createAsset(asset, authentication);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAsset(@PathVariable int id) {
        return assetBusiness.deleteAsset(id);
    }
}
