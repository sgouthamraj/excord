package com.deem.excord.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.deem.excord.domain.EcTestcase;

public interface SpringSearchRepository extends CrudRepository<EcTestcase, Long>  {

	public EcTestcase findById(Long id);
	
	public List<EcTestcase> findAllByEnabled(boolean enabled);
	
	public List<EcTestcase> findAllByAutomated(boolean automated);
	
	public List<EcTestcase> findAllByPriority(String priority);
	
	public List<EcTestcase> findAllByProduct(String product);
	
	public List<EcTestcase> findAllByFeature(String feature);
	
	public List<EcTestcase> findAllByCaseType(String caseType);
	
}
