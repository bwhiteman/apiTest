package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.HotlistRefCode;

import com.mycompany.myapp.repository.HotlistRefCodeRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HotlistRefCode.
 */
@RestController
@RequestMapping("/api")
public class HotlistRefCodeResource {

    private final Logger log = LoggerFactory.getLogger(HotlistRefCodeResource.class);

    private static final String ENTITY_NAME = "hotlistRefCode";

    private final HotlistRefCodeRepository hotlistRefCodeRepository;

    public HotlistRefCodeResource(HotlistRefCodeRepository hotlistRefCodeRepository) {
        this.hotlistRefCodeRepository = hotlistRefCodeRepository;
    }

    /**
     * POST  /hotlist-ref-codes : Create a new hotlistRefCode.
     *
     * @param hotlistRefCode the hotlistRefCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hotlistRefCode, or with status 400 (Bad Request) if the hotlistRefCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hotlist-ref-codes")
    @Timed
    public ResponseEntity<HotlistRefCode> createHotlistRefCode(@Valid @RequestBody HotlistRefCode hotlistRefCode) throws URISyntaxException {
        log.debug("REST request to save HotlistRefCode : {}", hotlistRefCode);
        if (hotlistRefCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hotlistRefCode cannot already have an ID")).body(null);
        }
        HotlistRefCode result = hotlistRefCodeRepository.save(hotlistRefCode);
        return ResponseEntity.created(new URI("/api/hotlist-ref-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hotlist-ref-codes : Updates an existing hotlistRefCode.
     *
     * @param hotlistRefCode the hotlistRefCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hotlistRefCode,
     * or with status 400 (Bad Request) if the hotlistRefCode is not valid,
     * or with status 500 (Internal Server Error) if the hotlistRefCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hotlist-ref-codes")
    @Timed
    public ResponseEntity<HotlistRefCode> updateHotlistRefCode(@Valid @RequestBody HotlistRefCode hotlistRefCode) throws URISyntaxException {
        log.debug("REST request to update HotlistRefCode : {}", hotlistRefCode);
        if (hotlistRefCode.getId() == null) {
            return createHotlistRefCode(hotlistRefCode);
        }
        HotlistRefCode result = hotlistRefCodeRepository.save(hotlistRefCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hotlistRefCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hotlist-ref-codes : get all the hotlistRefCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hotlistRefCodes in body
     */
    @GetMapping("/hotlist-ref-codes")
    @Timed
    public List<HotlistRefCode> getAllHotlistRefCodes() {
        log.debug("REST request to get all HotlistRefCodes");
        return hotlistRefCodeRepository.findAll();
    }

    /**
     * GET  /hotlist-ref-codes/:id : get the "id" hotlistRefCode.
     *
     * @param id the id of the hotlistRefCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hotlistRefCode, or with status 404 (Not Found)
     */
    @GetMapping("/hotlist-ref-codes/{id}")
    @Timed
    public ResponseEntity<HotlistRefCode> getHotlistRefCode(@PathVariable Long id) {
        log.debug("REST request to get HotlistRefCode : {}", id);
        HotlistRefCode hotlistRefCode = hotlistRefCodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hotlistRefCode));
    }

    /**
     * DELETE  /hotlist-ref-codes/:id : delete the "id" hotlistRefCode.
     *
     * @param id the id of the hotlistRefCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hotlist-ref-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteHotlistRefCode(@PathVariable Long id) {
        log.debug("REST request to delete HotlistRefCode : {}", id);
        hotlistRefCodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
