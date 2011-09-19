package tcc.model;

import java.io.File;
import java.util.List;

import tcc.ambiente.Fenotipo;
import tcc.util.IOUtil;

public class NetFile {
	private static String FINAL_TAG = "</tl-logic>";
	String content;
	File file;
	int tl_start_index;
	int tl_end_index;
	Net loadedNet;
	String firstConfigSlice;
	String finalConfigSlice;

	public void load(File file) {
		this.file = file;
		loadedNet = Net.load(file);
		String content = IOUtil.getFileString(file);
		load(content);
	}

	public void load(String content) {
		this.content = content;
		tl_start_index = content.indexOf("<tl-logic");
		int possible_end = content.indexOf("</tl-logic>");
		while (possible_end != -1) {
			tl_end_index = possible_end;
			possible_end = content.indexOf(FINAL_TAG, possible_end + FINAL_TAG.length());
		}
		tl_end_index = tl_end_index + FINAL_TAG.length();
	}

	public String getFirstConfigSlice() {
		if (firstConfigSlice == null) {
			firstConfigSlice = this.content.substring(0, tl_start_index);
		}
		return firstConfigSlice;
	}

	public String getFinalConfigSlice() {
		if (finalConfigSlice == null) {
			finalConfigSlice = this.content.substring(tl_end_index);
		}
		return finalConfigSlice;
	}

	/**
	 * Record the new net file to have its path used as parameter for SUDO
	 */
	public void program(List<Fenotipo> fenotipos) {
		Net testNet;
		try {
			testNet = loadedNet.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		for (int i = 0; i < fenotipos.size(); i++) {
			testNet.getTlLogics().get(i).setPhases(fenotipos.get(i).plano);
		}
		String newLogic = XStreamFacade.xStream.toXML(testNet);
		newLogic = newLogic.replace("<net>", "").replace("</net>", "");
		String newNet = getFirstConfigSlice() + newLogic.trim() + getFinalConfigSlice();
		IOUtil.writeTo(file, newNet);
	}

}
