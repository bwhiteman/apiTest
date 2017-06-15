package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HotlistRefCode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HotlistRefCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotlistRefCodeRepository extends JpaRepository<HotlistRefCode,Long> {
    
}
