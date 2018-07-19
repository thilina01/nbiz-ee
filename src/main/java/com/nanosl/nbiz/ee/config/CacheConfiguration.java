package com.nanosl.nbiz.ee.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.nanosl.nbiz.ee.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Brand.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Brand.class.getName() + ".items", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Category.class.getName() + ".items", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Item.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Item.class.getName() + ".purchaseInvoiceItems", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Supplier.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Supplier.class.getName() + ".purchaseInvoices", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.PurchaseInvoice.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.PurchaseInvoice.class.getName() + ".purchaseInvoices", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.PurchaseInvoiceItem.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.PurchaseInvoiceItem.class.getName() + ".saleInvoiceItems", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.PurchaseInvoiceItem.class.getName() + ".quotationItems", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Customer.class.getName() + ".saleInvoices", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Customer.class.getName() + ".quotations", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.SalesPerson.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.SalesPerson.class.getName() + ".saleInvoices", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.SaleInvoice.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.SaleInvoice.class.getName() + ".saleInvoiceItems", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.SaleInvoice.class.getName() + ".saleInvoices", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.SaleInvoiceItem.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Quotation.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.Quotation.class.getName() + ".quotations", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.QuotationItem.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.PaymentMethod.class.getName(), jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.PaymentMethod.class.getName() + ".saleInvoicePayments", jcacheConfiguration);
            cm.createCache(com.nanosl.nbiz.ee.domain.SaleInvoicePayment.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
