package com.yamibo.secretplacespa.domain;

import com.yamibo.secretplacespa.domain.enumeration.HousingType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HousingTemplate.
 */
@Entity
@Table(name = "housing_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HousingTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of housing
     */
    @NotNull
    @ApiModelProperty(value = "The type of housing", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "housing_type", nullable = false)
    private HousingType housingType;

    /**
     * Number of unit of this kind of housing the establishment has
     */
    @NotNull
    @Min(value = 1)
    @ApiModelProperty(value = "Number of unit of this kind of housing the establishment has", required = true)
    @Column(name = "nb_of_unit", nullable = false)
    private Integer nbOfUnit;

    /**
     * The maximum number of persons that can live in this kind of housing
     */
    @NotNull
    @Min(value = 1)
    @ApiModelProperty(value = "The maximum number of persons that can live in this kind of housing", required = true)
    @Column(name = "nb_max_of_occupants", nullable = false)
    private Integer nbMaxOfOccupants;

    /**
     * Price per night
     */
    @NotNull
    @ApiModelProperty(value = "Price per night", required = true)
    @Column(name = "price", nullable = false)
    private Float price;

    /**
     * The establishment that owns this housing template
     */
    @ApiModelProperty(value = "The establishment that owns this housing template")
    @ManyToOne
    private Establishment establishmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HousingTemplate id(Long id) {
        this.id = id;
        return this;
    }

    public HousingType getHousingType() {
        return this.housingType;
    }

    public HousingTemplate housingType(HousingType housingType) {
        this.housingType = housingType;
        return this;
    }

    public void setHousingType(HousingType housingType) {
        this.housingType = housingType;
    }

    public Integer getNbOfUnit() {
        return this.nbOfUnit;
    }

    public HousingTemplate nbOfUnit(Integer nbOfUnit) {
        this.nbOfUnit = nbOfUnit;
        return this;
    }

    public void setNbOfUnit(Integer nbOfUnit) {
        this.nbOfUnit = nbOfUnit;
    }

    public Integer getNbMaxOfOccupants() {
        return this.nbMaxOfOccupants;
    }

    public HousingTemplate nbMaxOfOccupants(Integer nbMaxOfOccupants) {
        this.nbMaxOfOccupants = nbMaxOfOccupants;
        return this;
    }

    public void setNbMaxOfOccupants(Integer nbMaxOfOccupants) {
        this.nbMaxOfOccupants = nbMaxOfOccupants;
    }

    public Float getPrice() {
        return this.price;
    }

    public HousingTemplate price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Establishment getEstablishmentId() {
        return this.establishmentId;
    }

    public HousingTemplate establishmentId(Establishment establishment) {
        this.setEstablishmentId(establishment);
        return this;
    }

    public void setEstablishmentId(Establishment establishment) {
        this.establishmentId = establishment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingTemplate)) {
            return false;
        }
        return id != null && id.equals(((HousingTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HousingTemplate{" +
            "id=" + getId() +
            ", housingType='" + getHousingType() + "'" +
            ", nbOfUnit=" + getNbOfUnit() +
            ", nbMaxOfOccupants=" + getNbMaxOfOccupants() +
            ", price=" + getPrice() +
            "}";
    }
}
