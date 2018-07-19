package com.nanosl.nbiz.ee.web.rest;

import com.nanosl.nbiz.ee.NBizEeApp;

import com.nanosl.nbiz.ee.domain.PurchaseInvoiceItem;
import com.nanosl.nbiz.ee.repository.PurchaseInvoiceItemRepository;
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
 * Test class for the PurchaseInvoiceItemResource REST controller.
 *
 * @see PurchaseInvoiceItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NBizEeApp.class)
public class PurchaseInvoiceItemResourceIntTest {

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Double DEFAULT_SELLING_PRICE = 1D;
    private static final Double UPDATED_SELLING_PRICE = 2D;

    private static final Instant DEFAULT_EXPIARY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIARY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final String DEFAULT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL = "BBBBBBBBBB";

    @Autowired
    private PurchaseInvoiceItemRepository purchaseInvoiceItemRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchaseInvoiceItemMockMvc;

    private PurchaseInvoiceItem purchaseInvoiceItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseInvoiceItemResource purchaseInvoiceItemResource = new PurchaseInvoiceItemResource(purchaseInvoiceItemRepository);
        this.restPurchaseInvoiceItemMockMvc = MockMvcBuilders.standaloneSetup(purchaseInvoiceItemResource)
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
    public static PurchaseInvoiceItem createEntity(EntityManager em) {
        PurchaseInvoiceItem purchaseInvoiceItem = new PurchaseInvoiceItem()
            .cost(DEFAULT_COST)
            .discount(DEFAULT_DISCOUNT)
            .sellingPrice(DEFAULT_SELLING_PRICE)
            .expiaryDate(DEFAULT_EXPIARY_DATE)
            .quantity(DEFAULT_QUANTITY)
            .serial(DEFAULT_SERIAL);
        return purchaseInvoiceItem;
    }

    @Before
    public void initTest() {
        purchaseInvoiceItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseInvoiceItem() throws Exception {
        int databaseSizeBeforeCreate = purchaseInvoiceItemRepository.findAll().size();

        // Create the PurchaseInvoiceItem
        restPurchaseInvoiceItemMockMvc.perform(post("/api/purchase-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoiceItem)))
            .andExpect(status().isCreated());

        // Validate the PurchaseInvoiceItem in the database
        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseInvoiceItem testPurchaseInvoiceItem = purchaseInvoiceItemList.get(purchaseInvoiceItemList.size() - 1);
        assertThat(testPurchaseInvoiceItem.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testPurchaseInvoiceItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testPurchaseInvoiceItem.getSellingPrice()).isEqualTo(DEFAULT_SELLING_PRICE);
        assertThat(testPurchaseInvoiceItem.getExpiaryDate()).isEqualTo(DEFAULT_EXPIARY_DATE);
        assertThat(testPurchaseInvoiceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchaseInvoiceItem.getSerial()).isEqualTo(DEFAULT_SERIAL);
    }

    @Test
    @Transactional
    public void createPurchaseInvoiceItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseInvoiceItemRepository.findAll().size();

        // Create the PurchaseInvoiceItem with an existing ID
        purchaseInvoiceItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseInvoiceItemMockMvc.perform(post("/api/purchase-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseInvoiceItem in the database
        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseInvoiceItemRepository.findAll().size();
        // set the field null
        purchaseInvoiceItem.setCost(null);

        // Create the PurchaseInvoiceItem, which fails.

        restPurchaseInvoiceItemMockMvc.perform(post("/api/purchase-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoiceItem)))
            .andExpect(status().isBadRequest());

        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseInvoiceItemRepository.findAll().size();
        // set the field null
        purchaseInvoiceItem.setSellingPrice(null);

        // Create the PurchaseInvoiceItem, which fails.

        restPurchaseInvoiceItemMockMvc.perform(post("/api/purchase-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoiceItem)))
            .andExpect(status().isBadRequest());

        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseInvoiceItemRepository.findAll().size();
        // set the field null
        purchaseInvoiceItem.setQuantity(null);

        // Create the PurchaseInvoiceItem, which fails.

        restPurchaseInvoiceItemMockMvc.perform(post("/api/purchase-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoiceItem)))
            .andExpect(status().isBadRequest());

        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseInvoiceItems() throws Exception {
        // Initialize the database
        purchaseInvoiceItemRepository.saveAndFlush(purchaseInvoiceItem);

        // Get all the purchaseInvoiceItemList
        restPurchaseInvoiceItemMockMvc.perform(get("/api/purchase-invoice-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseInvoiceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].expiaryDate").value(hasItem(DEFAULT_EXPIARY_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())));
    }
    

    @Test
    @Transactional
    public void getPurchaseInvoiceItem() throws Exception {
        // Initialize the database
        purchaseInvoiceItemRepository.saveAndFlush(purchaseInvoiceItem);

        // Get the purchaseInvoiceItem
        restPurchaseInvoiceItemMockMvc.perform(get("/api/purchase-invoice-items/{id}", purchaseInvoiceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseInvoiceItem.getId().intValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE.doubleValue()))
            .andExpect(jsonPath("$.expiaryDate").value(DEFAULT_EXPIARY_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPurchaseInvoiceItem() throws Exception {
        // Get the purchaseInvoiceItem
        restPurchaseInvoiceItemMockMvc.perform(get("/api/purchase-invoice-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseInvoiceItem() throws Exception {
        // Initialize the database
        purchaseInvoiceItemRepository.saveAndFlush(purchaseInvoiceItem);

        int databaseSizeBeforeUpdate = purchaseInvoiceItemRepository.findAll().size();

        // Update the purchaseInvoiceItem
        PurchaseInvoiceItem updatedPurchaseInvoiceItem = purchaseInvoiceItemRepository.findById(purchaseInvoiceItem.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseInvoiceItem are not directly saved in db
        em.detach(updatedPurchaseInvoiceItem);
        updatedPurchaseInvoiceItem
            .cost(UPDATED_COST)
            .discount(UPDATED_DISCOUNT)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .expiaryDate(UPDATED_EXPIARY_DATE)
            .quantity(UPDATED_QUANTITY)
            .serial(UPDATED_SERIAL);

        restPurchaseInvoiceItemMockMvc.perform(put("/api/purchase-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseInvoiceItem)))
            .andExpect(status().isOk());

        // Validate the PurchaseInvoiceItem in the database
        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeUpdate);
        PurchaseInvoiceItem testPurchaseInvoiceItem = purchaseInvoiceItemList.get(purchaseInvoiceItemList.size() - 1);
        assertThat(testPurchaseInvoiceItem.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testPurchaseInvoiceItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPurchaseInvoiceItem.getSellingPrice()).isEqualTo(UPDATED_SELLING_PRICE);
        assertThat(testPurchaseInvoiceItem.getExpiaryDate()).isEqualTo(UPDATED_EXPIARY_DATE);
        assertThat(testPurchaseInvoiceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchaseInvoiceItem.getSerial()).isEqualTo(UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseInvoiceItemRepository.findAll().size();

        // Create the PurchaseInvoiceItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPurchaseInvoiceItemMockMvc.perform(put("/api/purchase-invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseInvoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseInvoiceItem in the database
        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseInvoiceItem() throws Exception {
        // Initialize the database
        purchaseInvoiceItemRepository.saveAndFlush(purchaseInvoiceItem);

        int databaseSizeBeforeDelete = purchaseInvoiceItemRepository.findAll().size();

        // Get the purchaseInvoiceItem
        restPurchaseInvoiceItemMockMvc.perform(delete("/api/purchase-invoice-items/{id}", purchaseInvoiceItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseInvoiceItem> purchaseInvoiceItemList = purchaseInvoiceItemRepository.findAll();
        assertThat(purchaseInvoiceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseInvoiceItem.class);
        PurchaseInvoiceItem purchaseInvoiceItem1 = new PurchaseInvoiceItem();
        purchaseInvoiceItem1.setId(1L);
        PurchaseInvoiceItem purchaseInvoiceItem2 = new PurchaseInvoiceItem();
        purchaseInvoiceItem2.setId(purchaseInvoiceItem1.getId());
        assertThat(purchaseInvoiceItem1).isEqualTo(purchaseInvoiceItem2);
        purchaseInvoiceItem2.setId(2L);
        assertThat(purchaseInvoiceItem1).isNotEqualTo(purchaseInvoiceItem2);
        purchaseInvoiceItem1.setId(null);
        assertThat(purchaseInvoiceItem1).isNotEqualTo(purchaseInvoiceItem2);
    }
}
