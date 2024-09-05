package pos.presentation.históricos;


import pos.Application;
import pos.logic.Factura;
import pos.logic.Linea;
import pos.logic.Service;

import pos.presentation.históricos.View;

import javax.swing.*;
import java.io.FileNotFoundException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;


//public class Controller {
//    View view;
//    Model model;
//
//
//    public Controller(View view, Model model) {
//        model.init(Service.instance().search(new Factura()) , Service.instance().search(new Linea()) );
//        this.view = view;
//        this.model = model;
//        view.setController(this);
//        view.setModel(model);
//    }
//
//    public void search(Factura filter) throws Exception {
//        model.setFilter(filter);
//        model.setMode(Application.MODE_CREATE);
//        model.setCurrent(new Factura());
//        model.setList(Service.instance().search(model.getFilter()));
//    }
//
//    public void clear() {
//        model.setMode(Application.MODE_CREATE);
//        model.setCurrent(new Factura());
//    }
//    public void generatePDF() {
//        // Ruta donde se guardará el PDF
//        String pdfPath = "historico_reporter.pdf";
//
//        try {
//            // Crear escritor de PDF
//            PdfWriter writer = new PdfWriter(pdfPath);
//            // Crear documento PDF
//            PdfDocument pdfDoc = new PdfDocument(writer);
//            Document document = new Document(pdfDoc);
//
//            // Añadir un título al PDF
//            document.add(new Paragraph("Historico"));
//
//            // Crear una tabla con el número de columnas necesarias
//            Table table = new Table(5); // 6 columnas: código, descripción, unidad, precio, existencia, categoría
//            table.addHeaderCell(new Cell().add(new Paragraph("Número")));
//            table.addHeaderCell(new Cell().add(new Paragraph("Cliente")));
//            table.addHeaderCell(new Cell().add(new Paragraph("Cajero")));
//            table.addHeaderCell(new Cell().add(new Paragraph("Fecha")));
//            table.addHeaderCell(new Cell().add(new Paragraph("Importe")));
//
//            // Añadir datos de facturas a la tabla
//            for (Factura factura : model.getList()) {
//                table.addCell(new Cell().add(new Paragraph(factura.getNumero())));
//                table.addCell(new Cell().add(new Paragraph(factura.getCliente().getNombre())));
//                table.addCell(new Cell().add(new Paragraph(factura.getCajero().getNombre())));
//                table.addCell(new Cell().add(new Paragraph(factura.getFecha().toString())));
//                table.addCell(new Cell().add(new Paragraph(String.valueOf(factura.SubTotal()))));
//            }
//
//// Crear una tabla con 8 columnas (una para cada propiedad de Linea)
//            Table lineaTable = new Table(8);
//
//            // Añadir encabezados de tabla para Linea
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Código")));
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Artículo")));
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Categoría")));
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Cantidad")));
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Precio")));
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Descuento")));
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Neto")));
//            lineaTable.addHeaderCell(new Cell().add(new Paragraph("Importe")));
//
//            // Añadir datos de Linea a la tabla
//            for (Linea linea : model.getListL()) {
//                lineaTable.addCell(new Cell().add(new Paragraph(linea.getCodigo())));
//                lineaTable.addCell(new Cell().add(new Paragraph(linea.getProducto().getCodigo())));
//                lineaTable.addCell(new Cell().add(new Paragraph(linea.getProducto().getCategoria().getNombre())));
//                lineaTable.addCell(new Cell().add(new Paragraph(String.valueOf(linea.getCantidad()))));
//                lineaTable.addCell(new Cell().add(new Paragraph(String.valueOf(linea.getProducto().getPrecioUnitario()))));
//                lineaTable.addCell(new Cell().add(new Paragraph(String.valueOf(linea.getDescuento()))));
//                lineaTable.addCell(new Cell().add(new Paragraph(String.valueOf(linea.Neto()))));
//                lineaTable.addCell(new Cell().add(new Paragraph(String.valueOf(linea.Importe()))));
//            }
//
//            // Añadir la tabla al documento
//            document.add(table);
//            document.add(lineaTable);
//            // Cerrar el documento
//            document.close();
//
//            JOptionPane.showMessageDialog(null, "PDF generado correctamente: " + pdfPath, "Información", JOptionPane.INFORMATION_MESSAGE);
//
//        } catch (FileNotFoundException e) {
//            JOptionPane.showMessageDialog(null, "Error al crear PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//}
