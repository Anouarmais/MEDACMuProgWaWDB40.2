/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package batallas;

import DB40.BaseDatos40;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.query.Query;
import componentes.Componentes;
import componentes.animales.Elefante;
import componentes.animales.Heroes;
import componentes.animales.Tigre;
import componentes.personas.Caballeria;
import componentes.personas.General;
import componentes.personas.Infanteria;
import excepciones.animales.MaxAnimalesException;
import excepciones.batallas.*;
import excepciones.personas.GeneralMinimoException;
import excepciones.personas.MaxCapGeneralException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static DB40.BaseDatos40.*;

//import static DB40.BaseDatos40.cerrarConeccion;
//import static DB40.BaseDatos40.realizarConsulta;

/**
 * <p>Clase que representa un ejército.</p>
 *
 * @author Daniel Ojados
 * @author Daniel Romero
 * @version 1.0
 */
public class Ejercito {

    private static final int MAX_PESO = 50;
    private static final int MAX_ANIMALES = 3;
    private List<General> generalesDisponibles = new ArrayList<>();
    private static final int MIN_UNIDADES = 2;
    private static final List<String> nombres = new ArrayList<>();
    private final ArrayList<Componentes> unidades = new ArrayList<>();
    private int contadorAnimales = 0;
    private boolean hayGeneral = false;
    private int ataque;
    private int defensa;
    private int salud;
    private int saldoPeso;
    private String nombre;


    public Ejercito() {
        this.generalesDisponibles = new ArrayList<>();
        inicializarGeneralesDisponibles();
        nombre = "";
        saldoPeso = 0;
        restablecerAtributos();
        menu();
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getSalud() {
        return salud;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSaldoPeso() {
        return saldoPeso;
    }


    private void menu() {
        Scanner scanner = new Scanner(System.in);
        String opcion;

        String[] opciones = {"Crear ID para ejército", "Añadir infantería",
                "Añadir caballería", "Añadir general", "Añadir Heroes",
                "Consultar saldo ejército", "Eliminar unidad", "Confirmar ejercito", "Top socre", "Salir del juego"};


        do {

            char letra = 97; // Letra en código ASCII (a)
            for (String text : opciones) {
                System.out.println((letra) + ". " + text);
                letra++;
            }
            opcion = scanner.nextLine();

            switch (opcion) {
                case "a":

                    if (nombre.isBlank() || nombre.isEmpty()) {
                        System.out.print(System.lineSeparator() + "Asignale un nombre a tu ejército: ");

                        asignarNombre(scanner.nextLine());
                        if (!nombre.isEmpty()) {
                            System.out.println("Nombre del ejército: " + nombre + System.lineSeparator());
                        }
                    } else {
                        System.out.println(System.lineSeparator() + "El ejército ya tiene un nombre asignado.");
                        System.out.println("Nombre del ejército: " + nombre + System.lineSeparator());
                    }

                    break;
                case "b":

                    try {
                        if ((saldoPeso + Infanteria.PESO_INFANTERIA) < MAX_PESO) {

                            adicionarUnidad(new Infanteria());
                            imprimirInfo(unidades.getLast());
                        } else {
                            if (saldoPeso == MAX_PESO) {
                                throw new MaxCapPesoEjercitoException(Message.MAX_CAP_PESO_EJERCITO);
                            } else {
                                System.out.println(Message.UNIDAD_SUPERA_PESO + " " + Message.PESO_DISPONIBLE
                                        + (MAX_PESO - saldoPeso));
                            }
                        }
                    } catch (MaxCapPesoEjercitoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "c":

                    try {
                        if ((saldoPeso + Caballeria.PESO_CABALLERIA) < MAX_PESO) {

                            adicionarUnidad(new Caballeria());
                            imprimirInfo(unidades.getLast());
                        } else {
                            if (saldoPeso == MAX_PESO) {
                                throw new MaxCapPesoEjercitoException(Message.MAX_CAP_PESO_EJERCITO);
                            } else {
                                System.out.println(Message.UNIDAD_SUPERA_PESO + " " + Message.PESO_DISPONIBLE
                                        + (MAX_PESO - saldoPeso));
                            }
                        }
                    } catch (MaxCapPesoEjercitoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "d":
                    if (hayGeneral) {
                        System.out.println("Ya hay general.");
                    } else {
                        try {
                            System.out.println("Elige un general para añadir al ejército:");
                            for (int i = 0; i < generalesDisponibles.size(); i++) {
                                System.out.println((i + 1) + ". " + generalesDisponibles.get(i));
                            }

                            Scanner scanner1 = new Scanner(System.in);
                            int seleccion = scanner1.nextInt();

                            if (seleccion >= 1 && seleccion <= generalesDisponibles.size()) {
                                General generalSeleccionado = generalesDisponibles.remove(seleccion - 1); // Remover el general seleccionado de la lista

                                try {
                                    if (((saldoPeso + General.PESO_GENERAL) < MAX_PESO) && !hayGeneral) {
                                        adicionarUnidad(generalSeleccionado);
                                        imprimirInfo(unidades.getLast());
                                    } else {
                                        if (saldoPeso == MAX_PESO) {
                                            throw new MaxCapPesoEjercitoException(Message.MAX_CAP_PESO_EJERCITO);
                                        } else {
                                            throw new MaxCapGeneralException(Message.GENERAL_EXISTENTE);
                                        }
                                    }
                                } catch (MaxCapPesoEjercitoException | MaxCapGeneralException e) {
                                    System.out.println(e.getMessage());
                                }

                            } else {
                                System.out.println("Selección inválida.");
                            }
                        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;


                case "e":
                    try {
                        if (contadorAnimales == 3) {
                            System.out.println("Has alcanzado el máximo de animales");
                        } else {
                            Query consulta = HeroesyGenerales.query();
                            consulta.constrain(Heroes.class);
                            ObjectSet<Heroes> res = consulta.execute();

                            List<Heroes> heroes = new ArrayList<>();

                            while (res.hasNext()) {
                                heroes.add(res.next());
                            }

                            System.out.println("Elige un héroe para añadir al ejército:");
                            for (int i = 0; i < heroes.size(); i++) {
                                System.out.println((i + 1) + ". " + heroes.get(i));
                            }

                            Scanner scanner1 = new Scanner(System.in);
                            int seleccion = scanner1.nextInt();

                            if (seleccion >= 1 && seleccion <= heroes.size()) {
                                Heroes heroeSeleccionado = heroes.get(seleccion - 1);
                                try {
                                    if (((saldoPeso + Heroes.PESO_HEROE) < MAX_PESO) && contadorAnimales < MAX_ANIMALES) {
                                        adicionarUnidad(heroeSeleccionado);
                                        imprimirInfo(unidades.getLast());
                                    } else {
                                        if (saldoPeso == MAX_PESO) {
                                            throw new MaxCapPesoEjercitoException(Message.MAX_CAP_PESO_EJERCITO);
                                        } else if (contadorAnimales == MAX_ANIMALES) {
                                            throw new MaxAnimalesException(Message.MAX_ANIMALES);
                                        } else {
                                            System.out.println(Message.UNIDAD_SUPERA_PESO + " " + Message.PESO_DISPONIBLE
                                                    + (MAX_PESO - saldoPeso));
                                        }
                                    }
                                } catch (MaxCapPesoEjercitoException e) {
                                    System.out.println(e.getMessage());
                                } catch (MaxAnimalesException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case "f":
                    System.out.println(Message.SALDO_ACTUAL + getSaldoPeso());
                    break;
                case "g":
                    try {
                        if (!unidades.isEmpty()) {
                            System.out.println("Eliminar unidad del ejército: ");
                            informacionEjercito();

                            System.out.println("Nombre de la unidad a eliminar: ");
                            String nombreUnidad = scanner.nextLine();

                            eliminarUnidad(nombreUnidad);
                        } else {
                            throw new EjercitoVacioException(Message.EJERCITO_VACIO);
                        }
                    } catch (EjercitoVacioException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "h":
                    try {
                        if (saldoPeso >= MIN_UNIDADES && hayGeneral) {
                            System.out.println(System.lineSeparator() + "Su Ejército está formado por: "
                                    + System.lineSeparator());

                            informacionEjercito();

                            actualizarEjercito();
                            break;
                        }

                        if (nombre == null || nombre.isEmpty() || nombre.isBlank()) {
                            throw new EjercitoNombreException(Message.EJERCITO_SIN_NOMBRE);
                        }

                        if (saldoPeso < MIN_UNIDADES) {
                            throw new UnidadMinimaException(Message.UNIDADES_MINIMAS);
                        } else {
                            throw new GeneralMinimoException(Message.GENERAL_MINIMO);
                        }
                    } catch (EjercitoNombreException | UnidadMinimaException | GeneralMinimoException e) {
                        System.out.println(e.getMessage());
                    }

                    menu();

                    break;
                case "i":
                    vergeneralganador();
                    break;
                case "j" :
                    cerrarConexion();
                    System.exit(1);
                    break;
                default:
                    System.out.println(Message.OPCION_INAVLIDA);
                    break;
            }
        } while (!opcion.equals("h"));
    }

    private void imprimirInfo(Componentes componente) {
        System.out.println(Message.ADICIONAR_COMPONENTE + System.lineSeparator() + componente);
        System.out.println(System.lineSeparator() + Message.SALDO_ACTUAL + getSaldoPeso());
    }

    public void recibirDano(int dano) {
        Iterator<Componentes> iterador = unidades.iterator();

        while (dano > 0 && iterador.hasNext()) {
            Componentes componente = iterador.next();
            componente.recibirDano(dano);

            if (componente.getSalud() < 0) {

                dano = Math.abs(componente.getSalud());
                iterador.remove();
                unidades.remove(componente);

                System.out.println("El componente " + componente.getNombre() + " ha sido eliminado del ejército " +
                        nombre + " por falta de salud");
            }

            restablecerAtributos();
            actualizarEjercito();
        }
    }

    private void actualizarEjercito() {
        for (Componentes componente : unidades) {
            ataque += componente.getAtaque();
            defensa += componente.getDefensa();
            salud += componente.getSalud();
        }
    }

    private void restablecerAtributos() {
        ataque = 0;
        defensa = 0;
        salud = 0;
    }

    private void adicionarUnidad(Componentes componentes) {
        if (componentes instanceof Infanteria || componentes instanceof Caballeria) {
            unidades.add(componentes);
            saldoPeso += componentes.getPeso();
        } else if (componentes instanceof General) {
            unidades.add(componentes);
            saldoPeso += componentes.getPeso();
            hayGeneral = true;
        } else if (componentes instanceof Elefante || componentes instanceof Tigre || componentes instanceof Heroes) {
            unidades.add(componentes);
            saldoPeso += componentes.getPeso();
            contadorAnimales++;
        }
    }

    private void eliminarUnidad(String nombreUnidad) {

        try {
            for (Componentes unidad : unidades) {
                if (unidad.getNombre().equalsIgnoreCase(nombreUnidad)) {
                    if (unidad instanceof General) {
                        hayGeneral = false;
                    } else if (unidad instanceof Elefante || unidad instanceof Tigre) {
                        contadorAnimales--;
                    }

                    unidades.remove(unidad);
                    saldoPeso -= unidad.getPeso();

                    System.out.println(unidad.getNombre() + ": " + Message.UNIDAD_ELIM_SATIS);

                    break;
                } else {
                    throw new UnidadInexistenteException(Message.UNIDAD_INEXISTENTE);
                }
            }
        } catch (UnidadInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void informacionEjercito() {
        for (Componentes unidad : unidades) {
            System.out.println(unidad);
        }
    }

    private void asignarNombre(String nombre) {
        try {
            if (!nombres.contains(nombre)) {
                nombres.add(nombre);
                this.nombre = nombre;
            } else {
                throw new NombreExistenteException(Message.NOMBRE_EXISTENTE);
            }
        } catch (NombreExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    public General obtenerGeneralGanador() {
        for (Componentes unidad : unidades) {
            if (unidad instanceof General) {
                return (General) unidad;
            }
        }
        return null;
    }

    private void inicializarGeneralesDisponibles() {
        // Realizar consulta a la base de datos para obtener los generales disponibles
        Query consulta = HeroesyGenerales.query();
        consulta.constrain(General.class);
        ObjectSet<General> res = consulta.execute();

        while (res.hasNext()) {
            generalesDisponibles.add(res.next());
        }
    }

}