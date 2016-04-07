/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinaturing;

/**
 *
 * @author Javier
 */
public class Reglas {
    //(estadoActual, estadoAmoverse, simboloActual,simboloARemplazar,movimiento)
    String estadoActual="";
    String movimiento= "";
    String estadoNuevo="";
    String simboloActual="";
    String simboloNuevo="";
    public Reglas(){
        
    }   
    public Reglas(String estadoActual,String movimiento,String estadoNuevo,String simboloActual,String simboloNuevo){
        this.estadoActual = estadoActual;
        this.movimiento = movimiento;
        this.estadoNuevo = estadoNuevo;
        this.simboloActual = simboloActual;
        this.simboloNuevo = simboloNuevo;
    }
    public void setEstadoActual(String estadoActual){
        this.estadoActual = estadoActual;
    }
    public void setMovimiento(String movimiento){
        this.movimiento = movimiento;
    }
    public void setEstadoNuevo(String estadoNuevo){
        this.estadoNuevo = estadoNuevo;
    }
    public void setSimboloActual(String simboloActual){
        this.simboloActual = simboloActual;
    }
    public void setSimboloNuevo(String simboloNuevo){
        this.simboloNuevo = simboloNuevo;
    }
    
     public String getEstadoActual(){
        return estadoActual;
    }
    public String getMovimiento(){
        return movimiento;
    }
    public String getEstadoNuevo(){
        return estadoNuevo;
    }
    public String getSimboloActual(){
        return simboloActual;
    }
    public String getSimboloNuevo(){
        return simboloNuevo;
    }
    
}
