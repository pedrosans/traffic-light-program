package tcc.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOUtil {
	public static String getFileString(File file) {
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return contents.toString();
	}

	public static void writeTo(File file, String xml) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(xml);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
