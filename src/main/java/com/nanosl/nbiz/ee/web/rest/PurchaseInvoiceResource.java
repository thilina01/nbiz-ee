package com.nanosl.nbiz.ee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nanosl.nbiz.ee.domain.PurchaseInvoice;
import com.nanosl.nbiz.ee.service.PurchaseInvoiceService;
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
 * REST controller for managing PurchaseInvoice.
 */
@RestController
@RequestMapping("/api")
public class PurchaseInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseInvoiceResource.class);

    private static final String ENTITY_NAME = "purchaseInvoice";

    private final PurchaseInvoiceService purchaseInvoiceService;

    public PurchaseInvoiceResource(PurchaseInvoiceService purchaseInvoiceService) {
        this.purchaseInvoiceService = purchaseInvoiceService;
    }

    /**
     * POST  /purchase-invoices : Create a new purchaseInvoice.
     *
     * @param purchaseInvoice the purchaseInvoice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseInvoice, or with status 400 (Bad Request) if the purchaseInvoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-invoices")
    @Timed
    public ResponseEntity<PurchaseInvoice> createPurchaseInvoice(@RequestBody PurchaseInvoice purchaseInvoice) throws URISyntaxException {
        log.debug("REST request to save PurchaseInvoice : {}", purchaseInvoice);
        if (purchaseInvoice.getId() != null) {
            throw new BadRequestAlertException("A new purchaseInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseInvoice result = purchaseInvoiceService.save(purchaseInvoice);
        return ResponseEntity.created(new URI("/api/purchase-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-invoices : Updates an existing purchaseInvoice.
     *
     * @param purchaseInvoice the purchaseInvoice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseInvoice,
     * or with status 400 (Bad Request) if the purchaseInvoice is not valid,
     * or with status 500 (Internal Server Error) if the purchaseInvoice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-invoices")
    @Timed
    public ResponseEntity<PurchaseInvoice> updatePurchaseInvoice(@RequestBody PurchaseInvoice purchaseInvoice) throws URISyntaxException {
        log.debug("REST request to update PurchaseInvoice : {}", purchaseInvoice);
        if (purchaseInvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseInvoice result = purchaseInvoiceService.save(purchaseInvoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseInvoice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-invoices : get all the purchaseInvoices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseInvoices in body
     */
    @GetMapping("/purchase-invoices")
    @Timed
    public ResponseEntity<List<PurchaseInvoice>> getAllPurchaseInvoices(Pageable pageable) {
        log.debug("REST request to get a page of PurchaseInvoices");
        Page<PurchaseInvoice> page = purchaseInvoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-invoices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-invoices/:id : get the "id" purchaseInvoice.
     *
     * @param id the id of the purchaseInvoice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseInvoice, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-invoices/{id}")
    @Timed
    public ResponseEntity<PurchaseInvoice> getPurchaseInvoice(@PathVariable Long id) {
        log.debug("REST request to get PurchaseInvoice : {}", id);
        Optional<PurchaseInvoice> purchaseInvoice = purchaseInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseInvoice);
    }

    /**
     * DELETE  /purchase-invoices/:id : delete the "id" purchaseInvoice.
     *
     * @param id the id of the purchaseInvoice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-invoices/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseInvoice(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseInvoice : {}", id);
        purchaseInvoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
