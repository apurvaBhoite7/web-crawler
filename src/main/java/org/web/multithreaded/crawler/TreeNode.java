package org.web.multithreaded.crawler;

import java.util.LinkedHashSet;
import java.util.Set;

public class TreeNode {

	String data;
	TreeNode parent;
	private final Set<TreeNode> children = new LinkedHashSet<>();


	public TreeNode(String data) {
		this.data = data;
	}

	public TreeNode addChild(String child) {
		TreeNode childNode = new TreeNode(child);
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
			for (TreeNode child : children) {
				for (int i = 0; i <= offset; i++) {
					sb.append("  ");
				}
				sb.append("- ");
				sb.append(child.toOutput(offset + 1));
			}
		}
		return sb.toString();
	}
	
	public String toString() {
		return toOutput(0);
	}

}