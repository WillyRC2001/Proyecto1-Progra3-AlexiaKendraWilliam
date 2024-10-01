package pos.presentation.clientes;

import pos.Application;
import pos.logic.Cliente;
import pos.logic.Producto;
import pos.logic.Service;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init();
        this.view = view;
        this.model = model;
        model.setList(Service.instance().search(new Cliente()));
        view.setController(this);
        view.setModel(model);
    }

    public void search(Cliente filter) throws  Exception{
        model.setFilter(filter);

        model.setMode(Application.MODE_CREATE);
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Cliente e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Cliente());
        search(model.getFilter());
    }

    public void edit(int row){
        Cliente e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cliente());
    }

    public void generatePdf() {
        String dest = "clientes_report.pdf"; // Ruta donde se guardará el PDF
        List<Cliente> clientes = model.getList();

        try {
            // Inicializa el PDF Writer
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Agrega el título
            document.add(new Paragraph("Lista de Clientes"));

            // Agrega los clientes al PDF
            for (Cliente cliente : clientes) {
                document.add(new Paragraph("ID: " + cliente.getId()));
                document.add(new Paragraph("Nombre: " + cliente.getNombre()));
                document.add(new Paragraph("Teléfono: " + cliente.getTelefono()));
                document.add(new Paragraph("Email: " + cliente.getEmail()));
                document.add(new Paragraph("Descuento: " + cliente.getDescuento()));
                document.add(new Paragraph("------------------------------------------"));
            }

            // Cierra el documento
            document.close();

            // Notifica al usuario
            JOptionPane.showMessageDialog(null, "PDF generado exitosamente en: " + dest, "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
