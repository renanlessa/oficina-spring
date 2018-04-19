package com.lessa.vinhos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessa.vinhos.model.Vinho;

public interface VinhoRepository extends JpaRepository<Vinho, Long> {

}
