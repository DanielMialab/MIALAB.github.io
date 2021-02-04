/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;


public class n_cst_autoriza {
  public String error;
  
  public String estado;
  
  public String claveAcceso;
  
  public String numeroAutorizacion;
  
  public String fechaAutorizacion;
  
  public String comprobante;
  
  public String mensaje_1;
  
  public String mensaje_2;
  
  public String mensaje_3;
  
  public String info_1;
  
  public String info_2;
  
  public String info_3;
  
  public void agregar_mensaje(int i, String mensaje) {
    if (i == 1)
      this.mensaje_1 = mensaje; 
    if (i == 2)
      this.mensaje_2 = mensaje; 
    if (i == 3)
      this.mensaje_3 = mensaje; 
  }
  
  public void agregar_info(int i, String mensaje) {
    if (i == 1)
      this.info_1 = mensaje; 
    if (i == 2)
      this.info_2 = mensaje; 
    if (i == 3)
      this.info_3 = mensaje; 
  }
}
