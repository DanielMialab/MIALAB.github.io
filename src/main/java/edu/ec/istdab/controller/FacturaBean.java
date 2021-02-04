package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import Facturacion.ComprobantesFacturacion;
import Facturacion.FacturacionElectronica;
import Facturacion.n_cst_autoriza;
import Facturacion.Util.ArchivoUtils;
import Facturacion.Util.TipoAmbienteEnum;
import Facturacion.Util.TipoComprobanteEnum;
import Modelos.ClaveContingencia;
import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Examen;
import edu.ec.istdab.model.Factura;
import edu.ec.istdab.model.ItemFactura;
import edu.ec.istdab.service.IExamenService;
import edu.ec.istdab.service.IFacturaService;
import edu.ec.istdab.util.EmailManager;

@Named
@ViewScoped
public class FacturaBean implements Serializable {

	@Inject
	private IFacturaService service;

	private Cliente currentCliente;
	private Examen currentExamen;
	private Factura currentFactura;
	private Double amount;

	@PostConstruct
	public void init() {
		this.setAmount(1.0);
		this.currentFactura = new Factura();
		this.currentFactura.setFecha(new Date());
		this.currentFactura.setCodigoEstablecimiento("001");
		this.currentFactura.setCodigoPuntoEmision("001");
		this.currentFactura.setSecuencia(service.obtenerMaximaSecuencia());
		this.currentFactura.setSecuenciaComprobante(obtenerSecuenciaString(this.getCurrentFactura().getSecuencia()));
	}

	public String obtenerSecuenciaString(Long maxSecuencia) {
		String counter = maxSecuencia.toString();
		int nuemroZeros = 9 - counter.length();
		String path = "";
		for (int i = 1; i <= nuemroZeros; i++) {
			path = path + "0";
		}

		path = path + maxSecuencia.toString();
		System.out.println("))))))))))))))))))))))))SIGUIENTE NUMERO SECUENCIAL)))))))))))))))))))))))))))))" + path);
		return path;

	}

	public List<Factura> obtenerFaturasClienteLogueado(Cliente clienteLogueao) {

		return service.obtenerFaturasPorCliente(clienteLogueao);
	}

	public void addNewItem() {

		System.out.println("cliente actual ++++++++++++++++++++++++++++++" + this.getCurrentCliente().getNombres() + " "
				+ this.getCurrentCliente().getApellidos());
		System.out.println("examen actual ------------------------------" + this.getCurrentExamen().getNombres());
		System.out.println("cantidada de examen ooooooooooooooooooooooooo" + this.getAmount());

		ItemFactura currentItem = new ItemFactura();
		currentItem.setExamenVinculado(this.getCurrentExamen());
		currentItem.setPrecioUnitarioActual(currentItem.getExamenVinculado().getPrecioVenta());
		currentItem.setCatindad(this.getAmount());

		if (!this.getCurrentFactura().getDetalleFacturacion().contains(currentItem)) {
			currentItem.setPrecioTotalItem(currentItem.getPrecioUnitarioActual() * currentItem.getCatindad());
			this.getCurrentFactura().agregarItemFactura(currentItem);
			this.getCurrentFactura()
					.setSubtotal(this.getCurrentFactura().getSubtotal() + currentItem.getPrecioTotalItem());
			this.getCurrentFactura().setIva(this.getCurrentFactura().getSubtotal() * 0.12);
			this.getCurrentFactura()
					.setTotal(this.getCurrentFactura().getSubtotal() + this.getCurrentFactura().getIva());

		} else {

			for (int i = 0; i < this.getCurrentFactura().getDetalleFacturacion().size(); i++) {
				if (this.getCurrentFactura().getDetalleFacturacion().get(i).equals(currentItem)) {
					this.getCurrentFactura().getDetalleFacturacion().get(i).setCatindad(
							this.getCurrentFactura().getDetalleFacturacion().get(i).getCatindad() + this.getAmount());
					this.getCurrentFactura().getDetalleFacturacion().get(i).setPrecioTotalItem(
							this.getCurrentFactura().getDetalleFacturacion().get(i).getPrecioUnitarioActual()
									* this.getCurrentFactura().getDetalleFacturacion().get(i).getCatindad());
					this.getCurrentFactura().setIva(this.getCurrentFactura().getSubtotal() * 0.12);
					this.getCurrentFactura()
							.setTotal(this.getCurrentFactura().getSubtotal() + this.getCurrentFactura().getIva());
				}
			}

			this.getCurrentFactura().setSubtotal(0.0);
			for (int i = 0; i < this.getCurrentFactura().getDetalleFacturacion().size(); i++) {
				this.getCurrentFactura().setSubtotal(this.getCurrentFactura().getSubtotal()
						+ this.getCurrentFactura().getDetalleFacturacion().get(i).getPrecioTotalItem());
				this.getCurrentFactura().setIva(this.getCurrentFactura().getSubtotal() * 0.12);
				this.getCurrentFactura()
						.setTotal(this.getCurrentFactura().getSubtotal() + this.getCurrentFactura().getIva());
			}
		}

	}

	public void removeItem(ItemFactura item) {

		if (this.getCurrentFactura().getDetalleFacturacion().contains(item)) {
			this.getCurrentFactura().getDetalleFacturacion().remove(item);
			this.getCurrentFactura().setSubtotal(this.getCurrentFactura().getSubtotal() - item.getPrecioTotalItem());
			this.getCurrentFactura().setIva(this.getCurrentFactura().getSubtotal() * 0.12);
			this.getCurrentFactura()
					.setTotal(this.getCurrentFactura().getSubtotal() + this.getCurrentFactura().getIva());
			System.out.println("----------------------------------------->item eiminado");
		}

	}

	public void save() {

		if (this.getCurrentFactura().getDetalleFacturacion().isEmpty()) {
			System.out.println(
					"======================================================================>NO SE PUEDE EMITIR UNA FACTURA VACÍA");
			return;
		}

		// TODO code application logic here

		// ejemplo llamada metodos Y clases
		ClaveContingencia claveContingencia = new ClaveContingencia();
		ArchivoUtils util = new ArchivoUtils();
		ComprobantesFacturacion comprobante = new ComprobantesFacturacion(
				this.currentCliente.getApellidos() + this.currentCliente.getNombres(),
				this.getCurrentFactura().getSecuenciaComprobante(), this.getCurrentFactura().getDetalleFacturacion());

		Modelos.Factura.Factura facturaLLena = comprobante.generarComprobante();
		String claveAcceso = facturaLLena.getInfoTributaria().getClaveAcceso();
		Long secuencial = Long.valueOf(facturaLLena.getInfoTributaria().getSecuencial());
		String NombreXml = claveAcceso + ".xml";
		String directorioCrear = System.getProperty("jboss.server.temp.dir")
				+ "\\COMPROBANTES_ELECTRONICOS\\GENERADOS\\" + NombreXml;
		String respuesta = ArchivoUtils.crearArchivoXml2(directorioCrear, facturaLLena, claveContingencia, secuencial,
				TipoComprobanteEnum.FACTURA.getCode());

		System.out.println("Iniciando facturacion");
		String archivoACrear = System.getProperty("jboss.server.temp.dir") + "\\COMPROBANTES_ELECTRONICOS\\GENERADOS\\"
				+ NombreXml;
		String dirFirmados = System.getProperty("jboss.server.temp.dir") + "\\COMPROBANTES_ELECTRONICOS\\FIRMADOS";
		String dirNoFirmados = System.getProperty("jboss.server.temp.dir") + "\\COMPROBANTES_ELECTRONICOS\\NOFIRMADOS";
		String dirAutorizados = System.getProperty("jboss.server.temp.dir")
				+ "\\COMPROBANTES_ELECTRONICOS\\AUTORIZADOS";

		String passwordFirmaDigital = "Atmatias13.";/// password firma digital
		String tipo = TipoAmbienteEnum.PRUEBAS.getCode();// TipoAmbienteEnum.PRODICCION.getCode();ambiente 1 desarrollo
															// 2 producciÃ³n
		String archivo_firma_xml = System.getProperty("jboss.server.temp.dir")
				+ "\\COMPROBANTES_ELECTRONICOS\\CERTIFICADO\\andrea_maribel_tapia_jaramillo.p12";
		FacturacionElectronica firma = new FacturacionElectronica();
		// ejemplo firma documento
		String respuestaFirma = firma.firmar_doc_elec_xml_1(
				archivoACrear.substring(archivoACrear.lastIndexOf("\\") + 1), dirFirmados,
				archivoACrear.substring(0, archivoACrear.lastIndexOf("\\") + 1), archivo_firma_xml,
				passwordFirmaDigital, "comprobante");

		String dirFirmados1 = System.getProperty("jboss.server.temp.dir") + "\\COMPROBANTES_ELECTRONICOS\\FIRMADOS\\"
				+ NombreXml;
		// ejemplo valida documento
		n_cst_autoriza respuestaValida = firma.validar_doc_individual_off(dirFirmados1, tipo, dirNoFirmados, NombreXml);
		System.out.println("respuesta factura : " + respuestaValida.claveAcceso + ": FIN RES");

		// ejemplo autoriza documento
		n_cst_autoriza respuestaAutoriza = firma.autorizar_doc_individual_off(respuestaValida.claveAcceso, tipo,
				dirAutorizados, NombreXml);

		if (respuestaAutoriza.estado == null) {
			System.out.println(
					"===================================================================================reenviando pedido de autorizacion en 3 2 1 0...>");
			respuestaAutoriza = firma.autorizar_doc_individual_off(respuestaValida.claveAcceso, tipo, dirAutorizados,
					NombreXml);
		}

//		System.out.println("respuesta Autoriza:" + respuestaAutoriza.estado);

		EmailManager em = new EmailManager();
		em.enviar(this.getCurrentCliente(), this.getCurrentFactura());

		try {
			this.getCurrentFactura().setCliente(currentCliente);
			this.getCurrentFactura().setClaveAcceso(NombreXml);
			this.service.registrar(this.getCurrentFactura());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Factura Guardada con éxito! \n" + "Email enviado a " + this.getCurrentCliente().getCorreo()
									+ "\n clave de acceso al SRI: " + NombreXml,
							"Aviso"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Cliente getCurrentCliente() {
		return currentCliente;
	}

	public void setCurrentCliente(Cliente currentCliente) {
		this.currentCliente = currentCliente;
	}

	public Examen getCurrentExamen() {
		return currentExamen;
	}

	public void setCurrentExamen(Examen currentExamen) {
		this.currentExamen = currentExamen;
	}

	public Factura getCurrentFactura() {
		return currentFactura;
	}

	public void setCurrentFactura(Factura currentFactura) {
		this.currentFactura = currentFactura;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
