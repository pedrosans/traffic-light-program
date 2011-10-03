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
    Net programableNet;
    String firstConfigSlice;
    String finalConfigSlice;

    public void load(File file) {
        this.file = file;
        loadedNet = Net.load(file);

        programableNet = new Net();
        for (TLLogic tlLogic : loadedNet.getTlLogics()) {
            programableNet.getTlLogics().add(tlLogic.clone());
        }
        String content = IOUtil.getFileString(file);
        load(content);
    }

    String originalTL;
    int tl_bytes;

    public void load(String content) {
        this.content = content;
        tl_start_index = content.indexOf("<tl-logic");
        int possible_end = content.indexOf("</tl-logic>");
        while (possible_end != -1) {
            tl_end_index = possible_end;
            possible_end = content.indexOf(FINAL_TAG, possible_end + FINAL_TAG.length());
        }
        tl_end_index = tl_end_index + FINAL_TAG.length();
        tl_end_index = content.indexOf("<junction");
        originalTL = content.substring(tl_start_index, tl_end_index);
        tl_bytes = originalTL.getBytes().length;
    }

    public Net getLoadedNet() {
        return loadedNet;
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
        for (int i = 0; i < fenotipos.size(); i++) {
            TLLogic tlLogic = programableNet.getTlLogics().get(i);
            Fenotipo fenotipo = fenotipos.get(i);
            tlLogic.setPhases(fenotipo.planoSemaforico.getFases());
            tlLogic.setOffset(fenotipo.delay);
        }
        StringBuilder sb = new StringBuilder();
        programableNet.toXML(sb);
        String newLogic = sb.toString();
        newLogic = newLogic.replace("<net>", "").replace("</net>", "").trim();

        String newNet = getFirstConfigSlice() + newLogic.trim() + getFinalConfigSlice();
        IOUtil.writeTo(file, newNet);
    }
}
