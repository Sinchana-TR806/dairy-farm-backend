package com.sridairy.erp.repository;

import com.sridairy.erp.model.Cow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<Cow, Long> {
}
