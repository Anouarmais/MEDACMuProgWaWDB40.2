/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componentes.personas;

import java.util.List;
import java.util.Random;

/**
 *
 * @author danie
 */
public class GeneralTOPSCORE extends Persona {

    public GeneralTOPSCORE(){
        super();


    }
       
    @Override
public String toString() {
        return "El mitiquísimo " + this.getNombre() + " con un ataque de " + this.getAtaque()  +
                ", con una defensa de " + this.getDefensa()  +
                ", y por ultimo una salud de " + this.getSalud() ;
    }     
}
