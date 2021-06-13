package com.yamibo.secretplacespa.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Rating entity
 */
@ApiModel(description = "The Rating entity")
@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The rate gave
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    @ApiModelProperty(value = "The rate gave", required = true)
    @Column(name = "rate", nullable = false)
    private Integer rate;

    /**
     * comment on the rating
     */
    @Size(max = 250)
    @ApiModelProperty(value = "comment on the rating")
    @Column(name = "comment", length = 250)
    private String comment;

    /**
     * The date the rating has been done
     */
    @NotNull
    @ApiModelProperty(value = "The date the rating has been done", required = true)
    @Column(name = "rating_date", nullable = false)
    private Instant ratingDate;

    /**
     * The user that has got this favorite record
     */
    @ApiModelProperty(value = "The user that has got this favorite record")
    @ManyToOne
    private User userId;

    /**
     * The establishment the user put in his favorites
     */
    @ApiModelProperty(value = "The establishment the user put in his favorites")
    @ManyToOne
    private Establishment establishmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rating id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getRate() {
        return this.rate;
    }

    public Rating rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return this.comment;
    }

    public Rating comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getRatingDate() {
        return this.ratingDate;
    }

    public Rating ratingDate(Instant ratingDate) {
        this.ratingDate = ratingDate;
        return this;
    }

    public void setRatingDate(Instant ratingDate) {
        this.ratingDate = ratingDate;
    }

    public User getUserId() {
        return this.userId;
    }

    public Rating userId(User user) {
        this.setUserId(user);
        return this;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public Establishment getEstablishmentId() {
        return this.establishmentId;
    }

    public Rating establishmentId(Establishment establishment) {
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
        if (!(o instanceof Rating)) {
            return false;
        }
        return id != null && id.equals(((Rating) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", rate=" + getRate() +
            ", comment='" + getComment() + "'" +
            ", ratingDate='" + getRatingDate() + "'" +
            "}";
    }
}
