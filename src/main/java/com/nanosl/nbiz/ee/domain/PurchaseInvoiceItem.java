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
 * A PurchaseInvoiceItem.
 */
@Entity
@Table(name = "purchase_invoice_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseInvoiceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_cost", nullable = false)
    private Double cost;

    @Column(name = "discount")
    private Double discount;

    @NotNull
    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;

    @Column(name = "expiary_date")
    private Instant expiaryDate;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "serial")
    private String serial;

    @OneToMany(mappedBy = "purchaseInvoiceItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SaleInvoiceItem> saleInvoiceItems = new HashSet<>();

    @OneToMany(mappedBy = "purchaseInvoiceItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuotationItem> quotationItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("purchaseInvoiceItems")
    private PurchaseInvoice purchaseInvoice;

    @ManyToOne
    @JsonIgnoreProperties("purchaseInvoiceItems")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public PurchaseInvoiceItem cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getDiscount() {
        return discount;
    }

    public PurchaseInvoiceItem discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public PurchaseInvoiceItem sellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Instant getExpiaryDate() {
        return expiaryDate;
    }

    public PurchaseInvoiceItem expiaryDate(Instant expiaryDate) {
        this.expiaryDate = expiaryDate;
        return this;
    }

    public void setExpiaryDate(Instant expiaryDate) {
        this.expiaryDate = expiaryDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public PurchaseInvoiceItem quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getSerial() {
        return serial;
    }

    public PurchaseInvoiceItem serial(String serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Set<SaleInvoiceItem> getSaleInvoiceItems() {
        return saleInvoiceItems;
    }

    public PurchaseInvoiceItem saleInvoiceItems(Set<SaleInvoiceItem> saleInvoiceItems) {
        this.saleInvoiceItems = saleInvoiceItems;
        return this;
    }

    public PurchaseInvoiceItem addSaleInvoiceItem(SaleInvoiceItem saleInvoiceItem) {
        this.saleInvoiceItems.add(saleInvoiceItem);
        saleInvoiceItem.setPurchaseInvoiceItem(this);
        return this;
    }

    public PurchaseInvoiceItem removeSaleInvoiceItem(SaleInvoiceItem saleInvoiceItem) {
        this.saleInvoiceItems.remove(saleInvoiceItem);
        saleInvoiceItem.setPurchaseInvoiceItem(null);
        return this;
    }

    public void setSaleInvoiceItems(Set<SaleInvoiceItem> saleInvoiceItems) {
        this.saleInvoiceItems = saleInvoiceItems;
    }

    public Set<QuotationItem> getQuotationItems() {
        return quotationItems;
    }

    public PurchaseInvoiceItem quotationItems(Set<QuotationItem> quotationItems) {
        this.quotationItems = quotationItems;
        return this;
    }

    public PurchaseInvoiceItem addQuotationItem(QuotationItem quotationItem) {
        this.quotationItems.add(quotationItem);
        quotationItem.setPurchaseInvoiceItem(this);
        return this;
    }

    public PurchaseInvoiceItem removeQuotationItem(QuotationItem quotationItem) {
        this.quotationItems.remove(quotationItem);
        quotationItem.setPurchaseInvoiceItem(null);
        return this;
    }

    public void setQuotationItems(Set<QuotationItem> quotationItems) {
        this.quotationItems = quotationItems;
    }

    public PurchaseInvoice getPurchaseInvoice() {
        return purchaseInvoice;
    }

    public PurchaseInvoiceItem purchaseInvoice(PurchaseInvoice purchaseInvoice) {
        this.purchaseInvoice = purchaseInvoice;
        return this;
    }

    public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        this.purchaseInvoice = purchaseInvoice;
    }

    public Item getItem() {
        return item;
    }

    public PurchaseInvoiceItem item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
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
        PurchaseInvoiceItem purchaseInvoiceItem = (PurchaseInvoiceItem) o;
        if (purchaseInvoiceItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseInvoiceItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseInvoiceItem{" +
            "id=" + getId() +
            ", cost=" + getCost() +
            ", discount=" + getDiscount() +
            ", sellingPrice=" + getSellingPrice() +
            ", expiaryDate='" + getExpiaryDate() + "'" +
            ", quantity=" + getQuantity() +
            ", serial='" + getSerial() + "'" +
            "}";
    }
}
