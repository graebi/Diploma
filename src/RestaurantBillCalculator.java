//  RestaurantBillCalculator.java
// Calculates a table's bill.

//**** TODO ******  PROVIDE IMPORT STATEMENT Modify the import Statements so that only classes used are actually imported

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class RestaurantBillCalculator extends JFrame
{
   // JLabel for Restaurant
   private JLabel restaurantJLabel;

   // JPanel to display waiter information
   private JPanel waiterJPanel;
   
   // JLabel and JComboBox for Table Number added JComboBox
   private JLabel tableNumberJLabel;
   private JComboBox tableNumberJComboBox;
   
   // JLabel and JTextField for waiter name
   private JLabel waiterNameJLabel;
   private JTextField waiterNameJTextField;

   // JPanel to display menu items
   private JPanel menuItemsJPanel;

   // JLabel and JComboBox for beverage
   private JLabel beverageJLabel;
   private JComboBox beverageJComboBox;

   // JLabel and JComboBox for appetizer
   private JLabel appetizerJLabel;
   private JComboBox appetizerJComboBox;

   // JLabel and JComboBox for main course
   private JLabel mainCourseJLabel;
   private JComboBox mainCourseJComboBox;

   // JLabel and JComboBox for dessert
   private JLabel dessertJLabel;
   private JComboBox dessertJComboBox;

   // JLabel and JTextField for subtotal
   private JLabel subtotalJLabel;
   private JTextField subtotalJTextField;

   // JLabel and JTextField for tax
   private JLabel taxJLabel;
   private JTextField taxJTextField;

   // JLabel and JTextField for total
   private JLabel totalJLabel;
   private JTextField totalJTextField;
   
   // JButton for Save Table - added
   private JButton saveTableJButton; 
   
   // JButton for calculate bill
   private JButton calculateBillJButton;
   
   // JButton for Pay Bill -added
   private JButton payBillJButton;  
   
   // constant for tax rate
   private final static double TAX_RATE = 0.05;

   // declare instance variables for database processing
   private Connection myConnection;
   private Statement myStatement;
   private ResultSet myResultSet;

   // declare instance variable ArrayList to hold bill items
   private ArrayList billItems = new ArrayList();

   //declare instance variables Subtotal -added
   private double subtotal;
      
   // constructor
   public RestaurantBillCalculator( 
  String databaseUserName, String databasePassword )
   { 
    // Create database connection
       //JDBC driver and datbase name
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String driver = "com.mysql.jdbc.Driver";
        
        try{
         Class.forName(driver).newInstance();
         //Connect to database
         myConnection = DriverManager.getConnection(url,databaseUserName, databasePassword); 
         //Create statement
         myStatement = myConnection.createStatement();
//         System.out.println("Connected to DB"); 
         myStatement = myConnection.createStatement();

        }//end try
        catch(Exception e){
            e.printStackTrace();
        }
      createUserInterface(); // set up GUI  
            
      // TODO: code to connect to the database

   } // end constructor

   // create and position GUI components; register event handlers
   private void createUserInterface()
   {
      // get content pane for attaching GUI components
      Container contentPane = getContentPane();

      // enable explicit positioning of GUI components 
      contentPane.setLayout( null );

      // **** TODO ****** set up restaurantJLabel
     // set up restaurantJLabel
     restaurantJLabel = new JLabel();
     restaurantJLabel.setBounds( 80, 8, 128, 24 );
     restaurantJLabel.setText( "Restaurant" );
     restaurantJLabel.setFont(
     new Font( "SansSerif", Font.BOLD, 16 ) );
     contentPane.add( restaurantJLabel );

      // **** TODO ****** set up waiterJPanel
     // set up waiterJPanel
     createWaiterJPanel();
     contentPane.add( waiterJPanel );     

      // **** TODO ****** set up menuItemsJPanel
     // set up menuItemsJPanel
     createMenuItemsJPanel();
     contentPane.add( menuItemsJPanel );
     
    //Set up saveTableJButton
     saveTableJButton = new JButton();
     saveTableJButton.setBounds( 167, 328, 90, 24 );
     saveTableJButton.setText( "Save Table" );
     saveTableJButton.setBorder(
     BorderFactory.createRaisedBevelBorder() );
     saveTableJButton.setEnabled( false );
     contentPane.add( saveTableJButton );
     saveTableJButton.addActionListener(
        new ActionListener() // anonymous inner class
            {
            // event handler called when saveTableJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               saveTableJButtonActionPerformed( event ); 
            }           
         }// end anonymous inner class
      ); // end addActionListener              
     
     //Set up calculateBillJButton
     calculateBillJButton = new JButton();
     calculateBillJButton.setBounds( 167, 362, 90, 24 );
     calculateBillJButton.setText( "Calculate Bill" );
     calculateBillJButton.setBorder(
     BorderFactory.createRaisedBevelBorder() );
     calculateBillJButton.setEnabled( false );
     contentPane.add( calculateBillJButton );
     calculateBillJButton.addActionListener(
        new ActionListener() // anonymous inner class
            {
            // event handler called when calculateBillJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               calculateBillJButtonActionPerformed( event ); 
            }          
         }// end anonymous inner class
      ); // end addActionListener    
     
       //set up payBillJButton
      payBillJButton = new JButton();
      payBillJButton.setBounds(167, 396, 90, 24);
      payBillJButton.setText("Pay Bill");
      payBillJButton.setBorder(
        BorderFactory.createRaisedBevelBorder());
      payBillJButton.setEnabled(false);
      contentPane.add(payBillJButton);
      payBillJButton.addActionListener(
              new ActionListener()//anonmous inner class
              {
                  //event handler called when payBillJButton is clicked
                  public void actionPerformed(ActionEvent event)
                  {
                      payBillJButtonActionPerformed(event);
                  }//end actionPerformed method
              }//end inner class
      );//end addActionListener    
     

      // **** TODO ****** set up subtotalJLabel
     // set up subtotalJLabel
     subtotalJLabel = new JLabel();
     subtotalJLabel.setBounds( 15, 340, 56, 16 );
     subtotalJLabel.setText( "Subtotal:" );
     contentPane.add( subtotalJLabel );
     
      // **** TODO ****** set up subtotalJTextField
     subtotalJTextField = new JTextField();
     subtotalJTextField.setBounds( 70, 340, 80, 20 );
     subtotalJTextField.setEditable( false );
     subtotalJTextField.setBorder(
     BorderFactory.createLoweredBevelBorder() );
     subtotalJTextField.setHorizontalAlignment( JTextField.RIGHT );
     contentPane.add( subtotalJTextField );

      // **** TODO ****** set up taxJLabel
     // set up taxJLabel
     taxJLabel = new JLabel();
     taxJLabel.setBounds( 15, 375, 56, 16 );
     taxJLabel.setText( "Tax:" );
     contentPane.add( taxJLabel );

      // **** TODO ****** set up taxJTextField
     taxJTextField = new JTextField();
     taxJTextField.setBounds( 70, 375, 80, 20 );
     taxJTextField.setEditable( false );
     taxJTextField.setBorder(
     BorderFactory.createLoweredBevelBorder() );
     taxJTextField.setHorizontalAlignment( JTextField.RIGHT );
     contentPane.add( taxJTextField );

      // **** TODO ****** set up totalJLabel
     totalJLabel = new JLabel();
     totalJLabel.setBounds( 15, 410, 56, 16 );
     totalJLabel.setText( "Total:" );
     contentPane.add( totalJLabel );
     
      // **** TODO ****** set up totalJTextField
     totalJTextField = new JTextField();
     totalJTextField.setBounds( 70, 410, 80, 20 );
     totalJTextField.setEditable( false );
     totalJTextField.setBorder(
     BorderFactory.createLoweredBevelBorder() );
     totalJTextField.setHorizontalAlignment( JTextField.RIGHT );
     contentPane.add( totalJTextField );
         
      // **** TODO ****** set properties of application's window
     setTitle( "Restaurant Bill Calculator" ); // set window title 
     setSize( 280, 500 ); // set window size
     setVisible( true ); // display window

      // **** TODO ****** ensure database connection is closed
      // **** TODO ****** when user quits application
      addWindowListener(

         new WindowAdapter()//anonymous inner class
         {
            //  event handler called when close button is clicked
            public void windowClosing( WindowEvent event )
            {
               frameWindowClosing( event );
            }

         } //  end anonymous inner class

      ); // end addWindowListener

   } // end method createUserInterface

   // **** TODO ****** set up waiterJPanel
   private void createWaiterJPanel()
   {
      // **** TODO ****** set up waiterJPanel
     waiterJPanel = new JPanel();
     waiterJPanel.setBounds( 20, 48, 232, 88 );
     waiterJPanel.setBorder( BorderFactory.createTitledBorder(
     BorderFactory.createEtchedBorder(),
     "Waiter Information" ) );
     waiterJPanel.setLayout( null );

      // **** TODO ****** set up tableNumberJLabel
      //Create tableNumberJLabel in waiterJPanel
      //waiterJPanel.setLayout(null);//NO layout manager in use
      tableNumberJLabel = new JLabel();//Create tableNumberJLabel in waiterJPanel
      tableNumberJLabel.setText("Table number:");//JLabel table number in waiterJPanel
      tableNumberJLabel.setBounds(30, 25, 90, 20);// Size and position of tableNumberJLabel in Waiter information
      waiterJPanel.add(tableNumberJLabel);//Add tableNumberJLabel onto waiterJPane     

      //set up tableNumberJComboBox in waiterJPanel
      tableNumberJComboBox = new JComboBox();
      tableNumberJComboBox.setBounds( 150, 25, 70, 20 );
      waiterJPanel.add( tableNumberJComboBox );
      tableNumberJComboBox.addItemListener(

         new ItemListener()  // **** TODO ****** anonymous inner class
         {
            // **** TODO ****** event handler called when item in tableNumberJComboBox
            // **** TODO ****** is selected
            public void itemStateChanged( ItemEvent event )
            {
               tableNumberJComboBoxItemStateChanged( event );
            }

         } // end anonymous inner class

      ); // end addItemListener

//       **** TODO ****** add items to tableNumberJComboBox
//       add items to tableNumberJComboBox
      tableNumberJComboBox.addItem( "" );
//      loadTableNumbers( "Table number", tableNumberJComboBox );  -- delete later
      loadTableNumbers();            
      
      // **** TODO ****** set up waiterNameJLabel
      //Create waiterNameJLabel in waiterJPanel
      waiterNameJLabel = new JLabel();//Create waiterNameJLabel in waiterJPanel
      waiterNameJLabel.setText("Waiter name:");//JLabel Waiter name in waiterJPanel
      waiterNameJLabel.setBounds(30, 50, 90, 20);// Size and position of waiterNameJLabel in Waiter information
      waiterJPanel.add(waiterNameJLabel);//Add waiterNameJLabel onto waiterJPanel      

      // **** TODO ****** set up waiterNameJTextField
      //Create tableNumberJTextField in waiterJPanel
      waiterNameJTextField = new JTextField();//Create tableNumberJTextField in waiterJPanel
      waiterNameJTextField.setHorizontalAlignment(SwingConstants.RIGHT);//
      waiterNameJTextField.setEditable( false );
      waiterNameJTextField.setBounds(125, 50, 90, 20);// Size and position of tableNumberJTextField in Waiter information
      waiterJPanel.add(waiterNameJTextField);//Add tableNumberJTextField onto waiterJPanel

   } // end method createWaiterJPanel

   // **** TODO ****** create menuItemsJPanel
   private void createMenuItemsJPanel()
   {
      //  set up menuItemsJPanel
      menuItemsJPanel = new JPanel();
      menuItemsJPanel.setBounds( 20, 152, 232, 152 );
      menuItemsJPanel.setBorder( BorderFactory.createTitledBorder( 
      BorderFactory.createEtchedBorder(), "Menu Items" ) );
      menuItemsJPanel.setLayout( null );

      //  set up beverageJLabel
      beverageJLabel = new JLabel();
      beverageJLabel.setBounds( 8, 24, 80, 24 );
      beverageJLabel.setText( "Beverage:" );
      menuItemsJPanel.add( beverageJLabel );

      // set up beverageJComboBox
      beverageJComboBox = new JComboBox();
      beverageJComboBox.setBounds( 88, 24, 128, 24 );
      beverageJComboBox.setEnabled( false ); 
      menuItemsJPanel.add( beverageJComboBox );
      beverageJComboBox.addItemListener(

         new ItemListener()  // **** TODO ****** anonymous inner class
         {
            // **** TODO ****** event handler called when item in beverageJComboBox
            // **** TODO ****** is selected
            public void itemStateChanged( ItemEvent event )
            {
               beverageJComboBoxItemStateChanged( event );
            }

         } // end anonymous inner class

      ); // end addItemListener

//       **** TODO ****** add items to beverageJComboBox
//       add items to beverageJComboBox
      beverageJComboBox.addItem( "" );
      loadCategory("Beverage", beverageJComboBox);
     
      // **** TODO ****** set up appetizerJLabel
      appetizerJLabel = new JLabel();
      appetizerJLabel.setBounds( 8, 55, 80, 24 );
      appetizerJLabel.setText( "Appetizer:" );
      menuItemsJPanel.add( appetizerJLabel) ;     

      // **** TODO ****** set up appetizerJComboBox
      appetizerJComboBox = new JComboBox();
      appetizerJComboBox.setBounds( 88, 55, 128, 24 );
      menuItemsJPanel.add( appetizerJComboBox );
      appetizerJComboBox.setEnabled(false);
      appetizerJComboBox.addItemListener(

         new ItemListener()  // **** TODO ****** anonymous inner class
         {
            // **** TODO ****** event handler called when item in AppetizerJComboBox
            // **** TODO ****** is selected
            public void itemStateChanged( ItemEvent event )
            {
               appetizerJComboBoxItemStateChanged( event );
            }
         } // end anonymous inner class
      ); // end addItemListener

//       **** TODO ****** add items to AppetizerJComboBox
//       add items to AppetizerJComboBox
      appetizerJComboBox.addItem( "" );
      loadCategory( "Appetizer", appetizerJComboBox );     

      // **** TODO ****** set up mainCourseJLabel
      mainCourseJLabel = new JLabel();
      mainCourseJLabel.setBounds( 8, 86, 80, 24 );
      mainCourseJLabel.setText( "Main Course:" );
      menuItemsJPanel.add( mainCourseJLabel );
      
      // **** TODO ****** set up mainCourseJComboBox
      mainCourseJComboBox = new JComboBox();
      mainCourseJComboBox.setBounds( 88, 86, 128, 24 );
      mainCourseJComboBox.setEnabled( false );
      menuItemsJPanel.add( mainCourseJComboBox );
      
      mainCourseJComboBox.addItemListener(
      // **** TODO ****** add items to mainCourseJComboBox
      
         new ItemListener()  // **** TODO ****** anonymous inner class
         {
            // **** TODO ****** event handler called when item in mainCourseJComboBox
            // **** TODO ****** is selected
            public void itemStateChanged( ItemEvent event )
            {
               mainCourseJComboBoxItemStateChanged( event );
            }

         } // end anonymous inner class

      ); // end addItemListener

//       **** TODO ****** add items to mainCourseJComboBox
//       add items to mainCourseJComboBox
      mainCourseJComboBox.addItem( "" );
      loadCategory( "Main Course", mainCourseJComboBox );
      
      // **** TODO ****** set up dessertJLabel
      dessertJLabel = new JLabel();
      dessertJLabel.setBounds( 8, 117, 80, 24 );
      dessertJLabel.setText( "Dessert:" );
      menuItemsJPanel.add( dessertJLabel );
      
     // **** TODO ****** set up dessertJComboBox 
      dessertJComboBox = new JComboBox();
      dessertJComboBox.setBounds( 88, 117, 128, 24 );
      dessertJComboBox.setEnabled( false );
      menuItemsJPanel.add( dessertJComboBox );
      dessertJComboBox.addItemListener(
      // **** TODO ****** add items to dessertJComboBox 

         new ItemListener()  // **** TODO ****** anonymous inner class
         {
            // **** TODO ****** event handler called when item in dessertJComboBox
            // **** TODO ****** is selected
            public void itemStateChanged( ItemEvent event )
            {
               dessertJComboBoxItemStateChanged( event );
            }

         } // end anonymous inner class

      ); // end addItemListener

//       **** TODO ****** add items to dessertJComboBox
//       add items to dessertJComboBox
      dessertJComboBox.addItem( "" );
      loadCategory( "Dessert", dessertJComboBox );
      
   } // end method createMenuItemsJPanel
     
      // **** TODO ****** set up loadTableNumbers
      private void loadTableNumbers() 
      {
          // read all table numbers from database
          try
          {
              // obtain all table numbers
              myResultSet = myStatement.executeQuery(
                      "SELECT tableNumber FROM restaurantTables" );
              // add numbers to tableNumberJComboBox
              while ( myResultSet.next() == true )
              {
                  tableNumberJComboBox.addItem(
                         String.valueOf( myResultSet.getInt(
                              "tableNumber" ) ) );        
              }//end while
              myResultSet.close(); // close myResultSet
              
          }//end try
          // catch SQLException
          catch ( SQLException exception )
          {
              exception.printStackTrace();
          }
      }// end method loadTableNumbers   
   
   // **** TODO ****** add items to JComboBox
   private void loadCategory(
      String category, JComboBox categoryJComboBox )
   {
     // read all items from database for specified category
    try
    {
    // obtain all items in specified category
    myResultSet = myStatement.executeQuery( 
    "SELECT name FROM menu WHERE category='"+category+"'");
    // add items to JComboBox
    while ( myResultSet.next() == true )
        {
            categoryJComboBox.addItem(
            myResultSet.getString( "name" ) );
        }
            myResultSet.close(); // close myResultSet
    } // end try
    // catch SQLException
    catch ( SQLException exception )
    {
    exception.printStackTrace();
    }       
   } // end method loadCategory

    // **** TODO ****** user select tableNumber
 private void tableNumberJComboBoxItemStateChanged( ItemEvent event )
 {
     String selectedTableNumber = ( String ) event.getItem();
     // select a number
     if ( !selectedTableNumber.equals( "" ) && event.getStateChange() == ItemEvent.SELECTED )
     {
         // load table data
         try
         {
             // get table data
             myResultSet = myStatement.executeQuery( "SELECT * FROM "
                     + "restaurantTables WHERE tableNumber = " +
                     Integer.parseInt( selectedTableNumber ) );
             
             // if myResultSet not empty
             if ( myResultSet.next() == true )
             {
                 waiterNameJTextField.setText(
                 myResultSet.getString( "waiterName" ) );
                 subtotal = myResultSet.getDouble( "subtotal" );
                 displayTotal( subtotal );
             }//end if
             
             myResultSet.close(); // close myResultSet
         }//end try
         // catch SQLException
         catch ( SQLException exception )
         {
             exception.printStackTrace();
         }
        //enableJComboBoxes in menuItemsJPanel
           menuItemsJPanel.setEnabled(true);
           beverageJComboBox.setEnabled(true);
           mainCourseJComboBox.setEnabled(true);
           appetizerJComboBox.setEnabled(true);
           dessertJComboBox.setEnabled (true);
        //disable JComboBox in waiterJPanel
           waiterJPanel.setEnabled(false);
           tableNumberJComboBox.setEnabled(false);
           saveTableJButton.setEnabled(true);
           calculateBillJButton.setEnabled(true);
           payBillJButton.setEnabled(true);
         
     }//end if
     
 }// end method tableNumberJComboBoxItemStateChanged
   
   
   // **** TODO ****** user select beverage
   private void beverageJComboBoxItemStateChanged( ItemEvent event )
   {
      // select an item
       if ( event.getStateChange() == ItemEvent.SELECTED )
           {
             billItems.add(( String ) beverageJComboBox.getSelectedItem() );  
           }
           
   } // end method beverageJComboBoxItemStateChanged

   // **** TODO ****** user select appetizer
   private void appetizerJComboBoxItemStateChanged( ItemEvent event )
   {
       // select an item
       if ( event.getStateChange() == ItemEvent.SELECTED )
           {
             billItems.add(( String ) appetizerJComboBox.getSelectedItem() );  
           }
         
   } // end method appetizerJComboBoxItemStateChanged

   // **** TODO ****** user select main course
   private void mainCourseJComboBoxItemStateChanged( 
      ItemEvent event )
   {
       // select an item
       if ( event.getStateChange() == ItemEvent.SELECTED )
           {
             billItems.add(( String ) mainCourseJComboBox.getSelectedItem() );  
           }      
   } // end method mainCourseJComboBoxItemStateChanged

   // **** TODO ****** user select dessert
   private void dessertJComboBoxItemStateChanged( ItemEvent event )
   {
        // select an item
       if ( event.getStateChange() == ItemEvent.SELECTED )
           {
             billItems.add(( String ) dessertJComboBox.getSelectedItem() );  
           }       
   } // end method dessertJComboBoxItemStateChanged
   
    // **** TODO ****** user click saveTableJButton
    private void saveTableJButtonActionPerformed( ActionEvent event )  
    {
        // calculate subtotal
        subtotal = calculateSubtotal();
        // update subtotal in database
        updateTable();
        // reset JFrame
        resetJFrame();
        
    }// end method saveTableJButtonActionPerformed
    
    // **** TODO ****** user click Pay Bill Button    
    private void payBillJButtonActionPerformed(ActionEvent event )
    {
        // calculate subtotal
        subtotal = 0.0;
        // update subtotal in database
        updateTable();
        // reset JFrame
        resetJFrame();        
    }
    
    // **** TODO ****** method updateTable  
    private void updateTable()
    {
        // update subtotal for table number in database
        try
        {
            myStatement.executeUpdate( "UPDATE restaurantTables SET " +
            "subtotal = " + subtotal + " WHERE tableNumber = " +
            Integer.parseInt(
                    ( String ) tableNumberJComboBox.getSelectedItem() ) );
        }//end try
        catch ( SQLException exception )
        {
            exception.printStackTrace();
        }
    }// end method updateTable
    
    //delete this and where class get called
   // **** TODO ****** user click Calculate Bill JButton
   private void calculateBillJButtonActionPerformed( 
      ActionEvent event )          
   {
//     --   delete  later if(!tableNumberJTextField.getText().equals("") && !waiterNameJTextField.getText().equals(""))
//       {
           double total = calculateSubtotal();
           // display subtotal, tax and total
           displayTotal( total );   
//       }
//       else    
//       {    
//     --   delete  later   JOptionPane.showMessageDialog (null, "Enter value for table number and waiter number field.", "Please fill out", JOptionPane.INFORMATION_MESSAGE);
//      }
   } // end method calculateBillJButtonActionPerformed

   // **** TODO ****** calculate subtotal
   private double calculateSubtotal()
   {
       double total = subtotal;
       Object[] items = billItems.toArray();
       
       // get data from database
       try {
          // get price for each item in items array
           for ( int i = 0; i < items.length; i++ ) {
             // execute query to get price
               myResultSet = myStatement.executeQuery( "SELECT price " +
                  "FROM menu WHERE name = '" + ( String ) items[ i ] +"'" );
               // myResultSet not empty
               if ( myResultSet.next() == true )
               {
                total += myResultSet.getDouble( "price" );
               } 
               myResultSet.close(); // close myResultSet
               } // end for
           } // end try
       // catch SQLException
         catch ( SQLException exception ) {
             exception.printStackTrace();
             }
       return total;
       
   } // end method calculateSubtotal
   
   //method displayTotal is used to display subtotal, tax and total
   private void displayTotal( double total )
{
    // define display format
    DecimalFormat dollars = new DecimalFormat( "$0.00" );
    // display subtotal
    subtotalJTextField.setText( dollars.format( total ) );
    // calculate and display tax
    double tax = total * TAX_RATE;
    taxJTextField.setText( dollars.format( tax ) );
    // display total
    totalJTextField.setText(
            dollars.format( total + tax ) );    
} // end method displayTotal
 
    // **** TODO ****** resetJFrame method
   private void resetJFrame()
   {
       // reset instance variable
       billItems = new ArrayList();
       
       // reset and disable menuItemsJPanel
       menuItemsJPanel.setEnabled( false );
       beverageJComboBox.setSelectedIndex( 0 );
       beverageJComboBox.setEnabled( false );
       appetizerJComboBox.setSelectedIndex( 0 );
       appetizerJComboBox.setEnabled( false );
       mainCourseJComboBox.setSelectedIndex(0);
       mainCourseJComboBox.setEnabled(false);
       dessertJComboBox.setSelectedIndex(0);
       dessertJComboBox.setEnabled(false);
       
       // reset and enable waiterJPanel
       waiterJPanel.setEnabled( true );
       tableNumberJComboBox.setEnabled( true );
       tableNumberJComboBox.setSelectedIndex( 0 );
       waiterNameJTextField.setText( "" );
       
       // clear JTextFields
       subtotalJTextField.setText( "" );
       taxJTextField.setText( "" );
       totalJTextField.setText( "" );   
       
       // disable JButtons
       saveTableJButton.setEnabled( false );
       calculateBillJButton.setEnabled( false );
       payBillJButton.setEnabled( false );              
       
   }// end method resetJFrame 
   
   
    // **** TODO ****** user close window
   private void frameWindowClosing( WindowEvent event )
   {
           // close myStatement and database connection
           try
           {
               myStatement.close();
               myConnection.close();
           }
           catch ( SQLException sqlException )
           {
               sqlException.printStackTrace();
           }
           finally
           {
               System.exit( 0 );
           }
      
   }  // end method frameWindowClosing  
   
   // **** TODO ****** method main
   public static void main( String[] args ) 
   {
       //**** TODO ****** check command-line arguments
      if ( args.length == 2 )
      {
          
          //**** TODO ****** get command-line arguments
         String databaseUserName = args[ 0 ];
         String databasePassword = args[ 1 ];

          //**** TODO ****** create new RestaurantBillCalculator
         RestaurantBillCalculator application = 
            new RestaurantBillCalculator ( 
               databaseUserName, databasePassword );
      }
      else
      {
         System.out.println( "Usage: java " + 
            "RestaurantBillCalculator databaseUserName databasePassword" );
    }

   } // end method main

} // end class RestaurantBillCalculator

