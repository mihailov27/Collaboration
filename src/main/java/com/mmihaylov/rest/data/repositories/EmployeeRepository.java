package com.mmihaylov.rest.data.repositories;

import com.mmihaylov.rest.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Data repository for data access and manipulation of employees entities. */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findOneByEmail(String email);
}
