package com.deem.excord.listeners;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BuildSearchIndex implements ApplicationListener<ApplicationReadyEvent> {

	@PersistenceContext
	private EntityManager entityManager;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildSearchIndex.class);

	/**
	 * Create an initial Lucene index for the data already present in the
	 * database. This method is called when Spring's startup.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			LOGGER.error("An error occurred trying to build the serach index: " + e.toString());
		}
		return;
	}

}