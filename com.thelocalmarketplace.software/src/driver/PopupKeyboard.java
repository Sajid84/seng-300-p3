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

// Package declaration for the 'driver' package
package driver;

// Import statements for necessary Java classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class definition for the 'PopupKeyboard' class, extending 'JFrame' and implementing 'ActionListener'
public class PopupKeyboard extends JFrame implements ActionListener {

    // Declaration of JButton components representing the keyboard keys
    public JButton q, w, e1, r, t, y, u, i, o, p, a, s, d, f, g, h, j, k, l, z, x, c, v, b, n, m, shift, numbers, space, fullstop, enter, backspace, comma, shifted, symbol, numbers1, abc;

    // Declaration of JPanels for organizing the keyboard layout
    public JPanel panel;
    public JPanel panel_1;
    public JPanel panel_2;

    // Declaration of a JTextField to which the keyboard input will be directed
    private JTextField parentTextField;

    /**
     * Method to set the focus on a specific JTextField when the keyboard is activated.
     * @param textField - The JTextField to set the focus on
     */
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

            // Create a button 'e1', set its properties, and add an ActionListener (event handler)
            e1 = new JButton("e");
            e1.setFocusable(false);
            e1.addActionListener(this);
            e1.setBackground(SystemColor.inactiveCaption);
            e1.setForeground(Color.WHITE);
            e1.setFont(new Font("Roboto", Font.PLAIN, 20));
            
            // Add the 'e1' button to the panel
            panel.add(e1);
            
            // Repeat the same process for the 'r' button
            r = new JButton("r");
            r.setFocusable(false);
            r.addActionListener(this);
            r.setBackground(SystemColor.inactiveCaption);
            r.setForeground(Color.WHITE);
            r.setFont(new Font("Roboto", Font.PLAIN, 20));
            
            // Add the 'r' button to the panel
            panel.add(r);
            
            // Repeat the same process for the 't' button
            t = new JButton("t");
            t.setFocusable(false);
            t.addActionListener(this);
            t.setBackground(SystemColor.inactiveCaption);
            t.setForeground(Color.WHITE);
            t.setFont(new Font("Roboto", Font.PLAIN, 20));
            
            // Add the 't' button to the panel
            panel.add(t);
            
            // Repeat the same process for the 'y' button
            y = new JButton("y");
            y.setFocusable(false);
            y.addActionListener(this);
            y.setBackground(SystemColor.inactiveCaption);
            y.setForeground(Color.WHITE);
            y.setFont(new Font("Roboto", Font.PLAIN, 20));
            
            // Add the 'y' button to the panel
            panel.add(y);
            
            // Repeat the same process for the 'u' button
            u = new JButton("u");
            u.setFocusable(false);
            u.addActionListener(this);
            u.setBackground(SystemColor.inactiveCaption);
            u.setForeground(Color.WHITE);
            u.setFont(new Font("Roboto", Font.PLAIN, 20));
            
            // Add the 'u' button to the panel
            panel.add(u);

           // Repeat the same process for the 'i' button
          i = new JButton("i");
          i.setFocusable(false);
          i.addActionListener(this);
          i.setBackground(SystemColor.inactiveCaption);
          i.setForeground(Color.WHITE);
          i.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'i' button to the panel
          panel_1.add(i);
          
          // Repeat the same process for the 'o' button
          o = new JButton("o");
          o.setFocusable(false);
          o.addActionListener(this);
          o.setBackground(SystemColor.inactiveCaption);
          o.setForeground(Color.WHITE);
          o.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'o' button to the panel
          panel_1.add(o);
          
          // Repeat the same process for the 'p' button
          p = new JButton("p");
          p.setFocusable(false);
          p.addActionListener(this);
          p.setBackground(SystemColor.inactiveCaption);
          p.setForeground(Color.WHITE);
          p.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'p' button to the panel
          panel_1.add(p);
          
          // Create a new panel ('panel_1') to hold the second row of buttons, set its properties, and position it
          panel_1 = new JPanel();
          panel_1.setBackground(Color.BLACK);
          panel_1.setBounds(76, 102, 1252, 78);
          getContentPane().add(panel_1);
          
          // Set the layout manager of 'panel_1' to a 1-row, 9-column grid with 10-pixel horizontal and 0-pixel vertical gaps
          panel_1.setLayout(new GridLayout(0, 9, 10, 0));
          
          // Repeat the same process for the 'a' button
          a = new JButton("a");
          a.setFocusable(false);
          a.addActionListener(this);
          a.setBackground(SystemColor.inactiveCaption);
          a.setForeground(Color.WHITE);
          a.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'a' button to 'panel_1'
          panel_1.add(a);
          
          // Repeat the same process for the 's' button
          s = new JButton("s");
          s.setFocusable(false);
          s.addActionListener(this);
          s.setBackground(SystemColor.inactiveCaption);
          s.setForeground(Color.WHITE);
          s.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 's' button to 'panel_1'
          panel_1.add(s);
          
          // Repeat the same process for the 'd' button
          d = new JButton("d");
          d.setFocusable(false);
          d.addActionListener(this);
          d.setBackground(SystemColor.inactiveCaption);
          d.setForeground(Color.WHITE);
          d.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'd' button to 'panel_1'
          panel_1.add(d);
          
          // Repeat the same process for the 'f' button
          f = new JButton("f");
          f.setFocusable(false);
          f.addActionListener(this);
          f.setBackground(SystemColor.inactiveCaption);
          f.setForeground(Color.WHITE);
          f.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'f' button to 'panel_1'
          panel_1.add(f);
          
          // Repeat the same process for the 'g' button
          g = new JButton("g");
          g.setFocusable(false);
          g.addActionListener(this);
          g.setBackground(SystemColor.inactiveCaption);
          g.setForeground(Color.WHITE);
          g.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'g' button to 'panel_1'
          panel_1.add(g);
          
          // Repeat the same process for the 'h' button
          h = new JButton("h");
          h.setFocusable(false);
          h.addActionListener(this);
          h.setBackground(SystemColor.inactiveCaption);
          h.setForeground(Color.WHITE);
          h.setFont(new Font("Roboto", Font.PLAIN, 20));
          
          // Add the 'h' button to 'panel_1'
          panel_1.add(h);

       // Repeat the same process for the 'j' button
      j = new JButton("j");
      j.setFocusable(false);
      j.addActionListener(this);
      j.setBackground(SystemColor.inactiveCaption);
      j.setForeground(Color.WHITE);
      j.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'j' button to 'panel_1'
      panel_1.add(j);
      
      // Repeat the same process for the 'k' button
      k = new JButton("k");
      k.setFocusable(false);
      k.addActionListener(this);
      k.setBackground(SystemColor.inactiveCaption);
      k.setForeground(Color.WHITE);
      k.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'k' button to 'panel_1'
      panel_1.add(k);
      
      // Repeat the same process for the 'l' button
      l = new JButton("l");
      l.setFocusable(false);
      l.addActionListener(this);
      l.setBackground(SystemColor.inactiveCaption);
      l.setForeground(Color.WHITE);
      l.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'l' button to 'panel_1'
      panel_1.add(l);
      
      // Create a new panel ('panel_2') to hold the third row of buttons, set its properties, and position it
      panel_2 = new JPanel();
      panel_2.setBackground(Color.BLACK);
      panel_2.setBounds(152, 192, 1051, 78);
      getContentPane().add(panel_2);
      
      // Set the layout manager of 'panel_2' to a 1-row, 7-column grid with 15-pixel horizontal and 0-pixel vertical gaps
      panel_2.setLayout(new GridLayout(0, 7, 15, 0));
      
      // Repeat the same process for the 'z' button
      z = new JButton("z");
      z.setFocusable(false);
      z.addActionListener(this);
      z.setBackground(SystemColor.inactiveCaption);
      z.setForeground(Color.WHITE);
      z.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'z' button to 'panel_2'
      panel_2.add(z);
      
      // Repeat the same process for the 'x' button
      x = new JButton("x");
      x.setFocusable(false);
      x.addActionListener(this);
      x.setBackground(SystemColor.inactiveCaption);
      x.setForeground(Color.WHITE);
      x.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'x' button to 'panel_2'
      panel_2.add(x);
      
      // Repeat the same process for the 'c' button
      c = new JButton("c");
      c.setFocusable(false);
      c.addActionListener(this);
      c.setBackground(SystemColor.inactiveCaption);
      c.setForeground(Color.WHITE);
      c.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'c' button to 'panel_2'
      panel_2.add(c);
      
      // Repeat the same process for the 'v' button
      v = new JButton("v");
      v.setFocusable(false);
      v.addActionListener(this);
      v.setBackground(SystemColor.inactiveCaption);
      v.setForeground(Color.WHITE);
      v.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'v' button to 'panel_2'
      panel_2.add(v);
      
      // Repeat the same process for the 'b' button
      b = new JButton("b");
      b.setFocusable(false);
      b.addActionListener(this);
      b.setBackground(SystemColor.inactiveCaption);
      b.setForeground(Color.WHITE);
      b.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'b' button to 'panel_2'
      panel_2.add(b);
      
      // Repeat the same process for the 'n' button
      n = new JButton("n");
      n.setFocusable(false);
      n.addActionListener(this);
      n.setBackground(SystemColor.inactiveCaption);
      n.setForeground(Color.WHITE);
      n.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'n' button to 'panel_2'
      panel_2.add(n);
      
      // Repeat the same process for the 'm' button
      m = new JButton("m");
      m.setFocusable(false);
      m.addActionListener(this);
      m.setBackground(SystemColor.inactiveCaption);
      m.setForeground(Color.WHITE);
      m.setFont(new Font("Roboto", Font.PLAIN, 20));
      
      // Add the 'm' button to 'panel_2'
      panel_2.add(m);

      // Create a new JButton for backspace with the label "Backspace"
backspace = new JButton("Backspace");

// Set the focusability of the button to false
backspace.setFocusable(false);

// Add an ActionListener to handle the button click
backspace.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent arg0) {
        // Get the length of the text in the parentTextField
        int number = parentTextField.getText().length() - 1;
        String store;

        // Check if there are characters to delete (length is greater than or equal to 0)
        if (number >= 0) {
            // Create a StringBuilder with the text from parentTextField
            StringBuilder back = new StringBuilder(parentTextField.getText());

            // Delete the character at the specified position (number)
            back.deleteCharAt(number);

            // Convert the StringBuilder back to a String
            store = back.toString();

            // Set the modified text back to the parentTextField
            parentTextField.setText(store);
        }
    }
});
       // Set the background color of the backspace button
backspace.setBackground(SystemColor.inactiveCaption);

// Set the text color of the backspace button
backspace.setForeground(Color.WHITE);

// Set the font of the backspace button
backspace.setFont(new Font("Roboto", Font.PLAIN, 20));

// Set the bounds (position and size) of the backspace button
backspace.setBounds(1215, 192, 177, 79);

// Add the backspace button to the content pane
getContentPane().add(backspace);

// Create a new JButton for the "numbers" button with the label "#123"
numbers = new JButton("#123");

// Set the focusability of the numbers button to false
numbers.setFocusable(false);

// Set the background color of the numbers button
numbers.setBackground(SystemColor.inactiveCaption);

// Set the text color of the numbers button
numbers.setForeground(Color.WHITE);

// Set the font of the numbers button
numbers.setFont(new Font("Roboto", Font.PLAIN, 20));

// Add an ActionListener to handle the button click for the numbers button
numbers.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent e) {
        // Call the toNumbers() method (assuming it's defined elsewhere in your code)
        toNumbers();

        // Set the visibility of various buttons accordingly
        shift.setVisible(false);
        shifted.setVisible(false);
        numbers.setVisible(false);
        abc.setVisible(true);
        numbers1.setVisible(false);
        symbol.setVisible(true);
    }
});
       // Set the bounds (position and size) of the numbers button
numbers.setBounds(12, 283, 128, 78);

// Add the numbers button to the content pane
getContentPane().add(numbers);

// Create a new JButton for the "comma" button with the label ","
comma = new JButton(",");
comma.setFocusable(false);

// Add an ActionListener to handle the button click for the comma button
comma.addActionListener(this);

// Set the background color of the comma button
comma.setBackground(SystemColor.inactiveCaption);

// Set the text color of the comma button
comma.setForeground(Color.WHITE);

// Set the font of the comma button
comma.setFont(new Font("Roboto", Font.PLAIN, 20));

// Set the bounds (position and size) of the comma button
comma.setBounds(152, 282, 133, 78);

// Add the comma button to the content pane
getContentPane().add(comma);

// Create a new JButton for the "space" button with the label " "
space = new JButton(" ");
space.setFocusable(false);

// Add an ActionListener to handle the button click for the space button
space.addActionListener(this);

// Set the background color of the space button
space.setBackground(SystemColor.inactiveCaption);

// Set the text color of the space button
space.setForeground(Color.WHITE);

// Set the font of the space button
space.setFont(new Font("Roboto", Font.PLAIN, 20));

// Set the bounds (position and size) of the space button
space.setBounds(300, 282, 755, 78);

// Add the space button to the content pane
getContentPane().add(space);

// Create a new JButton for the "fullstop" button with the label "."
fullstop = new JButton(".");
fullstop.setFocusable(false);

// Add an ActionListener to handle the button click for the fullstop button
fullstop.addActionListener(this);

// Set the background color of the fullstop button
fullstop.setBackground(SystemColor.inactiveCaption);

// Set the text color of the fullstop button
fullstop.setForeground(Color.WHITE);

// Set the font of the fullstop button
fullstop.setFont(new Font("Roboto", Font.PLAIN, 20));

// Set the bounds (position and size) of the fullstop button
fullstop.setBounds(1067, 282, 128, 78);

// Add the fullstop button to the content pane
getContentPane().add(fullstop);

// Create a new JButton for the "enter" button with the label "Enter"
enter = new JButton("Enter");
enter.setFocusable(false);

// Add an ActionListener to handle the button click for the enter button
enter.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent e) {
        // Append a newline character to the text in the parentTextField
        parentTextField.setText(parentTextField.getText() + "\n");
    }
});
       // Set the background color of the enter button
enter.setBackground(SystemColor.inactiveCaption);

// Set the text color of the enter button
enter.setForeground(Color.WHITE);

// Set the font of the enter button
enter.setFont(new Font("Roboto", Font.PLAIN, 20));

// Set the bounds (position and size) of the enter button
enter.setBounds(1207, 283, 185, 78);

// Add the enter button to the content pane
getContentPane().add(enter);

// Create a new JButton for the "shift" button with the label "Shift"
shift = new JButton("Shift");
shift.setFocusable(false);

// Set the background color of the shift button
shift.setBackground(SystemColor.inactiveCaption);

// Set the text color of the shift button
shift.setForeground(Color.WHITE);

// Set the font of the shift button
shift.setFont(new Font("Roboto", Font.PLAIN, 20));

// Add an ActionListener to handle the button click for the shift button
shift.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent arg0) {
        // Call the uppercase() method (assuming it's defined elsewhere in your code)
        uppercase();

        // Set the visibility of other components accordingly
        shifted.setVisible(true);
        numbers1.setVisible(false);
    }
});
       // Set the bounds (position and size) of the shift button
shift.setBounds(12, 193, 128, 78);

// Add the shift button to the content pane
getContentPane().add(shift);

// Create a new JButton for the "shifted" button with the label "Shifted"
shifted = new JButton("Shifted");
shifted.setFocusable(false);

// Set the background color of the shifted button
shifted.setBackground(SystemColor.inactiveCaption);

// Set the text color of the shifted button
shifted.setForeground(Color.WHITE);

// Add an ActionListener to handle the button click for the shifted button
shifted.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent arg0) {
        // Call the lowercase() method (assuming it's defined elsewhere in your code)
        lowercase();

        // Set the visibility of the shift and shifted buttons accordingly
        shift.setVisible(true);
        shifted.setVisible(false);
    }
});

// Add the shifted button to the content pane
getContentPane().add(shifted);
// Set the bounds (position and size) of the shift button
shift.setBounds(12, 193, 128, 78);

// Add the shift button to the content pane
getContentPane().add(shift);

// Create a new JButton for the "shifted" button with the label "Shifted"
shifted = new JButton("Shifted");
shifted.setFocusable(false);

// Set the background color of the shifted button
shifted.setBackground(SystemColor.inactiveCaption);

// Set the text color of the shifted button
shifted.setForeground(Color.WHITE);

// Add an ActionListener to handle the button click for the shifted button
shifted.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent arg0) {
        // Call the lowercase() method (assuming it's defined elsewhere in your code)
        lowercase();

        // Set the visibility of the shift and shifted buttons accordingly
        shift.setVisible(true);
        shifted.setVisible(false);
    }
});

// Add the shifted button to the content pane
getContentPane().add(shifted);
// Set the bounds (position and size) of the shifted button
shifted.setBounds(12, 193, 128, 78);

// Add the shifted button to the content pane
getContentPane().add(shifted);

// Create a new JButton for the "symbol" button with the label "=\"
symbol = new JButton("=\\<");

// Set the background color of the symbol button
symbol.setBackground(SystemColor.inactiveCaption);

// Set the text color of the symbol button
symbol.setForeground(Color.WHITE);

// Set the focusability of the symbol button to false
symbol.setFocusable(false);

// Initially, set the visibility of the symbol button to false
symbol.setVisible(false);

// Add an ActionListener to handle the button click for the symbol button
symbol.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent e) {
        // Set the visibility of various buttons accordingly
        shift.setVisible(false);
        shifted.setVisible(false);
        numbers1.setVisible(true);
        symbol.setVisible(false);

        // Call the toSymbols() method (assuming it's defined elsewhere in your code)
        toSymbols();
    }
});

// Add the symbol button to the content pane
getContentPane().add(symbol);
// Set the bounds (position and size) of the symbol button
symbol.setBounds(12, 192, 128, 78);

// Add the symbol button to the content pane
getContentPane().add(symbol);

// Create a new JButton for the "numbers1" button with the label "#123"
numbers1 = new JButton("#123");

// Initially, set the visibility of the numbers1 button to false
numbers1.setVisible(false);

// Set the focusability of the numbers1 button to false
numbers1.setFocusable(false);

// Set the background color of the numbers1 button
numbers1.setBackground(SystemColor.inactiveCaption);

// Set the text color of the numbers1 button
numbers1.setForeground(Color.WHITE);

// Add an ActionListener to handle the button click for the numbers1 button
numbers1.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent e) {
        // Call the toNumbers() method (assuming it's defined elsewhere in your code)
        toNumbers();

        // Set the visibility of various buttons accordingly
        shift.setVisible(false);
        shifted.setVisible(false);
        numbers.setVisible(false);
        abc.setVisible(true);
        symbol.setVisible(true);
    }
});

// Add the numbers1 button to the content pane
getContentPane().add(numbers1);
// Set the bounds (position and size) of the numbers1 button
numbers1.setBounds(12, 192, 128, 78);

// Add the numbers1 button to the content pane
getContentPane().add(numbers1);

// Create a new JButton for the "abc" button with the label "ABC"
abc = new JButton("ABC");

// Set the focusability of the abc button to false
abc.setFocusable(false);

// Set the background color of the abc button
abc.setBackground(SystemColor.inactiveCaption);

// Set the text color of the abc button
abc.setForeground(Color.WHITE);

// Initially, set the visibility of the abc button to false
abc.setVisible(false);

// Add an ActionListener to handle the button click for the abc button
abc.addActionListener(new ActionListener() {
    // Implement the actionPerformed method for the ActionListener
    public void actionPerformed(ActionEvent e) {
        // Call the lowercase() method (assuming it's defined elsewhere in your code)
        lowercase();

        // Set the visibility of various buttons accordingly
        abc.setVisible(false);
        numbers.setVisible(true);
        symbol.setVisible(false);
        numbers1.setVisible(false);
        shift.setVisible(true);
    }
});

abc.setBounds(12, 282, 128, 78);
// Add the abc button to the content pane
getContentPane().add(abc);
}

  // Method to convert buttons to uppercase letters
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

    // Hide the shift button when uppercase letters are displayed
    shift.setVisible(false);
}

// Method to convert buttons to numbers and symbols
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

    // shift.setText("#+=");
}

  // Method to convert buttons to lowercase letters
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

    // shift.setText("Shift");
}

// Method to convert buttons to symbols
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

    // shift.setText("Some other text");
}

  @Override
public void actionPerformed(ActionEvent e) {
    // Get the action command associated with the event
    String actionCommand = e.getActionCommand();

    // Append the action command (button text) to the text in parentTextField
    parentTextField.setText(parentTextField.getText() + actionCommand);
  }
}







            
