package DB40;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.query.Constraint;
import com.db4o.query.Query;
import componentes.animales.Heroes;
import componentes.personas.Condecorados;
import componentes.personas.General;
import componentes.personas.GeneralTOPSCORE;

import java.util.List;

public class BaseDatos40 {

    public static ObjectContainer HeroesyGenerales;

    public BaseDatos40(String nombreArchivo) {
        this.HeroesyGenerales = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), nombreArchivo);
    }

    public static void guardarGenganador(GeneralTOPSCORE genganador) {
        HeroesyGenerales.store(genganador);
    }

    public static void cerrarConexion() {
        HeroesyGenerales.close();
    }

    public void cargarCondecorados(List<Condecorados> condecoradosList) {
        try {
            for (Condecorados condecorados : condecoradosList) {
                HeroesyGenerales.store(condecorados);
            }
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarBaseDeDatos() {
        try {
            ObjectSet<Condecorados> resultados = HeroesyGenerales.query(Condecorados.class);
            while (resultados.hasNext()) {
                Condecorados condecorados = resultados.next();
                HeroesyGenerales.delete(condecorados);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectSet<GeneralTOPSCORE> resultados = HeroesyGenerales.query(GeneralTOPSCORE.class);
            while (resultados.hasNext()) {
                GeneralTOPSCORE genetop = resultados.next();
                HeroesyGenerales.delete(genetop);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectSet<General> resultados = HeroesyGenerales.query(General.class);
            while (resultados.hasNext()) {
                General general = resultados.next();
                HeroesyGenerales.delete(general);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectSet<Heroes> resultados = HeroesyGenerales.query(Heroes.class);
            while (resultados.hasNext()) {
                Heroes heroes = resultados.next();
                HeroesyGenerales.delete(heroes);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  void verBaseDeDatos() {
        try {
            ObjectSet<Condecorados> resultados = HeroesyGenerales.queryByExample(new Condecorados(null, null, null, null, null));
            while (resultados.hasNext()) {
                System.out.println(resultados.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Query consulta = HeroesyGenerales.query();
            consulta.constrain(General.class);
            consulta.descend("nombre");
            ObjectSet<General> res = consulta.execute();
            while (res.hasNext()) {
                System.out.println(res.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Query consulta = HeroesyGenerales.query();
            consulta.constrain(Heroes.class);
            consulta.descend("nombre");
            ObjectSet<Heroes> res = consulta.execute();
            while (res.hasNext()) {
                System.out.println(res.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Query consulta = HeroesyGenerales.query();
            consulta.constrain(GeneralTOPSCORE.class);
            consulta.descend("salud").orderAscending();
            ObjectSet<GeneralTOPSCORE> res = consulta.execute();
            while (res.hasNext()) {
                System.out.println(res.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static   void vergeneralganador(){
        try {
            Query consulta = HeroesyGenerales.query();
            consulta.constrain(GeneralTOPSCORE.class);
            consulta.descend("salud").orderDescending();
            ObjectSet<GeneralTOPSCORE> res = consulta.execute();
            if(res.isEmpty() ){
                System.out.println("No has jugado ninguna partida");
            }
            while (res.hasNext()) {
                System.out.println(res.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean baseDeDatosVacia() {
        return HeroesyGenerales.queryByExample(Condecorados.class).isEmpty();
    }

    public void realizarConsulta() {
        Query consulta1 = HeroesyGenerales.query();
        consulta1.constrain(Condecorados.class);
        consulta1.descend("officerOrEnlistedIndividual").constrain("ENLISTED");
        ObjectSet<Condecorados> res1 = consulta1.execute();

        while (res1.hasNext()) {
            Condecorados condecorado = res1.next();
            Heroes heroes = new Heroes();
            heroes.setNombre(condecorado.getFirstName() + " " + condecorado.getLastName());
            HeroesyGenerales.store(heroes);

        }
    }


    public void realizarConsultaGeneral() {
        try {

            Query consulta1 = HeroesyGenerales.query();
            consulta1.constrain(Condecorados.class);
            consulta1.descend("nameOfApprovedAward").constrain("MEDAL OF HONOR");
            ObjectSet<Condecorados> res1 = consulta1.execute();


            Query consulta2 = HeroesyGenerales.query();
            consulta2.constrain(Condecorados.class);
            Constraint restriccion2 = consulta2.descend("nameOfApprovedAward").constrain("BRONZE STAR MEDAL");
            consulta2.descend("typeOfActionCommendedByOriginator").constrain("HEROIC").and(restriccion2);
            ObjectSet<Condecorados> res2 = consulta2.execute();


            while (res1.hasNext()) {
                Condecorados condecorado = res1.next();
                General general = new General();
                general.setNombre(condecorado.getFirstName() + " " + condecorado.getLastName());
                HeroesyGenerales.store(general);
            }


            while (res2.hasNext()) {
                Condecorados condecorado = res2.next();
                General general = new General();
                general.setNombre(condecorado.getFirstName() + " " + condecorado.getLastName());
                HeroesyGenerales.store(general);
            }
        } catch (DatabaseFileLockedException e) {
            System.err.println("Database file is locked: " + e.getMessage());
        }
    }

}
