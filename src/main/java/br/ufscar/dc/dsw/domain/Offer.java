package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.domain.enums.OfferStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "offers")
public class Offer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Min(value = 0)
    private BigDecimal value;

    @NotBlank
    @Length(min = 7, max = 1024)
    @Column(nullable = false, columnDefinition = "VARCHAR(1024) DEFAULT 'À vista'")
    private String paymentConditions = "À vista";

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate date;

    @Column
    private OfferStatus status = OfferStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonBackReference("vehicle-offers")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference("client-offers")
    private Client client;

    public Long getId() {
        return id;
    }

    public Offer setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Offer setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Offer setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getPaymentConditions() {
        return paymentConditions;
    }

    public Offer setPaymentConditions(String paymentConditions) {
        this.paymentConditions = paymentConditions;
        return this;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public Offer setStatus(OfferStatus status) {
        this.status = status;
        return this;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Offer setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Offer setClient(Client client) {
        this.client = client;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(getId(), offer.getId()) && Objects.equals(getValue(), offer.getValue()) && Objects.equals(getPaymentConditions(), offer.getPaymentConditions()) && Objects.equals(getDate(), offer.getDate()) && getStatus() == offer.getStatus() && Objects.equals(getVehicle(), offer.getVehicle()) && Objects.equals(getClient(), offer.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getPaymentConditions(), getDate(), getStatus(), getVehicle(), getClient());
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", value=" + value +
                ", paymentConditions='" + paymentConditions + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", vehicle=" + vehicle +
                ", client=" + client +
                '}';
    }
}
