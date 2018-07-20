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
 * A SaleInvoice.
 */
@Entity
@Table(name = "sale_invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SaleInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private Instant invoiceDate;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "balance_amount")
    private Double balanceAmount;

    @Column(name = "discount")
    private Double discount;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "saleInvoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SaleInvoiceItem> saleInvoiceItems = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "saleInvoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SaleInvoicePayment> saleInvoicePayments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("saleInvoices")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("saleInvoices")
    private SalesPerson salesPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInvoiceDate() {
        return invoiceDate;
    }

    public SaleInvoice invoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public SaleInvoice amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public SaleInvoice paidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public SaleInvoice balanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public SaleInvoice discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Set<SaleInvoiceItem> getSaleInvoiceItems() {
        return saleInvoiceItems;
    }

    public SaleInvoice saleInvoiceItems(Set<SaleInvoiceItem> saleInvoiceItems) {
        this.saleInvoiceItems = saleInvoiceItems;
        return this;
    }

    public SaleInvoice addSaleInvoiceItem(SaleInvoiceItem saleInvoiceItem) {
        this.saleInvoiceItems.add(saleInvoiceItem);
        saleInvoiceItem.setSaleInvoice(this);
        return this;
    }

    public SaleInvoice removeSaleInvoiceItem(SaleInvoiceItem saleInvoiceItem) {
        this.saleInvoiceItems.remove(saleInvoiceItem);
        saleInvoiceItem.setSaleInvoice(null);
        return this;
    }

    public void setSaleInvoiceItems(Set<SaleInvoiceItem> saleInvoiceItems) {
        this.saleInvoiceItems = saleInvoiceItems;
    }

    public Set<SaleInvoicePayment> getSaleInvoicePayments() {
        return saleInvoicePayments;
    }

    public SaleInvoice saleInvoicePayments(Set<SaleInvoicePayment> saleInvoicePayments) {
        this.saleInvoicePayments = saleInvoicePayments;
        return this;
    }

    public SaleInvoice addSaleInvoicePayment(SaleInvoicePayment saleInvoicePayment) {
        this.saleInvoicePayments.add(saleInvoicePayment);
        saleInvoicePayment.setSaleInvoice(this);
        return this;
    }

    public SaleInvoice removeSaleInvoicePayment(SaleInvoicePayment saleInvoicePayment) {
        this.saleInvoicePayments.remove(saleInvoicePayment);
        saleInvoicePayment.setSaleInvoice(null);
        return this;
    }

    public void setSaleInvoicePayments(Set<SaleInvoicePayment> saleInvoicePayments) {
        this.saleInvoicePayments = saleInvoicePayments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public SaleInvoice customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public SaleInvoice salesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
        return this;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
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
        SaleInvoice saleInvoice = (SaleInvoice) o;
        if (saleInvoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleInvoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleInvoice{" +
            "id=" + getId() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", amount=" + getAmount() +
            ", paidAmount=" + getPaidAmount() +
            ", balanceAmount=" + getBalanceAmount() +
            ", discount=" + getDiscount() +
            "}";
    }
}
