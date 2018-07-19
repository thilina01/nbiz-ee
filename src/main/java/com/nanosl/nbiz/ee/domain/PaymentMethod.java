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
 * A PaymentMethod.
 */
@Entity
@Table(name = "payment_method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "paymentMethod")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SaleInvoicePayment> saleInvoicePayments = new HashSet<>();

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

    public PaymentMethod code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public PaymentMethod name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SaleInvoicePayment> getSaleInvoicePayments() {
        return saleInvoicePayments;
    }

    public PaymentMethod saleInvoicePayments(Set<SaleInvoicePayment> saleInvoicePayments) {
        this.saleInvoicePayments = saleInvoicePayments;
        return this;
    }

    public PaymentMethod addSaleInvoicePayment(SaleInvoicePayment saleInvoicePayment) {
        this.saleInvoicePayments.add(saleInvoicePayment);
        saleInvoicePayment.setPaymentMethod(this);
        return this;
    }

    public PaymentMethod removeSaleInvoicePayment(SaleInvoicePayment saleInvoicePayment) {
        this.saleInvoicePayments.remove(saleInvoicePayment);
        saleInvoicePayment.setPaymentMethod(null);
        return this;
    }

    public void setSaleInvoicePayments(Set<SaleInvoicePayment> saleInvoicePayments) {
        this.saleInvoicePayments = saleInvoicePayments;
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
        PaymentMethod paymentMethod = (PaymentMethod) o;
        if (paymentMethod.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentMethod.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
