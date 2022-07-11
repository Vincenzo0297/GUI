import java.awt.*;
import java.awt.event.*;

import javax.print.attribute.standard.PrinterName;
import javax.swing.*;
import javax.xml.crypto.Data;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

class QuotationItem {
  // Declare Instance
  private String Code;
  private int Quantity;
  private double Price;
  private double Discount;

  // Constructor
  public QuotationItem(String Code, int Quantity, double Price, double Discount) {
    this.Code = Code;
    this.Quantity = Quantity;
    this.Price = Price;
    this.Discount = Discount;
  }

  // Get method
  public String getCode() {
    return Code;
  }

  public int getQuantity() {
    return Quantity;
  }

  public double getPrice() {
    return Price;
  }

  public double getDiscount() {
    return Discount;
  }

  // Set method
  public void setCode(String Code) {
    this.Code = Code;
  }

  public void setQuantity(int Quantity) {
    this.Quantity = Quantity;
  }

  public void setPrice(double Price) {
    this.Price = Price;
  }

  public void setDiscount(double Discount) {
    this.Discount = Discount;
  }

  public double getTotal() {
    double total = (getQuantity() * getPrice()) - (getDiscount() / 100);
    return total;
  }

  // String to display info
  public String toString() {
    return "Code: " + Code + " Quantity: " + Quantity + " Price: " + Price + " Discount: " + Discount + " Total: " + getTotal();
  }
}


class GUI extends JFrame {
  static ArrayList<QuotationItem> List = new ArrayList<>(); 

  private JTextField txCode, txQuantity, txPrice, txDiscount, txTotal;
  private JButton bnLoad, bnPrevious, bnNext;
  //Global Variables
  private static final int Min = 0;
  private int counter;

  public GUI() {
    createUI();
  }

  private void createUI() {
    // Create title
    this.setTitle("Quotation Management");
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Change the layout to gridLayout
    Container uiPane = this.getContentPane();
    
    uiPane.setLayout(new GridLayout(7, 0));

    // Create UI component
    // Create Text
    txCode = new JTextField();
    uiPane.add(new JLabel("Code "));
    uiPane.add(txCode);

    txQuantity = new JTextField();
    uiPane.add(new JLabel("Quantity "));
    uiPane.add(txQuantity);

    txPrice = new JTextField();
    uiPane.add(new JLabel("Price "));
    uiPane.add(txPrice);

    txDiscount = new JTextField();
    uiPane.add(new JLabel("Discount "));
    uiPane.add(txDiscount);

    txTotal = new JTextField();
    uiPane.add(new JLabel("Total "));
    uiPane.add(txTotal);

    // Create 3 Buttons into a "Container"
    Box bnBox = Box.createHorizontalBox();
    uiPane.add(new JLabel());

    bnLoad = new JButton("Load");
    bnBox.add(bnLoad);

    bnPrevious = new JButton("Pevious");
    bnBox.add(bnPrevious);

    bnNext = new JButton("Next");
    bnBox.add(bnNext);

    this.add(bnBox);

    // resize screen to fit all UI Components
    this.pack();
    // add event handlers
    addEventHandlers();
  }

  private void addEventHandlers() {
    // Create actionlistener for bnLoad
    ActionListener bnload = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bnPrevious.setEnabled(false);
        bnLoad.setEnabled(false);
        //user press load will only load the first index (index 0)
        //to the UI so have to use the setText method to put it inside the UI
        txCode.setText(Main.List.get(0).getCode()); 
        txQuantity.setText(Integer.toString(Main.List.get(0).getQuantity())); //Converting from Int to String
        txPrice.setText(Double.toString(Main.List.get(0).getPrice())); //Converting from Double to String
        txDiscount.setText(Double.toString(Main.List.get(0).getDiscount())); //Converting from Double to String
        txTotal.setText(Double.toString(Main.List.get(0).getTotal())); //Converting from Double to String
      }
    };
    bnLoad.addActionListener(bnload);

    // Create actionlistener for bnPervious
    bnPrevious.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Previous();
        }
      });

    // Create actionlistener for bnNext
    bnNext.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	   Next();
	 }
      });    
  }

  private void Previous() { //To read pervious data
    counter --;
    if(counter == Min) {
      bnPrevious.setEnabled(false);
    }
    bnLoad.setEnabled(false);
    bnNext.setEnabled(true);

    txCode.setText(Main.List.get(counter).getCode()); 
    txQuantity.setText(Integer.toString(Main.List.get(counter).getQuantity())); //Converting from Int to String
    txPrice.setText(Double.toString(Main.List.get(counter).getPrice())); //Converting from Double to String
    txDiscount.setText(Double.toString(Main.List.get(counter).getDiscount())); //Converting from Double to String
    txTotal.setText(Double.toString(Main.List.get(counter).getTotal())); //Converting from Double to String
  }

  private void Next() { //To read next data
    counter ++;
    if(counter+1 == Main.List.size()) {//Using ArrayList for counter
      bnNext.setEnabled(false); 
    }
    bnPrevious.setEnabled(true);
    bnLoad.setEnabled(false);

    txCode.setText(Main.List.get(counter).getCode()); 
    txQuantity.setText(Integer.toString(Main.List.get(counter).getQuantity())); //Converting from Int to String
    txPrice.setText(Double.toString(Main.List.get(counter).getPrice())); //Converting from Double to String
    txDiscount.setText(Double.toString(Main.List.get(counter).getDiscount())); //Converting from Double to String
    txTotal.setText(Double.toString(Main.List.get(counter).getTotal())); //Converting from Double to String
  }
}

public class Main {
  static ArrayList<QuotationItem> List = new ArrayList<>();
  public static void main(String[] argv) {
    // The program will execeute this method once started, so it will bring the
    // data from the file to the arraylist
    LoadFile();
    new GUI().setVisible(true);
  }

  // Just read data from file
  public static void LoadFile() {
    String filename = "data.txt"; //Text file name must be same as program in the folder
    File datafile = new File(filename);
    
    try (Scanner reader = new Scanner(datafile)) {
      while (reader.hasNext()) {
        // Break each line into parts and process them
        String oneLine = reader.nextLine();
        String[] values = oneLine.split(","); // try not to use spaces or " "

        String Code = (values[0]); //Code
        int Quantity = Integer.parseInt(values[1]); //Integer 
        double Price = Double.parseDouble(values[2]); //Double 
        double Discount = Double.parseDouble(values[3]); //Double 
       
        QuotationItem qItem = new QuotationItem(Code, Quantity, Price, Discount);
        List.add(qItem);
      }
    }

    catch (FileNotFoundException error) {
      System.out.println(error);
      String err = "No Quatation Record ";
      JOptionPane.showMessageDialog(null, err);
    }
  }
}
