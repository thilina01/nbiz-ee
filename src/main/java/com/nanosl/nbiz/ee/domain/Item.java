package com.nanosl.nbiz.ee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "description")
    private String description;

    @Column(name = "re_order_level")
    private Double reOrderLevel;

    @Column(name = "default_price")
    private Double defaultPrice;

    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseInvoiceItem> purchaseInvoiceItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Brand brand;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Category category;

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

    public Item code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public Item model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public Item description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getReOrderLevel() {
        return reOrderLevel;
    }

    public Item reOrderLevel(Double reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
        return this;
    }

    public void setReOrderLevel(Double reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public Item defaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
        return this;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Set<PurchaseInvoiceItem> getPurchaseInvoiceItems() {
        return purchaseInvoiceItems;
    }

    public Item purchaseInvoiceItems(Set<PurchaseInvoiceItem> purchaseInvoiceItems) {
        this.purchaseInvoiceItems = purchaseInvoiceItems;
        return this;
    }

    public Item addPurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
        this.purchaseInvoiceItems.add(purchaseInvoiceItem);
        purchaseInvoiceItem.setItem(this);
        return this;
    }

    public Item removePurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
        this.purchaseInvoiceItems.remove(purchaseInvoiceItem);
        purchaseInvoiceItem.setItem(null);
        return this;
    }

    public void setPurchaseInvoiceItems(Set<PurchaseInvoiceItem> purchaseInvoiceItems) {
        this.purchaseInvoiceItems = purchaseInvoiceItems;
    }

    public Brand getBrand() {
        return brand;
    }

    public Item brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public Item category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        Item item = (Item) o;
        if (item.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", description='" + getDescription() + "'" +
            ", reOrderLevel=" + getReOrderLevel() +
            ", defaultPrice=" + getDefaultPrice() +
            "}";
    }
}
