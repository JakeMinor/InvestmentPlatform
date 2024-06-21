package investment.api.dtos;

import lombok.Data;

@Data
public class AssetDto {
    private int broker_id;
    private AssetKindEnum kind;
    private String name;
}
