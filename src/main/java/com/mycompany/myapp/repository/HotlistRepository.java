package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Hotlist;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Hotlist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotlistRepository extends JpaRepository<Hotlist,Long> {
    
}
