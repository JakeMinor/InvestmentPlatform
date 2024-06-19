package investment.api.repositories.entities;

import jakarta.persistence.*;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int asset_id;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private Broker broker;
}
