/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinaturing;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Javier
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField estados,alfabeto,alfabeto2,eInicial,eFinal,cadena,salida;
    @FXML
    private ScrollPane scrollP;
    @FXML
    private AnchorPane menu, datosView,tablaView,principalView;
    @FXML
    private TableView<DatosTu> tablaPri;
    @FXML
    private TableColumn<DatosTu, String> columnaCN,columnaE,columnaCA,columnaR;
    @FXML private RadioButton rizquierda,rderecha;
    private ObservableList<DatosTu> tablaPrincipal = FXCollections.observableArrayList();

     GridPane tablaEstados =  new GridPane();
    String estadoI = ""; 
    String estadoF =  "";
    String texto = "";
    boolean lectura = true; //true izq
    int index = 0; //Indice de la cinta
    ArrayList<Reglas> re = new ArrayList<Reglas>(); 
    boolean bandera = false;
    
    
    
//--------------------------Vistas-------------------------------------    
    public void showMenu(){
        menu.setVisible(true);
        datosView.setVisible(false);
        tablaView.setVisible(false);
        principalView.setVisible(false);
      
        
    }
    public void showDatosView(){
        menu.setVisible(false);
        datosView.setVisible(true);
        tablaView.setVisible(false);
        principalView.setVisible(false);
        estados.setText("");
        alfabeto.setText("");
        alfabeto2.setText("");
        eInicial.setText("");
        eFinal.setText("");
    }
    public void showTablaView(){
        menu.setVisible(false);
        datosView.setVisible(false);
        tablaView.setVisible(true);
        principalView.setVisible(false);
    }
    public void showPrincipalView(){
        menu.setVisible(false);
        datosView.setVisible(false);
        tablaView.setVisible(false);
        principalView.setVisible(true);
        cadena.setText("");
        salida.setText("");
        tablaPrincipal.clear();
        tablaPri.setItems(tablaPrincipal);
    }
//----------------------------------------------------------------------
    
    @FXML
    private void empezar(ActionEvent event) {
       showDatosView();   
       rizquierda.setSelected(true);
    }
    @FXML
    private void continuar(ActionEvent event) {
        tablaEstados = new GridPane();
        setFilas();
        setColumnas();
        setContenidoTabla();
      setDireccionCabezal();
        scrollP.setContent(tablaEstados);
        setEstadoFinal();
        setEstadoInicial();
        showTablaView();
       
    }
    private void setDireccionCabezal(){
        lectura = rizquierda.isSelected();
        System.out.println("Lectura:" +lectura);
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
    private boolean setCadena(){
        String aux = cadena.getText();
        String aux2 = alfabeto.getText();
        for (int i = 0; i < aux2.length(); i++) {
            aux = aux.replace(aux2.charAt(i)+"","");
        }
        if(!"".equals(cadena.getText()) && aux.length() == 0){
        texto = cadena.getText();
        
        return true;
        }
        Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Information Dialog");
alert.setHeaderText(null);
alert.setContentText("La cadena no coincide con el alfabeto");

alert.showAndWait();
        return false;
    }
    @FXML
    private void continuar2(ActionEvent event) {
        getReglas();
        showPrincipalView();
    }
    @FXML
    private void handleButtonAction3(ActionEvent event) {
        tablaPrincipal.clear();
        tablaPri.setItems(tablaPrincipal);
        setEstadoInicial();
        boolean bandera = setCadena();
        if(bandera)
        ejecutarMT(texto);
    }
    
    private void ejecutarMT(String cadena){
        bandera = false;
        
        int indexC = 0;
        if(lectura){
            indexC = 0;
        }else{
            indexC = cadena.length()-1;
        }
        System.out.println("IndexC "+ indexC);
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
                    DatosTu auxTu = new DatosTu();
                    auxTu.setCadena(cadena);
                    
                   // System.out.println("Simbolo: "+simbolo+"----index: "+indexC);
                   // System.out.println(aux.getEstadoActual()+":"+aux.getEstadoNuevo()+":"+aux.getSimboloActual()+":"+aux.getSimboloNuevo()+":"+aux.getMovimiento());
                   // System.out.println("Cadena Actual: "+ cadena);
                    cadena = replaceCharAt(cadena, indexC, aux.getSimboloNuevo().charAt(0));
                   // System.out.println("Cadena Nueva: "+cadena);
                    auxTu.setCadenaN(cadena);
                    if("R".equals(aux.getMovimiento())){
                        indexC++;
                        System.out.println("Aumento: "+indexC);
                    }else{
                        indexC--;
                        System.out.println("Reduccion "+indexC);
                    }
                    auxTu.setEstado(estadoI);
                    System.out.println("Estado Actual: "+estadoI);
                    estadoI = aux.getEstadoNuevo();
                    System.out.println("Estado Nuevo: "+estadoI);
                    auxTu.setRegla(aux.getEstadoNuevo(), aux.getSimboloNuevo(), aux.getMovimiento());
                    
                    tablaPrincipal.add(auxTu);
                    tablaPri.setItems(tablaPrincipal);
                    if(estadoI.equals(estadoF)){
                        salida.setText(cadena);
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
        showMenu();
         rizquierda.setSelected(true);
        columnaCA.setCellValueFactory(cellData -> cellData.getValue().cadenaProperty());
        columnaCN.setCellValueFactory(cellData -> cellData.getValue().cadenaNProperty());
        columnaE.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        columnaR.setCellValueFactory(cellData -> cellData.getValue().reglaProperty());
    }    
    
}
