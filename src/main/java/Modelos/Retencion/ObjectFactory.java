/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.Retencion;

/*  3:   */ import Modelos.InfoTributaria;
/*  4:   */ import javax.xml.bind.annotation.XmlRegistry;
/*  5:   */ 
/*  6:   */ @XmlRegistry
/*  7:   */ public class ObjectFactory
/*  8:   */ {
/*  9:   */   public ComprobanteRetencion.InfoAdicional createComprobanteRetencionInfoAdicional()
/* 10:   */   {
/* 11:45 */     return new ComprobanteRetencion.InfoAdicional();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public ComprobanteRetencion.Impuestos createComprobanteRetencionImpuestos()
/* 15:   */   {
/* 16:53 */     return new ComprobanteRetencion.Impuestos();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ComprobanteRetencion.InfoCompRetencion createComprobanteRetencionInfoCompRetencion()
/* 20:   */   {
/* 21:61 */     return new ComprobanteRetencion.InfoCompRetencion();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ComprobanteRetencion.InfoAdicional.CampoAdicional createComprobanteRetencionInfoAdicionalCampoAdicional()
/* 25:   */   {
/* 26:69 */     return new ComprobanteRetencion.InfoAdicional.CampoAdicional();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public InfoTributaria createInfoTributaria()
/* 30:   */   {
/* 31:77 */     return new InfoTributaria();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ComprobanteRetencion createComprobanteRetencion()
/* 35:   */   {
/* 36:85 */     return new ComprobanteRetencion();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Impuesto createImpuesto()
/* 40:   */   {
/* 41:93 */     return new Impuesto();
/* 42:   */   }
/* 43:   */ }
