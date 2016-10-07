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
	  this.axesXPosition=(squarePosition%16)+1;
	  this.axesYPosition=intMe(((squarePosition-(squarePosition%16))/16)+1);
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
		this.squarePosition = ((this.axesXPosition-1))+(intMe(this.axesYPosition)-1)*16;
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
		this.squarePosition = ((this.axesXPosition-1))+(intMe(this.axesYPosition)-1)*16;
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
			this.squarePosition = ((this.axesXPosition-1))+(intMe(this.axesYPosition)-1)*16;
		}
		
	}


	/**
	 * Transform a letter position to a int value.
	 * @param squarePos
	 * @return int - a number 0 to 8
	 */
	
	private int intMe(char valueOf) {
		switch (valueOf){
		case 'a': {
			return 1;
		}	
		case 'b': {
			return 2;
		}
		case 'c': {
			return 3;
		}	
		case 'd': {
			return 4;
		}	
		case 'e': {
			return 5;
		}	
		case 'f': {
			return 6;
		}	
		case 'g': { 
			return 7;
		}	
		case 'h': {
			return 8;
		}	
		default: {
			return 0;
		}

		}
	}
	
	/**
	 * Transform a int position to a String position value.
	 * @param xValue
	 * @return int - a number 0 to 8
	 */

	private String stringMe(int xValue) {

		int zahl = xValue;
		switch (zahl) {
		case 1:{
			return "a";
		}
		case 2:{
			return  "b";
		}
		case 3:{
			return  "c";
		}
		case 4:{
			return  "d";
		}
		case 5:{
			return  "e";
		}
		case 6:{
			return  "f";
		}
		case 7:{
			return  "g";
		}
		case 8:{
			return  "h";
		}
		default:{
			return null;
		}

		}

	}

	/**
	 * Transform a int 0 to 8 to his opposite in a modular aritimetic of mod 9 
	 * @param value
	 * @return int - a number 0 to 8
	 */
	
	private int intMe(int value) {
		int zahl = value;
		switch (zahl) {
		case 1:{
			return 8;
		}
		case 2:{
			return 7;
		}
		case 3:{
			return 6;
		}
		case 4:{
			return 5;
		}
		case 5:{
			return 4;
		}
		case 6:{
			return 3;
		}
		case 7:{
			return 2;
		}
		case 8:{
			return 1;
		}
		default:{
			return 0;
		}

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

}
