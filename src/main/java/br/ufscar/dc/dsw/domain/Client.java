package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.domain.enums.SexEnum;
import br.ufscar.dc.dsw.domain.enums.UserRoleEnum;
import br.ufscar.dc.dsw.validation.UniqueCPF;
import br.ufscar.dc.dsw.validation.UniquePhone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@UniqueCPF
@UniquePhone
@Table(name = "clients")
public class Client extends User {
    @NotBlank
    @Column(nullable = false, length = 14)
    private String cpf;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String phone;

    @NotNull
    @Column(nullable = false)
    private SexEnum sex;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Offer> offers = new ArrayList<>();

    public Client() {
        super(UserRoleEnum.CLIENT);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public Client setOffers(List<Offer> offers) {
        this.offers = offers;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(cpf, client.cpf) && Objects.equals(name, client.name) && Objects.equals(phone, client.phone) && sex == client.sex && Objects.equals(dateOfBirth, client.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, name, phone, sex, dateOfBirth);
    }
}
