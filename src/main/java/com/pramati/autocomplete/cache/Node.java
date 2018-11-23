package com.pramati.autocomplete.cache;

import java.util.HashMap;
import java.util.Map;

public class Node {
	
	public String prefix;
	public Map<Character, Node> children;
	public boolean isWord;
	
	public Node(String prefix){
		this.prefix = prefix;
		this.children = new HashMap<>();
	}
}
