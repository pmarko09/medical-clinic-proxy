package com.pmarko09.medical_clinic_proxy.repository;

import com.pmarko09.medical_clinic_proxy.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);
}
