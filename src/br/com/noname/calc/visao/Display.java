package br.com.noname.calc.visao;

import br.com.noname.calc.modelo.Memoria;
import br.com.noname.calc.modelo.MemoriaObservador;

import javax.swing.*;
import javax.swing.text.FlowView;
import java.awt.*;

public class Display extends JPanel implements MemoriaObservador {

    private final JLabel label;

    public Display() {
        Memoria.getMem().adicionarObservador(this);

        setBackground(new Color(46,49,50));
        label = new JLabel(Memoria.getMem().getTextoAtual());
        add(label);
        label.setForeground(Color.white);
        label.setFont(new Font("courier",Font.PLAIN,30));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10,25));
    }

    @Override
    public void valorAlterado(String novoValor) {
    label.setText(novoValor);
    }
}
