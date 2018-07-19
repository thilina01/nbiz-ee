package com.nanosl.nbiz.ee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nanosl.nbiz.ee.domain.SaleInvoice;
import com.nanosl.nbiz.ee.service.SaleInvoiceService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SaleInvoice.
 */
@RestController
@RequestMapping("/api")
public class SaleInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(SaleInvoiceResource.class);

    private static final String ENTITY_NAME = "saleInvoice";

    private final SaleInvoiceService saleInvoiceService;

    public SaleInvoiceResource(SaleInvoiceService saleInvoiceService) {
        this.saleInvoiceService = saleInvoiceService;
    }

    /**
     * POST  /sale-invoices : Create a new saleInvoice.
     *
     * @param saleInvoice the saleInvoice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saleInvoice, or with status 400 (Bad Request) if the saleInvoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sale-invoices")
    @Timed
    public ResponseEntity<SaleInvoice> createSaleInvoice(@Valid @RequestBody SaleInvoice saleInvoice) throws URISyntaxException {
        log.debug("REST request to save SaleInvoice : {}", saleInvoice);
        if (saleInvoice.getId() != null) {
            throw new BadRequestAlertException("A new saleInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleInvoice result = saleInvoiceService.save(saleInvoice);
        return ResponseEntity.created(new URI("/api/sale-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sale-invoices : Updates an existing saleInvoice.
     *
     * @param saleInvoice the saleInvoice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saleInvoice,
     * or with status 400 (Bad Request) if the saleInvoice is not valid,
     * or with status 500 (Internal Server Error) if the saleInvoice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sale-invoices")
    @Timed
    public ResponseEntity<SaleInvoice> updateSaleInvoice(@Valid @RequestBody SaleInvoice saleInvoice) throws URISyntaxException {
        log.debug("REST request to update SaleInvoice : {}", saleInvoice);
        if (saleInvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SaleInvoice result = saleInvoiceService.save(saleInvoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saleInvoice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sale-invoices : get all the saleInvoices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saleInvoices in body
     */
    @GetMapping("/sale-invoices")
    @Timed
    public ResponseEntity<List<SaleInvoice>> getAllSaleInvoices(Pageable pageable) {
        log.debug("REST request to get a page of SaleInvoices");
        Page<SaleInvoice> page = saleInvoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-invoices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sale-invoices/:id : get the "id" saleInvoice.
     *
     * @param id the id of the saleInvoice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saleInvoice, or with status 404 (Not Found)
     */
    @GetMapping("/sale-invoices/{id}")
    @Timed
    public ResponseEntity<SaleInvoice> getSaleInvoice(@PathVariable Long id) {
        log.debug("REST request to get SaleInvoice : {}", id);
        Optional<SaleInvoice> saleInvoice = saleInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleInvoice);
    }

    /**
     * DELETE  /sale-invoices/:id : delete the "id" saleInvoice.
     *
     * @param id the id of the saleInvoice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sale-invoices/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaleInvoice(@PathVariable Long id) {
        log.debug("REST request to delete SaleInvoice : {}", id);
        saleInvoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
