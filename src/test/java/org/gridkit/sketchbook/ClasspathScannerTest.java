package org.gridkit.sketchbook;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

public class ClasspathScannerTest {

	@Test
	public void scan_myself() throws IOException {
		List<String> files = ClasspathScanner.findFiles("org/gridkit/sketchbook");
		Assert.assertTrue(files.contains(getClass().getName().replace('.', '/') + ".class"));
	}

	@Test
	public void scan_mixed_package() throws IOException {
		List<String> files = ClasspathScanner.findFiles("org/junit");
		Assert.assertTrue(files.contains(Assert.class.getName().replace('.', '/') + ".class"));
		Assert.assertTrue(files.contains(RunWith.class.getName().replace('.', '/') + ".class"));
	}
	
}
