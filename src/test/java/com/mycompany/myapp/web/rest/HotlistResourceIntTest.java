package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ApiTestApp;

import com.mycompany.myapp.domain.Hotlist;
import com.mycompany.myapp.repository.HotlistRepository;
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
 * Test class for the HotlistResource REST controller.
 *
 * @see HotlistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiTestApp.class)
public class HotlistResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DB_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DB_KEY = "BBBBBBBBBB";

    @Autowired
    private HotlistRepository hotlistRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHotlistMockMvc;

    private Hotlist hotlist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HotlistResource hotlistResource = new HotlistResource(hotlistRepository);
        this.restHotlistMockMvc = MockMvcBuilders.standaloneSetup(hotlistResource)
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
    public static Hotlist createEntity(EntityManager em) {
        Hotlist hotlist = new Hotlist()
            .value(DEFAULT_VALUE)
            .dbKey(DEFAULT_DB_KEY);
        return hotlist;
    }

    @Before
    public void initTest() {
        hotlist = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotlist() throws Exception {
        int databaseSizeBeforeCreate = hotlistRepository.findAll().size();

        // Create the Hotlist
        restHotlistMockMvc.perform(post("/api/hotlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlist)))
            .andExpect(status().isCreated());

        // Validate the Hotlist in the database
        List<Hotlist> hotlistList = hotlistRepository.findAll();
        assertThat(hotlistList).hasSize(databaseSizeBeforeCreate + 1);
        Hotlist testHotlist = hotlistList.get(hotlistList.size() - 1);
        assertThat(testHotlist.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testHotlist.getDbKey()).isEqualTo(DEFAULT_DB_KEY);
    }

    @Test
    @Transactional
    public void createHotlistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotlistRepository.findAll().size();

        // Create the Hotlist with an existing ID
        hotlist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotlistMockMvc.perform(post("/api/hotlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlist)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Hotlist> hotlistList = hotlistRepository.findAll();
        assertThat(hotlistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotlistRepository.findAll().size();
        // set the field null
        hotlist.setValue(null);

        // Create the Hotlist, which fails.

        restHotlistMockMvc.perform(post("/api/hotlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlist)))
            .andExpect(status().isBadRequest());

        List<Hotlist> hotlistList = hotlistRepository.findAll();
        assertThat(hotlistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDbKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotlistRepository.findAll().size();
        // set the field null
        hotlist.setDbKey(null);

        // Create the Hotlist, which fails.

        restHotlistMockMvc.perform(post("/api/hotlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlist)))
            .andExpect(status().isBadRequest());

        List<Hotlist> hotlistList = hotlistRepository.findAll();
        assertThat(hotlistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHotlists() throws Exception {
        // Initialize the database
        hotlistRepository.saveAndFlush(hotlist);

        // Get all the hotlistList
        restHotlistMockMvc.perform(get("/api/hotlists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotlist.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].dbKey").value(hasItem(DEFAULT_DB_KEY.toString())));
    }

    @Test
    @Transactional
    public void getHotlist() throws Exception {
        // Initialize the database
        hotlistRepository.saveAndFlush(hotlist);

        // Get the hotlist
        restHotlistMockMvc.perform(get("/api/hotlists/{id}", hotlist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hotlist.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.dbKey").value(DEFAULT_DB_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHotlist() throws Exception {
        // Get the hotlist
        restHotlistMockMvc.perform(get("/api/hotlists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotlist() throws Exception {
        // Initialize the database
        hotlistRepository.saveAndFlush(hotlist);
        int databaseSizeBeforeUpdate = hotlistRepository.findAll().size();

        // Update the hotlist
        Hotlist updatedHotlist = hotlistRepository.findOne(hotlist.getId());
        updatedHotlist
            .value(UPDATED_VALUE)
            .dbKey(UPDATED_DB_KEY);

        restHotlistMockMvc.perform(put("/api/hotlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotlist)))
            .andExpect(status().isOk());

        // Validate the Hotlist in the database
        List<Hotlist> hotlistList = hotlistRepository.findAll();
        assertThat(hotlistList).hasSize(databaseSizeBeforeUpdate);
        Hotlist testHotlist = hotlistList.get(hotlistList.size() - 1);
        assertThat(testHotlist.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testHotlist.getDbKey()).isEqualTo(UPDATED_DB_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingHotlist() throws Exception {
        int databaseSizeBeforeUpdate = hotlistRepository.findAll().size();

        // Create the Hotlist

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHotlistMockMvc.perform(put("/api/hotlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotlist)))
            .andExpect(status().isCreated());

        // Validate the Hotlist in the database
        List<Hotlist> hotlistList = hotlistRepository.findAll();
        assertThat(hotlistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHotlist() throws Exception {
        // Initialize the database
        hotlistRepository.saveAndFlush(hotlist);
        int databaseSizeBeforeDelete = hotlistRepository.findAll().size();

        // Get the hotlist
        restHotlistMockMvc.perform(delete("/api/hotlists/{id}", hotlist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hotlist> hotlistList = hotlistRepository.findAll();
        assertThat(hotlistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hotlist.class);
        Hotlist hotlist1 = new Hotlist();
        hotlist1.setId(1L);
        Hotlist hotlist2 = new Hotlist();
        hotlist2.setId(hotlist1.getId());
        assertThat(hotlist1).isEqualTo(hotlist2);
        hotlist2.setId(2L);
        assertThat(hotlist1).isNotEqualTo(hotlist2);
        hotlist1.setId(null);
        assertThat(hotlist1).isNotEqualTo(hotlist2);
    }
}
