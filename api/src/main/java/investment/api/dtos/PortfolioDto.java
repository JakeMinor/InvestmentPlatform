package investment.api.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class PortfolioDto {

    private int id;

    private LocalDateTime creationDate;

    private String companyName;

    private Collection<AssetDto> assets;

    public PortfolioDto(int id, LocalDateTime creationDate, String companyName, Collection<AssetDto> assets) {
        this.id = id;
        this.creationDate = creationDate;
        this.companyName = companyName;
        this.assets = assets;
    }
}
