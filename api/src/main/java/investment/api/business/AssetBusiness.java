package investment.api.business;

import investment.api.dtos.UserDto;
import investment.api.repositories.AssetRepository;
import investment.api.repositories.entities.Asset;
import investment.api.security.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class AssetBusiness {

    private final AssetRepository assetRepository;
    private final CustomUserDetailsService userDetailsService;

    public AssetBusiness(AssetRepository assetRepository, CustomUserDetailsService userDetailsService) {
        this.assetRepository = assetRepository;
        this.userDetailsService = userDetailsService;
    }

    public ResponseEntity<List<Asset>> getAssets(Principal principal) {
        UserDto user = userDetailsService.loadUserByUsername(principal.getName());

        return new ResponseEntity<>(assetRepository.findAssetsByBroker_id(user.getBroker_id()), HttpStatus.OK);
    }
}
