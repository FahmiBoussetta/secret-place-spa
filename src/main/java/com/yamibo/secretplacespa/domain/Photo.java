package com.yamibo.secretplacespa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The photo entity
 */
@ApiModel(description = "The photo entity")
@Entity
@Table(name = "photo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The photo name
     */
    @NotNull
    @Size(min = 1)
    @ApiModelProperty(value = "The photo name", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The Housing template associated with this photo, maybe null if it's a photo only associated to the establishment
     */
    @ApiModelProperty(
        value = "The Housing template associated with this photo, maybe null if it's a photo only associated to the establishment"
    )
    @ManyToOne
    @JsonIgnoreProperties(value = { "establishmentId" }, allowSetters = true)
    private HousingTemplate housingTemplateId;

    /**
     * The Establishement associated with this photo, cannot be null
     */
    @ApiModelProperty(value = "The Establishement associated with this photo, cannot be null")
    @ManyToOne
    private Establishment establishmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Photo id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Photo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HousingTemplate getHousingTemplateId() {
        return this.housingTemplateId;
    }

    public Photo housingTemplateId(HousingTemplate housingTemplate) {
        this.setHousingTemplateId(housingTemplate);
        return this;
    }

    public void setHousingTemplateId(HousingTemplate housingTemplate) {
        this.housingTemplateId = housingTemplate;
    }

    public Establishment getEstablishmentId() {
        return this.establishmentId;
    }

    public Photo establishmentId(Establishment establishment) {
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
        if (!(o instanceof Photo)) {
            return false;
        }
        return id != null && id.equals(((Photo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
