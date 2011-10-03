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

import java.util.ArrayList;
import java.util.List;

import tcc.model.TLLogic.Phase;

public class PlanoSemaforico {

    private List<TLLogic.Phase> fases = new ArrayList<TLLogic.Phase>();

    public List<TLLogic.Phase> getFases() {
        return fases;
    }

    /**
     * 30:G;5:y;30:r
     * 
     * @param string
     * @return pharse list
     */
    public static PlanoSemaforico plan(String plan) {
        PlanoSemaforico planoSemaforico = new PlanoSemaforico();
        String[] pharses = plan.split(";");
        for (String pharseInput : pharses) {
            String[] parseArgs = pharseInput.split(":");
            int duration = new Integer(parseArgs[0].trim());
            String state = parseArgs[1].trim();
            Phase pharse = new Phase();
            pharse.setDuration(duration);
            pharse.setState(state);
            planoSemaforico.fases.add(pharse);
        }
        return planoSemaforico;
    }

}
