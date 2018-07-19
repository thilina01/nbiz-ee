package com.nanosl.nbiz.ee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nanosl.nbiz.ee.domain.PurchaseInvoiceItem;
import com.nanosl.nbiz.ee.repository.PurchaseInvoiceItemRepository;
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
 * REST controller for managing PurchaseInvoiceItem.
 */
@RestController
@RequestMapping("/api")
public class PurchaseInvoiceItemResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseInvoiceItemResource.class);

    private static final String ENTITY_NAME = "purchaseInvoiceItem";

    private final PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;

    public PurchaseInvoiceItemResource(PurchaseInvoiceItemRepository purchaseInvoiceItemRepository) {
        this.purchaseInvoiceItemRepository = purchaseInvoiceItemRepository;
    }

    /**
     * POST  /purchase-invoice-items : Create a new purchaseInvoiceItem.
     *
     * @param purchaseInvoiceItem the purchaseInvoiceItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseInvoiceItem, or with status 400 (Bad Request) if the purchaseInvoiceItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-invoice-items")
    @Timed
    public ResponseEntity<PurchaseInvoiceItem> createPurchaseInvoiceItem(@Valid @RequestBody PurchaseInvoiceItem purchaseInvoiceItem) throws URISyntaxException {
        log.debug("REST request to save PurchaseInvoiceItem : {}", purchaseInvoiceItem);
        if (purchaseInvoiceItem.getId() != null) {
            throw new BadRequestAlertException("A new purchaseInvoiceItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseInvoiceItem result = purchaseInvoiceItemRepository.save(purchaseInvoiceItem);
        return ResponseEntity.created(new URI("/api/purchase-invoice-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-invoice-items : Updates an existing purchaseInvoiceItem.
     *
     * @param purchaseInvoiceItem the purchaseInvoiceItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseInvoiceItem,
     * or with status 400 (Bad Request) if the purchaseInvoiceItem is not valid,
     * or with status 500 (Internal Server Error) if the purchaseInvoiceItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-invoice-items")
    @Timed
    public ResponseEntity<PurchaseInvoiceItem> updatePurchaseInvoiceItem(@Valid @RequestBody PurchaseInvoiceItem purchaseInvoiceItem) throws URISyntaxException {
        log.debug("REST request to update PurchaseInvoiceItem : {}", purchaseInvoiceItem);
        if (purchaseInvoiceItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseInvoiceItem result = purchaseInvoiceItemRepository.save(purchaseInvoiceItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseInvoiceItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-invoice-items : get all the purchaseInvoiceItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseInvoiceItems in body
     */
    @GetMapping("/purchase-invoice-items")
    @Timed
    public ResponseEntity<List<PurchaseInvoiceItem>> getAllPurchaseInvoiceItems(Pageable pageable) {
        log.debug("REST request to get a page of PurchaseInvoiceItems");
        Page<PurchaseInvoiceItem> page = purchaseInvoiceItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-invoice-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-invoice-items/:id : get the "id" purchaseInvoiceItem.
     *
     * @param id the id of the purchaseInvoiceItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseInvoiceItem, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-invoice-items/{id}")
    @Timed
    public ResponseEntity<PurchaseInvoiceItem> getPurchaseInvoiceItem(@PathVariable Long id) {
        log.debug("REST request to get PurchaseInvoiceItem : {}", id);
        Optional<PurchaseInvoiceItem> purchaseInvoiceItem = purchaseInvoiceItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(purchaseInvoiceItem);
    }

    /**
     * DELETE  /purchase-invoice-items/:id : delete the "id" purchaseInvoiceItem.
     *
     * @param id the id of the purchaseInvoiceItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-invoice-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseInvoiceItem(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseInvoiceItem : {}", id);

        purchaseInvoiceItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
