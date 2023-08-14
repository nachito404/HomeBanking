package com.minhub.homebanking.repositories;

import com.minhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
