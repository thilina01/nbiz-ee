package com.nanosl.nbiz.ee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nanosl.nbiz.ee.domain.QuotationItem;
import com.nanosl.nbiz.ee.repository.QuotationItemRepository;
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
 * REST controller for managing QuotationItem.
 */
@RestController
@RequestMapping("/api")
public class QuotationItemResource {

    private final Logger log = LoggerFactory.getLogger(QuotationItemResource.class);

    private static final String ENTITY_NAME = "quotationItem";

    private final QuotationItemRepository quotationItemRepository;

    public QuotationItemResource(QuotationItemRepository quotationItemRepository) {
        this.quotationItemRepository = quotationItemRepository;
    }

    /**
     * POST  /quotation-items : Create a new quotationItem.
     *
     * @param quotationItem the quotationItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quotationItem, or with status 400 (Bad Request) if the quotationItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quotation-items")
    @Timed
    public ResponseEntity<QuotationItem> createQuotationItem(@RequestBody QuotationItem quotationItem) throws URISyntaxException {
        log.debug("REST request to save QuotationItem : {}", quotationItem);
        if (quotationItem.getId() != null) {
            throw new BadRequestAlertException("A new quotationItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuotationItem result = quotationItemRepository.save(quotationItem);
        return ResponseEntity.created(new URI("/api/quotation-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quotation-items : Updates an existing quotationItem.
     *
     * @param quotationItem the quotationItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quotationItem,
     * or with status 400 (Bad Request) if the quotationItem is not valid,
     * or with status 500 (Internal Server Error) if the quotationItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quotation-items")
    @Timed
    public ResponseEntity<QuotationItem> updateQuotationItem(@RequestBody QuotationItem quotationItem) throws URISyntaxException {
        log.debug("REST request to update QuotationItem : {}", quotationItem);
        if (quotationItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuotationItem result = quotationItemRepository.save(quotationItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotationItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quotation-items : get all the quotationItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quotationItems in body
     */
    @GetMapping("/quotation-items")
    @Timed
    public ResponseEntity<List<QuotationItem>> getAllQuotationItems(Pageable pageable) {
        log.debug("REST request to get a page of QuotationItems");
        Page<QuotationItem> page = quotationItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quotation-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quotation-items/:id : get the "id" quotationItem.
     *
     * @param id the id of the quotationItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quotationItem, or with status 404 (Not Found)
     */
    @GetMapping("/quotation-items/{id}")
    @Timed
    public ResponseEntity<QuotationItem> getQuotationItem(@PathVariable Long id) {
        log.debug("REST request to get QuotationItem : {}", id);
        Optional<QuotationItem> quotationItem = quotationItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotationItem);
    }

    /**
     * DELETE  /quotation-items/:id : delete the "id" quotationItem.
     *
     * @param id the id of the quotationItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quotation-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuotationItem(@PathVariable Long id) {
        log.debug("REST request to delete QuotationItem : {}", id);

        quotationItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
