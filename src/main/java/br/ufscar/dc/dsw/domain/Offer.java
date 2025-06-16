package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.domain.enums.OfferStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate date;

    @NotNull
    private OfferStatus status = OfferStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "client_id")
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
        return Objects.equals(getId(), offer.getId()) && Objects.equals(getValue(), offer.getValue()) && Objects.equals(getDate(), offer.getDate()) && getStatus() == offer.getStatus() && Objects.equals(getVehicle(), offer.getVehicle()) && Objects.equals(getClient(), offer.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getDate(), getStatus(), getVehicle(), getClient());
    }
}
