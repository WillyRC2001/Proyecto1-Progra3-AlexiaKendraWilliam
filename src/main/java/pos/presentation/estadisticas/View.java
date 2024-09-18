package pos.presentation.estadisticas;
//==================================================================================================================
//nuevos imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
//==================================================================================================================
import pos.Application;
import pos.logic.Categoria;
import pos.logic.Producto;
import pos.logic.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.stream.IntStream;

public class View implements PropertyChangeListener {
    private JComboBox<Integer> añoDesdeCbx;
    private JComboBox<String > mesDesdeCbx;
    private JComboBox<Integer> añoHastaCbx2;
    private JComboBox<String> mesHastaCbx2;
    private JComboBox<Categoria>  categoriaCbx;
    private JButton agregarCategoria;
    private JButton agregarTodo;
    private JButton quitarCategoria;
    private JButton quitarTodo;
    private JTable list;
    private JLabel hastaLbl;
    private JLabel desdeLbl;
    private JLabel categoriaLbl;
    private JPanel panel;
    private JPanel JpanelGrafico; //NUEVO

    public JPanel getPanel() {
        return panel;
    }

    public View() {
        JpanelGrafico.setPreferredSize(new Dimension(800, 600));
        int currentYear = LocalDate.now().getYear();
        // Llenar los JComboBox de años (5 años antes y 5 años después del actual)
        IntStream.range(currentYear - 5, currentYear + 6)
                .forEach(year -> {
                    añoDesdeCbx.addItem(year);
                    añoHastaCbx2.addItem(year);
                });

        // Llenar los JComboBox de meses con el formato "1-Enero", "2-Febrero", etc.
        IntStream.range(1, 13).forEach(month -> {
            String monthName = Month.of(month).getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault());
            // Asegurarse de que el nombre del mes empiece con mayúscula
            monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
            mesDesdeCbx.addItem(month + "-" + monthName);
            mesHastaCbx2.addItem(month + "-" + monthName);
        });

        agregarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Categoria categoria = (Categoria) categoriaCbx.getSelectedItem();
                    try {
                            controller.agregar(categoria);
                            Rango R = new Rango((int) añoDesdeCbx.getSelectedItem(),(int) añoHastaCbx2.getSelectedItem(),mesDesdeCbx.getSelectedIndex(),mesHastaCbx2.getSelectedIndex());
                            Model.setRango(R);
                            controller.actualizarData();

                        JOptionPane.showMessageDialog(panel, "Categoria agregada", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        agregarTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (validarAnios() && validarMeses()) {
                    try {
                        controller.agregarTodo();
                        Rango R = new Rango((int) añoDesdeCbx.getSelectedItem(), (int) añoHastaCbx2.getSelectedItem(),mesDesdeCbx.getSelectedIndex(),mesHastaCbx2.getSelectedIndex());
                        Model.setRango(R);
                        controller.actualizarData();
                        JOptionPane.showMessageDialog(panel, "Categorias agregadas", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        quitarTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarAnios() && validarMeses()) {
                    try {
                        controller.clear();
                        Rango R = new Rango((int) añoDesdeCbx.getSelectedItem(),(int) añoHastaCbx2.getSelectedItem(),mesDesdeCbx.getSelectedIndex(),mesHastaCbx2.getSelectedIndex());
                        Model.setRango(R);
                        controller.actualizarData();
                        JOptionPane.showMessageDialog(panel, "Eliminadas", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        quitarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
               if (validarCategoria()) {
                    Categoria categoria = (Categoria) categoriaCbx.getSelectedItem();
                    try {
                        controller.eliminar(categoria);
                        Rango R = new Rango((int) añoDesdeCbx.getSelectedItem(),(int) añoHastaCbx2.getSelectedItem(),mesDesdeCbx.getSelectedIndex(),mesHastaCbx2.getSelectedIndex());
                        Model.setRango(R);
                        controller.actualizarData();
                        JOptionPane.showMessageDialog(panel, "Eliminado", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private boolean validarAnios() {
        boolean valido = true;

        Integer añoDesde = (Integer) añoDesdeCbx.getSelectedItem();
        Integer añoHasta = (Integer) añoHastaCbx2.getSelectedItem();

        if (añoDesde == null) {
            valido = false;
            desdeLbl.setBorder(Application.BORDER_ERROR);
            desdeLbl.setToolTipText("Año desde requerido");
        } else {
            desdeLbl.setBorder(null);
            desdeLbl.setToolTipText(null);
        }

        if (añoHasta == null) {
            valido = false;
            hastaLbl.setBorder(Application.BORDER_ERROR);
            hastaLbl.setToolTipText("Año hasta requerido");
        } else {
            hastaLbl.setBorder(null);
            hastaLbl.setToolTipText(null);
        }

        if (añoDesde != null && añoHasta != null) {
            if (añoDesde > añoHasta) {
                valido = false;
                desdeLbl.setBorder(Application.BORDER_ERROR);
                hastaLbl.setBorder(Application.BORDER_ERROR);
                desdeLbl.setToolTipText("Año desde no puede ser mayor que Año hasta");
                hastaLbl.setToolTipText("Año hasta no puede ser menor que Año desde");
            }
        }

        return valido;
    }

    private boolean validarMeses() {
        boolean valido = true;

        Integer añoDesde = (Integer) añoDesdeCbx.getSelectedItem();
        Integer añoHasta = (Integer) añoHastaCbx2.getSelectedItem();
        String mesDesdeStr = (String) mesDesdeCbx.getSelectedItem();
        String mesHastaStr = (String) mesHastaCbx2.getSelectedItem();

        // Extraer el número del mes desde los Strings "1-Enero", "2-Febrero", etc.
        Integer mesDesde = mesDesdeStr != null ? Integer.parseInt(mesDesdeStr.split("-")[0]) : null;
        Integer mesHasta = mesHastaStr != null ? Integer.parseInt(mesHastaStr.split("-")[0]) : null;

        if (añoDesde != null && añoHasta != null && mesDesde != null && mesHasta != null) {
            if (añoDesde.equals(añoHasta)) {
                if (mesDesde > mesHasta) {
                    valido = false;
                    mesDesdeCbx.setBorder(Application.BORDER_ERROR);
                    mesHastaCbx2.setBorder(Application.BORDER_ERROR);
                    mesDesdeCbx.setToolTipText("Mes desde no puede ser mayor que Mes hasta");
                    mesHastaCbx2.setToolTipText("Mes hasta no puede ser menor que Mes desde");
                } else {
                    mesDesdeCbx.setBorder(null);
                    mesHastaCbx2.setBorder(null);
                    mesDesdeCbx.setToolTipText(null);
                    mesHastaCbx2.setToolTipText(null);
                }
            } else {
                // Limpia errores de mes si los años son válidos
                mesDesdeCbx.setBorder(null);
                mesHastaCbx2.setBorder(null);
                mesDesdeCbx.setToolTipText(null);
                mesHastaCbx2.setToolTipText(null);
            }
        }

        return valido;
    }

    private boolean validarCategoria() {
        boolean valido = true;

        if (categoriaCbx.getSelectedItem() == null) {
            valido = false;
            categoriaLbl.setBorder(Application.BORDER_ERROR);
            categoriaLbl.setToolTipText("Categoría requerida");
        } else {
            categoriaLbl.setBorder(null);
            categoriaLbl.setToolTipText(null);
        }

        return valido;
    }

    private boolean validate() {
        boolean valido = true;

        valido &= validarAnios();
        valido &= validarMeses();
        valido &= validarCategoria();

        return valido;
    }

    // MVC
     model Model;
    controller controller;

    public void setModel(model model) {
        this.Model = model;
        model.addPropertyChangeListener(this);

    }

    public void setController(controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case model.CATEGORIES_ALL:
                this.categoriaCbx.setModel(new DefaultComboBoxModel(this.Model.getCategoriasAll().toArray()));
                break;
            case model.RANGE:
                añoDesdeCbx.setSelectedItem(Model.getRango().getAnoDesde());
                añoHastaCbx2.setSelectedItem(Model.getRango().getAnoHasta());
                mesDesdeCbx.setSelectedItem(Model.getRango().getMesDesde());
                mesHastaCbx2.setSelectedItem(Model.getRango().getMesHasta());
                break;
            case model.DATA:
                list.setModel(Model.getTableModel());
                Grafico();
                break;
            case model.COLS:
                list.setModel(Model.getTableModel());
                break;
        }
        this.panel.revalidate();
    }
    public void Grafico() {
        if(Model.getCategorias().size() > 0){
            DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
            int añoDesde = (int) añoDesdeCbx.getSelectedItem();
            int mesDesde = mesDesdeCbx.getSelectedIndex() + 1;
            int añoHasta = (int) añoHastaCbx2.getSelectedItem();
            int mesHasta = mesHastaCbx2.getSelectedIndex() + 1;

            for (int i = 0; i < Model.getCategorias().size(); i++) {
                // Iterar desde añoDesde hasta añoHasta
                for (int año = añoDesde; año <= añoHasta; año++) {
                    // Determinar el mes de inicio y fin para el año actual
                    int inicioMes = (año == añoDesde) ? mesDesde : 1;
                    int finMes = (año == añoHasta) ? mesHasta : 12;

                    // Iterar a través de los meses en el rango determinado
                    for (int mes = inicioMes; mes <= finMes; mes++) {
                        line_chart_dataset.addValue(
                                Service.instance().getVentas(Model.getCategorias().get(i), año, mes),
                                Model.getCategorias().get(i).getNombre(),
                                String.format("%d-%02d", año, mes + 1) // Formato año-mes para la etiqueta
                        );
                    }
                }
            }
            // usar fileReader & BufferedReader
            JFreeChart lineChartObject = ChartFactory.createLineChart(
                    "Ventas por Mes", "Meses", "Ventas",
                    line_chart_dataset, PlotOrientation.VERTICAL, true, true, false);
            // Mostrar el gráfico en un JPanel o JFrame
            ChartPanel chartPanel = new ChartPanel(lineChartObject);

            // Hacer que el gráfico tenga un tamaño más grande
            chartPanel.setPreferredSize(new Dimension(800, 600));  // Aquí configuras el tamaño

            // Configurar JpanelGrafico con un BorderLayout
            if (JpanelGrafico != null) {
                JpanelGrafico.setLayout(new BorderLayout());
                JpanelGrafico.removeAll();
                JpanelGrafico.add(chartPanel, BorderLayout.CENTER);
                JpanelGrafico.revalidate();
                JpanelGrafico.repaint();
            }
        }
    }
}

