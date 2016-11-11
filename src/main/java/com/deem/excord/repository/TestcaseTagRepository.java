package com.deem.excord.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.deem.excord.domain.EcTag;
import com.deem.excord.domain.EcTestcase;
import com.deem.excord.domain.EcTestcaseTagMapping;

public interface TestcaseTagRepository extends CrudRepository<EcTestcaseTagMapping, Long> {

	public EcTestcaseTagMapping findByTestcaseAndTag(EcTestcase testcase, EcTag tag);
	
	public List<EcTestcaseTagMapping> findAllByTestcase(EcTestcase testcase);
	
	public List<EcTestcaseTagMapping> findAllByTag(EcTag tag);
	
    @Transactional
    public Integer deleteByTestcase(EcTestcase testcase);
	
}
