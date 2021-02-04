package edu.ec.istdab.util;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import com.itextpdf.text.FontFactory;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import edu.ec.istdab.model.Factura;

import com.itextpdf.text.Paragraph;

@Named
@ViewScoped

public class ReportManager implements Serializable {

	public void imprimir(Factura factura) {

		Document document = new Document(PageSize.LETTER);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			PdfWriter.getInstance(document, baos);
			// METADATA
//			AlumnoDao alumnodaos = new AlumnoDao();
//			List<Alumno> alList = alumnodaos.buscarTodos();
			document.open();

			document.add(new Paragraph(" FACTURAS Nro : " + factura.getCodigoEstablecimiento() + "-"
					+ factura.getCodigoPuntoEmision() + "-" + factura.getSecuenciaComprobante()));
			document.add(new Paragraph(
					" Cliente : " + factura.getCliente().getNombres() + " " + factura.getCliente().getApellidos()));

			document.add(new Paragraph(" Autorización : " + factura.getClaveAcceso()));

			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			String date = formatter.format(factura.getFecha());
			document.add(new Paragraph("Fecha Facturación: " + date));
			document.add(new Paragraph("\n"));

			PdfPTable table = new PdfPTable(4);

			PdfPCell cell = new PdfPCell(new Paragraph("MIALAB LABORATORIO CLINICO", FontFactory.getFont("arial", // fuente
					20, // tamaño
					Font.BOLD, // estilo
					BaseColor.WHITE)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.BLACK);
			cell.setColspan(6);
			table.addCell(cell);

			PdfPCell cell2 = new PdfPCell(new Paragraph(
					"Dra. ANDREA MARIBEL TAPIA JARAMILLO \n  RUC: 1104707813001 \n Dir: Lauro Guererro 411-16, Venezuela Y Maximiliano Rodriguez",
					FontFactory.getFont("arial", // fuente
							10, // tamaño
							Font.BOLD, // estilo
							BaseColor.WHITE)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBackgroundColor(BaseColor.BLACK);
			cell2.setColspan(6);
			table.addCell(cell2);
			document.add(table);

			PdfPTable table2 = new PdfPTable(4);
			table2.addCell("Cantidad");
			table2.addCell("Detalle");
			table2.addCell("V. Unit");
			table2.addCell("Subtotal");

			for (int i = 0; i < factura.getDetalleFacturacion().size(); i++) {
				System.out.println();
				table2.addCell(factura.getDetalleFacturacion().get(i).getCatindad().toString());
				table2.addCell(factura.getDetalleFacturacion().get(i).getExamenVinculado().getNombres().toString());
				table2.addCell(factura.getDetalleFacturacion().get(i).getExamenVinculado().getPrecioVenta() + "");
				table2.addCell(factura.getDetalleFacturacion().get(i).getPrecioTotalItem() + "");
			}

			document.add(table2);

			document.add(new Paragraph("\n"));
			PdfPTable table3 = new PdfPTable(2);
			table3.addCell("Subtotal:");
			table3.addCell(factura.getSubtotal() + "");
			table3.addCell("IVA 12%");
			table3.addCell("" + factura.getIva());
			table3.addCell("TOTAL:");
			table3.addCell("" + factura.getTotal());

			document.add(table3);
		} catch (Exception ex) {
			System.out.println("Error " + ex.getMessage());
		}
		document.close();
		FacesContext context = FacesContext.getCurrentInstance();
		Object response = context.getExternalContext().getResponse();
		if (response instanceof HttpServletResponse) {
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.setContentType("application/pdf");
			hsr.setHeader("Content-disposition", "attachment");
			hsr.setContentLength(baos.size());
			try {
				ServletOutputStream out = hsr.getOutputStream();
				baos.writeTo(out);
				out.flush();
			} catch (IOException ex) {
				System.out.println("Error:  " + ex.getMessage());
			}
			context.responseComplete();
		}
	}

}
