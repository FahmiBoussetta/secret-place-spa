package com.yamibo.secretplacespa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Booking entity
 */
@ApiModel(description = "The Booking entity")
@Entity
@Table(name = "booking_element")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BookingElement implements Serializable {

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

    /**
     * The total price of the booking, computed with the price per night of the HousingCategory and the duration of the booking
     */
    @NotNull
    @ApiModelProperty(
        value = "The total price of the booking, computed with the price per night of the HousingCategory and the duration of the booking",
        required = true
    )
    @Column(name = "element_price", nullable = false)
    private Integer elementPrice;

    /**
     * The Booking this booking element is a part of
     */
    @ApiModelProperty(value = "The Booking this booking element is a part of")
    @ManyToOne
    @JsonIgnoreProperties(value = { "jhiUserId", "establishmentId" }, allowSetters = true)
    private Booking bookingId;

    /**
     * The housing template this booking element is on
     */
    @ApiModelProperty(value = "The housing template this booking element is on")
    @ManyToOne
    @JsonIgnoreProperties(value = { "establishmentId" }, allowSetters = true)
    private HousingTemplate housingTemplateId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingElement id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public BookingElement startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public BookingElement endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getElementPrice() {
        return this.elementPrice;
    }

    public BookingElement elementPrice(Integer elementPrice) {
        this.elementPrice = elementPrice;
        return this;
    }

    public void setElementPrice(Integer elementPrice) {
        this.elementPrice = elementPrice;
    }

    public Booking getBookingId() {
        return this.bookingId;
    }

    public BookingElement bookingId(Booking booking) {
        this.setBookingId(booking);
        return this;
    }

    public void setBookingId(Booking booking) {
        this.bookingId = booking;
    }

    public HousingTemplate getHousingTemplateId() {
        return this.housingTemplateId;
    }

    public BookingElement housingTemplateId(HousingTemplate housingTemplate) {
        this.setHousingTemplateId(housingTemplate);
        return this;
    }

    public void setHousingTemplateId(HousingTemplate housingTemplate) {
        this.housingTemplateId = housingTemplate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingElement)) {
            return false;
        }
        return id != null && id.equals(((BookingElement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingElement{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", elementPrice=" + getElementPrice() +
            "}";
    }
}
