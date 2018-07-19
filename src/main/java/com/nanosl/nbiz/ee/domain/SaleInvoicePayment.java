package com.nanosl.nbiz.ee.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SaleInvoicePayment.
 */
@Entity
@Table(name = "sale_invoice_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SaleInvoicePayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JsonIgnoreProperties("saleInvoices")
    private SaleInvoice saleInvoice;

    @ManyToOne
    @JsonIgnoreProperties("saleInvoicePayments")
    private PaymentMethod paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public SaleInvoicePayment paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public SaleInvoicePayment amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SaleInvoice getSaleInvoice() {
        return saleInvoice;
    }

    public SaleInvoicePayment saleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoice = saleInvoice;
        return this;
    }

    public void setSaleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoice = saleInvoice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public SaleInvoicePayment paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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
        SaleInvoicePayment saleInvoicePayment = (SaleInvoicePayment) o;
        if (saleInvoicePayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleInvoicePayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleInvoicePayment{" +
            "id=" + getId() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
