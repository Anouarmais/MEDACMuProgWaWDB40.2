/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package medac_programacionbatalla;

import DB40.BaseDatos40;
import DB40.VolcarInfo;
import batallas.Batalla;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import componentes.personas.Condecorados;

import java.util.List;

import static DB40.BaseDatos40.*;

/**
 * @author danie
 */
public class MEDAC_ProgramacionBatalla {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VolcarInfo s = new VolcarInfo();
        BaseDatos40 ss = new BaseDatos40("HeroesYGenerales");
   //     ss.verBaseDeDatos();
 if (ss.baseDeDatosVacia()) {

            List<Condecorados> condecoradosList = s.leerArchivo();
            ss.cargarCondecorados(condecoradosList);
            ss.realizarConsultaGeneral();
            ss.realizarConsulta();
        }

        Batalla batalla = new Batalla();
     //   ss.verBaseDeDatos();
      cerrarConexion();
    }

}
    

 
