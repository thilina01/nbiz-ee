package com.nanosl.nbiz.ee.web.rest;

import com.nanosl.nbiz.ee.NBizEeApp;

import com.nanosl.nbiz.ee.domain.SaleInvoice;
import com.nanosl.nbiz.ee.repository.SaleInvoiceRepository;
import com.nanosl.nbiz.ee.service.SaleInvoiceService;
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
 * Test class for the SaleInvoiceResource REST controller.
 *
 * @see SaleInvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NBizEeApp.class)
public class SaleInvoiceResourceIntTest {

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_PAID_AMOUNT = 1D;
    private static final Double UPDATED_PAID_AMOUNT = 2D;

    private static final Double DEFAULT_BALANCE_AMOUNT = 1D;
    private static final Double UPDATED_BALANCE_AMOUNT = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    @Autowired
    private SaleInvoiceRepository saleInvoiceRepository;

    

    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleInvoiceMockMvc;

    private SaleInvoice saleInvoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaleInvoiceResource saleInvoiceResource = new SaleInvoiceResource(saleInvoiceService);
        this.restSaleInvoiceMockMvc = MockMvcBuilders.standaloneSetup(saleInvoiceResource)
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
    public static SaleInvoice createEntity(EntityManager em) {
        SaleInvoice saleInvoice = new SaleInvoice()
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .amount(DEFAULT_AMOUNT)
            .paidAmount(DEFAULT_PAID_AMOUNT)
            .balanceAmount(DEFAULT_BALANCE_AMOUNT)
            .discount(DEFAULT_DISCOUNT);
        return saleInvoice;
    }

    @Before
    public void initTest() {
        saleInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleInvoice() throws Exception {
        int databaseSizeBeforeCreate = saleInvoiceRepository.findAll().size();

        // Create the SaleInvoice
        restSaleInvoiceMockMvc.perform(post("/api/sale-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoice)))
            .andExpect(status().isCreated());

        // Validate the SaleInvoice in the database
        List<SaleInvoice> saleInvoiceList = saleInvoiceRepository.findAll();
        assertThat(saleInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        SaleInvoice testSaleInvoice = saleInvoiceList.get(saleInvoiceList.size() - 1);
        assertThat(testSaleInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testSaleInvoice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSaleInvoice.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testSaleInvoice.getBalanceAmount()).isEqualTo(DEFAULT_BALANCE_AMOUNT);
        assertThat(testSaleInvoice.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createSaleInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleInvoiceRepository.findAll().size();

        // Create the SaleInvoice with an existing ID
        saleInvoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleInvoiceMockMvc.perform(post("/api/sale-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoice)))
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoice in the database
        List<SaleInvoice> saleInvoiceList = saleInvoiceRepository.findAll();
        assertThat(saleInvoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSaleInvoices() throws Exception {
        // Initialize the database
        saleInvoiceRepository.saveAndFlush(saleInvoice);

        // Get all the saleInvoiceList
        restSaleInvoiceMockMvc.perform(get("/api/sale-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceAmount").value(hasItem(DEFAULT_BALANCE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getSaleInvoice() throws Exception {
        // Initialize the database
        saleInvoiceRepository.saveAndFlush(saleInvoice);

        // Get the saleInvoice
        restSaleInvoiceMockMvc.perform(get("/api/sale-invoices/{id}", saleInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleInvoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.balanceAmount").value(DEFAULT_BALANCE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSaleInvoice() throws Exception {
        // Get the saleInvoice
        restSaleInvoiceMockMvc.perform(get("/api/sale-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleInvoice() throws Exception {
        // Initialize the database
        saleInvoiceService.save(saleInvoice);

        int databaseSizeBeforeUpdate = saleInvoiceRepository.findAll().size();

        // Update the saleInvoice
        SaleInvoice updatedSaleInvoice = saleInvoiceRepository.findById(saleInvoice.getId()).get();
        // Disconnect from session so that the updates on updatedSaleInvoice are not directly saved in db
        em.detach(updatedSaleInvoice);
        updatedSaleInvoice
            .invoiceDate(UPDATED_INVOICE_DATE)
            .amount(UPDATED_AMOUNT)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .balanceAmount(UPDATED_BALANCE_AMOUNT)
            .discount(UPDATED_DISCOUNT);

        restSaleInvoiceMockMvc.perform(put("/api/sale-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSaleInvoice)))
            .andExpect(status().isOk());

        // Validate the SaleInvoice in the database
        List<SaleInvoice> saleInvoiceList = saleInvoiceRepository.findAll();
        assertThat(saleInvoiceList).hasSize(databaseSizeBeforeUpdate);
        SaleInvoice testSaleInvoice = saleInvoiceList.get(saleInvoiceList.size() - 1);
        assertThat(testSaleInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testSaleInvoice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSaleInvoice.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testSaleInvoice.getBalanceAmount()).isEqualTo(UPDATED_BALANCE_AMOUNT);
        assertThat(testSaleInvoice.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleInvoice() throws Exception {
        int databaseSizeBeforeUpdate = saleInvoiceRepository.findAll().size();

        // Create the SaleInvoice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaleInvoiceMockMvc.perform(put("/api/sale-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoice)))
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoice in the database
        List<SaleInvoice> saleInvoiceList = saleInvoiceRepository.findAll();
        assertThat(saleInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaleInvoice() throws Exception {
        // Initialize the database
        saleInvoiceService.save(saleInvoice);

        int databaseSizeBeforeDelete = saleInvoiceRepository.findAll().size();

        // Get the saleInvoice
        restSaleInvoiceMockMvc.perform(delete("/api/sale-invoices/{id}", saleInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleInvoice> saleInvoiceList = saleInvoiceRepository.findAll();
        assertThat(saleInvoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleInvoice.class);
        SaleInvoice saleInvoice1 = new SaleInvoice();
        saleInvoice1.setId(1L);
        SaleInvoice saleInvoice2 = new SaleInvoice();
        saleInvoice2.setId(saleInvoice1.getId());
        assertThat(saleInvoice1).isEqualTo(saleInvoice2);
        saleInvoice2.setId(2L);
        assertThat(saleInvoice1).isNotEqualTo(saleInvoice2);
        saleInvoice1.setId(null);
        assertThat(saleInvoice1).isNotEqualTo(saleInvoice2);
    }
}
