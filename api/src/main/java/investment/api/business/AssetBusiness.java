package investment.api.business;

import investment.api.dtos.AssetDto;
import investment.api.enums.AssetKindEnum;
import investment.api.dtos.UserDto;
import investment.api.repositories.AssetRepository;
import investment.api.repositories.entities.Asset;
import investment.api.security.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    public ResponseEntity<List<AssetDto>> getAssets(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        var assets = assetRepository.findAssetsByBroker_id(user.getBroker().getId())
                .stream()
                .map(asset ->
                        new AssetDto(asset.getAsset_id(),
                                asset.getBroker().getId(),
                                AssetKindEnum.valueOf(asset.getKind().toUpperCase()),
                                asset.getName()
                        )
                );

        return new ResponseEntity<>(assets.toList(), HttpStatus.OK);
    }

    public ResponseEntity createAsset(AssetDto asset, Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();

        assetRepository.save(new Asset(user.getBroker(), asset.getKind(), asset.getName()));

        return new ResponseEntity<>("Asset created", HttpStatus.CREATED);
    }

    public ResponseEntity deleteAsset(int id) {
        if(!assetRepository.existsById(id)) {
            return new ResponseEntity<>("Asset doesn't exist.", HttpStatus.BAD_REQUEST);
        }

        var asset = assetRepository.findById(id);

        if(!asset.get().getPortfolios().isEmpty()) {
            return new ResponseEntity<>("Cannot delete Asset as it belongs to a Portfolio.", HttpStatus.BAD_REQUEST);
        }

        assetRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
