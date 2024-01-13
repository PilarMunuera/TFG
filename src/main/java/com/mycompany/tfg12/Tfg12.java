package com.mycompany.tfg12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;


// Clase que representa un gasto individual
class Gasto {
    private String descripcion;
    private double cantidad;
    private String moneda;

    public Gasto(String descripcion, double cantidad, String moneda) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.moneda = moneda;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion + ": " + cantidad + " " + moneda;
    }
}

public class Tfg12 extends JFrame {
    private List<Gasto> gastos;
    private JTextField descripcionTextField;
    private JTextField cantidadTextField;
    private JComboBox<String> monedaComboBox;

    private DefaultTableModel crearModeloDeTabla() {
        String[] columnNames = {"Descripción", "Cantidad", "Moneda"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Gasto gasto : gastos) {
            Object[] row = new Object[] { gasto.getDescripcion(), gasto.getCantidad(), gasto.getMoneda() };
            model.addRow(row);
        }

        return model;
    }

    public Tfg12() {
        gastos = new ArrayList<>();

        // Configuración de la ventana
        setTitle("Gestor de Gastos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Panel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(225, 225, 225)); 
        inputPanel.setBorder(BorderFactory.createTitledBorder("Agregar Gasto"));
        inputPanel.setLayout(new GridLayout(0, 2, 10, 10));
        inputPanel.add(new JLabel("Descripción:"));
        descripcionTextField = new JTextField();
        inputPanel.add(descripcionTextField);
        inputPanel.add(new JLabel("Cantidad:"));
        cantidadTextField = new JTextField();
        inputPanel.add(cantidadTextField);
        monedaComboBox = new JComboBox<>(new String[]{"EUR", "USD", "GBP", "JPY", "MXN"}); 
        inputPanel.add(new JLabel("Moneda:"));
        inputPanel.add(monedaComboBox);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(225, 225, 225)); 
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(new Color(130, 180, 210)); 
        addButton.setForeground(Color.WHITE); 
        JButton showButton = new JButton("Mostrar Gastos");
        showButton.setBackground(new Color(100, 150, 190)); 
        showButton.setForeground(Color.WHITE); 
        JButton sumButton = new JButton("Mostrar Suma Total");
        sumButton.setBackground(new Color(120, 160, 180));
        sumButton.setForeground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(showButton);
        buttonPanel.add(sumButton);

        // Contenedor principal
        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout(10, 10));
        mainContainer.add(inputPanel, BorderLayout.NORTH);
        mainContainer.add(buttonPanel, BorderLayout.CENTER);

        // Eventos de los botones
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarGasto();
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGastos();
            }
        });

        sumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSumaTotal();
            }
        });
    }

    private void agregarGasto() {
        String descripcion = descripcionTextField.getText();
        try {
            double cantidad = Double.parseDouble(cantidadTextField.getText());
            String moneda = (String) monedaComboBox.getSelectedItem();
            gastos.add(new Gasto(descripcion, cantidad, moneda));
            descripcionTextField.setText("");
            cantidadTextField.setText("");
            JOptionPane.showMessageDialog(this, "Gasto agregado con éxito.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarGastos() {
        DefaultTableModel modelo = crearModeloDeTabla();
        JTable table = new JTable(modelo);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Gastos Registrados", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarSumaTotal() {
        String monedaSeleccionada = (String) monedaComboBox.getSelectedItem();
        double sumaTotal = calcularSumaTotal(monedaSeleccionada);
        JOptionPane.showMessageDialog(this, "Suma Total de Gastos en " + monedaSeleccionada + ": " + sumaTotal, "Suma Total", JOptionPane.INFORMATION_MESSAGE);
    }

    private double calcularSumaTotal(String moneda) {
        double sumaTotal = 0;
        for (Gasto gasto : gastos) {
            if (gasto.getMoneda().equals(moneda)) {
                sumaTotal += gasto.getCantidad();
            } else {
                // Aquí implementarías la conversión de moneda
            }
        }
        return sumaTotal;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tfg12().setVisible(true);
            }
        });
    }
}
