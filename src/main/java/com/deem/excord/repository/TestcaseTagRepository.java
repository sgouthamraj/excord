package com.deem.excord.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.deem.excord.domain.EcTag;
import com.deem.excord.domain.EcTestcase;
import com.deem.excord.domain.EcTestcaseTagMapping;

public interface TestcaseTagRepository extends CrudRepository<EcTestcaseTagMapping, Long> {

	public EcTestcaseTagMapping findByTestcaseIdAndTagId(EcTestcase testcaseId, EcTag tagId);
	
	public List<EcTestcaseTagMapping> findAllByTestcaseId(EcTestcase testcaseId);
	
    @Transactional
    public Integer deleteByTestcaseId(EcTestcase testcaseId);
	
}
