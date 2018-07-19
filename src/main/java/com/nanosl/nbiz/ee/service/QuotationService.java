package com.nanosl.nbiz.ee.service;

import com.nanosl.nbiz.ee.domain.Quotation;
import com.nanosl.nbiz.ee.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Quotation.
 */
@Service
@Transactional
public class QuotationService {

    private final Logger log = LoggerFactory.getLogger(QuotationService.class);

    private final QuotationRepository quotationRepository;

    public QuotationService(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    /**
     * Save a quotation.
     *
     * @param quotation the entity to save
     * @return the persisted entity
     */
    public Quotation save(Quotation quotation) {
        log.debug("Request to save Quotation : {}", quotation);        return quotationRepository.save(quotation);
    }

    /**
     * Get all the quotations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Quotation> findAll(Pageable pageable) {
        log.debug("Request to get all Quotations");
        return quotationRepository.findAll(pageable);
    }


    /**
     * Get one quotation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Quotation> findOne(Long id) {
        log.debug("Request to get Quotation : {}", id);
        return quotationRepository.findById(id);
    }

    /**
     * Delete the quotation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Quotation : {}", id);
        quotationRepository.deleteById(id);
    }
}
