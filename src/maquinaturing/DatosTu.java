/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinaturing;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Javier
 */
public class DatosTu {
    StringProperty estado;
    StringProperty cadenaN;
    StringProperty cadena;
    StringProperty regla;
    
    
    
     public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado = new SimpleStringProperty(estado);
    }

    public StringProperty estadoProperty() {
        return estado;
    }
     public String getCadena() {
        return cadena.get();
    }

    public void setCadena(String cadena) {
        this.cadena = new SimpleStringProperty(cadena);
    }

    public StringProperty cadenaProperty() {
        return cadena;
    }
      public String getCadenaN() {
        return cadenaN.get();
    }

    public void setCadenaN(String cadena) {
        this.cadenaN=new SimpleStringProperty(cadena);
    }

    public StringProperty cadenaNProperty() {
        return cadenaN;
    }
     public String getRegla() {
        return regla.get();
    }

    public void setRegla(String estado,String simbolo, String movimiento) {
        this.regla = new SimpleStringProperty("("+estado+", "+simbolo+", "+movimiento+")");
    }

    public StringProperty reglaProperty() {
        return regla;
    }
    
}
