package com.tongzhu.kittypay.orchestrator.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RequestRepository extends JpaRepository<Request, String> {
}
