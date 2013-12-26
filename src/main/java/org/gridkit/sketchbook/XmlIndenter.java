/**
 * Copyright 2013 Alexey Ragozin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gridkit.sketchbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple code fixing XML indentation.
 * @author Alexey Ragozin (alexey.ragozin@gmail.com)
 */
class XmlIndenter {

	public static String indent(String string, int indent) {
		StringBuilder sb = new StringBuilder();
		
		Pattern regex = Pattern.compile("(\n)|(<[^/>]+>)|(<[/][^/>]+>)|(<[^/>]+[/]>)");
		Matcher m = regex.matcher(string);
		int ci = 0;
		int p = 0;
		while(true) {
			if (m.find(p)) {
				if (m.start() > p) {
					sb.append(string.substring(p, m.start()));
				}				
				if ("\n".equals(m.group())) {
					sb.append("\n");
					for(int i = 0; i < ci * indent; ++i) {
						sb.append(' ');
					}
				}
				else if (m.group().endsWith("/>")) {
					sb.append(m.group());
				}
				else if (m.group().startsWith("</")) {
					--ci;
					// undo indentation before closing tag
					for(int i = 0; i < indent; ++i) {
						if (sb.charAt(sb.length() - 1) == ' ') {
							sb.setLength(sb.length() - 1);
						}
					}
					sb.append(m.group());
				}
				else {
					++ci;
					sb.append(m.group());
				}
				p = m.end();
			}
			else {
				sb.append(string.substring(p, string.length()));
				break;
			}
		}
		
		return sb.toString();
	}
	
}
