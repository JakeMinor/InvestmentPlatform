package investment.api.repositories.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Broker_Id;

    private String username;

    private String company;

    private byte[] passwordHash;

    private byte[] passwordSalt;

    public Broker() { }
    public Broker(String username, String company, byte[] passwordHash, byte[] passwordSalt) {
        this.username = username;
        this.company = company;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }
}
