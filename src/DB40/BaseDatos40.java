package DB40;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import componentes.personas.Condecorados;
import componentes.personas.General;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseDatos40 {

    static ObjectContainer HeroesyGenerales;
    public BaseDatos40() {

        HeroesyGenerales = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration() , "HeoresYGenerales");
    }
    public static void cerrarConeccion(){
        HeroesyGenerales.close();

    }
    public void Cargarcondyheroes(List<Condecorados> condecoradosList){
        try{
            for(Condecorados condecorados : condecoradosList){
                HeroesyGenerales.store(condecorados);
            }

        } catch (DatabaseClosedException e) {
            throw new RuntimeException(e);
        } catch (DatabaseReadOnlyException e) {
            throw new RuntimeException(e);
        }

    }
    public void borrarBBDD(){
        try {

            ObjectSet<Condecorados> resultados = HeroesyGenerales.query(Condecorados.class);

            while (resultados.hasNext()) {
                Condecorados condecorados = resultados.next();
                HeroesyGenerales.delete(condecorados);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void verBBDD() {
        try {
            Condecorados consulta = new Condecorados(null, null, null, null, null);


            ObjectSet<Condecorados> resultados = HeroesyGenerales.queryByExample(consulta);

            while (resultados.hasNext()) {
                Condecorados condecorados = resultados.next();
                System.out.println(condecorados);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List <General> generalesquery = new ArrayList<>();
    public static List<General> realizarConsulta(Condecorados conde) {
        List<General> generalesquery = new ArrayList<>(); // Lista local para almacenar los generales de esta consulta

        try {
            ObjectSet<Condecorados> condes = HeroesyGenerales.queryByExample(conde);

            while (condes.hasNext()) {
                Condecorados condecorado = condes.next();
                General general = new General();
                general.setNombre(condecorado.getFirstName() + " " + condecorado.getLastName());

                // System.out.println(general);

                generalesquery.add(general); // Agregar el general a la lista local
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return generalesquery; // Devolver la lista local
    }


}

