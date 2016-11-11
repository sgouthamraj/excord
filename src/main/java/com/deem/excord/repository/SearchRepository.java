package com.deem.excord.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deem.excord.domain.EcTag;
import com.deem.excord.domain.EcTestcase;
import com.deem.excord.domain.EcTestcaseTagMapping;
import com.deem.excord.domain.EcTestfolder;

@Repository
public class SearchRepository {

	@Autowired
	SpringSearchRepository springSearchDao;

	@Autowired
	CustomSearchRepository customSearchDao;

	@Autowired
	TestFolderRepository testFolderDao;
	
	@Autowired
	TagRepository tagDao;
	
	@Autowired
	TestcaseTagRepository testcaseTagDao;

	public List<EcTestcase> search(String queryString) {
		List<String> query = tokenizeQuery(queryString);
		List<String> postfixQuery = createPostfixQuery(query);
		return executePostFixQuery(postfixQuery);
	}

	public List<String> tokenizeQuery(String query) {
		char[] charArray = query.toCharArray();
		List<String> tokenizeQuery = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for(char chr : charArray) {
			if(chr == '(' || chr == ')' || chr == '&' || chr =='|' ) {
				tokenizeQuery.add(sb.toString().trim());
				tokenizeQuery.add("" + chr);
				sb = new StringBuilder();
			} else {
				sb.append(chr);
			}
		}
		tokenizeQuery.add(sb.toString().trim());
		return tokenizeQuery;
	}

	public List<String> createPostfixQuery(List<String> query) {
		Map<String, Integer> precision = new HashMap<>();
		List<String> postfixQuery = new ArrayList<>();
		Stack<String> stack = new Stack<>();
		precision.put("(", 1);
		precision.put("&", 2);
		precision.put("|", 3);
		for(String item : query) {
			if(!precision.containsKey(item)) {
				postfixQuery.add(item);
			} else if(item.equals("(")) {
				stack.push(item);
			} else if(item.equals(")")) {
				String peek = stack.pop();
				while(!peek.equals("(")) {
					postfixQuery.add(peek);
					peek = stack.pop();
				}
			} else {
				while(!stack.isEmpty() && precision.get(stack.peek()) >= precision.get(item)) {
					postfixQuery.add(stack.pop());
				}
				stack.push(item);
			}
		}
		while(!stack.isEmpty()) {
			postfixQuery.add(stack.pop());
		}
		return postfixQuery;
	}

	@SuppressWarnings("unchecked")
	public List<EcTestcase> executePostFixQuery(List<String> postfixQuery) {
		Stack<List<EcTestcase>> stack = new Stack<>();
		for(String item : postfixQuery) {
			if(!(item.equals("&") || item.equals("|"))) {
				stack.push(executeSearch(item));
			} else {
				if(item.equals("&")) {
					stack.push((List<EcTestcase>) CollectionUtils.intersection(stack.pop(), stack.pop()));
				} else {
					stack.push((List<EcTestcase>) CollectionUtils.union(stack.pop(), stack.pop()));
				}
			}
		}
		List<EcTestcase> returnVal = stack.pop();
		return returnVal;
	}

	public List<EcTestcase> executeSearch(String query) {
		List<EcTestcase> result = new ArrayList<>();
		if (query.contains(":")) {
			int index = query.indexOf(":");
			String key = query.substring(0, query.indexOf(":"));
			String searchFor = query.substring(index + 1);
			result.addAll(executeSearch(key.toLowerCase(), searchFor));
		} else {
			if (query.matches("^\\d+$")) {
				EcTestcase tc = springSearchDao.findById(Long.parseLong(query));
				if (tc != null)
					result.add(tc);
			}
			result.addAll(customSearchDao.searchQuery(query));
		}
		return result;
	}

	public List<EcTestcase> executeSearch(String key, String searchFor) {
		List<EcTestcase> result = new ArrayList<>();
		switch (key) {
		case "name":
			result.addAll(customSearchDao.searchQuery(searchFor, new String[] { "name" }));
			break;
		case "description":
			result.addAll(customSearchDao.searchQuery(searchFor, new String[] { "description" }));
			break;
		case "enabled":
			result.addAll(springSearchDao.findAllByEnabled(searchFor.toLowerCase().equals("yes")));
			break;
		case "automated":
			result.addAll(springSearchDao.findAllByAutomated(searchFor.toLowerCase().equals("yes")));
			break;
		case "folder":
			List<EcTestfolder> folders = testFolderDao.findAllByNameContaining(searchFor);
			for (EcTestfolder folder : folders) {
				result.addAll(folder.getEcTestcaseList());
			}
			break;
		case "priority":
			result.addAll(springSearchDao.findAllByPriority(searchFor));
			break;
		case "product":
			result.addAll(springSearchDao.findAllByProduct(searchFor));
			break;
		case "feature":
			result.addAll(springSearchDao.findAllByFeature(searchFor));
			break;
		case "type":
			result.addAll(springSearchDao.findAllByCaseType(searchFor));
			break;
		case "tag":
			EcTag tagObj = tagDao.findByTag(searchFor);
			if(tagObj != null) {
				List<EcTestcaseTagMapping> list = testcaseTagDao.findAllByTag(tagObj);
				for (EcTestcaseTagMapping element : list) {
					result.add(element.getTestcaseId());
				}
			}
			break;
		}
		return result;
	}
}
