package com.nanosl.nbiz.ee.web.rest;

import com.nanosl.nbiz.ee.NBizEeApp;

import com.nanosl.nbiz.ee.domain.SaleInvoicePayment;
import com.nanosl.nbiz.ee.repository.SaleInvoicePaymentRepository;
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
 * Test class for the SaleInvoicePaymentResource REST controller.
 *
 * @see SaleInvoicePaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NBizEeApp.class)
public class SaleInvoicePaymentResourceIntTest {

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private SaleInvoicePaymentRepository saleInvoicePaymentRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleInvoicePaymentMockMvc;

    private SaleInvoicePayment saleInvoicePayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaleInvoicePaymentResource saleInvoicePaymentResource = new SaleInvoicePaymentResource(saleInvoicePaymentRepository);
        this.restSaleInvoicePaymentMockMvc = MockMvcBuilders.standaloneSetup(saleInvoicePaymentResource)
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
    public static SaleInvoicePayment createEntity(EntityManager em) {
        SaleInvoicePayment saleInvoicePayment = new SaleInvoicePayment()
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .amount(DEFAULT_AMOUNT);
        return saleInvoicePayment;
    }

    @Before
    public void initTest() {
        saleInvoicePayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleInvoicePayment() throws Exception {
        int databaseSizeBeforeCreate = saleInvoicePaymentRepository.findAll().size();

        // Create the SaleInvoicePayment
        restSaleInvoicePaymentMockMvc.perform(post("/api/sale-invoice-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoicePayment)))
            .andExpect(status().isCreated());

        // Validate the SaleInvoicePayment in the database
        List<SaleInvoicePayment> saleInvoicePaymentList = saleInvoicePaymentRepository.findAll();
        assertThat(saleInvoicePaymentList).hasSize(databaseSizeBeforeCreate + 1);
        SaleInvoicePayment testSaleInvoicePayment = saleInvoicePaymentList.get(saleInvoicePaymentList.size() - 1);
        assertThat(testSaleInvoicePayment.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testSaleInvoicePayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createSaleInvoicePaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleInvoicePaymentRepository.findAll().size();

        // Create the SaleInvoicePayment with an existing ID
        saleInvoicePayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleInvoicePaymentMockMvc.perform(post("/api/sale-invoice-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoicePayment)))
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoicePayment in the database
        List<SaleInvoicePayment> saleInvoicePaymentList = saleInvoicePaymentRepository.findAll();
        assertThat(saleInvoicePaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleInvoicePaymentRepository.findAll().size();
        // set the field null
        saleInvoicePayment.setPaymentDate(null);

        // Create the SaleInvoicePayment, which fails.

        restSaleInvoicePaymentMockMvc.perform(post("/api/sale-invoice-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoicePayment)))
            .andExpect(status().isBadRequest());

        List<SaleInvoicePayment> saleInvoicePaymentList = saleInvoicePaymentRepository.findAll();
        assertThat(saleInvoicePaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleInvoicePaymentRepository.findAll().size();
        // set the field null
        saleInvoicePayment.setAmount(null);

        // Create the SaleInvoicePayment, which fails.

        restSaleInvoicePaymentMockMvc.perform(post("/api/sale-invoice-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoicePayment)))
            .andExpect(status().isBadRequest());

        List<SaleInvoicePayment> saleInvoicePaymentList = saleInvoicePaymentRepository.findAll();
        assertThat(saleInvoicePaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaleInvoicePayments() throws Exception {
        // Initialize the database
        saleInvoicePaymentRepository.saveAndFlush(saleInvoicePayment);

        // Get all the saleInvoicePaymentList
        restSaleInvoicePaymentMockMvc.perform(get("/api/sale-invoice-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoicePayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getSaleInvoicePayment() throws Exception {
        // Initialize the database
        saleInvoicePaymentRepository.saveAndFlush(saleInvoicePayment);

        // Get the saleInvoicePayment
        restSaleInvoicePaymentMockMvc.perform(get("/api/sale-invoice-payments/{id}", saleInvoicePayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleInvoicePayment.getId().intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSaleInvoicePayment() throws Exception {
        // Get the saleInvoicePayment
        restSaleInvoicePaymentMockMvc.perform(get("/api/sale-invoice-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleInvoicePayment() throws Exception {
        // Initialize the database
        saleInvoicePaymentRepository.saveAndFlush(saleInvoicePayment);

        int databaseSizeBeforeUpdate = saleInvoicePaymentRepository.findAll().size();

        // Update the saleInvoicePayment
        SaleInvoicePayment updatedSaleInvoicePayment = saleInvoicePaymentRepository.findById(saleInvoicePayment.getId()).get();
        // Disconnect from session so that the updates on updatedSaleInvoicePayment are not directly saved in db
        em.detach(updatedSaleInvoicePayment);
        updatedSaleInvoicePayment
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amount(UPDATED_AMOUNT);

        restSaleInvoicePaymentMockMvc.perform(put("/api/sale-invoice-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSaleInvoicePayment)))
            .andExpect(status().isOk());

        // Validate the SaleInvoicePayment in the database
        List<SaleInvoicePayment> saleInvoicePaymentList = saleInvoicePaymentRepository.findAll();
        assertThat(saleInvoicePaymentList).hasSize(databaseSizeBeforeUpdate);
        SaleInvoicePayment testSaleInvoicePayment = saleInvoicePaymentList.get(saleInvoicePaymentList.size() - 1);
        assertThat(testSaleInvoicePayment.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testSaleInvoicePayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleInvoicePayment() throws Exception {
        int databaseSizeBeforeUpdate = saleInvoicePaymentRepository.findAll().size();

        // Create the SaleInvoicePayment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaleInvoicePaymentMockMvc.perform(put("/api/sale-invoice-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoicePayment)))
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoicePayment in the database
        List<SaleInvoicePayment> saleInvoicePaymentList = saleInvoicePaymentRepository.findAll();
        assertThat(saleInvoicePaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaleInvoicePayment() throws Exception {
        // Initialize the database
        saleInvoicePaymentRepository.saveAndFlush(saleInvoicePayment);

        int databaseSizeBeforeDelete = saleInvoicePaymentRepository.findAll().size();

        // Get the saleInvoicePayment
        restSaleInvoicePaymentMockMvc.perform(delete("/api/sale-invoice-payments/{id}", saleInvoicePayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleInvoicePayment> saleInvoicePaymentList = saleInvoicePaymentRepository.findAll();
        assertThat(saleInvoicePaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleInvoicePayment.class);
        SaleInvoicePayment saleInvoicePayment1 = new SaleInvoicePayment();
        saleInvoicePayment1.setId(1L);
        SaleInvoicePayment saleInvoicePayment2 = new SaleInvoicePayment();
        saleInvoicePayment2.setId(saleInvoicePayment1.getId());
        assertThat(saleInvoicePayment1).isEqualTo(saleInvoicePayment2);
        saleInvoicePayment2.setId(2L);
        assertThat(saleInvoicePayment1).isNotEqualTo(saleInvoicePayment2);
        saleInvoicePayment1.setId(null);
        assertThat(saleInvoicePayment1).isNotEqualTo(saleInvoicePayment2);
    }
}
