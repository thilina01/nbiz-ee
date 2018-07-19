package com.nanosl.nbiz.ee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Quotation.
 */
@Entity
@Table(name = "quotation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quotation_date", nullable = false)
    private Instant quotationDate;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "discount")
    private Double discount;

    @OneToMany(mappedBy = "quotation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuotationItem> quotations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("quotations")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getQuotationDate() {
        return quotationDate;
    }

    public Quotation quotationDate(Instant quotationDate) {
        this.quotationDate = quotationDate;
        return this;
    }

    public void setQuotationDate(Instant quotationDate) {
        this.quotationDate = quotationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public Quotation amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public Quotation discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Set<QuotationItem> getQuotations() {
        return quotations;
    }

    public Quotation quotations(Set<QuotationItem> quotationItems) {
        this.quotations = quotationItems;
        return this;
    }

    public Quotation addQuotation(QuotationItem quotationItem) {
        this.quotations.add(quotationItem);
        quotationItem.setQuotation(this);
        return this;
    }

    public Quotation removeQuotation(QuotationItem quotationItem) {
        this.quotations.remove(quotationItem);
        quotationItem.setQuotation(null);
        return this;
    }

    public void setQuotations(Set<QuotationItem> quotationItems) {
        this.quotations = quotationItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Quotation customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        Quotation quotation = (Quotation) o;
        if (quotation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Quotation{" +
            "id=" + getId() +
            ", quotationDate='" + getQuotationDate() + "'" +
            ", amount=" + getAmount() +
            ", discount=" + getDiscount() +
            "}";
    }
}
