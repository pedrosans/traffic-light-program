package tcc;

import java.io.File;

import tcc.ambiente.Ambiente;
import tcc.model.NetFile;
import tcc.model.TLLogic;
import tcc.rotinas.Simulador;
import tcc.rotinas.output.StandartDevrivation;

public class AmbienteTeste1 extends Ambiente {
    public static String path_teste_1 = "C:\\TCC\\cenarios\\teste-1\\";

    public AmbienteTeste1() {
        this.qt_genotipos = 3;
        this.planos.add(TLLogic.plan("30:G;3:y;30:r"));
        this.planos.add(TLLogic.plan("15:G;3:y;15:r"));
        this.genes_plano = new int[] {0, 1};
        int range = 63;
        this.genes_delay = new int[range];
        for (int i = 0; i < genes_delay.length; i++) {
            genes_delay[i] = i;
        }

        NetFile netFile = new NetFile();
        netFile.load(new File(AmbienteTeste1.path_teste_1 + "example.net.xml"));

        // Simulador simulador = new Simulador(new PerfornanceOutput());
        Simulador simulador = new Simulador(new StandartDevrivation());
        simulador.setNetFile(netFile);
        simulador.setSumoConfigFile(new File(AmbienteTeste1.path_teste_1 + "example.sumo.cfg"));

        setSimulador(simulador);
    }
}
