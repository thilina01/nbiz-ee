package com.nanosl.nbiz.ee.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A QuotationItem.
 */
@Entity
@Table(name = "quotation_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuotationItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;

    @Column(name = "discount")
    private Double discount;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @ManyToOne
    @JsonIgnoreProperties("quotations")
    private Quotation quotation;

    @ManyToOne
    @JsonIgnoreProperties("quotationItems")
    private PurchaseInvoiceItem purchaseInvoiceItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public QuotationItem sellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public QuotationItem discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getQuantity() {
        return quantity;
    }

    public QuotationItem quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public QuotationItem quotation(Quotation quotation) {
        this.quotation = quotation;
        return this;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    public PurchaseInvoiceItem getPurchaseInvoiceItem() {
        return purchaseInvoiceItem;
    }

    public QuotationItem purchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
        this.purchaseInvoiceItem = purchaseInvoiceItem;
        return this;
    }

    public void setPurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
        this.purchaseInvoiceItem = purchaseInvoiceItem;
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
        QuotationItem quotationItem = (QuotationItem) o;
        if (quotationItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotationItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotationItem{" +
            "id=" + getId() +
            ", sellingPrice=" + getSellingPrice() +
            ", discount=" + getDiscount() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
