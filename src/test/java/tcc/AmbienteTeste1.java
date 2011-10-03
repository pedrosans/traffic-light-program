package tcc;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import tcc.ambiente.Ambiente;
import tcc.model.NetFile;
import tcc.model.PlanoSemaforico;
import tcc.rotinas.Simulador;
import tcc.rotinas.output.StatisticalOutput;

public class AmbienteTeste1 extends Ambiente {
    public static String path_teste_1 = "C:\\TCC\\cenarios\\teste-1\\";

    private static final PlanoSemaforico PLANO_1 = PlanoSemaforico.plan("30:G;3:y;30:r");
    private static final PlanoSemaforico PLANO_2 = PlanoSemaforico.plan("15:G;3:y;15:r");

    public static final List<PlanoSemaforico> PLANOS = Arrays.asList(PLANO_1, PLANO_2);

    private int[][] genes_plano;

    public AmbienteTeste1() {
        this.qt_genotipos = 3;
        this.genes_plano = new int[][] { {0, 1}, {0, 1}, {0, 1}};
        int range = 63;
        this.genes_delay = new int[range];
        for (int i = 0; i < genes_delay.length; i++) {
            genes_delay[i] = i;
        }

        NetFile netFile = new NetFile();
        netFile.load(new File(AmbienteTeste1.path_teste_1 + "example.net.xml"));

        // Simulador simulador = new Simulador(new PerfornanceOutput());
        Simulador simulador = new Simulador(new StatisticalOutput());
        simulador.setNetFile(netFile);
        simulador.setSumoConfigFile(new File(AmbienteTeste1.path_teste_1 + "example.sumo.cfg"));

        setSimulador(simulador);
    }

    @Override
    public PlanoSemaforico getPlanoSemaforico(int gene_plano) {
        return PLANOS.get(gene_plano);
    }

    @Override
    public int[] getGenesPlano(int locus) {
        return genes_plano[locus];
    }
}
