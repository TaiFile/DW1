package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.validation.ChassisProperties;
import br.ufscar.dc.dsw.validation.PlateProperties;
import br.ufscar.dc.dsw.validation.UniqueChassi;
import br.ufscar.dc.dsw.validation.UniquePlate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vehicles")
@UniqueChassi
@UniquePlate
public class Vehicle implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @PlateProperties
    @NotBlank
    @Column(nullable = false, unique = true)
    private String plate;

    @NotBlank
    @Column(nullable = false)
    private String model;

    @ChassisProperties
    @NotBlank
    @Column(nullable = false, unique = true)
    private String chassi;

    @NotNull
    @Min(value = 1900)
    private Integer year;

    @Min(value = 0)
    private Integer mileage;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    private BigDecimal value;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public List<String> getImages() {
        return images;
    }

    public Vehicle setImages(List<String> images) {
        this.images = images;
        return this;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public Vehicle setOffers(List<Offer> offers) {
        this.offers = offers;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(plate, vehicle.plate) && Objects.equals(model, vehicle.model) && Objects.equals(chassi, vehicle.chassi) && Objects.equals(year, vehicle.year) && Objects.equals(mileage, vehicle.mileage) && Objects.equals(description, vehicle.description) && Objects.equals(value, vehicle.value) && Objects.equals(images, vehicle.images) && Objects.equals(store, vehicle.store) && Objects.equals(offers, vehicle.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plate, model, chassi, year, mileage, description, value, images, store, offers);
    }
}
