package com.nanosl.nbiz.ee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nanosl.nbiz.ee.domain.SaleInvoiceItem;
import com.nanosl.nbiz.ee.repository.SaleInvoiceItemRepository;
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
 * REST controller for managing SaleInvoiceItem.
 */
@RestController
@RequestMapping("/api")
public class SaleInvoiceItemResource {

    private final Logger log = LoggerFactory.getLogger(SaleInvoiceItemResource.class);

    private static final String ENTITY_NAME = "saleInvoiceItem";

    private final SaleInvoiceItemRepository saleInvoiceItemRepository;

    public SaleInvoiceItemResource(SaleInvoiceItemRepository saleInvoiceItemRepository) {
        this.saleInvoiceItemRepository = saleInvoiceItemRepository;
    }

    /**
     * POST  /sale-invoice-items : Create a new saleInvoiceItem.
     *
     * @param saleInvoiceItem the saleInvoiceItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saleInvoiceItem, or with status 400 (Bad Request) if the saleInvoiceItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sale-invoice-items")
    @Timed
    public ResponseEntity<SaleInvoiceItem> createSaleInvoiceItem(@Valid @RequestBody SaleInvoiceItem saleInvoiceItem) throws URISyntaxException {
        log.debug("REST request to save SaleInvoiceItem : {}", saleInvoiceItem);
        if (saleInvoiceItem.getId() != null) {
            throw new BadRequestAlertException("A new saleInvoiceItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleInvoiceItem result = saleInvoiceItemRepository.save(saleInvoiceItem);
        return ResponseEntity.created(new URI("/api/sale-invoice-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sale-invoice-items : Updates an existing saleInvoiceItem.
     *
     * @param saleInvoiceItem the saleInvoiceItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saleInvoiceItem,
     * or with status 400 (Bad Request) if the saleInvoiceItem is not valid,
     * or with status 500 (Internal Server Error) if the saleInvoiceItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sale-invoice-items")
    @Timed
    public ResponseEntity<SaleInvoiceItem> updateSaleInvoiceItem(@Valid @RequestBody SaleInvoiceItem saleInvoiceItem) throws URISyntaxException {
        log.debug("REST request to update SaleInvoiceItem : {}", saleInvoiceItem);
        if (saleInvoiceItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SaleInvoiceItem result = saleInvoiceItemRepository.save(saleInvoiceItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saleInvoiceItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sale-invoice-items : get all the saleInvoiceItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saleInvoiceItems in body
     */
    @GetMapping("/sale-invoice-items")
    @Timed
    public ResponseEntity<List<SaleInvoiceItem>> getAllSaleInvoiceItems(Pageable pageable) {
        log.debug("REST request to get a page of SaleInvoiceItems");
        Page<SaleInvoiceItem> page = saleInvoiceItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-invoice-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sale-invoice-items/:id : get the "id" saleInvoiceItem.
     *
     * @param id the id of the saleInvoiceItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saleInvoiceItem, or with status 404 (Not Found)
     */
    @GetMapping("/sale-invoice-items/{id}")
    @Timed
    public ResponseEntity<SaleInvoiceItem> getSaleInvoiceItem(@PathVariable Long id) {
        log.debug("REST request to get SaleInvoiceItem : {}", id);
        Optional<SaleInvoiceItem> saleInvoiceItem = saleInvoiceItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(saleInvoiceItem);
    }

    /**
     * DELETE  /sale-invoice-items/:id : delete the "id" saleInvoiceItem.
     *
     * @param id the id of the saleInvoiceItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sale-invoice-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaleInvoiceItem(@PathVariable Long id) {
        log.debug("REST request to delete SaleInvoiceItem : {}", id);

        saleInvoiceItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
