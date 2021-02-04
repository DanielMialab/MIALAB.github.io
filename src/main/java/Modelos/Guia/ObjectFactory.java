/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.Guia;

/*   3:    */ import Modelos.InfoTributaria;
/*   4:    */ import javax.xml.bind.annotation.XmlRegistry;
/*   5:    */ 
/*   6:    */ @XmlRegistry
/*   7:    */ public class ObjectFactory
/*   8:    */ {
/*   9:    */   public GuiaRemision.Destinatarios createGuiaRemisionDestinatarios()
/*  10:    */   {
/*  11: 45 */     return new GuiaRemision.Destinatarios();
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Detalle.DetallesAdicionales createDetalleDetallesAdicionales()
/*  15:    */   {
/*  16: 53 */     return new Detalle.DetallesAdicionales();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Destinatario createDestinatario()
/*  20:    */   {
/*  21: 61 */     return new Destinatario();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Detalle.DetallesAdicionales.DetAdicional createDetalleDetallesAdicionalesDetAdicional()
/*  25:    */   {
/*  26: 69 */     return new Detalle.DetallesAdicionales.DetAdicional();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Destinatario.Detalles createDestinatarioDetalles()
/*  30:    */   {
/*  31: 77 */     return new Destinatario.Detalles();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public GuiaRemision createGuiaRemision()
/*  35:    */   {
/*  36: 85 */     return new GuiaRemision();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public GuiaRemision.InfoAdicional.CampoAdicional createGuiaRemisionInfoAdicionalCampoAdicional()
/*  40:    */   {
/*  41: 93 */     return new GuiaRemision.InfoAdicional.CampoAdicional();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public GuiaRemision.InfoAdicional createGuiaRemisionInfoAdicional()
/*  45:    */   {
/*  46:101 */     return new GuiaRemision.InfoAdicional();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public InfoTributaria createInfoTributaria()
/*  50:    */   {
/*  51:109 */     return new InfoTributaria();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public GuiaRemision.InfoGuiaRemision createGuiaRemisionInfoGuiaRemision()
/*  55:    */   {
/*  56:117 */     return new GuiaRemision.InfoGuiaRemision();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Detalle createDetalle()
/*  60:    */   {
/*  61:125 */     return new Detalle();
/*  62:    */   }
/*  63:    */ }
