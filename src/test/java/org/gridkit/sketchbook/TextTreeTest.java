package org.gridkit.sketchbook;

import static org.gridkit.sketchbook.TextTree.t;

import org.junit.Test;

public class TextTreeTest {

	@Test
	public void simple_tree() {
		TextTree tree1 = t("a", t("b1", t("X")), t("b2", t("Y"), t("Z")), t("alpha", t("betta", t("gamma"))));
		TextTree tree2 = t("a", t("b1", t("X")), t("b2", t("Y"), t("Z")), t("alpha", t("betta", t("gamma"))));

		System.out.println(tree1.printAsTree());
		System.out.println();
		System.out.println(t("", tree1, tree2).printAsTree());
		System.out.println();
		System.out.println(t("<>", tree1, tree2).printAsTree());
	}

}
