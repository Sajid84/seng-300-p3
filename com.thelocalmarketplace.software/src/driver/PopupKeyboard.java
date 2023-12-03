// Liam Major			- 30223023
// Md Abu Sinan			- 30154627
// Ali Akbari			- 30171539
// Shaikh Sajid Mahmood	- 30182396
// Abdullah Ishtiaq		- 30153185
// Adefikayo Akande		- 30185937
// Alecxia Zaragoza		- 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal			- 30159559
// Emmanuel Trinidad	- 30172372
// Gurjit Samra			- 30172814
// Kelvin Jamila		- 30117164
// Kevlam Chundawat		- 30180662
// Logan Miszaniec		- 30156384
// Maleeha Siddiqui		- 30179762
// Michael Hoang		- 30123605
// Nezla Annaisha		- 30123223
// Nicholas MacKinnon	- 30172737
// Ohiomah Imohi		- 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman			- 30169819

package driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupKeyboard extends JFrame implements ActionListener {
    public JButton q, w, e1, r, t, y, u, i, o, p, a, s, d, f, g, h, j, k, l, z, x, c, v, b, n, m, shift, numbers, space, fullstop, enter, backspace, comma, shifted, symbol, numbers1, abc;

    public JPanel panel;
    public JPanel panel_1;
    public JPanel panel_2;

    private JTextField parentTextField;

    public void setTextboxFocus(JTextField textField) {
        parentTextField = textField;
        parentTextField.setFocusable(true);
    }

    public PopupKeyboard() {
        getContentPane().setBackground(Color.BLACK);
        setSize(1404, 376);
        setUndecorated(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(12, 12, 1380, 78);
        getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 10, 10, 0));

        q = new JButton("q");
        q.setFocusable(false);
        q.addActionListener(this);
        q.setBackground(SystemColor.inactiveCaption);
        q.setForeground(Color.WHITE);
        q.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(q);

        w = new JButton("w");
        w.setFocusable(false);
        w.addActionListener(this);
        w.setBackground(SystemColor.inactiveCaption);
        w.setForeground(Color.WHITE);
        w.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(w);

        e1 = new JButton("e");
        e1.setFocusable(false);
        e1.addActionListener(this);
        e1.setBackground(SystemColor.inactiveCaption);
        e1.setForeground(Color.WHITE);
        e1.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(e1);

        r = new JButton("r");
        r.setFocusable(false);
        r.addActionListener(this);
        r.setBackground(SystemColor.inactiveCaption);
        r.setForeground(Color.WHITE);
        r.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(r);

        t = new JButton("t");
        t.setFocusable(false);
        t.addActionListener(this);
        t.setBackground(SystemColor.inactiveCaption);
        t.setForeground(Color.WHITE);
        t.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(t);

        y = new JButton("y");
        y.setFocusable(false);
        y.addActionListener(this);
        y.setBackground(SystemColor.inactiveCaption);
        y.setForeground(Color.WHITE);
        y.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(y);

        u = new JButton("u");
        u.setFocusable(false);
        u.addActionListener(this);
        u.setBackground(SystemColor.inactiveCaption);
        u.setForeground(Color.WHITE);
        u.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(u);

        i = new JButton("i");
        i.setFocusable(false);
        i.addActionListener(this);
        i.setBackground(SystemColor.inactiveCaption);
        i.setForeground(Color.WHITE);
        i.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(i);

        o = new JButton("o");
        o.setFocusable(false);
        o.addActionListener(this);
        o.setBackground(SystemColor.inactiveCaption);
        o.setForeground(Color.WHITE);
        o.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(o);

        p = new JButton("p");
        p.setFocusable(false);
        p.addActionListener(this);
        p.setBackground(SystemColor.inactiveCaption);
        p.setForeground(Color.WHITE);
        p.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel.add(p);

        panel_1 = new JPanel();
        panel_1.setBackground(Color.BLACK);
        panel_1.setBounds(76, 102, 1252, 78);
        getContentPane().add(panel_1);
        panel_1.setLayout(new GridLayout(0, 9, 10, 0));

        a = new JButton("a");
        a.setFocusable(false);
        a.addActionListener(this);
        a.setBackground(SystemColor.inactiveCaption);
        a.setForeground(Color.WHITE);
        a.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(a);

        s = new JButton("s");
        s.setFocusable(false);
        s.addActionListener(this);
        s.setBackground(SystemColor.inactiveCaption);
        s.setForeground(Color.WHITE);
        s.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(s);

        d = new JButton("d");
        d.setFocusable(false);
        d.addActionListener(this);
        d.setBackground(SystemColor.inactiveCaption);
        d.setForeground(Color.WHITE);
        d.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(d);

        f = new JButton("f");
        f.setFocusable(false);
        f.addActionListener(this);
        f.setBackground(SystemColor.inactiveCaption);
        f.setForeground(Color.WHITE);
        f.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(f);

        g = new JButton("g");
        g.setFocusable(false);
        g.addActionListener(this);
        g.setBackground(SystemColor.inactiveCaption);
        g.setForeground(Color.WHITE);
        g.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(g);

        h = new JButton("h");
        h.setFocusable(false);
        h.addActionListener(this);
        h.setBackground(SystemColor.inactiveCaption);
        h.setForeground(Color.WHITE);
        h.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(h);

        j = new JButton("j");
        j.setFocusable(false);
        j.addActionListener(this);
        j.setBackground(SystemColor.inactiveCaption);
        j.setForeground(Color.WHITE);
        j.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(j);

        k = new JButton("k");
        k.setFocusable(false);
        k.addActionListener(this);
        k.setBackground(SystemColor.inactiveCaption);
        k.setForeground(Color.WHITE);
        k.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(k);

        l = new JButton("l");
        l.setFocusable(false);
        l.addActionListener(this);
        l.setBackground(SystemColor.inactiveCaption);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_1.add(l);

        panel_2 = new JPanel();
        panel_2.setBackground(Color.BLACK);
        panel_2.setBounds(152, 192, 1051, 78);
        getContentPane().add(panel_2);
        panel_2.setLayout(new GridLayout(0, 7, 15, 0));

        z = new JButton("z");
        z.setFocusable(false);
        z.addActionListener(this);
        z.setBackground(SystemColor.inactiveCaption);
        z.setForeground(Color.WHITE);
        z.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_2.add(z);

        x = new JButton("x");
        x.setFocusable(false);
        x.addActionListener(this);
        x.setBackground(SystemColor.inactiveCaption);
        x.setForeground(Color.WHITE);
        x.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_2.add(x);

        c = new JButton("c");
        c.setFocusable(false);
        c.addActionListener(this);
        c.setBackground(SystemColor.inactiveCaption);
        c.setForeground(Color.WHITE);
        c.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_2.add(c);

        v = new JButton("v");
        v.setFocusable(false);
        v.addActionListener(this);
        v.setBackground(SystemColor.inactiveCaption);
        v.setForeground(Color.WHITE);
        v.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_2.add(v);

        b = new JButton("b");
        b.setFocusable(false);
        b.addActionListener(this);
        b.setBackground(SystemColor.inactiveCaption);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_2.add(b);

        n = new JButton("n");
        n.setFocusable(false);
        n.addActionListener(this);
        n.setBackground(SystemColor.inactiveCaption);
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_2.add(n);

        m = new JButton("m");
        m.setFocusable(false);
        m.addActionListener(this);
        m.setBackground(SystemColor.inactiveCaption);
        m.setForeground(Color.WHITE);
        m.setFont(new Font("Roboto", Font.PLAIN, 20));
        panel_2.add(m);

        backspace = new JButton("Backspace");
        backspace.setFocusable(false);
        backspace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int number = parentTextField.getText().length() - 1;
                String store;

                if (number >= 0) {
                    StringBuilder back = new StringBuilder(parentTextField.getText());
                    back.deleteCharAt(number);
                    store = back.toString();
                    parentTextField.setText(store);
                }
            }
        });

        backspace.setBackground(SystemColor.inactiveCaption);
        backspace.setForeground(Color.WHITE);
        backspace.setFont(new Font("Roboto", Font.PLAIN, 20));
        backspace.setBounds(1215, 192, 177, 79);
        getContentPane().add(backspace);

        numbers = new JButton("#123");
        numbers.setFocusable(false);
        numbers.setBackground(SystemColor.inactiveCaption);
        numbers.setForeground(Color.WHITE);
        numbers.setFont(new Font("Roboto", Font.PLAIN, 20));
        numbers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toNumbers();
                shift.setVisible(false);
                shifted.setVisible(false);
                numbers.setVisible(false);
                abc.setVisible(true);
                numbers1.setVisible(false);
                symbol.setVisible(true);
            }
        });
        numbers.setBounds(12, 283, 128, 78);
        getContentPane().add(numbers);

        comma = new JButton(",");
        comma.setFocusable(false);
        comma.addActionListener(this);
        comma.setBackground(SystemColor.inactiveCaption);
        comma.setForeground(Color.WHITE);
        comma.setFont(new Font("Roboto", Font.PLAIN, 20));
        comma.setBounds(152, 282, 133, 78);
        getContentPane().add(comma);

        space = new JButton(" ");
        space.setFocusable(false);
        space.addActionListener(this);
        space.setBackground(SystemColor.inactiveCaption);
        space.setForeground(Color.WHITE);
        space.setFont(new Font("Roboto", Font.PLAIN, 20));
        space.setBounds(300, 282, 755, 78);
        getContentPane().add(space);

        fullstop = new JButton(".");
        fullstop.setFocusable(false);
        fullstop.addActionListener(this);
        fullstop.setBackground(SystemColor.inactiveCaption);
        fullstop.setForeground(Color.WHITE);
        fullstop.setFont(new Font("Roboto", Font.PLAIN, 20));
        fullstop.setBounds(1067, 282, 128, 78);
        getContentPane().add(fullstop);

        enter = new JButton("Enter");
        enter.setFocusable(false);
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentTextField.setText(parentTextField.getText() + "\n");
            }
        });
        enter.setBackground(SystemColor.inactiveCaption);
        enter.setForeground(Color.WHITE);
        enter.setFont(new Font("Roboto", Font.PLAIN, 20));
        enter.setBounds(1207, 283, 185, 78);
        getContentPane().add(enter);

        shift = new JButton("Shift");
        shift.setFocusable(false);
        shift.setBackground(SystemColor.inactiveCaption);
        shift.setForeground(Color.WHITE);
        shift.setFont(new Font("Roboto", Font.PLAIN, 20));

        shift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                uppercase();
                shifted.setVisible(true);
                numbers1.setVisible(false);
            }
        });


        shift.setBounds(12, 193, 128, 78);
        getContentPane().add(shift);

        shifted = new JButton("Shifted");
        shifted.setFocusable(false);
        shifted.setBackground(SystemColor.inactiveCaption);
        shifted.setForeground(Color.WHITE);
        shifted.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                lowercase();
                shift.setVisible(true);
                shifted.setVisible(false);
            }
        });
        shifted.setBounds(12, 193, 128, 78);
        getContentPane().add(shifted);

        symbol = new JButton("=\\<");
        symbol.setBackground(SystemColor.inactiveCaption);
        symbol.setForeground(Color.WHITE);
        symbol.setFocusable(false);
        symbol.setVisible(false);

        symbol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shift.setVisible(false);
                shifted.setVisible(false);
                numbers1.setVisible(true);
                symbol.setVisible(false);
                toSymbols();

            }
        });
        symbol.setBounds(12, 192, 128, 78);
        getContentPane().add(symbol);

        numbers1 = new JButton("#123");
        numbers1.setVisible(false);
        numbers1.setFocusable(false);
        numbers1.setBackground(SystemColor.inactiveCaption);
        numbers1.setForeground(Color.WHITE);
        numbers1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toNumbers();
                shift.setVisible(false);
                shifted.setVisible(false);
                numbers.setVisible(false);
                abc.setVisible(true);
                symbol.setVisible(true);

            }
        });
        numbers1.setBounds(12, 192, 128, 78);
        getContentPane().add(numbers1);

        abc = new JButton("ABC");
        abc.setFocusable(false);
        abc.setBackground(SystemColor.inactiveCaption);
        abc.setForeground(Color.WHITE);
        abc.setVisible(false);
        abc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lowercase();
                abc.setVisible(false);
                numbers.setVisible(true);
                symbol.setVisible(false);
                numbers1.setVisible(false);
                shift.setVisible(true);
            }
        });
        abc.setBounds(12, 282, 128, 78);
        getContentPane().add(abc);
    }

    public void uppercase() {
        q.setText("Q");
        w.setText("W");
        e1.setText("E");
        r.setText("R");
        t.setText("T");
        y.setText("Y");
        u.setText("U");
        i.setText("I");
        o.setText("O");
        p.setText("P");
        a.setText("A");
        s.setText("S");
        d.setText("D");
        f.setText("F");
        g.setText("G");
        h.setText("H");
        j.setText("J");
        k.setText("K");
        l.setText("L");
        z.setText("Z");
        x.setText("X");
        c.setText("C");
        v.setText("V");
        b.setText("B");
        n.setText("N");
        m.setText("M");
        //shift.setText("Shifted");
        shift.setVisible(false);

    }

    public void toNumbers() {
        q.setText("1");
        w.setText("2");
        e1.setText("3");
        r.setText("4");
        t.setText("5");
        y.setText("6");
        u.setText("7");
        i.setText("8");
        o.setText("9");
        p.setText("0");
        a.setText("@");
        s.setText("#");
        d.setText("$");
        f.setText("_");
        g.setText("&");
        h.setText("-");
        j.setText("+");
        k.setText("(");
        l.setText(")");
        z.setText("/");
        x.setText("*");
        c.setText("\"");
        v.setText("'");
        b.setText(":");
        n.setText(";");
        m.setText("?");
        //shift.setText("#+=");
    }

    public void lowercase() {
        q.setText("q");
        w.setText("w");
        e1.setText("e");
        r.setText("r");
        t.setText("t");
        y.setText("y");
        u.setText("u");
        i.setText("i");
        o.setText("o");
        p.setText("p");
        a.setText("a");
        s.setText("s");
        d.setText("d");
        f.setText("f");
        g.setText("g");
        h.setText("h");
        j.setText("j");
        k.setText("k");
        l.setText("l");
        z.setText("z");
        x.setText("x");
        c.setText("c");
        v.setText("v");
        b.setText("b");
        n.setText("n");
        m.setText("m");
        //shift.setText("Shift");
    }

    public void toSymbols() {
        q.setText("~");
        w.setText("`");
        e1.setText("|");
        r.setText("•");
        t.setText("√");
        y.setText("π");
        u.setText("÷");
        i.setText("×");
        o.setText("¶");
        p.setText("Δ");
        a.setText("€");
        s.setText("¥");
        d.setText("₹");
        f.setText("!");
        g.setText("^");
        h.setText("°");
        j.setText("=");
        k.setText("{");
        l.setText("}");
        z.setText("\\");
        x.setText("%");
        c.setText("©");
        v.setText("®");
        b.setText("™");
        n.setText("[");
        m.setText("]");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        parentTextField.setText(parentTextField.getText() + actionCommand);

    }
}
