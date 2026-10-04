package com.event.eventregistration;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    boolean existsByRollNo(String rollNo);

}
