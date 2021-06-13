package com.yamibo.secretplacespa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HousingClosure.
 */
@Entity
@Table(name = "housing_closure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HousingClosure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Size(max = 250)
    @Column(name = "cause", length = 250)
    private String cause;

    /**
     * The establishment that is closed
     */
    @ApiModelProperty(value = "The establishment that is closed")
    @ManyToOne
    @JsonIgnoreProperties(value = { "establishmentId" }, allowSetters = true)
    private HousingTemplate establishmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HousingClosure id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public HousingClosure startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public HousingClosure endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getCause() {
        return this.cause;
    }

    public HousingClosure cause(String cause) {
        this.cause = cause;
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public HousingTemplate getEstablishmentId() {
        return this.establishmentId;
    }

    public HousingClosure establishmentId(HousingTemplate housingTemplate) {
        this.setEstablishmentId(housingTemplate);
        return this;
    }

    public void setEstablishmentId(HousingTemplate housingTemplate) {
        this.establishmentId = housingTemplate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingClosure)) {
            return false;
        }
        return id != null && id.equals(((HousingClosure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HousingClosure{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", cause='" + getCause() + "'" +
            "}";
    }
}
