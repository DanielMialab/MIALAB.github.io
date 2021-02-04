/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

import Modelos.Factura.Factura;
import Modelos.Producto;
import Modelos.InformacionAdicionalProducto;
import Modelos.Factura.Impuesto;
import exception.TotalDescuentoException;
import recepcion.RespuestaSolicitud.Comprobantes;

import java.math.BigDecimal;
import Modelos.InfoTributaria;
import Facturacion.Util.TipoCompradorEnum;
import Modelos.Emisor;
import Facturacion.Util.TipoComprobanteEnum;
import Modelos.Factura.ObjectFactory;
import edu.ec.istdab.model.Examen;
import edu.ec.istdab.model.ItemFactura;
import Facturacion.Util.Constantes;
import Facturacion.Util.FormGenerales;
import Facturacion.Util.TipoAmbienteEnum;
import Modelos.FacturaModel;
import java.util.Date;
import Modelos.SubtotalImpuesto;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.math.RoundingMode;
import java.util.logging.Logger;
import java.util.logging.Level;
import Facturacion.Util.ClaveDeAcceso;
import Modelos.ImpuestoProducto;
import Modelos.ImpuestoValor;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.AbstractList;
import Modelos.ClaveContingencia;
import Modelos.Factura.Factura.InfoFactura.Pagos.Pago;
import Modelos.Factura.Factura.InfoFactura.Pagos;
import java.math.MathContext;

/**
 *
 * @author User
 */
public class ComprobantesFacturacion {

	private long VALOR_MAXIMO_CONSUMIDOR_FINAL = 200L;
	private BigDecimal valorDescuento = BigDecimal.ZERO;
	private BigDecimal totalValorFactura = null;
	private InfoTributaria infoTributaria = null;
	private String secuencialComprobante = null;
	private Emisor emisor = null;
	private String claveDeAcceso = null;
	private Factura.InfoFactura infoFactura = null;
	private ObjectFactory facturaFactory = null;
	private Date fechaEmision;
	private String razonSocialComprador = null;
	private String identificacionComprador = null;
	private BigDecimal subtotalSinImpuestos = null;
	private BigDecimal totalSinImpuestos = null;
	private BigDecimal totalDescuento = null;
	private BigDecimal valorPropina = BigDecimal.ZERO;
	// private FacturaModel modeloDetalle;
	Producto producto = null;
	private List<Producto> modeloDetalle = new ArrayList<>();

	public ComprobantesFacturacion(String razonSocialComprador, String secuencialComprobante,
			List<ItemFactura> detalleFatura) {
		
		
		double totalSinImpuestos=0.0;
		for(int i=0;i<detalleFatura.size();i++) {
			totalSinImpuestos=totalSinImpuestos+detalleFatura.get(i).getPrecioTotalItem();
		}
		
		
		
		
		this.subtotalSinImpuestos = BigDecimal.valueOf(totalSinImpuestos);
		this.valorPropina = BigDecimal.valueOf(0);
		this.totalSinImpuestos = BigDecimal.valueOf(totalSinImpuestos);
		this.totalDescuento = BigDecimal.valueOf(0);

		this.razonSocialComprador = razonSocialComprador;
		this.secuencialComprobante = secuencialComprobante;

		this.emisor = new Emisor();
		this.emisor.setTipoAmbiente(TipoAmbienteEnum.PRUEBAS.getCode());
		this.emisor.setRazonSocial("TAPIA JARAMILLO ANDREA MARIBEL");
		this.emisor.setRuc("1104707813001");
		this.emisor.setCodigoEstablecimiento("001");
		this.emisor.setCodPuntoEmision("001");
		this.emisor.setLlevaContabilidad("NO");
		this.emisor.setTipoEmision("1");
		this.emisor.setClaveInterna("12345678");
		this.emisor.setDirEstablecimiento("LAURO GUERRERO 411-16, VENEZUELA Y MAXIMILIANO RODRIGUEZ");
		this.emisor.setDireccionMatriz("LAURO GUERRERO 411-16, VENEZUELA Y MAXIMILIANO RODRIGUEZ");
		this.emisor.setNombreComercial("Laboratorio Clinico MIALAB");
		this.fechaEmision = new Date();
		// generar clave acceso

		// clacular valor factura
		this.totalValorFactura = BigDecimal.valueOf(66.00);
		ClaveContingencia cl = this.obtieneClaveDeAcceso(this.secuencialComprobante, this.emisor, "001001",
				claveDeAcceso, this.fechaEmision, TipoComprobanteEnum.FACTURA.getCode());
		// llenar detalle
		this.claveDeAcceso = cl.getCodigoComprobante();
		// this.modeloDetalle = new FacturaModel();

		for (int i = 0; i < detalleFatura.size(); i++) {

			this.producto = new Producto();
			this.producto.setCantidad(detalleFatura.get(i).getCatindad());
			this.producto.setCodigo(1);
			this.producto.setCodigoAuxiliar(detalleFatura.get(i).getExamenVinculado().getNombres());
 //	      this.producto.setBaseImponileIRBPNR(BigDecimal.valueOf(1509.00));
			this.producto.setCodigoImpuesto(2);
			this.producto.setCodigoPrincipal(detalleFatura.get(i).getExamenVinculado().getNombres());

			ImpuestoProducto imp = new ImpuestoProducto();
			imp.setCodigoImpuesto("2");
			imp.setCodigoProducto(1);

			List<ImpuestoProducto> lsImpuesto = new ArrayList<ImpuestoProducto>();
			lsImpuesto.add(imp);
			this.producto.setImpuestoProducto(lsImpuesto);

			ImpuestoValor impValor = new ImpuestoValor();
			impValor.setCodigo("1");
			impValor.setCodigoImpuesto(2);
			impValor.setDescripcion("Iva");
			impValor.setFechaFin(new Date());
			impValor.setFechaInicio(new Date());
			impValor.setPorcentaje(12.00);
			impValor.setPorcentajeRentencion(0.00);
			impValor.setTipoImpuesto("2");
			List<ImpuestoValor> lsImpV = new ArrayList<>();
			lsImpV.add(impValor);
			this.producto.setImpuestoValor(lsImpV);

			this.producto.setIva("12");
			this.producto.setNombre(detalleFatura.get(i).getExamenVinculado().getNombres());
			this.producto.setTipoProducto("servicio");
			this.producto.setValorUnitario(BigDecimal.valueOf(detalleFatura.get(i).getPrecioUnitarioActual()));
			this.producto.setTotalItemComprado(
					new BigDecimal(this.producto.getCantidad() * this.producto.getValorUnitario().doubleValue()));

			List<InformacionAdicionalProducto> infoAdicionalList = new ArrayList<InformacionAdicionalProducto>();
			InformacionAdicionalProducto infoAdd = new InformacionAdicionalProducto();
			infoAdd.setAtributo("attr1");
			infoAdd.setCodigo(1);
			infoAdd.setCodigoProducto(1);
			infoAdd.setValor("val1");
			infoAdicionalList.add(infoAdd);
			this.producto.setInfoAdicionalList(infoAdicionalList);

			this.modeloDetalle.add(producto);

		}

	}

	
	public Factura generarComprobante() {
		Factura factura = null;
		try {
			if (!llenarObjetoComprobante(true)) {
				Factura.Detalles detalles = generarDetalle();
				Factura.InfoAdicional informacion = generarInformacionAdicional();
				factura = new Factura();
				factura.setInfoTributaria(this.infoTributaria);
				factura.setInfoFactura(this.infoFactura);
				if (detalles != null) {
					factura.setDetalles(detalles);
				}
				if (informacion.getCampoAdicional().size() > 0) {
					factura.setInfoAdicional(informacion);
				}
				factura.setVersion("1.0.0");
				factura.setId("comprobante");
			}
		} catch (Exception ex) {
			Logger.getLogger("").log(Level.SEVERE, null, ex);
			System.out.println("Se ha producido un error ");
		}
		return factura;
	}

	private boolean llenarObjetoComprobante(Boolean ConsumidorF) throws TotalDescuentoException {
		if (this.valorDescuento.compareTo(this.totalValorFactura) > 0) {
			throw new TotalDescuentoException("El valor total de la factura no puede ser menor ha 0");
		}
		boolean error = false;
		this.infoTributaria = new InfoTributaria();
		this.infoTributaria.setSecuencial(this.secuencialComprobante);
		this.infoTributaria.setAmbiente(this.emisor.getTipoAmbiente());
		this.infoTributaria.setTipoEmision(this.emisor.getTipoEmision());
		this.infoTributaria.setRazonSocial(this.emisor.getRazonSocial());
		this.infoTributaria.setRuc(this.emisor.getRuc());
		this.infoTributaria.setCodDoc(TipoComprobanteEnum.FACTURA.getCode());
		this.infoTributaria.setEstab(this.emisor.getCodigoEstablecimiento());
		this.infoTributaria.setPtoEmi(this.emisor.getCodPuntoEmision());
		this.infoTributaria.setDirMatriz(this.emisor.getDireccionMatriz());
		if (this.claveDeAcceso != null) {
			this.infoTributaria.setClaveAcceso(this.claveDeAcceso);
		} else {

			System.out.println("La clave de Acceso no puede ser nula Se ha producido un error ");

			error = true;
		}
		if ((this.emisor.getNombreComercial() != null) && (!this.emisor.getNombreComercial().isEmpty())) {
			this.infoTributaria.setNombreComercial(this.emisor.getNombreComercial());
		}
		this.infoFactura = new Factura.InfoFactura();// this.facturaFactory.createFacturaInfoFactura();
		// Constantes.dateFormat.format(FormGenerales.eliminaHora(this.fechaEmision))
		this.infoFactura.setFechaEmision(Constantes.dateFormat.format(FormGenerales.eliminaHora(this.fechaEmision)));
		if ((this.emisor.getDirEstablecimiento() != null) && (!this.emisor.getDirEstablecimiento().isEmpty())) {
			this.infoFactura.setDirEstablecimiento(this.emisor.getDirEstablecimiento());
		}
		String guiaRemision = "";// this.idenComprob.textGuia1.getText() + "-" +
									// this.idenComprob.textGuia2.getText() + "-" +
									// this.idenComprob.textGuia3.getText();
		/*
		 * if (guiaRemision.length() == 17) {
		 * this.infoFactura.setGuiaRemision(guiaRemision); } else if
		 * (guiaRemision.length() != 2) { System.out.println(
		 * "Guía de Remisión debe ser de 15 números: ej: 123-123-123456789 Se ha producido un error "
		 * );
		 * 
		 * error = true; }
		 */
		if (ConsumidorF) {
			this.razonSocialComprador = "CONSUMIDOR FINAL";
			this.infoFactura.setTipoIdentificacionComprador(TipoCompradorEnum.CONSUMIDOR_FINAL.getCode());
			this.identificacionComprador = "9999999999999";
			if (this.totalValorFactura.compareTo(BigDecimal.valueOf(this.VALOR_MAXIMO_CONSUMIDOR_FINAL)) == 1) {
				System.out.println(
						"No se pueden emitir facturas mayores a USD 200.00 donde el comprador conste como: CONSUMIDOR FINAL Se ha producido un error ");

				error = true;
			}
		}

		// this.razonSocialComprador = "";//;this.clienteSeleccionado.getApellido();
		// this.identificacionComprador =
		// "";//;this.clienteSeleccionado.getNumeroIdentificacio();
		// this.infoFactura.setTipoIdentificacionComprador(TipoCompradorEnum.CEDULA.getCode());
		this.infoFactura.setIdentificacionComprador(this.identificacionComprador);
		this.infoFactura.setRazonSocialComprador(this.razonSocialComprador);
		this.infoFactura.setTotalSinImpuestos(this.totalSinImpuestos);
		this.infoFactura.setTotalDescuento(this.valorDescuento);
		if (this.valorPropina.compareTo(
				this.subtotalSinImpuestos.multiply(BigDecimal.valueOf(0.1D)).setScale(2, RoundingMode.HALF_UP)) <= 0) {
			this.infoFactura.setPropina(this.valorPropina);
		} else {
			System.out.println(
					"El valor ingresado en el campo Propina exede o es menor al 10% del Subtotal Se ha producido un error ");

			error = true;
		}
		if (this.valorPropina.floatValue() == 0.0F) {
			this.infoFactura.setPropina(BigDecimal.ZERO.setScale(2));
		}

		double sinimp = 0.0;

		for (int i = 0; i < this.modeloDetalle.size(); i++) {
			sinimp = sinimp + modeloDetalle.get(i).getTotalItemComprado().doubleValue();
		}
		sinimp = sinimp + sinimp * 0.12;
		System.out.println("--------------------IMPORTE TOTAL------------>" + sinimp);
		this.infoFactura.setImporteTotal(new BigDecimal(sinimp).round(new MathContext(3)));
		this.infoFactura.setMoneda("DOLAR");
		this.infoFactura.setTotalConImpuestos(generaTotalesImpuesto());
		if ((this.emisor.getContribuyenteEspecial() != null) && (!this.emisor.getContribuyenteEspecial().isEmpty())) {
			this.infoFactura.setContribuyenteEspecial(this.emisor.getContribuyenteEspecial());
		}
		if (this.emisor.getLlevaContabilidad() != null) {
			if (this.emisor.getLlevaContabilidad().equals("S")) {
				this.infoFactura.setObligadoContabilidad("SI");
			} else {
				this.infoFactura.setObligadoContabilidad("NO");
			}
		}

		this.infoFactura.setPagos(this.generarPagos());
		return error;
	}

	private Factura.InfoFactura.Pagos generarPagos() {
		Pagos respuesta = new Pagos();
		Pago item = new Pago();

		double sinimp = 0.0;

		for (int i = 0; i < this.modeloDetalle.size(); i++) {
			sinimp = sinimp + modeloDetalle.get(i).getTotalItemComprado().doubleValue();
		}
		sinimp = sinimp + sinimp * 0.12;

		item.setFormaPago("01");
		item.setTotal(BigDecimal.valueOf(sinimp).round(new MathContext(3)));
		item.setPlazo("0");
		item.setUnidadTiempo("dias");

		respuesta.getPagos().add(item);
		return respuesta;
	}

	private Factura.InfoFactura.TotalConImpuestos generaTotalesImpuesto() {
		Factura.InfoFactura.TotalConImpuestos respuesta = new Factura.InfoFactura.TotalConImpuestos();// this.facturaFactory.createFacturaInfoFacturaTotalConImpuestos();
		Factura.InfoFactura.TotalConImpuestos.TotalImpuesto item = new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();// this.facturaFactory.createFacturaInfoFacturaTotalConImpuestosTotalImpuesto();
		// if (this.modeloDetalle.getListaIva0().size() > 0)
		// {
		
		
		double sinimp = 0.0;

		for (int i = 0; i < this.modeloDetalle.size(); i++) {
			sinimp = sinimp + modeloDetalle.get(i).getTotalItemComprado().doubleValue();
		}
		
		System.out.println("----------BASE IMPONIBLE---------------------->" + sinimp);
		
		
		
		SubtotalImpuesto sub = new SubtotalImpuesto(this.producto, 2, "1", BigDecimal.valueOf(12.00),
				BigDecimal.valueOf(sinimp),
				BigDecimal.valueOf(sinimp * 0.12));
		List<SubtotalImpuesto> lsSub = new ArrayList<SubtotalImpuesto>();
		lsSub.add(sub);
		item = obtieneTotal(lsSub);
		respuesta.getTotalImpuesto().add(item);
		// }
		/*
		 * if (this.modeloDetalle.getListaIva12().size() > 0) { item =
		 * obtieneTotal(this.modeloDetalle.getListaIva12());
		 * respuesta.getTotalImpuesto().add(item); } if
		 * (this.modeloDetalle.getListaNoIva().size() > 0) { item =
		 * obtieneTotal(this.modeloDetalle.getListaNoIva());
		 * respuesta.getTotalImpuesto().add(item); } if
		 * (this.modeloDetalle.getListaExentoIVA().size() > 0) { item =
		 * obtieneTotal(this.modeloDetalle.getListaExentoIVA());
		 * respuesta.getTotalImpuesto().add(item); } if
		 * (this.modeloDetalle.getListaICE().size() > 0) {
		 * respuesta.getTotalImpuesto().addAll(obtieneTotales(this.modeloDetalle.
		 * getListaICE())); } if (this.modeloDetalle.getListaIRBPNR().size() > 0) {
		 * respuesta.getTotalImpuesto().addAll(obtieneTotales(this.modeloDetalle.
		 * getListaIRBPNR())); }
		 */
		return respuesta;
	}

	private Factura.InfoFactura.TotalConImpuestos.TotalImpuesto obtieneTotal(List<SubtotalImpuesto> lista) {
		Factura.InfoFactura.TotalConImpuestos.TotalImpuesto impuesto = new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
		BigDecimal baseImponible = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;

		SubtotalImpuesto primer = (SubtotalImpuesto) lista.get(0);

		impuesto.setCodigo(String.valueOf(primer.getCodigoImpuesto()));
		impuesto.setCodigoPorcentaje("2");
		for (SubtotalImpuesto item : lista) {
			total = total.add(item.getSubtotal());
			baseImponible = baseImponible.add(item.getBaseImponible());
		}
		impuesto.setBaseImponible(baseImponible);
		impuesto.setTarifa(BigDecimal.valueOf(12));
		impuesto.setValor(total);

		return impuesto;
	}

	private List<Factura.InfoFactura.TotalConImpuestos.TotalImpuesto> obtieneTotales(List<SubtotalImpuesto> lista) {
		List<Factura.InfoFactura.TotalConImpuestos.TotalImpuesto> respuesta = new ArrayList();

		Factura.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuesto = null;

		BigDecimal baseImponibleGrupo = BigDecimal.ZERO;
		BigDecimal valorTotalGrupo = BigDecimal.ZERO;
		String codigoGrupo = null;
		ArrayList encontrados = new ArrayList();
		for (SubtotalImpuesto subtotal : lista) {
			totalImpuesto = this.facturaFactory.createFacturaInfoFacturaTotalConImpuestosTotalImpuesto();
			codigoGrupo = subtotal.getCodigo();
			Collections.sort(encontrados);
			baseImponibleGrupo = BigDecimal.ZERO;
			valorTotalGrupo = BigDecimal.ZERO;
			if (Collections.binarySearch(encontrados, codigoGrupo) < 0) {
				for (SubtotalImpuesto i : lista) {
					if (i.getCodigo().equals(codigoGrupo)) {
						baseImponibleGrupo = baseImponibleGrupo.add(i.getBaseImponible());
						valorTotalGrupo = valorTotalGrupo.add(i.getSubtotal());
					}
				}
				totalImpuesto.setCodigo(String.valueOf(subtotal.getCodigoImpuesto()));
				totalImpuesto.setCodigoPorcentaje(codigoGrupo);
				totalImpuesto.setBaseImponible(baseImponibleGrupo);
				totalImpuesto.setValor(valorTotalGrupo);
				respuesta.add(totalImpuesto);
				encontrados.add(codigoGrupo);
			}
		}
		return respuesta;
	}

	private Factura.Detalles generarDetalle() {
		Factura.Detalles resultado = new Factura.Detalles();// this.facturaFactory.createFacturaDetalles();
		for (int i = 0; i < this.modeloDetalle.size(); i++) {
			try {
				String codigoPrincipal = (String) this.modeloDetalle.get(i).getCodigoPrincipal();
				String codigoAuxiliar = (String) this.modeloDetalle.get(i).getCodigoAuxiliar();
				String descripcion = (String) this.modeloDetalle.get(i).getNombre();
				Producto prod = this.modeloDetalle.get(i);

				Factura.Detalles.Detalle detalle = new Factura.Detalles.Detalle();// this.facturaFactory.createFacturaDetallesDetalle();
				if ((prod.getCodigoPrincipal() != null) && (!prod.getCodigoPrincipal().isEmpty())) {
					detalle.setCodigoPrincipal(prod.getCodigoPrincipal());
				}
				if ((prod.getCodigoAuxiliar() != null) && (!prod.getCodigoAuxiliar().isEmpty())) {
					detalle.setCodigoAuxiliar(prod.getCodigoAuxiliar());
				}
				detalle.setDescripcion(prod.getNombre());
				detalle.setCantidad(
						new BigDecimal(Double.parseDouble(this.modeloDetalle.get(i).getCantidad().toString()))
								.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros());
				detalle.setPrecioUnitario((BigDecimal) this.modeloDetalle.get(i).getValorUnitario());
				detalle.setDescuento(BigDecimal.valueOf(0.00));
				detalle.setPrecioTotalSinImpuesto((BigDecimal) this.modeloDetalle.get(i).getValorUnitario());

				detalle.setImpuestos(obtenerImpuestosProducto(prod));

//                List<InformacionAdicionalProducto> listaInfo = prod.getInfoAdicionalList();
//                if (!listaInfo.isEmpty()) {
//                    Factura.Detalles.Detalle.DetallesAdicionales obj = new Factura.Detalles.Detalle.DetallesAdicionales();//this.facturaFactory.createFacturaDetallesDetalleDetallesAdicionales();
//                    for (InformacionAdicionalProducto item : listaInfo) {
//                        Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional det = new Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional();//this.facturaFactory.createFacturaDetallesDetalleDetallesAdicionalesDetAdicional();
//
//                        det.setNombre(item.getAtributo());
//                        det.setValor(item.getValor());
//                        obj.getDetAdicional().add(det);
//                    }
//                    detalle.setDetallesAdicionales(obj);
//                }
				resultado.getDetalle().add(detalle);
			} catch (Exception ex) {
				Logger.getLogger("Error").log(Level.SEVERE, null, ex);
			}
		}
		return resultado;
	}

	private Factura.Detalles.Detalle.Impuestos obtenerImpuestosProducto(Producto producto) {
		Factura.Detalles.Detalle.Impuestos result = new Factura.Detalles.Detalle.Impuestos();// this.facturaFactory.createFacturaDetallesDetalleImpuestos();
		List<SubtotalImpuesto> lista = new ArrayList();

		SubtotalImpuesto imp = new SubtotalImpuesto(producto, producto.getCodigoImpuesto(), "2",
				BigDecimal.valueOf(12.00), BigDecimal.valueOf(producto.getValorUnitario().doubleValue()),
				BigDecimal.valueOf(producto.getTotalItemComprado().doubleValue() * 0.12));
		lista.add(imp);
//  if (this.modeloDetalle.getListaIva0().size() > 0) {
//       lista.addAll(obtieneItems(lista, producto));
		// }
		/**
		 * if (this.modeloDetalle.getListaIva12().size() > 0) {
		 * lista.addAll(obtieneItems(this.modeloDetalle.getListaIva12(), producto)); }
		 * if (this.modeloDetalle.getListaNoIva().size() > 0) {
		 * lista.addAll(obtieneItems(this.modeloDetalle.getListaNoIva(), producto)); }
		 * if (this.modeloDetalle.getListaICE().size() > 0) {
		 * lista.addAll(obtieneItems(this.modeloDetalle.getListaICE(), producto)); } if
		 * (this.modeloDetalle.getListaExentoIVA().size() > 0) {
		 * lista.addAll(obtieneItems(this.modeloDetalle.getListaExentoIVA(), producto));
		 * } if (this.modeloDetalle.getListaIRBPNR().size() > 0) {
		 * lista.addAll(obtieneItems(this.modeloDetalle.getListaIRBPNR(), producto)); }
		 */
		for (SubtotalImpuesto s : lista) {
			Impuesto i = new Impuesto();
			i.setCodigo(String.valueOf(s.getCodigoImpuesto()));
			i.setCodigoPorcentaje(s.getCodigo());
			i.setTarifa(s.getPorcentaje().multiply(BigDecimal.valueOf(1L).setScale(0, RoundingMode.HALF_UP)));
			i.setBaseImponible(s.getBaseImponible());
			i.setValor(s.getSubtotal());

			result.getImpuesto().add(i);
		}
		return result;
	}

	private List<SubtotalImpuesto> obtieneItems(List<SubtotalImpuesto> lista, Producto producto) {
		List<SubtotalImpuesto> resultado = new ArrayList();
		for (SubtotalImpuesto s : lista) {
			if (s.verificarProducto(producto)) {
				resultado.add(s);
			}
		}
		return resultado;
	}

	private Factura.InfoAdicional generarInformacionAdicional() {
		Factura.InfoAdicional info = new Factura.InfoAdicional();// this.facturaFactory.createFacturaInfoAdicional();
		for (int i = 0; i < 3; i++) {
			/*
			 * Factura.InfoAdicional.CampoAdicional detalle = new
			 * Factura.InfoAdicional.CampoAdicional(); detalle.setNombre((String)"cliente");
			 * // otener datos desde fuente db detalle.setValue((String)"valor");// otener
			 * datos desde fuente db
			 * 
			 * info.getCampoAdicional().add(detalle);
			 */
		}
		return info;
	}

	public ClaveContingencia obtieneClaveDeAcceso(String secuencialComprobante, Emisor emisor, String serie,
			String claveDeAcceso, Date fechaEmision, String tipoComprobante) {
		ClaveContingencia clave = new ClaveContingencia();
		if (emisor != null) {
			serie = emisor.getCodigoEstablecimiento().concat(emisor.getCodPuntoEmision());
		}
		if ((emisor != null) && (emisor.getTipoEmision().equals("1"))) {
			claveDeAcceso = new ClaveDeAcceso().generaClave(fechaEmision, tipoComprobante, emisor.getRuc(),
					emisor.getTipoAmbiente(), serie, secuencialComprobante, emisor.getClaveInterna(), "1");

			clave.setCodigoComprobante(claveDeAcceso);
		} else if ((emisor != null)
				&& ((emisor.getTipoEmision().equals("2")) || (emisor.getTipoEmision().equals("3")))) {
			try {

				if (clave.getClave() != null) {
					claveDeAcceso = new ClaveDeAcceso().generaClaveContingencia(fechaEmision, tipoComprobante,
							clave.getClave(), emisor.getTipoEmision());

					clave.setCodigoComprobante(claveDeAcceso);
				} else {
					System.out.println(
							"No existen claves de contingencia, por favor cargue claves en el Sistema o cambie su estado de Emisión a: NORMAL INFORMACION IMPORTANTE");
				}
			} catch (Exception ex) {
				;
			}
		}
		return clave;
	}

}
