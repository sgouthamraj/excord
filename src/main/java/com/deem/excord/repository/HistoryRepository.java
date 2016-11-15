package com.deem.excord.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.deem.excord.domain.EcHistory;

public interface HistoryRepository extends CrudRepository<EcHistory, Long> {

    public List<EcHistory> findTop50ByOrderByIdDesc();

    public List<EcHistory> findByChangeSummaryContainingOrSlugIs(String summaryKey, String slug);
}
