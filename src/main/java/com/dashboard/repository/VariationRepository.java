package com.dashboard.repository;

import com.dashboard.domain.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariationRepository  extends JpaRepository<Variation, String> {
}
