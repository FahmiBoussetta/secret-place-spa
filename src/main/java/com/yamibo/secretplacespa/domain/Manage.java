package com.yamibo.secretplacespa.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Manage.
 */
@Entity
@Table(name = "manage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Manage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user that manage the establishment
     */
    @ApiModelProperty(value = "The user that manage the establishment")
    @ManyToOne
    private User userId;

    /**
     * The establishment that is managed
     */
    @ApiModelProperty(value = "The establishment that is managed")
    @ManyToOne
    private Establishment establishmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Manage id(Long id) {
        this.id = id;
        return this;
    }

    public User getUserId() {
        return this.userId;
    }

    public Manage userId(User user) {
        this.setUserId(user);
        return this;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public Establishment getEstablishmentId() {
        return this.establishmentId;
    }

    public Manage establishmentId(Establishment establishment) {
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
        if (!(o instanceof Manage)) {
            return false;
        }
        return id != null && id.equals(((Manage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manage{" +
            "id=" + getId() +
            "}";
    }
}
