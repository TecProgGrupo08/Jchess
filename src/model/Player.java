/**
 * File: Player.java
 * Purpose: A Player with the relevant characteristics for the game
 * 
 * */

package model;

import static lookup.Pieces.WHITE;

public abstract class Player {

	private String name = "";
	protected byte colour = 0;
	private int score = 0;
	private long timeRemaining = 0;
	private boolean human = false;

	
	/**
	 * Constructor method of Player
	 * @param = String name - name pf the player
	 * @param = Byte colour - color of the player's pieces
	 * */
	public Player( String name, byte colour ) {
		this.setName( name );
		this.setColour( colour );
		this.score = 0;
		this.setTimeRemaining( -1 );
	}

	/**
	 * Constructor method of Player
	 * @param = String name - name pf the player
	 * @param = Byte colour - color of the player's pieces
	 * @param = Long timeRemaining - time remaining for this player lose
	 * */
	
	public Player( String name, byte colour, long timeRemaining ) {
		this.setName( name );
		this.setColour( colour );
		this.score = 0;
		this.setTimeRemaining( timeRemaining );
	}

	public abstract Move getMove(Board b);

	public abstract boolean isHuman();

	/**
	 * Get the color of the player's pieces
	 * @return Colour - pieces colour
	 * */
	protected String getColour() {
		return ( this.colour == WHITE ? "White" : "Black" );
	}

	/**
	 * Get the color of the player's pieces
	 * @return colour - pieces colour 
	 * */
	public byte getColourByte(){
		return colour;
	}
	

	/**
	 * Set the color of the player's pieces
	 * @param colour - pieces colour 
	 * */
	protected void setColour( byte colour ) {
		this.colour = colour;
	}


	/**
	 * Get the name of the player
	 * @return name - name of the player 
	 * */
	public String getName() {
		return name;
	}


	/**
	 * Set the name of the player
	 * @param String name - name of the player 
	 * */
	protected void setName( String name ) {
		this.name = name;
	}
	/**
	 * set the time that the player have.
	 * @return Long timeRemaining 
	 * */
	protected long getTimeRemaining() {
		return timeRemaining;
	}
	

	/**
	 * set the time that the player have.
	 * @param Long timeRemaining 
	 * */
	protected void setTimeRemaining( long timeRemaining ) {
		this.timeRemaining = timeRemaining;
	}
	

}