package com.nanosl.nbiz.ee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nanosl.nbiz.ee.domain.SaleInvoicePayment;
import com.nanosl.nbiz.ee.repository.SaleInvoicePaymentRepository;
import com.nanosl.nbiz.ee.web.rest.errors.BadRequestAlertException;
import com.nanosl.nbiz.ee.web.rest.util.HeaderUtil;
import com.nanosl.nbiz.ee.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SaleInvoicePayment.
 */
@RestController
@RequestMapping("/api")
public class SaleInvoicePaymentResource {

    private final Logger log = LoggerFactory.getLogger(SaleInvoicePaymentResource.class);

    private static final String ENTITY_NAME = "saleInvoicePayment";

    private final SaleInvoicePaymentRepository saleInvoicePaymentRepository;

    public SaleInvoicePaymentResource(SaleInvoicePaymentRepository saleInvoicePaymentRepository) {
        this.saleInvoicePaymentRepository = saleInvoicePaymentRepository;
    }

    /**
     * POST  /sale-invoice-payments : Create a new saleInvoicePayment.
     *
     * @param saleInvoicePayment the saleInvoicePayment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saleInvoicePayment, or with status 400 (Bad Request) if the saleInvoicePayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sale-invoice-payments")
    @Timed
    public ResponseEntity<SaleInvoicePayment> createSaleInvoicePayment(@RequestBody SaleInvoicePayment saleInvoicePayment) throws URISyntaxException {
        log.debug("REST request to save SaleInvoicePayment : {}", saleInvoicePayment);
        if (saleInvoicePayment.getId() != null) {
            throw new BadRequestAlertException("A new saleInvoicePayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleInvoicePayment result = saleInvoicePaymentRepository.save(saleInvoicePayment);
        return ResponseEntity.created(new URI("/api/sale-invoice-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sale-invoice-payments : Updates an existing saleInvoicePayment.
     *
     * @param saleInvoicePayment the saleInvoicePayment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saleInvoicePayment,
     * or with status 400 (Bad Request) if the saleInvoicePayment is not valid,
     * or with status 500 (Internal Server Error) if the saleInvoicePayment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sale-invoice-payments")
    @Timed
    public ResponseEntity<SaleInvoicePayment> updateSaleInvoicePayment(@RequestBody SaleInvoicePayment saleInvoicePayment) throws URISyntaxException {
        log.debug("REST request to update SaleInvoicePayment : {}", saleInvoicePayment);
        if (saleInvoicePayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SaleInvoicePayment result = saleInvoicePaymentRepository.save(saleInvoicePayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saleInvoicePayment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sale-invoice-payments : get all the saleInvoicePayments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saleInvoicePayments in body
     */
    @GetMapping("/sale-invoice-payments")
    @Timed
    public ResponseEntity<List<SaleInvoicePayment>> getAllSaleInvoicePayments(Pageable pageable) {
        log.debug("REST request to get a page of SaleInvoicePayments");
        Page<SaleInvoicePayment> page = saleInvoicePaymentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-invoice-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sale-invoice-payments/:id : get the "id" saleInvoicePayment.
     *
     * @param id the id of the saleInvoicePayment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saleInvoicePayment, or with status 404 (Not Found)
     */
    @GetMapping("/sale-invoice-payments/{id}")
    @Timed
    public ResponseEntity<SaleInvoicePayment> getSaleInvoicePayment(@PathVariable Long id) {
        log.debug("REST request to get SaleInvoicePayment : {}", id);
        Optional<SaleInvoicePayment> saleInvoicePayment = saleInvoicePaymentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(saleInvoicePayment);
    }

    /**
     * DELETE  /sale-invoice-payments/:id : delete the "id" saleInvoicePayment.
     *
     * @param id the id of the saleInvoicePayment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sale-invoice-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaleInvoicePayment(@PathVariable Long id) {
        log.debug("REST request to delete SaleInvoicePayment : {}", id);

        saleInvoicePaymentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
