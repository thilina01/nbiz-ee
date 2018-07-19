package com.nanosl.nbiz.ee.web.rest;

import com.nanosl.nbiz.ee.NBizEeApp;

import com.nanosl.nbiz.ee.domain.SaleInvoiceItem;
import com.nanosl.nbiz.ee.repository.SaleInvoiceItemRepository;
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
import java.util.List;


import static com.nanosl.nbiz.ee.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SaleInvoiceItemResource REST controller.
 *
 * @see SaleInvoiceItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NBizEeApp.class)
public class SaleInvoiceItemResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    @Autowired
    private SaleInvoiceItemRepository saleInvoiceItemRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaleInvoiceItemMockMvc;

    private SaleInvoiceItem saleInvoiceItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaleInvoiceItemResource saleInvoiceItemResource = new SaleInvoiceItemResource(saleInvoiceItemRepository);
        this.restSaleInvoiceItemMockMvc = MockMvcBuilders.standaloneSetup(saleInvoiceItemResource)
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
    public static SaleInvoiceItem createEntity(EntityManager em) {
        SaleInvoiceItem saleInvoiceItem = new SaleInvoiceItem()
            .amount(DEFAULT_AMOUNT)
            .discount(DEFAULT_DISCOUNT)
            .quantity(DEFAULT_QUANTITY);
        return saleInvoiceItem;
    }

    @Before
    public void initTest() {
        saleInvoiceItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaleInvoiceItem() throws Exception {
        int databaseSizeBeforeCreate = saleInvoiceItemRepository.findAll().size();

        // Create the SaleInvoiceItem
        restSaleInvoiceItemMockMvc.perform(post("/api/sale-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoiceItem)))
            .andExpect(status().isCreated());

        // Validate the SaleInvoiceItem in the database
        List<SaleInvoiceItem> saleInvoiceItemList = saleInvoiceItemRepository.findAll();
        assertThat(saleInvoiceItemList).hasSize(databaseSizeBeforeCreate + 1);
        SaleInvoiceItem testSaleInvoiceItem = saleInvoiceItemList.get(saleInvoiceItemList.size() - 1);
        assertThat(testSaleInvoiceItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSaleInvoiceItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testSaleInvoiceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createSaleInvoiceItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleInvoiceItemRepository.findAll().size();

        // Create the SaleInvoiceItem with an existing ID
        saleInvoiceItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleInvoiceItemMockMvc.perform(post("/api/sale-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceItem in the database
        List<SaleInvoiceItem> saleInvoiceItemList = saleInvoiceItemRepository.findAll();
        assertThat(saleInvoiceItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleInvoiceItemRepository.findAll().size();
        // set the field null
        saleInvoiceItem.setAmount(null);

        // Create the SaleInvoiceItem, which fails.

        restSaleInvoiceItemMockMvc.perform(post("/api/sale-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoiceItem)))
            .andExpect(status().isBadRequest());

        List<SaleInvoiceItem> saleInvoiceItemList = saleInvoiceItemRepository.findAll();
        assertThat(saleInvoiceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleInvoiceItemRepository.findAll().size();
        // set the field null
        saleInvoiceItem.setQuantity(null);

        // Create the SaleInvoiceItem, which fails.

        restSaleInvoiceItemMockMvc.perform(post("/api/sale-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoiceItem)))
            .andExpect(status().isBadRequest());

        List<SaleInvoiceItem> saleInvoiceItemList = saleInvoiceItemRepository.findAll();
        assertThat(saleInvoiceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaleInvoiceItems() throws Exception {
        // Initialize the database
        saleInvoiceItemRepository.saveAndFlush(saleInvoiceItem);

        // Get all the saleInvoiceItemList
        restSaleInvoiceItemMockMvc.perform(get("/api/sale-invoice-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoiceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getSaleInvoiceItem() throws Exception {
        // Initialize the database
        saleInvoiceItemRepository.saveAndFlush(saleInvoiceItem);

        // Get the saleInvoiceItem
        restSaleInvoiceItemMockMvc.perform(get("/api/sale-invoice-items/{id}", saleInvoiceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saleInvoiceItem.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSaleInvoiceItem() throws Exception {
        // Get the saleInvoiceItem
        restSaleInvoiceItemMockMvc.perform(get("/api/sale-invoice-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaleInvoiceItem() throws Exception {
        // Initialize the database
        saleInvoiceItemRepository.saveAndFlush(saleInvoiceItem);

        int databaseSizeBeforeUpdate = saleInvoiceItemRepository.findAll().size();

        // Update the saleInvoiceItem
        SaleInvoiceItem updatedSaleInvoiceItem = saleInvoiceItemRepository.findById(saleInvoiceItem.getId()).get();
        // Disconnect from session so that the updates on updatedSaleInvoiceItem are not directly saved in db
        em.detach(updatedSaleInvoiceItem);
        updatedSaleInvoiceItem
            .amount(UPDATED_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .quantity(UPDATED_QUANTITY);

        restSaleInvoiceItemMockMvc.perform(put("/api/sale-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSaleInvoiceItem)))
            .andExpect(status().isOk());

        // Validate the SaleInvoiceItem in the database
        List<SaleInvoiceItem> saleInvoiceItemList = saleInvoiceItemRepository.findAll();
        assertThat(saleInvoiceItemList).hasSize(databaseSizeBeforeUpdate);
        SaleInvoiceItem testSaleInvoiceItem = saleInvoiceItemList.get(saleInvoiceItemList.size() - 1);
        assertThat(testSaleInvoiceItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSaleInvoiceItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testSaleInvoiceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingSaleInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = saleInvoiceItemRepository.findAll().size();

        // Create the SaleInvoiceItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaleInvoiceItemMockMvc.perform(put("/api/sale-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saleInvoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceItem in the database
        List<SaleInvoiceItem> saleInvoiceItemList = saleInvoiceItemRepository.findAll();
        assertThat(saleInvoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaleInvoiceItem() throws Exception {
        // Initialize the database
        saleInvoiceItemRepository.saveAndFlush(saleInvoiceItem);

        int databaseSizeBeforeDelete = saleInvoiceItemRepository.findAll().size();

        // Get the saleInvoiceItem
        restSaleInvoiceItemMockMvc.perform(delete("/api/sale-invoice-items/{id}", saleInvoiceItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SaleInvoiceItem> saleInvoiceItemList = saleInvoiceItemRepository.findAll();
        assertThat(saleInvoiceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleInvoiceItem.class);
        SaleInvoiceItem saleInvoiceItem1 = new SaleInvoiceItem();
        saleInvoiceItem1.setId(1L);
        SaleInvoiceItem saleInvoiceItem2 = new SaleInvoiceItem();
        saleInvoiceItem2.setId(saleInvoiceItem1.getId());
        assertThat(saleInvoiceItem1).isEqualTo(saleInvoiceItem2);
        saleInvoiceItem2.setId(2L);
        assertThat(saleInvoiceItem1).isNotEqualTo(saleInvoiceItem2);
        saleInvoiceItem1.setId(null);
        assertThat(saleInvoiceItem1).isNotEqualTo(saleInvoiceItem2);
    }
}
