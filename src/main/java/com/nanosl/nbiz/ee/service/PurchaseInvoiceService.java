package com.nanosl.nbiz.ee.service;

import com.nanosl.nbiz.ee.domain.PurchaseInvoice;
import com.nanosl.nbiz.ee.repository.PurchaseInvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing PurchaseInvoice.
 */
@Service
@Transactional
public class PurchaseInvoiceService {

    private final Logger log = LoggerFactory.getLogger(PurchaseInvoiceService.class);

    private final PurchaseInvoiceRepository purchaseInvoiceRepository;

    public PurchaseInvoiceService(PurchaseInvoiceRepository purchaseInvoiceRepository) {
        this.purchaseInvoiceRepository = purchaseInvoiceRepository;
    }

    /**
     * Save a purchaseInvoice.
     *
     * @param purchaseInvoice the entity to save
     * @return the persisted entity
     */
    public PurchaseInvoice save(PurchaseInvoice purchaseInvoice) {
        log.debug("Request to save PurchaseInvoice : {}", purchaseInvoice);        return purchaseInvoiceRepository.save(purchaseInvoice);
    }

    /**
     * Get all the purchaseInvoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseInvoice> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseInvoices");
        return purchaseInvoiceRepository.findAll(pageable);
    }


    /**
     * Get one purchaseInvoice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PurchaseInvoice> findOne(Long id) {
        log.debug("Request to get PurchaseInvoice : {}", id);
        return purchaseInvoiceRepository.findById(id);
    }

    /**
     * Delete the purchaseInvoice by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseInvoice : {}", id);
        purchaseInvoiceRepository.deleteById(id);
    }
}
