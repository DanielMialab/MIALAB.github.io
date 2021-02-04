/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorizacion;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autorizacion", propOrder = {"estado", "numeroAutorizacion", "fechaAutorizacion", "ambiente", "comprobante", "mensajes"})
public class Autorizacion {
  protected String estado;
  
  protected String numeroAutorizacion;
  
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar fechaAutorizacion;
  
  protected String ambiente;
  
  protected String comprobante;
  
  protected Mensajes mensajes;
  
  public String getEstado() {
    return this.estado;
  }
  
  public void setEstado(String value) {
    this.estado = value;
  }
  
  public String getNumeroAutorizacion() {
    return this.numeroAutorizacion;
  }
  
  public void setNumeroAutorizacion(String value) {
    this.numeroAutorizacion = value;
  }
  
  public XMLGregorianCalendar getFechaAutorizacion() {
    return this.fechaAutorizacion;
  }
  
  public void setFechaAutorizacion(XMLGregorianCalendar value) {
    this.fechaAutorizacion = value;
  }
  
  public String getAmbiente() {
    return this.ambiente;
  }
  
  public void setAmbiente(String value) {
    this.ambiente = value;
  }
  
  public String getComprobante() {
    return this.comprobante;
  }
  
  public void setComprobante(String value) {
    this.comprobante = value;
  }
  
  public Mensajes getMensajes() {
    return this.mensajes;
  }
  
  public void setMensajes(Mensajes value) {
    this.mensajes = value;
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {"mensaje"})
  public static class Mensajes {
    protected List<Mensaje> mensaje;
    
    public List<Mensaje> getMensaje() {
      if (this.mensaje == null)
        this.mensaje = new ArrayList<>(); 
      return this.mensaje;
    }
  }
}
