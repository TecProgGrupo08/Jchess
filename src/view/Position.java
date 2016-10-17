package view;

public class Position {
	private int axesXPosition;
	private int axesYPosition;
	private String axesXString;
	private int axesYString;
	private int squarePosition;

	/**
	 * calculates the graphical position out of the byte-square-board.
	 * @param squarePos
	 */
	public Position(int squarePosition){
	  final int NUMBER_OF_COORDINATES = 16;
		
	  this.axesXPosition=(squarePosition%NUMBER_OF_COORDINATES)+1;
	  this.axesYPosition=intMe(((squarePosition-(squarePosition%NUMBER_OF_COORDINATES))/NUMBER_OF_COORDINATES)+1);
	  this.axesYString=intMe(this.axesYPosition);
	  this.axesXString=stringMe(this.axesXPosition);
	  this.squarePosition=squarePosition;
	}
	
	/**
	 * calculates the graphical position out of the byte-square-board.
	 * @param int - x, y
	 */
	public Position(int axesXPosition, int axesYPosition) {
		this.axesXPosition = axesXPosition;
		this.axesYPosition = axesYPosition;
		this.axesXString=stringMe(axesXPosition);
		this.axesYString = intMe(axesYPosition);
		
		setSquarePosition();

	}

	/**
	 * calculates the graphical position out of the byte-square-board.
	 * @param x, y
	 */
	public Position(String axesXPosition, int axesYPosition) {
		this.axesXString = axesXPosition;
		this.axesYString = axesYPosition;
		this.axesXPosition=intMe(axesXPosition.charAt(0));
		this.axesYPosition = intMe(axesYPosition);
		
		setSquarePosition();

	}
	/**
	 * calculates the graphical position out of the byte-square-board.
	 * @param String - string
	 */
	public Position(String string) {
		char[] c = string.toCharArray();
		if(c.length==2){
			this.axesYString=Character.digit(c[1], 10);
			this.axesXString = String.valueOf(c[0]);
			this.axesXPosition=intMe(c[0]);
			this.axesYPosition = intMe(Character.digit(c[1], 10));
			
			setSquarePosition();

		}
		
	}
	
	/**
	 * 	Get the x position 
	 * @return int - x
	 */
	
	public int getX() {
		return axesXPosition;
	}

	/**
	 * 	Get the y position 
	 * @return int - y
	 */
	
	public int getY() {
		return axesYPosition;
	}

	/**
	 * 	Get the String version of x position 
	 * @return int - x
	 */
	public String getXName() {
		return axesXString;
	}

	/**
	 * 	Get the String version of y position 
	 * @return int - y
	 */
	public int getYName() {
		return axesYString;
	}
	
	/**
	 * 	Get the square position 
	 * @return int - squarePosition
	 */
	public int getSquarePosition(){
	  return squarePosition;
	}

	/**
	 * 	Get the concatenated value go xString and yString 
	 * @return int - x
	 */
	public String getName() {
		if (axesXString != "null" && axesYString != 0) {
			return axesXString + axesYString;
		} else
			return "null";
	}


	/**
	 * Transform a letter position to a int value.
	 * @param squarePos
	 * @return int - a number 0 to 8
	 */
	
	private int intMe(char valueOf) {
		int equivalentNumber = 0;
		
		switch (valueOf){
		case 'a': {
			equivalentNumber = 1;
			break;
		}	
		case 'b': {
			equivalentNumber = 2;
			break;
		}
		case 'c': {
			equivalentNumber = 3;
			break;
		}	
		case 'd': {
			equivalentNumber = 4;
			break;
		}	
		case 'e': {
			equivalentNumber = 5;
			break;
		}	
		case 'f': {
			equivalentNumber = 6;
			break;
		}	
		case 'g': { 
			equivalentNumber = 7;
			break;
		}	
		case 'h': {
			equivalentNumber = 8;
			break;
		}	
		default: {
			equivalentNumber = 0;
			break;
		}

		}
		assert(equivalentNumber>=0):"invalid equivalent number"+equivalentNumber;
        assert(equivalentNumber<=8):"invalid equivalent number"+equivalentNumber;
        
        return equivalentNumber;
	}
	
	/**
	 * Transform a int 0 to 8 to his opposite in a modular aritimetic of mod 9 
	 * @param value
	 * @return int - a number 0 to 8
	 */
	
	private int intMe(int value) {
		int equivalentNumber = 0;
		int zahl = value;
		switch (zahl) {
			case 1:{
				equivalentNumber = 8;
				break;
			}
			case 2:{
				equivalentNumber = 7;
				break;
			}
			case 3:{
				equivalentNumber = 6;
				break;
			}
			case 4:{
				equivalentNumber = 5;
				break;
			}
			case 5:{
				equivalentNumber = 4;
				break;
			}
			case 6:{
				equivalentNumber = 3;
				break;
			}
			case 7:{
				equivalentNumber = 2;
				break;
			}
			case 8:{
				equivalentNumber = 1;
				break;
			}
			default:{
				equivalentNumber = 0;
				break;
			}

		}
		assert(equivalentNumber>=0):"invalid equivalent number"+equivalentNumber;
        assert(equivalentNumber<=8):"invalid equivalent number"+equivalentNumber;
		return equivalentNumber;
	}
	
	/**
	 * Transform a int position to a String position value.
	 * @param xValue
	 * @return int - a number 0 to 8
	 */

	private String stringMe(int xValue) {
		String equivalentLetter = null;
		int zahl = xValue;
		switch (zahl) {
			case 1:{
				equivalentLetter = "a";
			}
			case 2:{
				equivalentLetter =  "b";
			}
			case 3:{
				equivalentLetter =  "c";
			}
			case 4:{
				equivalentLetter =  "d";
			}
			case 5:{
				equivalentLetter =  "e";
			}
			case 6:{
				equivalentLetter =  "f";
			}
			case 7:{
				equivalentLetter =  "g";
			}
			case 8:{
				equivalentLetter =  "h";
			}
			default:{
				equivalentLetter = null;
			}

		}
		return equivalentLetter;

	}
	/**
	 * set the square position
	 * 
	 * */
	private void setSquarePosition(){
		final int NUMBER_OF_COORDINATES = 16;
		int Xposition = getX()-1;
		int Yposition = intMe(getY())-1;
		
		this.squarePosition = Xposition + Yposition * NUMBER_OF_COORDINATES;
	}
	


}
