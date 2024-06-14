package investment.api.repositories.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Broker_Id;

    private String username;

    private String company;

    private String passwordHash;

    private String passwordSalt;

    public Broker() { }
    public Broker(String username, String company, String passwordHash, String passwordSalt) {
        this.username = username;
        this.company = company;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }
}
