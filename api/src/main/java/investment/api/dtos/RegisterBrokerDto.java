package investment.api.dtos;

import lombok.Data;

@Data
public class RegisterBrokerDto extends LoginBrokerDto {
    private String company;
}
