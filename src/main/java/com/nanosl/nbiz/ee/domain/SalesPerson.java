package com.nanosl.nbiz.ee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SalesPerson.
 */
@Entity
@Table(name = "sales_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "salesPerson")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SaleInvoice> saleInvoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public SalesPerson code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public SalesPerson name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SaleInvoice> getSaleInvoices() {
        return saleInvoices;
    }

    public SalesPerson saleInvoices(Set<SaleInvoice> saleInvoices) {
        this.saleInvoices = saleInvoices;
        return this;
    }

    public SalesPerson addSaleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoices.add(saleInvoice);
        saleInvoice.setSalesPerson(this);
        return this;
    }

    public SalesPerson removeSaleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoices.remove(saleInvoice);
        saleInvoice.setSalesPerson(null);
        return this;
    }

    public void setSaleInvoices(Set<SaleInvoice> saleInvoices) {
        this.saleInvoices = saleInvoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SalesPerson salesPerson = (SalesPerson) o;
        if (salesPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salesPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalesPerson{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
