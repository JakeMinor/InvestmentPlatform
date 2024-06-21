package investment.api.business;

import investment.api.dtos.AssetDto;
import investment.api.dtos.AssetKindEnum;
import investment.api.dtos.UserDto;
import investment.api.repositories.AssetRepository;
import investment.api.repositories.BrokerRepository;
import investment.api.repositories.entities.Asset;
import investment.api.repositories.entities.Broker;
import investment.api.security.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.security.Principal;
import java.util.Arrays;
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

        return new ResponseEntity<>(assetRepository.findAssetsByBroker_id(user.getBroker().getId()), HttpStatus.OK);
    }

    public ResponseEntity createAsset(AssetDto asset, Principal principal) {
        UserDto user = userDetailsService.loadUserByUsername(principal.getName());

        return new ResponseEntity<>(assetRepository.save(new Asset(user.getBroker(), asset.getKind(), asset.getName())), HttpStatus.CREATED);
    }
}
