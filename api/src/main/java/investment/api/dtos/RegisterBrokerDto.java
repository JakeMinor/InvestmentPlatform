package investment.api.dtos;

import lombok.Data;

@Data
public class RegisterBrokerDto {
    private String username;

    private String company;

    private String password;

}
