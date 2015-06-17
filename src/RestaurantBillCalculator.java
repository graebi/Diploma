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
import java.text.*;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
   private JTextField tableNumberJTextField;
   
//   // JLabel and JTextField for table number
//   private JLabel tableNumberJLabel;
//   private JTextField tableNumberJTextField;

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
   
   //declare instance variables Tax -added
   private double tax;
   
   //declare instance variables Total -added
   private double total;   
   
   
   // constructor
   public RestaurantBillCalculator( 
  String databaseUserName, String databasePassword )
   {
 
    // make database connection
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String driver = "com.mysql.jdbc.Driver";
        
        try{
         Class.forName(driver).newInstance();
         myConnection = DriverManager.getConnection(url,databaseUserName, databasePassword); 
         myStatement = myConnection.createStatement();
         System.out.println("Connected to DB");
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
     
     //Calculate Bill button
     calculateBillJButton = new JButton();
     calculateBillJButton.setBounds( 70, 310, 110, 20 );
     calculateBillJButton.setText( "Calculate Bill" );
     contentPane.add( calculateBillJButton ); 
     calculateBillJButton.addActionListener(
        new ActionListener()
            {
             public void actionPerformed( ActionEvent event ) 
            {
               calculateBillJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end addActionListener
         
     
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
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//stop program when close the jframe
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
      //tableNumberJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
      waiterJPanel.add(tableNumberJLabel);//Add tableNumberJLabel onto waiterJPane     

      // **** TODO ****** set up tableNumberJTextField
      //Create tableNumberJTextField in waiterJPanel
      tableNumberJTextField = new JTextField();//Create tableNumberJTextField in waiterJPanel
      tableNumberJTextField.setHorizontalAlignment(SwingConstants.RIGHT);//
      tableNumberJTextField.setBounds(125, 25, 90, 20);// Size and position of tableNumberJTextField in Waiter information
      waiterJPanel.add(tableNumberJTextField);//Add tableNumberJTextField onto waiterJPanel
       
      // **** TODO ****** set up waiterNameJLabel
      //Create waiterNameJLabel in waiterJPanel
      waiterJPanel.setLayout(null);//NO layout manager in use
      waiterNameJLabel = new JLabel();//Create waiterNameJLabel in waiterJPanel
      waiterNameJLabel.setText("Waiter name:");//JLabel Waiter name in waiterJPanel
      waiterNameJLabel.setBounds(30, 50, 90, 20);// Size and position of waiterNameJLabel in Waiter information
      //waiterNameJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
      waiterJPanel.add(waiterNameJLabel);//Add waiterNameJLabel onto waiterJPanel      

      // **** TODO ****** set up waiterNameJTextField
      //Create tableNumberJTextField in waiterJPanel
      tableNumberJTextField = new JTextField();//Create tableNumberJTextField in waiterJPanel
      tableNumberJTextField.setHorizontalAlignment(SwingConstants.RIGHT);//
      tableNumberJTextField.setBounds(125, 50, 90, 20);// Size and position of tableNumberJTextField in Waiter information
      waiterJPanel.add(tableNumberJTextField);//Add tableNumberJTextField onto waiterJPanel

   } // end method createWaiterJPanel

   // **** TODO ****** create menuItemsJPanel
   private void createMenuItemsJPanel()
   {
      //  set up menuItemsJPanel
      menuItemsJPanel = new JPanel();
      menuItemsJPanel.setBounds( 20, 152, 232, 152 );
      menuItemsJPanel.setBorder( BorderFactory.createTitledBorder( 
      BorderFactory.createEtchedBorder(), "Menu Items" ) );
//      menuItemsJPanel.setEnabled( false ); - delete this
      menuItemsJPanel.setLayout( null );

      //  set up beverageJLabel
      beverageJLabel = new JLabel();
      beverageJLabel.setBounds( 8, 24, 80, 24 );
      beverageJLabel.setText( "Beverage:" );
      menuItemsJPanel.add( beverageJLabel );

      // set up beverageJComboBox
      beverageJComboBox = new JComboBox();
      beverageJComboBox.setBounds( 88, 24, 128, 24 );
//      beverageJComboBox.setEnabled( false ); - delete this
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
      loadCategory( "Beverage", beverageJComboBox );


      // **** TODO ****** set up appetizerJLabel
      appetizerJLabel = new JLabel();
      appetizerJLabel.setBounds( 8, 55, 80, 24 );
      appetizerJLabel.setText( "Appetizer:" );
      menuItemsJPanel.add( appetizerJLabel) ;
     

      // **** TODO ****** set up appetizerJComboBox
      appetizerJComboBox = new JComboBox();
      appetizerJComboBox.setBounds( 88, 55, 128, 24 );
//      appetizerJComboBox.setEnabled( false ); - delete this
      menuItemsJPanel.add( appetizerJComboBox );
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
     // mainCourseJComboBox.setEnabled( false );- delete this
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
//      dessertJComboBox.setEnabled( false );- delete this
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

   // **** TODO ****** user click Calculate Bill JButton
   private void calculateBillJButtonActionPerformed( 
      ActionEvent event )
   {
       double total = calculateSubtotal();
       // display subtotal, tax and total
       displayTotal( total );
      
   } // end method calculateBillJButtonActionPerformed

   // **** TODO ****** calculate subtotal
   private double calculateSubtotal()
   {
      return 0;

   } // end method calculateSubtotal

//   // **** TODO ****** user close window
//   private void frameWindowClosing( WindowEvent event )
//   {
      
//   }  // end method frameWindowClosing

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

