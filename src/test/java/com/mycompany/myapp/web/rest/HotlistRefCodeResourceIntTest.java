package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ApiTestApp;

import com.mycompany.myapp.domain.HotlistRefCode;
import com.mycompany.myapp.repository.HotlistRefCodeRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HotlistRefCodeResource REST controller.
 *
 * @see HotlistRefCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiTestApp.class)
public class HotlistRefCodeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DB_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DB_KEY = "BBBBBBBBBB";

    @Autowired
    private HotlistRefCodeRepository hotlistRefCodeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHotlistRefCodeMockMvc;

    private HotlistRefCode hotlistRefCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HotlistRefCodeResource hotlistRefCodeResource = new HotlistRefCodeResource(hotlistRefCodeRepository);
        this.restHotlistRefCodeMockMvc = MockMvcBuilders.standaloneSetup(hotlistRefCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotlistRefCode createEntity(EntityManager em) {
        HotlistRefCode hotlistRefCode = new HotlistRefCode()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .dbKey(DEFAULT_DB_KEY);
        return hotlistRefCode;
    }

    @Before
    public void initTest() {
        hotlistRefCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotlistRefCode() throws Exception {
        int databaseSizeBeforeCreate = hotlistRefCodeRepository.findAll().size();

        // Create the HotlistRefCode
        restHotlistRefCodeMockMvc.perform(post("/api/hotlist-ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlistRefCode)))
            .andExpect(status().isCreated());

        // Validate the HotlistRefCode in the database
        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeCreate + 1);
        HotlistRefCode testHotlistRefCode = hotlistRefCodeList.get(hotlistRefCodeList.size() - 1);
        assertThat(testHotlistRefCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testHotlistRefCode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotlistRefCode.getDbKey()).isEqualTo(DEFAULT_DB_KEY);
    }

    @Test
    @Transactional
    public void createHotlistRefCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotlistRefCodeRepository.findAll().size();

        // Create the HotlistRefCode with an existing ID
        hotlistRefCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotlistRefCodeMockMvc.perform(post("/api/hotlist-ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlistRefCode)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotlistRefCodeRepository.findAll().size();
        // set the field null
        hotlistRefCode.setCode(null);

        // Create the HotlistRefCode, which fails.

        restHotlistRefCodeMockMvc.perform(post("/api/hotlist-ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlistRefCode)))
            .andExpect(status().isBadRequest());

        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotlistRefCodeRepository.findAll().size();
        // set the field null
        hotlistRefCode.setName(null);

        // Create the HotlistRefCode, which fails.

        restHotlistRefCodeMockMvc.perform(post("/api/hotlist-ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlistRefCode)))
            .andExpect(status().isBadRequest());

        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDbKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotlistRefCodeRepository.findAll().size();
        // set the field null
        hotlistRefCode.setDbKey(null);

        // Create the HotlistRefCode, which fails.

        restHotlistRefCodeMockMvc.perform(post("/api/hotlist-ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlistRefCode)))
            .andExpect(status().isBadRequest());

        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHotlistRefCodes() throws Exception {
        // Initialize the database
        hotlistRefCodeRepository.saveAndFlush(hotlistRefCode);

        // Get all the hotlistRefCodeList
        restHotlistRefCodeMockMvc.perform(get("/api/hotlist-ref-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotlistRefCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dbKey").value(hasItem(DEFAULT_DB_KEY.toString())));
    }

    @Test
    @Transactional
    public void getHotlistRefCode() throws Exception {
        // Initialize the database
        hotlistRefCodeRepository.saveAndFlush(hotlistRefCode);

        // Get the hotlistRefCode
        restHotlistRefCodeMockMvc.perform(get("/api/hotlist-ref-codes/{id}", hotlistRefCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hotlistRefCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dbKey").value(DEFAULT_DB_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHotlistRefCode() throws Exception {
        // Get the hotlistRefCode
        restHotlistRefCodeMockMvc.perform(get("/api/hotlist-ref-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotlistRefCode() throws Exception {
        // Initialize the database
        hotlistRefCodeRepository.saveAndFlush(hotlistRefCode);
        int databaseSizeBeforeUpdate = hotlistRefCodeRepository.findAll().size();

        // Update the hotlistRefCode
        HotlistRefCode updatedHotlistRefCode = hotlistRefCodeRepository.findOne(hotlistRefCode.getId());
        updatedHotlistRefCode
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .dbKey(UPDATED_DB_KEY);

        restHotlistRefCodeMockMvc.perform(put("/api/hotlist-ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotlistRefCode)))
            .andExpect(status().isOk());

        // Validate the HotlistRefCode in the database
        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeUpdate);
        HotlistRefCode testHotlistRefCode = hotlistRefCodeList.get(hotlistRefCodeList.size() - 1);
        assertThat(testHotlistRefCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testHotlistRefCode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotlistRefCode.getDbKey()).isEqualTo(UPDATED_DB_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingHotlistRefCode() throws Exception {
        int databaseSizeBeforeUpdate = hotlistRefCodeRepository.findAll().size();

        // Create the HotlistRefCode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHotlistRefCodeMockMvc.perform(put("/api/hotlist-ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlistRefCode)))
            .andExpect(status().isCreated());

        // Validate the HotlistRefCode in the database
        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHotlistRefCode() throws Exception {
        // Initialize the database
        hotlistRefCodeRepository.saveAndFlush(hotlistRefCode);
        int databaseSizeBeforeDelete = hotlistRefCodeRepository.findAll().size();

        // Get the hotlistRefCode
        restHotlistRefCodeMockMvc.perform(delete("/api/hotlist-ref-codes/{id}", hotlistRefCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HotlistRefCode> hotlistRefCodeList = hotlistRefCodeRepository.findAll();
        assertThat(hotlistRefCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotlistRefCode.class);
        HotlistRefCode hotlistRefCode1 = new HotlistRefCode();
        hotlistRefCode1.setId(1L);
        HotlistRefCode hotlistRefCode2 = new HotlistRefCode();
        hotlistRefCode2.setId(hotlistRefCode1.getId());
        assertThat(hotlistRefCode1).isEqualTo(hotlistRefCode2);
        hotlistRefCode2.setId(2L);
        assertThat(hotlistRefCode1).isNotEqualTo(hotlistRefCode2);
        hotlistRefCode1.setId(null);
        assertThat(hotlistRefCode1).isNotEqualTo(hotlistRefCode2);
    }
}
