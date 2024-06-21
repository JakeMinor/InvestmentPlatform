package investment.api.repositories.entities;

import investment.api.dtos.AssetKindEnum;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int asset_id;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    @Setter
    private Broker broker;

    private String kind;

    private String name;

    public Asset(Broker broker, AssetKindEnum kind, String name) {
        this.broker = broker;
        this.kind = kind.assetKind;
        this.name = name;
    }

    public Asset() {

    }
}
