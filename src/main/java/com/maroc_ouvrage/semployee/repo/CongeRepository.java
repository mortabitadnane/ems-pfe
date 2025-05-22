package com.maroc_ouvrage.semployee.repo;
import com.maroc_ouvrage.semployee.model.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CongeRepository extends JpaRepository<Conge, Long> {
    Optional<Conge> findByEmployeeCin(String employeeCin);

    List<Conge> findByEmployeeCinOrderByStartDateDesc(String employeeCin);
}

