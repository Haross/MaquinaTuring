/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinaturing;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Javier
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField estados,alfabeto,alfabeto2,eInicial,eFinal,cadena;
    @FXML
    private ScrollPane scrollP;
     GridPane tablaEstados =  new GridPane();
    String estadoI = ""; 
    String estadoF =  "";
    String texto = "";
    int index = 0; //Indice de la cinta
    ArrayList<Reglas> re = new ArrayList<Reglas>(); 
    boolean bandera = false;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        setFilas();
        setColumnas();
        setContenidoTabla();
        scrollP.setContent(tablaEstados);
        setEstadoFinal();
        setEstadoInicial();
        setCadena();
       
    }
    private void setEstadoInicial(){
        if(!"".equals(eInicial.getText())){
        estadoI = eInicial.getText();
        }else{
            //Alert error
        }
    }
    private void setEstadoFinal(){
        if(!"".equals(eFinal.getText())){
        estadoF = eFinal.getText();
        }else{
            //Alert error
        }
    }
    private void setCadena(){
        if(!"".equals(cadena.getText())){
        texto = cadena.getText();
        }else{
            //Alert error
        }
    }
    @FXML
    private void handleButtonAction2(ActionEvent event) {
       
        getReglas();
    }
    @FXML
    private void handleButtonAction3(ActionEvent event) {
        setEstadoInicial();
        setCadena();
        ejecutarMT(texto);
    }
    
    private void ejecutarMT(String cadena){
        bandera = false;
        int indexC = 0;
        String simbolo = "";
        System.out.println("cadena: "+cadena);
        while(!bandera){
            if(indexC < 0 || indexC > cadena.length()-1){
                 simbolo =  "B";
            }else {
                simbolo =  cadena.charAt(indexC) + "";
                System.out.println("Calculo de simbolo: "+simbolo+"index:"+indexC);
            }
            
            for (int i = 0; i < re.size(); i++) {
                Reglas aux = re.get(i);
                if(estadoI.equals(aux.getEstadoActual()) && simbolo.equals(aux.getSimboloActual())){
                     //(estadoActual, estadoAmoverse, simboloActual,simboloARemplazar,movimiento)
                    System.out.println("Simbolo: "+simbolo+"----index: "+indexC);
                    System.out.println(aux.getEstadoActual()+":"+aux.getEstadoNuevo()+":"+aux.getSimboloActual()+":"+aux.getSimboloNuevo()+":"+aux.getMovimiento());
                    System.out.println("Cadena Actual: "+ cadena);
                    cadena = replaceCharAt(cadena, indexC, aux.getSimboloNuevo().charAt(0));
                    System.out.println("Cadena Nueva: "+cadena);
                    if("R".equals(aux.getMovimiento())){
                        indexC++;
                        System.out.println("Aumento: "+indexC);
                    }else{
                        indexC--;
                        System.out.println("Reduccion "+indexC);
                    }
                    System.out.println("Estado Actual: "+estadoI);
                    estadoI = aux.getEstadoNuevo();
                    System.out.println("Estado Nuevo: "+estadoI);
                    if(estadoI.equals(estadoF)){
                        System.out.println("FIN DEL PROGRAMA "+cadena);
                        bandera = true;
                    }
                    break;
                }
                
            }
        }
        
    }
    
    public String replaceCharAt(String s, int pos, char c) {
        System.out.println("pos"+pos);
        if(pos < 0){
            if(!"B".equals(c+""))
                 return c+s;                
            }else if(( pos > s.length()-1)){
              if(!"B".equals(c+""))
                 return s+c;  
            }else{    
                return s.substring(0, pos) + c + s.substring(pos + 1);
            }
       return s; 
    }
    private void verificarCadena(){
        //verificar que la cadena ingresada solo tenga simbolos del alfabeto
    }
    
    private void getReglas(){
        re.clear(); 
        ObservableList<Node> childrens = tablaEstados.getChildren();    
        for (int i = 1; i < tablaEstados.getColumnConstraints().size(); i++) {
            for (int j = 1; j <= tablaEstados.getRowConstraints().size(); j++) {
                TextField aux1 = (TextField) getCelda(j, i, tablaEstados);
                if(!"".equals(aux1.getText())){
                Label aux = (Label) getCelda(j, 0, tablaEstados);
                String estadoA = aux.getText();
                aux = (Label) getCelda(0, i, tablaEstados);
                String simboloA = aux.getText();
                    
                String[] datos = aux1.getText().replace("(", "").replace(")", "").split(",");
                re.add(new Reglas(estadoA,datos[2],datos[0],simboloA,datos[1]));
                    System.out.println(estadoA+":"+datos[2]+":"+datos[0]+":"+simboloA+":"+datos[1]);
                }
            }
        }
       
    }
     public Node getCelda(final int row,final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
    
    
    
    private void setContenidoTabla(){
        System.out.println("filas: "+tablaEstados.getRowConstraints().size());
        
        System.out.println("columnas: "+tablaEstados.getColumnConstraints().size());
        for (int i = 1; i < tablaEstados.getColumnConstraints().size(); i++) {
            for (int j = 1; j <= tablaEstados.getRowConstraints().size(); j++) {
                tablaEstados.add(new TextField(),  i, j);
            }
        }
    }
    private void setFilas(){
        String[] datosEstados = estados.getText().split(",");
         for (int i = 0; i < datosEstados.length; i++) {
            RowConstraints con = new RowConstraints();
            // Here we set the pref height of the row, but you could also use .setPercentHeight(double) if you don't know much space you will need for each label.
            con.setPrefHeight(40);
            
            tablaEstados.getRowConstraints().add(con);
            tablaEstados.add(new Label(datosEstados[i]),  0, i+1);
        }
    }
    
    private void setColumnas(){
        String[] datosAl = alfabeto2.getText().split(",");
        ColumnConstraints column = new ColumnConstraints(100);
        tablaEstados.getColumnConstraints().add(column);
        tablaEstados.add(new Label("Estados"), 0, 0);
         for (int i = 0; i < datosAl.length; i++) {
            column = new ColumnConstraints(100);
            tablaEstados.getColumnConstraints().add(column);
            tablaEstados.add(new Label(datosAl[i]), i+1, 0);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    
    
}
