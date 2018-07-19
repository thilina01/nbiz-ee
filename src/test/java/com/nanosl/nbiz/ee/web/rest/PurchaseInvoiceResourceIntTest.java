package com.nanosl.nbiz.ee.web.rest;

import com.nanosl.nbiz.ee.NBizEeApp;

import com.nanosl.nbiz.ee.domain.PurchaseInvoice;
import com.nanosl.nbiz.ee.repository.PurchaseInvoiceRepository;
import com.nanosl.nbiz.ee.service.PurchaseInvoiceService;
import com.nanosl.nbiz.ee.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.nanosl.nbiz.ee.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PurchaseInvoiceResource REST controller.
 *
 * @see PurchaseInvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NBizEeApp.class)
public class PurchaseInvoiceResourceIntTest {

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    @Autowired
    private PurchaseInvoiceRepository purchaseInvoiceRepository;

    

    @Autowired
    private PurchaseInvoiceService purchaseInvoiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchaseInvoiceMockMvc;

    private PurchaseInvoice purchaseInvoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseInvoiceResource purchaseInvoiceResource = new PurchaseInvoiceResource(purchaseInvoiceService);
        this.restPurchaseInvoiceMockMvc = MockMvcBuilders.standaloneSetup(purchaseInvoiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseInvoice createEntity(EntityManager em) {
        PurchaseInvoice purchaseInvoice = new PurchaseInvoice()
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .amount(DEFAULT_AMOUNT)
            .discount(DEFAULT_DISCOUNT);
        return purchaseInvoice;
    }

    @Before
    public void initTest() {
        purchaseInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseInvoice() throws Exception {
        int databaseSizeBeforeCreate = purchaseInvoiceRepository.findAll().size();

        // Create the PurchaseInvoice
        restPurchaseInvoiceMockMvc.perform(post("/api/purchase-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoice)))
            .andExpect(status().isCreated());

        // Validate the PurchaseInvoice in the database
        List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAll();
        assertThat(purchaseInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseInvoice testPurchaseInvoice = purchaseInvoiceList.get(purchaseInvoiceList.size() - 1);
        assertThat(testPurchaseInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testPurchaseInvoice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPurchaseInvoice.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createPurchaseInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseInvoiceRepository.findAll().size();

        // Create the PurchaseInvoice with an existing ID
        purchaseInvoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseInvoiceMockMvc.perform(post("/api/purchase-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoice)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseInvoice in the database
        List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAll();
        assertThat(purchaseInvoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInvoiceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseInvoiceRepository.findAll().size();
        // set the field null
        purchaseInvoice.setInvoiceDate(null);

        // Create the PurchaseInvoice, which fails.

        restPurchaseInvoiceMockMvc.perform(post("/api/purchase-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoice)))
            .andExpect(status().isBadRequest());

        List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAll();
        assertThat(purchaseInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseInvoiceRepository.findAll().size();
        // set the field null
        purchaseInvoice.setAmount(null);

        // Create the PurchaseInvoice, which fails.

        restPurchaseInvoiceMockMvc.perform(post("/api/purchase-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoice)))
            .andExpect(status().isBadRequest());

        List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAll();
        assertThat(purchaseInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseInvoices() throws Exception {
        // Initialize the database
        purchaseInvoiceRepository.saveAndFlush(purchaseInvoice);

        // Get all the purchaseInvoiceList
        restPurchaseInvoiceMockMvc.perform(get("/api/purchase-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getPurchaseInvoice() throws Exception {
        // Initialize the database
        purchaseInvoiceRepository.saveAndFlush(purchaseInvoice);

        // Get the purchaseInvoice
        restPurchaseInvoiceMockMvc.perform(get("/api/purchase-invoices/{id}", purchaseInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseInvoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPurchaseInvoice() throws Exception {
        // Get the purchaseInvoice
        restPurchaseInvoiceMockMvc.perform(get("/api/purchase-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseInvoice() throws Exception {
        // Initialize the database
        purchaseInvoiceService.save(purchaseInvoice);

        int databaseSizeBeforeUpdate = purchaseInvoiceRepository.findAll().size();

        // Update the purchaseInvoice
        PurchaseInvoice updatedPurchaseInvoice = purchaseInvoiceRepository.findById(purchaseInvoice.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseInvoice are not directly saved in db
        em.detach(updatedPurchaseInvoice);
        updatedPurchaseInvoice
            .invoiceDate(UPDATED_INVOICE_DATE)
            .amount(UPDATED_AMOUNT)
            .discount(UPDATED_DISCOUNT);

        restPurchaseInvoiceMockMvc.perform(put("/api/purchase-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseInvoice)))
            .andExpect(status().isOk());

        // Validate the PurchaseInvoice in the database
        List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAll();
        assertThat(purchaseInvoiceList).hasSize(databaseSizeBeforeUpdate);
        PurchaseInvoice testPurchaseInvoice = purchaseInvoiceList.get(purchaseInvoiceList.size() - 1);
        assertThat(testPurchaseInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testPurchaseInvoice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPurchaseInvoice.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseInvoice() throws Exception {
        int databaseSizeBeforeUpdate = purchaseInvoiceRepository.findAll().size();

        // Create the PurchaseInvoice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPurchaseInvoiceMockMvc.perform(put("/api/purchase-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoice)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseInvoice in the database
        List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAll();
        assertThat(purchaseInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseInvoice() throws Exception {
        // Initialize the database
        purchaseInvoiceService.save(purchaseInvoice);

        int databaseSizeBeforeDelete = purchaseInvoiceRepository.findAll().size();

        // Get the purchaseInvoice
        restPurchaseInvoiceMockMvc.perform(delete("/api/purchase-invoices/{id}", purchaseInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAll();
        assertThat(purchaseInvoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseInvoice.class);
        PurchaseInvoice purchaseInvoice1 = new PurchaseInvoice();
        purchaseInvoice1.setId(1L);
        PurchaseInvoice purchaseInvoice2 = new PurchaseInvoice();
        purchaseInvoice2.setId(purchaseInvoice1.getId());
        assertThat(purchaseInvoice1).isEqualTo(purchaseInvoice2);
        purchaseInvoice2.setId(2L);
        assertThat(purchaseInvoice1).isNotEqualTo(purchaseInvoice2);
        purchaseInvoice1.setId(null);
        assertThat(purchaseInvoice1).isNotEqualTo(purchaseInvoice2);
    }
}
