package tcc;

import java.io.File;

import tcc.ambiente.Ambiente;
import tcc.model.NetFile;
import tcc.model.TLLogic;
import tcc.rotinas.Simulador;
import tcc.rotinas.output.PerfornanceOutput;

public class AmbienteTeste1 extends Ambiente {
    public static String path_teste_1 = "C:\\TCC\\cenarios\\teste-1\\";

    public AmbienteTeste1() {
        this.qt_genotipos = 3;
        this.planos.add(TLLogic.plan("30:G;3:y;30:r"));
        this.planos.add(TLLogic.plan("15:G;3:y;15:r"));
        this.genes_plano = new int[] {0, 1};
        this.genes_delay = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};

        NetFile netFile = new NetFile();
        netFile.load(new File(AmbienteTeste1.path_teste_1 + "example.net.xml"));

        Simulador simulador = new Simulador(new PerfornanceOutput());
        simulador.setNetFile(netFile);
        simulador.setSumoConfigFile(new File(AmbienteTeste1.path_teste_1 + "example.sumo.cfg"));

        setSimulador(simulador);
    }
}
