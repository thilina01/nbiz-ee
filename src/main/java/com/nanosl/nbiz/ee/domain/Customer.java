package com.nanosl.nbiz.ee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SaleInvoice> saleInvoices = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Quotation> quotations = new HashSet<>();

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

    public Customer code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SaleInvoice> getSaleInvoices() {
        return saleInvoices;
    }

    public Customer saleInvoices(Set<SaleInvoice> saleInvoices) {
        this.saleInvoices = saleInvoices;
        return this;
    }

    public Customer addSaleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoices.add(saleInvoice);
        saleInvoice.setCustomer(this);
        return this;
    }

    public Customer removeSaleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoices.remove(saleInvoice);
        saleInvoice.setCustomer(null);
        return this;
    }

    public void setSaleInvoices(Set<SaleInvoice> saleInvoices) {
        this.saleInvoices = saleInvoices;
    }

    public Set<Quotation> getQuotations() {
        return quotations;
    }

    public Customer quotations(Set<Quotation> quotations) {
        this.quotations = quotations;
        return this;
    }

    public Customer addQuotation(Quotation quotation) {
        this.quotations.add(quotation);
        quotation.setCustomer(this);
        return this;
    }

    public Customer removeQuotation(Quotation quotation) {
        this.quotations.remove(quotation);
        quotation.setCustomer(null);
        return this;
    }

    public void setQuotations(Set<Quotation> quotations) {
        this.quotations = quotations;
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
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
