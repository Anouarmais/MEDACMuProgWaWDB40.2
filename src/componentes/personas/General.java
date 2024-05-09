/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componentes.personas;

import controladores.ExploradorFicheros;

import java.util.List;
import java.util.Random;

/**
 *
 * @author danie
 */
public class General extends Persona {
public static final int PESO_GENERAL = 1;    
    public General(){
        super();
        Random random = new Random(); 
        setMultiplicador(random.nextFloat(2, 3));   
        setPeso(PESO_GENERAL);

        List<Integer> atributos = generarAtributos(100);
        setAtaque((int) Math.ceil(atributos.get(0)*getMultiplicador()));
        setDefensa((int) Math.ceil(atributos.get(1)*getMultiplicador()));
        setSalud((int) Math.ceil(atributos.get(2)*getMultiplicador()));

    }
       
    @Override
public String toString() {
        return "El mitiquísimo " + this.getNombre() + " con un ataque de " + this.getAtaque()  +
                ", con una defensa de " + this.getDefensa()  +
                ", y por ultimo una salud de " + this.getSalud() ;
    }     
}
