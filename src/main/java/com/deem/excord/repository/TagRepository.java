package com.deem.excord.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.deem.excord.domain.EcTag;

public interface TagRepository extends CrudRepository<EcTag, Long> {

	@Query(value = "select case when count(*) > 0 then 'true' else 'false' end from ec_tag where tag = :tag", nativeQuery = true)
	public boolean checkTagExists(@Param("tag") String tag);
	
	public EcTag findByTag(String tag);
	
}