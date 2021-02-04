/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepcion;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaSolicitud", propOrder = {"estado", "comprobantes"})
public class RespuestaSolicitud1 {
  protected String estado;
  
  protected Comprobantes comprobantes;
  
  public String getEstado() {
    return this.estado;
  }
  
  public void setEstado(String value) {
    this.estado = value;
  }
  
  public Comprobantes getComprobantes() {
    return this.comprobantes;
  }
  
  public void setComprobantes(Comprobantes value) {
    this.comprobantes = value;
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {"comprobante"})
  public static class Comprobantes {
    protected List<Comprobante> comprobante;
    
    public List<Comprobante> getComprobante() {
      if (this.comprobante == null)
        this.comprobante = new ArrayList<>(); 
      return this.comprobante;
    }
  }
}
