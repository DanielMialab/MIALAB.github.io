/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion.Util;

import Modelos.Proxy;
import java.net.URL;
import javax.xml.namespace.QName;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.logging.Level;
import Modelos.Emisor;
import java.util.Date;
import java.util.Calendar;
/**
 *
 * @author User
 */
public class FormGenerales {
    
    private static char SEPARADOR_DECIMAL = '.';
    
    
    
    // DEVOLVER 
    public static String devuelveUrlWs(String ambiente, String nombreServicio)
   {
     StringBuilder url = new StringBuilder();
     String direccionIPServicio = null;
     Proxy configuracion = null;
    
    

       if (ambiente.equals(TipoAmbienteEnum.PRODUCCION.getCode()) == true) {
         direccionIPServicio = configuracion.getWsProduccion();
       } else if (ambiente.equals(TipoAmbienteEnum.PRUEBAS.getCode()) == true) {
         direccionIPServicio = configuracion.getWsPruebas();
       }
       url.append(direccionIPServicio);
       
       url.append("/comprobantes-electronicos-ws/");
       url.append(nombreServicio);
       url.append("?wsdl");
       System.out.print(url.toString());
     
     return url.toString();
   }
    
   public static String insertarCaracteres(String cadenaLarga, String aInsertar, int longitud)
   {
     StringBuilder sb = new StringBuilder(cadenaLarga);
     
     int i = 0;
     while ((i = sb.indexOf(" ", i + longitud)) != -1) {
       sb.replace(i, i + 1, aInsertar);
     }
     return sb.toString();
   }
   
   //METODO CREAR EMISOR
public static Emisor actualizaEmisor(String tipoEmision, Emisor emisor)
{
  emisor.setTipoEmision(StringUtil.obtenerTipoEmision(tipoEmision));
  /*try
  {
    new EmisorSQL().crearEmisor(emisor);
  }
  catch (SQLException ex)
  {
    Logger.getLogger(FacturaView.class.getName()).log(Level.SEVERE, null, ex);
  }
  catch (ClassNotFoundException ex)
  {
    Logger.getLogger(FacturaView.class.getName()).log(Level.SEVERE, null, ex);
  }*/
  return emisor;
}
    

public static Date eliminaHora(Date date)
{
  Calendar cal = Calendar.getInstance();
  cal.setTime(date);
  cal.set(11, 0);
  cal.set(12, 0);
  cal.set(13, 0);
  cal.set(14, 0);
  return cal.getTime();
}
    
    
}
