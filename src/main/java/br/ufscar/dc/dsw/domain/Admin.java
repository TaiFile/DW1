package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.domain.enums.UserRoleEnum;
import jakarta.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin() {
        super(UserRoleEnum.ADMIN);
    }
}
