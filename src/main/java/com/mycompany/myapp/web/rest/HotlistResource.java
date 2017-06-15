package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Hotlist;

import com.mycompany.myapp.repository.HotlistRepository;
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
 * REST controller for managing Hotlist.
 */
@RestController
@RequestMapping("/api")
public class HotlistResource {

    private final Logger log = LoggerFactory.getLogger(HotlistResource.class);

    private static final String ENTITY_NAME = "hotlist";

    private final HotlistRepository hotlistRepository;

    public HotlistResource(HotlistRepository hotlistRepository) {
        this.hotlistRepository = hotlistRepository;
    }

    /**
     * POST  /hotlists : Create a new hotlist.
     *
     * @param hotlist the hotlist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hotlist, or with status 400 (Bad Request) if the hotlist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hotlists")
    @Timed
    public ResponseEntity<Hotlist> createHotlist(@Valid @RequestBody Hotlist hotlist) throws URISyntaxException {
        log.debug("REST request to save Hotlist : {}", hotlist);
        if (hotlist.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hotlist cannot already have an ID")).body(null);
        }
        Hotlist result = hotlistRepository.save(hotlist);
        return ResponseEntity.created(new URI("/api/hotlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hotlists : Updates an existing hotlist.
     *
     * @param hotlist the hotlist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hotlist,
     * or with status 400 (Bad Request) if the hotlist is not valid,
     * or with status 500 (Internal Server Error) if the hotlist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hotlists")
    @Timed
    public ResponseEntity<Hotlist> updateHotlist(@Valid @RequestBody Hotlist hotlist) throws URISyntaxException {
        log.debug("REST request to update Hotlist : {}", hotlist);
        if (hotlist.getId() == null) {
            return createHotlist(hotlist);
        }
        Hotlist result = hotlistRepository.save(hotlist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hotlist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hotlists : get all the hotlists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hotlists in body
     */
    @GetMapping("/hotlists")
    @Timed
    public List<Hotlist> getAllHotlists() {
        log.debug("REST request to get all Hotlists");
        return hotlistRepository.findAll();
    }

    /**
     * GET  /hotlists/:id : get the "id" hotlist.
     *
     * @param id the id of the hotlist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hotlist, or with status 404 (Not Found)
     */
    @GetMapping("/hotlists/{id}")
    @Timed
    public ResponseEntity<Hotlist> getHotlist(@PathVariable Long id) {
        log.debug("REST request to get Hotlist : {}", id);
        Hotlist hotlist = hotlistRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hotlist));
    }

    /**
     * DELETE  /hotlists/:id : delete the "id" hotlist.
     *
     * @param id the id of the hotlist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hotlists/{id}")
    @Timed
    public ResponseEntity<Void> deleteHotlist(@PathVariable Long id) {
        log.debug("REST request to delete Hotlist : {}", id);
        hotlistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
