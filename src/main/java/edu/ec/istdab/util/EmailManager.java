package edu.ec.istdab.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Factura;

public class EmailManager {
	public void enviar() {
		final String username = "danieltj.198@gmail.com";
		final String password = "Alexander18.";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("victorlies@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("fernando.davilat@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Estimado cliente," + "\n\n Le damos la bienvenida mediante TLS!");
			Transport.send(message);
			System.out.println("Correcto!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void enviar(Cliente cliente, Factura factura) {

		String facturaContenido = "Estimado " + cliente.getNombres() + " " + cliente.getApellidos()
				+ " se ha emitido la  Factura Nro:" + factura.getCodigoEstablecimiento() + "-"
				+ factura.getCodigoPuntoEmision() + "-" + factura.getSecuenciaComprobante() + "\n" + "por el valor de: "
				+ factura.getTotal() + " IVA: $" + factura.getIva() + " por concepto de la compra de: \n";

		String cont = "/-------------------/-------------------/--------------/---------------/\n/  Cantidad    /     Detalle   /     V.unit    /    V.Tot      /\n/-------------------/-------------------/--------------/---------------/\n";

		for (int i = 0; i < factura.getDetalleFacturacion().size(); i++) {
			cont = cont + "/--------" + factura.getDetalleFacturacion().get(i).getCatindad() + "--------/--------"
					+ factura.getDetalleFacturacion().get(i).getExamenVinculado().getNombres() + "-----------/-----"
					+ factura.getDetalleFacturacion().get(i).getExamenVinculado().getPrecioVenta() + "---------/-------"
					+ factura.getDetalleFacturacion().get(i).getPrecioTotalItem() + "--------/\n";
		}

		facturaContenido = facturaContenido + cont;

		final String username = "danieltj.198@gmail.com";
		final String password = "Alexander18.";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("victorlies@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cliente.getCorreo()));
			message.setSubject("MIALAB FACTURA ELECTRONICA Nro:" + factura.getCodigoEstablecimiento() + "-"
					+ factura.getCodigoPuntoEmision() + "-" + factura.getSecuenciaComprobante());
			message.setText(facturaContenido);
			Transport.send(message);
			System.out.println("Correcto!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}