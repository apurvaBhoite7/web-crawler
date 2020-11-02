package org.web.crawler.implement;

import java.util.LinkedHashSet;
import java.util.Set;

public class TreeRepresentation {

	private final String data;
	private TreeRepresentation parent;
	private final Set<TreeRepresentation> children = new LinkedHashSet<>();

	public TreeRepresentation(String data) {
		this.data = data;
	}

	public TreeRepresentation addChild(String child) {
		TreeRepresentation childNode = new TreeRepresentation(child);
		childNode.parent = this;
		this.children.add(childNode);
		return childNode;
	}

	public String toOutput(int offset) {
		StringBuilder sb = new StringBuilder();
		if (this.children.isEmpty()) {
			sb.append(data);
			sb.append("\n");
		}
		if (!children.isEmpty()) {
			sb.append(this.data).append("\n");
			for (TreeRepresentation child : children) {
				for (int i = 0; i <= offset; i++) {
					sb.append("  ");
				}
				sb.append("- ");
				sb.append(child.toOutput(offset + 1));
			}
		}
		return sb.toString();
	}
}
