package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.domain.enums.UserRoleEnum;
import br.ufscar.dc.dsw.validation.UniqueCNPJ;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

@Entity
@UniqueCNPJ
@Table(name = "stores")
public class Store extends User {
    @NotBlank
    @Column(nullable = false)
    private String cnpj;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference("store-vehicles")
    private List<Vehicle> vehicles;

    public Store() {
        super(UserRoleEnum.STORE);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(cnpj, store.cnpj) && Objects.equals(name, store.name) && Objects.equals(description, store.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, name, description);
    }
}
