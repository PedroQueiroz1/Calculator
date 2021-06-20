package br.com.noname.calc.visao;

import javax.swing.*;
import java.awt.*;

public class Calculadora extends JFrame {

    public Calculadora() {

        organizarLayout();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(232,322);
        setLocationRelativeTo(null);
        setTitle("Calculadora");
        setVisible(true);
    }

    private void organizarLayout() {
        setLayout(new BorderLayout());
        var display = new Display();
        display.setPreferredSize(new Dimension(233, 60));
        add(display, BorderLayout.NORTH);
        var teclado = new Teclado();
        add(teclado, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        new Calculadora();
    }
}
