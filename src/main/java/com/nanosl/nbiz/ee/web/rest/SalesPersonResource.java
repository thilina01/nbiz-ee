package com.nanosl.nbiz.ee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nanosl.nbiz.ee.domain.SalesPerson;
import com.nanosl.nbiz.ee.repository.SalesPersonRepository;
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
 * REST controller for managing SalesPerson.
 */
@RestController
@RequestMapping("/api")
public class SalesPersonResource {

    private final Logger log = LoggerFactory.getLogger(SalesPersonResource.class);

    private static final String ENTITY_NAME = "salesPerson";

    private final SalesPersonRepository salesPersonRepository;

    public SalesPersonResource(SalesPersonRepository salesPersonRepository) {
        this.salesPersonRepository = salesPersonRepository;
    }

    /**
     * POST  /sales-people : Create a new salesPerson.
     *
     * @param salesPerson the salesPerson to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesPerson, or with status 400 (Bad Request) if the salesPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-people")
    @Timed
    public ResponseEntity<SalesPerson> createSalesPerson(@Valid @RequestBody SalesPerson salesPerson) throws URISyntaxException {
        log.debug("REST request to save SalesPerson : {}", salesPerson);
        if (salesPerson.getId() != null) {
            throw new BadRequestAlertException("A new salesPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesPerson result = salesPersonRepository.save(salesPerson);
        return ResponseEntity.created(new URI("/api/sales-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-people : Updates an existing salesPerson.
     *
     * @param salesPerson the salesPerson to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesPerson,
     * or with status 400 (Bad Request) if the salesPerson is not valid,
     * or with status 500 (Internal Server Error) if the salesPerson couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-people")
    @Timed
    public ResponseEntity<SalesPerson> updateSalesPerson(@Valid @RequestBody SalesPerson salesPerson) throws URISyntaxException {
        log.debug("REST request to update SalesPerson : {}", salesPerson);
        if (salesPerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesPerson result = salesPersonRepository.save(salesPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salesPerson.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-people : get all the salesPeople.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salesPeople in body
     */
    @GetMapping("/sales-people")
    @Timed
    public ResponseEntity<List<SalesPerson>> getAllSalesPeople(Pageable pageable) {
        log.debug("REST request to get a page of SalesPeople");
        Page<SalesPerson> page = salesPersonRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sales-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sales-people/:id : get the "id" salesPerson.
     *
     * @param id the id of the salesPerson to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesPerson, or with status 404 (Not Found)
     */
    @GetMapping("/sales-people/{id}")
    @Timed
    public ResponseEntity<SalesPerson> getSalesPerson(@PathVariable Long id) {
        log.debug("REST request to get SalesPerson : {}", id);
        Optional<SalesPerson> salesPerson = salesPersonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(salesPerson);
    }

    /**
     * DELETE  /sales-people/:id : delete the "id" salesPerson.
     *
     * @param id the id of the salesPerson to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalesPerson(@PathVariable Long id) {
        log.debug("REST request to delete SalesPerson : {}", id);

        salesPersonRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
