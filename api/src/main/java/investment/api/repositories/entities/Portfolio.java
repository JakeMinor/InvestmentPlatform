package investment.api.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Entity
public class Portfolio {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    @Setter
    @Getter
    private Broker broker;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    @Setter
    @Getter
    private Investor investor;

    @ManyToMany
    @JoinTable(
            name = "portfolio_assets",
            joinColumns = @JoinColumn(name = "portfolio_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    @Getter
    @Setter
    private Collection<Asset> portfolioAssets;

    @Getter
    @Setter
    private Date creation_date;
}
