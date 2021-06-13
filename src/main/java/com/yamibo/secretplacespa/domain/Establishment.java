package com.yamibo.secretplacespa.domain;

import com.yamibo.secretplacespa.domain.enumeration.EstablishmentType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Establishment.
 */
@Entity
@Table(name = "establishment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Establishment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name of the establishement
     */
    @NotNull
    @Size(min = 1, max = 150)
    @ApiModelProperty(value = "name of the establishement", required = true)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    /**
     * adress of the establishement
     */
    @NotNull
    @Size(min = 1, max = 150)
    @ApiModelProperty(value = "adress of the establishement", required = true)
    @Column(name = "address", length = 150, nullable = false)
    private String address;

    /**
     * latitude of the establishement, useful to place it on a map
     */
    @ApiModelProperty(value = "latitude of the establishement, useful to place it on a map")
    @Column(name = "latitude")
    private Float latitude;

    /**
     * longitude of the establishement, useful to place it on a map
     */
    @ApiModelProperty(value = "longitude of the establishement, useful to place it on a map")
    @Column(name = "longitude")
    private Float longitude;

    /**
     * globalRate of the establishement, computed after each new Rate
     */
    @ApiModelProperty(value = "globalRate of the establishement, computed after each new Rate")
    @Column(name = "global_rate")
    private Float globalRate;

    /**
     * The type of establishment
     */
    @NotNull
    @ApiModelProperty(value = "The type of establishment", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "establishment_type", nullable = false)
    private EstablishmentType establishmentType;

    /**
     * Does the establishment has a private parking ?
     */
    @NotNull
    @ApiModelProperty(value = "Does the establishment has a private parking ?", required = true)
    @Column(name = "has_parking", nullable = false)
    private Boolean hasParking;

    /**
     * Does the establishment has a restaurant ?
     */
    @NotNull
    @ApiModelProperty(value = "Does the establishment has a restaurant ?", required = true)
    @Column(name = "has_restaurant", nullable = false)
    private Boolean hasRestaurant;

    /**
     * Does the establishment has free wifi ?
     */
    @NotNull
    @ApiModelProperty(value = "Does the establishment has free wifi ?", required = true)
    @Column(name = "has_free_wifi", nullable = false)
    private Boolean hasFreeWifi;

    /**
     * Description of the establishment
     */
    @Size(max = 300)
    @ApiModelProperty(value = "Description of the establishment")
    @Column(name = "description", length = 300)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Establishment id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Establishment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Establishment address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public Establishment latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Establishment longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getGlobalRate() {
        return this.globalRate;
    }

    public Establishment globalRate(Float globalRate) {
        this.globalRate = globalRate;
        return this;
    }

    public void setGlobalRate(Float globalRate) {
        this.globalRate = globalRate;
    }

    public EstablishmentType getEstablishmentType() {
        return this.establishmentType;
    }

    public Establishment establishmentType(EstablishmentType establishmentType) {
        this.establishmentType = establishmentType;
        return this;
    }

    public void setEstablishmentType(EstablishmentType establishmentType) {
        this.establishmentType = establishmentType;
    }

    public Boolean getHasParking() {
        return this.hasParking;
    }

    public Establishment hasParking(Boolean hasParking) {
        this.hasParking = hasParking;
        return this;
    }

    public void setHasParking(Boolean hasParking) {
        this.hasParking = hasParking;
    }

    public Boolean getHasRestaurant() {
        return this.hasRestaurant;
    }

    public Establishment hasRestaurant(Boolean hasRestaurant) {
        this.hasRestaurant = hasRestaurant;
        return this;
    }

    public void setHasRestaurant(Boolean hasRestaurant) {
        this.hasRestaurant = hasRestaurant;
    }

    public Boolean getHasFreeWifi() {
        return this.hasFreeWifi;
    }

    public Establishment hasFreeWifi(Boolean hasFreeWifi) {
        this.hasFreeWifi = hasFreeWifi;
        return this;
    }

    public void setHasFreeWifi(Boolean hasFreeWifi) {
        this.hasFreeWifi = hasFreeWifi;
    }

    public String getDescription() {
        return this.description;
    }

    public Establishment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Establishment)) {
            return false;
        }
        return id != null && id.equals(((Establishment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Establishment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", globalRate=" + getGlobalRate() +
            ", establishmentType='" + getEstablishmentType() + "'" +
            ", hasParking='" + getHasParking() + "'" +
            ", hasRestaurant='" + getHasRestaurant() + "'" +
            ", hasFreeWifi='" + getHasFreeWifi() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
