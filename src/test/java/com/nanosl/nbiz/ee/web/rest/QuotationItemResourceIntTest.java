package com.nanosl.nbiz.ee.web.rest;

import com.nanosl.nbiz.ee.NBizEeApp;

import com.nanosl.nbiz.ee.domain.QuotationItem;
import com.nanosl.nbiz.ee.repository.QuotationItemRepository;
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
 * Test class for the QuotationItemResource REST controller.
 *
 * @see QuotationItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NBizEeApp.class)
public class QuotationItemResourceIntTest {

    private static final Double DEFAULT_SELLING_PRICE = 1D;
    private static final Double UPDATED_SELLING_PRICE = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    @Autowired
    private QuotationItemRepository quotationItemRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuotationItemMockMvc;

    private QuotationItem quotationItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuotationItemResource quotationItemResource = new QuotationItemResource(quotationItemRepository);
        this.restQuotationItemMockMvc = MockMvcBuilders.standaloneSetup(quotationItemResource)
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
    public static QuotationItem createEntity(EntityManager em) {
        QuotationItem quotationItem = new QuotationItem()
            .sellingPrice(DEFAULT_SELLING_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .quantity(DEFAULT_QUANTITY);
        return quotationItem;
    }

    @Before
    public void initTest() {
        quotationItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotationItem() throws Exception {
        int databaseSizeBeforeCreate = quotationItemRepository.findAll().size();

        // Create the QuotationItem
        restQuotationItemMockMvc.perform(post("/api/quotation-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationItem)))
            .andExpect(status().isCreated());

        // Validate the QuotationItem in the database
        List<QuotationItem> quotationItemList = quotationItemRepository.findAll();
        assertThat(quotationItemList).hasSize(databaseSizeBeforeCreate + 1);
        QuotationItem testQuotationItem = quotationItemList.get(quotationItemList.size() - 1);
        assertThat(testQuotationItem.getSellingPrice()).isEqualTo(DEFAULT_SELLING_PRICE);
        assertThat(testQuotationItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testQuotationItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createQuotationItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotationItemRepository.findAll().size();

        // Create the QuotationItem with an existing ID
        quotationItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationItemMockMvc.perform(post("/api/quotation-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationItem)))
            .andExpect(status().isBadRequest());

        // Validate the QuotationItem in the database
        List<QuotationItem> quotationItemList = quotationItemRepository.findAll();
        assertThat(quotationItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSellingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationItemRepository.findAll().size();
        // set the field null
        quotationItem.setSellingPrice(null);

        // Create the QuotationItem, which fails.

        restQuotationItemMockMvc.perform(post("/api/quotation-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationItem)))
            .andExpect(status().isBadRequest());

        List<QuotationItem> quotationItemList = quotationItemRepository.findAll();
        assertThat(quotationItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationItemRepository.findAll().size();
        // set the field null
        quotationItem.setQuantity(null);

        // Create the QuotationItem, which fails.

        restQuotationItemMockMvc.perform(post("/api/quotation-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationItem)))
            .andExpect(status().isBadRequest());

        List<QuotationItem> quotationItemList = quotationItemRepository.findAll();
        assertThat(quotationItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotationItems() throws Exception {
        // Initialize the database
        quotationItemRepository.saveAndFlush(quotationItem);

        // Get all the quotationItemList
        restQuotationItemMockMvc.perform(get("/api/quotation-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getQuotationItem() throws Exception {
        // Initialize the database
        quotationItemRepository.saveAndFlush(quotationItem);

        // Get the quotationItem
        restQuotationItemMockMvc.perform(get("/api/quotation-items/{id}", quotationItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quotationItem.getId().intValue()))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingQuotationItem() throws Exception {
        // Get the quotationItem
        restQuotationItemMockMvc.perform(get("/api/quotation-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotationItem() throws Exception {
        // Initialize the database
        quotationItemRepository.saveAndFlush(quotationItem);

        int databaseSizeBeforeUpdate = quotationItemRepository.findAll().size();

        // Update the quotationItem
        QuotationItem updatedQuotationItem = quotationItemRepository.findById(quotationItem.getId()).get();
        // Disconnect from session so that the updates on updatedQuotationItem are not directly saved in db
        em.detach(updatedQuotationItem);
        updatedQuotationItem
            .sellingPrice(UPDATED_SELLING_PRICE)
            .discount(UPDATED_DISCOUNT)
            .quantity(UPDATED_QUANTITY);

        restQuotationItemMockMvc.perform(put("/api/quotation-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuotationItem)))
            .andExpect(status().isOk());

        // Validate the QuotationItem in the database
        List<QuotationItem> quotationItemList = quotationItemRepository.findAll();
        assertThat(quotationItemList).hasSize(databaseSizeBeforeUpdate);
        QuotationItem testQuotationItem = quotationItemList.get(quotationItemList.size() - 1);
        assertThat(testQuotationItem.getSellingPrice()).isEqualTo(UPDATED_SELLING_PRICE);
        assertThat(testQuotationItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testQuotationItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotationItem() throws Exception {
        int databaseSizeBeforeUpdate = quotationItemRepository.findAll().size();

        // Create the QuotationItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuotationItemMockMvc.perform(put("/api/quotation-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationItem)))
            .andExpect(status().isBadRequest());

        // Validate the QuotationItem in the database
        List<QuotationItem> quotationItemList = quotationItemRepository.findAll();
        assertThat(quotationItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuotationItem() throws Exception {
        // Initialize the database
        quotationItemRepository.saveAndFlush(quotationItem);

        int databaseSizeBeforeDelete = quotationItemRepository.findAll().size();

        // Get the quotationItem
        restQuotationItemMockMvc.perform(delete("/api/quotation-items/{id}", quotationItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuotationItem> quotationItemList = quotationItemRepository.findAll();
        assertThat(quotationItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationItem.class);
        QuotationItem quotationItem1 = new QuotationItem();
        quotationItem1.setId(1L);
        QuotationItem quotationItem2 = new QuotationItem();
        quotationItem2.setId(quotationItem1.getId());
        assertThat(quotationItem1).isEqualTo(quotationItem2);
        quotationItem2.setId(2L);
        assertThat(quotationItem1).isNotEqualTo(quotationItem2);
        quotationItem1.setId(null);
        assertThat(quotationItem1).isNotEqualTo(quotationItem2);
    }
}
