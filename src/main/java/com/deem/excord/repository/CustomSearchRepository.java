package com.deem.excord.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.deem.excord.domain.EcTestcase;

@Repository
@Transactional
public class CustomSearchRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<EcTestcase> searchQuery(String queryString, String[] fields) {

		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(EcTestcase.class).get();

		org.apache.lucene.search.Query query = queryBuilder.keyword()
				.onFields(fields).matching(queryString)
				.createQuery();

		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
				EcTestcase.class);

		List<EcTestcase> results = jpaQuery.getResultList();

		return results;
	}
	
	public List<EcTestcase> searchQuery(String queryString) {
		return searchQuery(queryString, new String[] { "name", "description" });
	}

}
