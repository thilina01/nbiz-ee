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
 * A PurchaseInvoice.
 */
@Entity
@Table(name = "purchase_invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseInvoice implements Serializable {

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

    @Column(name = "discount")
    private Double discount;

    @OneToMany(mappedBy = "purchaseInvoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseInvoiceItem> purchaseInvoiceItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("purchaseInvoices")
    private Supplier supplier;

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

    public PurchaseInvoice invoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public PurchaseInvoice amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public PurchaseInvoice discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Set<PurchaseInvoiceItem> getPurchaseInvoiceItems() {
        return purchaseInvoiceItems;
    }

    public PurchaseInvoice purchaseInvoiceItems(Set<PurchaseInvoiceItem> purchaseInvoiceItems) {
        this.purchaseInvoiceItems = purchaseInvoiceItems;
        return this;
    }

    public PurchaseInvoice addPurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
        this.purchaseInvoiceItems.add(purchaseInvoiceItem);
        purchaseInvoiceItem.setPurchaseInvoice(this);
        return this;
    }

    public PurchaseInvoice removePurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
        this.purchaseInvoiceItems.remove(purchaseInvoiceItem);
        purchaseInvoiceItem.setPurchaseInvoice(null);
        return this;
    }

    public void setPurchaseInvoiceItems(Set<PurchaseInvoiceItem> purchaseInvoiceItems) {
        this.purchaseInvoiceItems = purchaseInvoiceItems;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public PurchaseInvoice supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        PurchaseInvoice purchaseInvoice = (PurchaseInvoice) o;
        if (purchaseInvoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseInvoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseInvoice{" +
            "id=" + getId() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", amount=" + getAmount() +
            ", discount=" + getDiscount() +
            "}";
    }
}
