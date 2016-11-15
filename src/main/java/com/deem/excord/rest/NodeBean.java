package com.deem.excord.rest;

import java.io.Serializable;
import java.util.Map;

public class NodeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public String id;
	public String text;
	public String parent;
	public Map<String, String> a_attr;
}
