/*    
 * traffic-light-program uses evolutionary computing to control traffic lights
 * Copyright (C) 2011  Pedro Henrique Oliveira dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tcc.model;

import java.io.File;
import java.util.List;

import tcc.ambiente.Phenotype;
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
    public void program(List<Phenotype> fenotipos) {
        for (int i = 0; i < fenotipos.size(); i++) {
            TLLogic tlLogic = programableNet.getTlLogics().get(i);
            Phenotype fenotipo = fenotipos.get(i);
            tlLogic.setPhases(fenotipo.planoSemaforico.getPhases());
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
