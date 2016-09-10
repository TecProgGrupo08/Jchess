package view;

import java.awt.Color;
/**
 * Theme provides Theme objects with color-information, a themeName and the folder number of a theme
 * @author Andrew Meikle, Joshua Schaeuble (all containing methods)
 *
 */
public class Theme {
  private String themeName = "";
  private Color blackColor = null;
  private Color whiteColor = null;
  private Color activePosition = null;
  private Color possiblePosition = null;
  private Color previousArrow = null;
  private int folderNumber = 0;

  /**Get the black color
   * @return Color - blackColor
   * */
  public Color getBlackColor() {
    return blackColor;
  }


  /**Get the white color
   * @return Color - whiteColor
   * */
  public Color getWhiteColor() {
    return whiteColor;
  }


  /**Get the color of the activate position 
   * @return Color - activatePosition
   * */
  public Color getActivePosition() {
    return activePosition;
  }


  /**Get the color of possibles moves positions
   * @return Color - possiblePosition
   * */
  public Color getPossiblePosition() {
    return possiblePosition;
  }


  /**Get the color of previous arrow
   * @return Color - previousArrow
   * */
  public Color getPreviousArrow() {
    return previousArrow;
  }

  
  /**Get the folder number
   * @return int - folderNumber
   * */
  public int getFolderNumber(){
    return this.folderNumber;
  }
  
  /**Get the theme name
   * @return String - themeName
   * */
  public String getName () {
    return themeName;
  }
  
  /**
   * constructor to instantiate a new theme
   * @param themeName
   * @param black
   * @param white
   * @param active
   * @param possible
   * @param previous
   * @param folderNumber
   */
  public Theme(String themeName, Color black, Color white, Color active, Color possible, Color previous, int folderNumber) {
    this.themeName=themeName;
    this.blackColor=black;
    this.whiteColor=white;
    this.activePosition=active;
    this.possiblePosition=possible;
    this.previousArrow=previous;
    this.folderNumber=folderNumber;    
  }
}
