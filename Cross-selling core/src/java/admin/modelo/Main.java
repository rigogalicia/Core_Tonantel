/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import rally.modelo.Asignar;

/**
 *
 * @author RigoGalicia
 */
public class Main {
    
    public static void main(String args[]){
//        Asignar a = new Asignar();
//        a.setUserConect("todesaragb");
//        a.consultarRefParaAsignarme();

        Departamento dep = new Departamento();
        for(Departamento d : dep.mostrarDepartamentos()){
            System.out.println("Nombre: " + d.getNombre());
        }
    }
    
}
