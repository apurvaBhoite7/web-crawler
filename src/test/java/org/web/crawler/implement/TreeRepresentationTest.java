package org.web.crawler.implement;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TreeRepresentationTest {

	private static final String TWO_SUBLEVEL_SIMPLE = "foobar.com/baz\n  - bar/\n    - xyz\n    - abc\n  - mno\n    - about\n";
	private static final String EMPTY_CHILD = "  - contacts\n";
	private static final String HAS_MORE_LINKS = "    - has\n    - more links\n    - more links\n    - more links\n";

	@Test
	public void testTreeRepresentationHasMoreLinks() {

		TreeRepresentation root = new TreeRepresentation("foobar.com/baz");
		TreeRepresentation bar = root.addChild("bar/");
		TreeRepresentation mno = root.addChild("mno");
		TreeRepresentation contacts = root.addChild("contacts");
		bar.addChild("xyz");
		bar.addChild("abc");
		mno.addChild("about");

		contacts.addChild("has");
		contacts.addChild("more links");
		contacts.addChild("more links");
		contacts.addChild("more links");

		String actual = root.toOutput(0);
		String expected = TWO_SUBLEVEL_SIMPLE + EMPTY_CHILD + HAS_MORE_LINKS;
		assertEquals(expected, actual);

	}

	@Test
	public void testTreeRepresentationEmptyChild() {

		TreeRepresentation root = new TreeRepresentation("foobar.com/baz");
		TreeRepresentation bar = root.addChild("bar/");
		TreeRepresentation mno = root.addChild("mno");
		root.addChild("contacts");
		bar.addChild("xyz");
		bar.addChild("abc");
		mno.addChild("about");

		String actual = root.toOutput(0);
		String expected = TWO_SUBLEVEL_SIMPLE + EMPTY_CHILD;
		assertEquals(expected, actual);

	}

	@Test
	public void testTreeRepresentationToString() {

		TreeRepresentation root = new TreeRepresentation("foobar.com/baz");
		TreeRepresentation bar = root.addChild("bar/");
		bar.addChild("xyz");
		bar.addChild("abc");
		TreeRepresentation mno = root.addChild("mno");
		mno.addChild("about");

		String actual = root.toOutput(0);
		String expected = TWO_SUBLEVEL_SIMPLE;
		assertEquals(expected, actual);

	}
}
