package com.yamibo.secretplacespa.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Favorites entity : no attributes beacause it's an association table
 */
@ApiModel(description = "The Favorites entity : no attributes beacause it's an association table")
@Entity
@Table(name = "favorites")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Favorites implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Favorites id(Long id) {
        this.id = id;
        return this;
    }

    public User getUserId() {
        return this.userId;
    }

    public Favorites userId(User user) {
        this.setUserId(user);
        return this;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public Establishment getEstablishmentId() {
        return this.establishmentId;
    }

    public Favorites establishmentId(Establishment establishment) {
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
        if (!(o instanceof Favorites)) {
            return false;
        }
        return id != null && id.equals(((Favorites) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Favorites{" +
            "id=" + getId() +
            "}";
    }
}
