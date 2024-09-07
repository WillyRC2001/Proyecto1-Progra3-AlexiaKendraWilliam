package pos.presentation.productos;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Categoria;
import pos.logic.Service;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import javax.swing.*;
import java.io.FileNotFoundException;


public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Producto()),Service.instance().search(new Categoria()));
        //model.init(Service.instance().search(new Categoria()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }
    public void search(Producto filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Producto p) throws Exception {
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(p);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(p);
                break;
        }
        model.setFilter(new Producto());
        search(model.getFilter());
    }

    public void edit(int row) {
        Producto p = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(p));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
    }

    public void generatePDF() {
        // Ruta donde se guardará el PDF
        String pdfPath = "productos_reporter.pdf";

        try {
            // Crear escritor de PDF
            PdfWriter writer = new PdfWriter(pdfPath);
            // Crear documento PDF
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Añadir un título al PDF
            document.add(new Paragraph("Lista de Productos"));

            // Crear una tabla con el número de columnas necesarias
            Table table = new Table(6); // 6 columnas: código, descripción, unidad, precio, existencia, categoría

            // Añadir encabezados de tabla
            table.addHeaderCell(new Cell().add(new Paragraph("Código")));
            table.addHeaderCell(new Cell().add(new Paragraph("Descripción")));
            table.addHeaderCell(new Cell().add(new Paragraph("Unidad")));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio")));
            table.addHeaderCell(new Cell().add(new Paragraph("Existencia")));
            table.addHeaderCell(new Cell().add(new Paragraph("Categoría")));

            // Añadir datos de productos a la tabla
            for (Producto producto : model.getList()) {
                table.addCell(new Cell().add(new Paragraph(producto.getCodigo())));
                table.addCell(new Cell().add(new Paragraph(producto.getDescripcion())));
                table.addCell(new Cell().add(new Paragraph(producto.getUnidadMedida())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(producto.getPrecioUnitario()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(producto.getExistencias()))));
                table.addCell(new Cell().add(new Paragraph(producto.getCategoria().getNombre())));
            }

            // Añadir la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();

            JOptionPane.showMessageDialog(null, "PDF generado correctamente: " + pdfPath, "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al crear PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
