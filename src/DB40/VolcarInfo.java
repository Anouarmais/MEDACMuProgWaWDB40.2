package DB40;

import componentes.personas.Condecorados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VolcarInfo {

    public List<Condecorados> leerArchivo() {
        List<Condecorados> condecoradosList = new ArrayList<>();
        String archivoQueLeer = "Heroes.csv";
        String linea = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivoQueLeer));
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                Condecorados condecorado = new Condecorados(valores[0], valores[1], valores[5], valores[13], valores[29]);
                condecoradosList.add(condecorado);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return condecoradosList;
    }
}
