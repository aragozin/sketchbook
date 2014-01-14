package org.gridkit.sketchbook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClasspathScanner {

	/**
	 * Returns a list of classpath entries under certain path. May contain duplicates if classpath has multiple URLs for certain path. 
	 */
	public static List<String> findFiles(String path) throws IOException {
		return findFiles(path, Thread.currentThread().getContextClassLoader());
	}

	/**
	 * Returns a list of classpath entries under certain path. May contain duplicates if classpath has multiple URLs for certain path. 
	 */
	public static List<String> findFiles(String path, ClassLoader cl) throws IOException {
		List<String> result = new ArrayList<String>();
		Enumeration<URL> en = cl.getResources(path);
		while(en.hasMoreElements()) {
			URL u = en.nextElement();
			listFiles(result, u, path);
		}
		return result;
	}
	
	static List<String> listFiles(List<String> results, URL packageURL, String path) throws IOException {

	    if(packageURL.getProtocol().equals("jar")){
	        String jarFileName;
	        JarFile jf ;
	        Enumeration<JarEntry> jarEntries;
	        String entryName;

	        // build jar file name, then loop through zipped entries
	        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
	        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
	        jf = new JarFile(jarFileName);
	        jarEntries = jf.entries();
	        while(jarEntries.hasMoreElements()){
	            entryName = jarEntries.nextElement().getName();
	            if(entryName.startsWith(path)){
	                results.add(entryName);
	            }
	        }

	    // loop through files in classpath
	    }else{
	        File dir = new File(packageURL.getFile());
	        String cp = dir.getCanonicalPath();
	        File root = dir;
	        while(true) {
	        	if (cp.equals(new File(root, path).getCanonicalPath())) {
	        		break;
	        	}
	        	root = root.getParentFile();
	        }
	        listFiles(results, root, dir);
	    }
	    return results;
	}

	static void listFiles(List<String> names, File root, File dir) {
		String rootPath = root.getAbsolutePath(); 
		if (dir.exists() && dir.isDirectory()) {
			for(File file: dir.listFiles()) {
				if (file.isDirectory()) {
					listFiles(names, root, file);
				}
				else {
					String name = file.getAbsolutePath().substring(rootPath.length() + 1);
					name = name.replace('\\', '/');
					names.add(name);
				}
			}
		}
	}	
}
