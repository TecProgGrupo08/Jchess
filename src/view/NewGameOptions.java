package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//Class where the player choose the game settings
public class NewGameOptions {

  private final int EASY_DIFFICULTY_DEPTH = 1;
  private final int MEDIUM_DIFFICULTY_DEPTH = 3;
  private final int HARD_DIFFICULTY_DEPTH = 5;
  private final int TEXTFIELD_LIMIT = 12;

  // Declare dimensions to match joshua's start screen
  private final Dimension MINDIM = new Dimension (300, 300);
  private final Dimension STARTDIM = new Dimension (410, 350);

  private JComboBox<String> player1TypeComboBox = null;
  private JComboBox<String> player1DifficultyComboBox = null;
  private JComboBox<String> player2DifficultyComboBox = null;
  private JComboBox<String> player2TypeComboBox = null;
  private JCheckBox timerCheckBox = null;
  private JTextField player2Name = null;
  private JTextField player1Name = null;
  private JComboBox<String> timerDataCheckBox = null;

  private MainWindow myParentWindow = null;

  private javax.swing.JDialog thisFrame = null;
  private javax.swing.JButton beginButton = null;
  private javax.swing.JPanel timerPanel = null;

  /**
   * Constructor that takes a MainWindow object and sets this window to be the parentWindow of this new frame.
   * @param mainWindow
   */
  public NewGameOptions(MainWindow mainWindow) {
    this.myParentWindow = mainWindow;
    start ();
  }

  /**
   * Acts as the constructor for the NewGameOptions frame
   */
  private void start() {	  
	thisFrame = new JDialog ();
    thisFrame.setTitle ("New game options");
    thisFrame.setMinimumSize (MINDIM);
    thisFrame.setSize (STARTDIM);
    thisFrame.setLayout (new java.awt.GridBagLayout ());
    GridBagConstraints layout = new GridBagConstraints ();

    thisFrame.setDefaultCloseOperation (javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    this.initiliseComponents ();
    this.setComponentsText ();
    this.setPreferredSize ();
    this.addComponentsToFrame (layout);
    this.setUpListeners ();

    
    //Centers the frame
    thisFrame.setLocationRelativeTo (null);
    thisFrame.setVisible (true);
    
    assert(thisFrame!=null):"invalid thisFrame";
  }

  /**
   * Initialises all the components
   */
  private void initiliseComponents() {
    player2Name = new JTextField ();
    player1Name = new JTextField ();
    player1DifficultyComboBox = new JComboBox<String>();
    beginButton = new javax.swing.JButton ();
    player2DifficultyComboBox = new JComboBox<String>();
    player1TypeComboBox = new JComboBox<String>();
    player2TypeComboBox = new JComboBox<String>();

    timerPanel = new javax.swing.JPanel ();
    timerCheckBox = new JCheckBox ();
    timerDataCheckBox = new JComboBox<String>();
    
    
  }

  /**
   * Sets up the texts that the components display
   */
  private void setComponentsText() {
    /* 
     * Setting up player 1's name,the contents of the drop down menus and the
     * names of the check boxes
     */
    player1Name.setText ("Player 1");
    player1DifficultyComboBox.setModel(new javax.swing.DefaultComboBoxModel<String> (
    									new String[] { "Easy", "Medium", "Hard"}));

    /* 
     * Setting up the fields to be displayed, default player 1 human ticked and
     * the AI fields are disabled
     */
    player1DifficultyComboBox.setEnabled (false);

    /* 
     * Setting up player 2's name,the contents of the drop down menus and the
     * names of the check boxes
     */
    player2Name.setText ("Player 2");
    player2DifficultyComboBox.setModel (new javax.swing.DefaultComboBoxModel<String> (
    										new String[] { "Easy", "Medium", "Hard"}));

    //Populates the player type combo boxes with the appropriate data
    player1TypeComboBox.setModel (new javax.swing.DefaultComboBoxModel<String> (
    								new String[] { "Human", "AI" }));
    player2TypeComboBox.setModel (new javax.swing.DefaultComboBoxModel<String> (
    								new String[] { "Human", "AI" }));

    // default to medium for AI's
    player1DifficultyComboBox.setSelectedIndex (1);
    player2DifficultyComboBox.setSelectedIndex (1);

    // Player 1 default human, player 2 default AI
    player1TypeComboBox.setSelectedIndex (0);
    player2TypeComboBox.setSelectedIndex (1);

    //Sets the button text
    timerCheckBox.setText ("Timer");
    beginButton.setText ("Begin");
    
    //Sets the timer functionality, options and disables it
    timerDataCheckBox.setModel (new javax.swing.DefaultComboBoxModel<String> (
        new String[] { "1","2","5","10","15","20","30","45","60" }));
    timerDataCheckBox.setEnabled (false);
    timerDataCheckBox.setSelectedIndex (2);
    
    assert(player1Name!=null);
    assert(player1DifficultyComboBox!=null);
    assert(player2Name!=null);
    assert(player2DifficultyComboBox!=null);
    assert(timerDataCheckBox!=null);

  }

  /**
   * Sets the preferred size of all the components
   */
  private void setPreferredSize() {

    player1Name.setPreferredSize (new Dimension (51, 17));
    player1DifficultyComboBox.setPreferredSize (new Dimension (92, 29));

    player2Name.setPreferredSize (new Dimension (51, 17));
    player2DifficultyComboBox.setPreferredSize (new Dimension (92, 29));

    timerCheckBox.setPreferredSize (new Dimension (58, 21));
    timerDataCheckBox.setMinimumSize (new Dimension (50, 29));
    timerPanel.setPreferredSize (new Dimension (115, 41));

    beginButton.setPreferredSize (new Dimension (50, 29));
    player1TypeComboBox.setPreferredSize (new Dimension (88, 29));
    player2TypeComboBox.setPreferredSize (new Dimension (88, 29));

    assert(player1Name!=null);
    assert(player1DifficultyComboBox!=null);
    assert(player2Name!=null);
    assert(player2DifficultyComboBox!=null);
    assert(beginButton!=null);

  }



  /**
   * Adds components to the frame
   * 
   * @param layout
   */
  private void addComponentsToFrame(GridBagConstraints layout) {

    layout.fill = GridBagConstraints.HORIZONTAL;
    javax.swing.JLabel temp = new javax.swing.JLabel ();
    temp.setPreferredSize (MINDIM);
    layout = setXYAndWeight (layout, 1, 0, 0, 1);

    /* 
     * Insets the player label away from the box boundary and away from the left
     *  boundary grid space
     */
    layout.insets = new Insets (0, 10, 0, 80);

    // player 1 position
    layout = setXYAndWeight (layout, 0, 0, 1, 1);
    thisFrame.add (player1Name, layout);
    // player 2 position
    layout = setXYAndWeight (layout, 2, 0, 1, 1);
    thisFrame.add (player2Name, layout);

    /* 
     * Sets the inset for each of the player type combo boxes, so they are away
     * from the edge
     */
    layout.insets = new Insets (0, 10, 0, 70);

    // player 1 type combo box
    layout = setXYAndWeight (layout, 0, 1, 1, 0);
    thisFrame.add (player1TypeComboBox, layout);
    // player 2 type combo box
    layout = setXYAndWeight (layout, 2, 1, 0, 1);
    thisFrame.add (player2TypeComboBox, layout);

    /* Insets all the AI difficulty boxes from the outside boundary and brings
     * them in from the right
     * so they dont fill up their grid space
     */
    layout.insets = new Insets (0, 10, 0, 55);

    // player 1 difficulty box
    layout = setXYAndWeight (layout, 0, 2, 0, 1);
    thisFrame.add (player1DifficultyComboBox, layout);
    // player 2 AI difficulty box
    layout = setXYAndWeight (layout, 2, 2, 0, 1);
    thisFrame.add (player2DifficultyComboBox, layout);

    /* 
     * Moves the Version boxes closer to the difficulty boxes, which brings
     * everything further up
     */
    layout.insets = new Insets (0, 10, 95, 55);

    // Adds timer stuff and moves timer check box to the left
    layout.insets = new Insets (0, 60, 0, 0);
    layout = setXYAndWeight (layout, 0, 4, 0, 1);
    thisFrame.add (timerCheckBox, layout);

    // Inset, movest the check box away from the right hand side
    layout.insets = new Insets (0, 0, 0, 40);
    layout = setXYAndWeight (layout, 1, 4, 0, 1);
    thisFrame.add (timerDataCheckBox, layout);
    layout = resetWeights (layout);

    layout.insets = new Insets (0, 40, 0, 40); // shrinks the button on both sides
    // begin button position
    layout = setXYAndWeight (layout, 2, 4, 0, 1);
    thisFrame.add (beginButton, layout);
    layout = resetWeights (layout);
    
    assert(layout!=null);
  }

  /**
   * Sets up the constraints for the gridbag and return the gridbagconstraint
   * 
   * @param layout
   *          GridBagConstraint
   * @param x
   *          value to be made equals to layout.gridx
   * @param y
   *          value to be made equals to layout.gridy
   * @param weight
   *          value to be inserted into weightx
   * @param weighty
   *          value to be inserted into weighty
   * @return Same gridbag constraint that was passed in
   */
  private GridBagConstraints setXYAndWeight(GridBagConstraints layout, int x, int y,
		 double weight, double weighty) {
	  		assert(layout!=null);
	  		layout.gridx = x;
		  	layout.gridy = y;
		  	if ( weight != -1 ) layout.weightx = weight;
		  	if ( weighty != -1 ) layout.weighty = weighty;
		  	return layout;
  }

  /**
   * Resets the weight to 0 for the parameter C nd then returns it reset
   * 
   * @param layout
   *  gridbagconstraints object to change
   * @return layout
   *  same gridbagconstraints objects but weightx and weighty have been set to 0
   */
  private GridBagConstraints resetWeights(GridBagConstraints layout) {
	  assert(layout!=null);
	  layout.weightx = 0.0;
	  layout.weighty = 0.0;
	  return layout;
  }

  /**
   * A private method for setting up the listeners for all the components on the screen
   */
  private void setUpListeners() {

    // Action listener for begin button
    beginButton.addActionListener (new ActionListener () {
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		player1Name.setForeground (Color.BLACK);
    		player2Name.setForeground (Color.BLACK);
    		createNewGame ();
    	}
    }
    );

    /* Sets up code for the the combo boxes, enabling/disabling the appropraite
     * fields
     * 
     */

    player1TypeComboBox.addActionListener (new ActionListener () {
    	
    	
    	
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		
    		if ( isPlayerAI (player1TypeComboBox) ){ 
    			player1DifficultyComboBox.setEnabled(true);
    		}
    		
    		else {
    			player1DifficultyComboBox.setEnabled(false);
    		}
    		
    	}
    }
    );

    player2TypeComboBox.addActionListener (new ActionListener () {
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		
    		if ( isPlayerAI (player2TypeComboBox) ){ 
    			player2DifficultyComboBox.setEnabled (true);
    		}
    		else{ 
    			player2DifficultyComboBox.setEnabled (false);
    		}
    		
    	}
    }
    );

    timerCheckBox.addActionListener (new ActionListener () {
    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		/*Checks whether or not the timer is selected, if it is then it disables it
    		 * otherwise it enables it
    		 * 
    		 */
    		if ( timerCheckBox.isSelected () ){
    			timerDataCheckBox.setEnabled (true);
    		}
    		else if ( !timerCheckBox.isSelected () ){
    			timerDataCheckBox.setEnabled (false);
    		}
    	}
    
    }
    );

  }

  /**
   * This method will create the ViewPlayer objects from the data supplied by the form objects and
   * pass them into the main window instance (myParentWindow). Also checks that the username is not over the specified limit
   * 
   */
  private void createNewGame(){
    int timer;
    //Checks if the two player name fields are within the length boundaries
     if (player1Name.getText ().length () >= TEXTFIELD_LIMIT ){
       player1Name.setForeground (Color.RED);
       JOptionPane.showMessageDialog (new JFrame (),"Player 1 name is too long, can be a maximum of "+TEXTFIELD_LIMIT+" characters");
       return;
     }
     else if (player2Name.getText ().length() >= TEXTFIELD_LIMIT){
       player2Name.setForeground (Color.RED);
       JOptionPane.showMessageDialog (new JFrame (),"Player2 name is too long, can be a maximum of "+TEXTFIELD_LIMIT+"characters");
       return;
     }
     //Creates the two player objects from the data occupying the form
    ViewPlayer player1 = new ViewPlayer(player1Name.getText(),
                        	getDepth(player1DifficultyComboBox),
                        	getIntFromDifficulty(player1DifficultyComboBox),
                        	!isPlayerAI(player1TypeComboBox));
    
    ViewPlayer player2 = new ViewPlayer(player2Name.getText(),
                        	getDepth(player2DifficultyComboBox),
                        	getIntFromDifficulty(player2DifficultyComboBox),
                        	!isPlayerAI(player2TypeComboBox));

    /*
     * Checks if the timer is selected, if its not it makes timer = -1, otherwise
     * it makes it equal to the time in the field in milliseconds
     */
    
    if(!timerCheckBox.isSelected ()){
      timer = -1;
     }
    
    else{
      timer=Integer.parseInt ((String)timerDataCheckBox.getSelectedItem ());
      timer = (timer * 60) * 1000;
    }


    myParentWindow.initGame (player1,player2, timer);

    thisFrame.dispose ();

  }

  /**
   * Given a combo box, if the selected item is easy it will return 1 otherwise it will return 2 (medium)
   * 
   * @param difficultyComboBox
   * @return
   */
  private int getIntFromDifficulty (JComboBox<String> difficultyComboBox){
    String textDifficulty = (String)difficultyComboBox.getSelectedItem ();
    if (textDifficulty.equalsIgnoreCase ("easy")){
    	return 1;
    }
    else{ 
    	return 2;
    }
  }

  /**
   * Given a combobox will take out the text and check if it contains the specified difficulties, easy, medium or hard and return a depth
   * dependant on the text in the combo box
   *
   * @param textDifficulty
   * @return int
   */
  private int getDepth (JComboBox<String> difficultyComboBox){
	assert(difficultyComboBox!=null);
	String textDifficulty = (String)difficultyComboBox.getSelectedItem ();
    if (textDifficulty.equalsIgnoreCase ("hard")) return HARD_DIFFICULTY_DEPTH;
    if (textDifficulty.equalsIgnoreCase ("medium")) return MEDIUM_DIFFICULTY_DEPTH;
    return EASY_DIFFICULTY_DEPTH;
  }

  /**
   * Returns true if the player type, passed in via the selected combo box is "AI"
   * False otherwise
   * @param thisComboBox
   * @return
   */
  private boolean isPlayerAI(JComboBox<String> thisComboBox) {
    return ( ( (String) thisComboBox.getSelectedItem () ).equalsIgnoreCase ("AI") );
  }
}
