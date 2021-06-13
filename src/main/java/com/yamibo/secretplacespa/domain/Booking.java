package com.yamibo.secretplacespa.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Booking entity
 */
@ApiModel(description = "The Booking entity")
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * The total price of the booking, computed with the price per night of the HousingCategory and the duration of the booking
     */
    @NotNull
    @ApiModelProperty(
        value = "The total price of the booking, computed with the price per night of the HousingCategory and the duration of the booking",
        required = true
    )
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Size(max = 500)
    @Column(name = "comment", length = 500)
    private String comment;

    @NotNull
    @Column(name = "validate", nullable = false)
    private Boolean validate;

    @Column(name = "paypal_link")
    private String paypalLink;

    /**
     * The user that made the Booking, may be null if the Booking was made by an unauthentified user
     */
    @ApiModelProperty(value = "The user that made the Booking, may be null if the Booking was made by an unauthentified user")
    @ManyToOne
    private User jhiUserId;

    /**
     * The establishment where the booking was made
     */
    @ApiModelProperty(value = "The establishment where the booking was made")
    @ManyToOne
    private Establishment establishmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking id(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public Booking email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTotalPrice() {
        return this.totalPrice;
    }

    public Booking totalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComment() {
        return this.comment;
    }

    public Booking comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getValidate() {
        return this.validate;
    }

    public Booking validate(Boolean validate) {
        this.validate = validate;
        return this;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public String getPaypalLink() {
        return this.paypalLink;
    }

    public Booking paypalLink(String paypalLink) {
        this.paypalLink = paypalLink;
        return this;
    }

    public void setPaypalLink(String paypalLink) {
        this.paypalLink = paypalLink;
    }

    public User getJhiUserId() {
        return this.jhiUserId;
    }

    public Booking jhiUserId(User user) {
        this.setJhiUserId(user);
        return this;
    }

    public void setJhiUserId(User user) {
        this.jhiUserId = user;
    }

    public Establishment getEstablishmentId() {
        return this.establishmentId;
    }

    public Booking establishmentId(Establishment establishment) {
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
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", comment='" + getComment() + "'" +
            ", validate='" + getValidate() + "'" +
            ", paypalLink='" + getPaypalLink() + "'" +
            "}";
    }
}
