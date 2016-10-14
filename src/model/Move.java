/**
 * File: Move.java
 * Purpose: Class responsable for make a valid move
 * 
 * */


package model;

/**
 * The move class represents a move in a chess game.
 *
 * @author Gary Blackwood
 */
public class Move {
	private int from = 0;
	private int to = 0;
	
	/**
	 * Initialise and create a new Move object that represents the move as specified
	 * in the arguments.
	 *
	 * @param from    The array index of the square that a piece is moving from, its starting position.
	 * @param to      The array index of the square that a piece is moving to, its destination.
	 */
	public Move( int from, int to ) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Retreive the starting position of a move.
	 *
	 * @return The array index of the square that a piece started on.
	 */
	public int from() {
		return ( this.from );
	}

	/**
	 * Retreive the destination of a move.
	 *
	 * @return The array index of the destination square.
	 */
	public int to() {
		return ( this.to );
	}

	public String getGuiString(){
		return (indexToNotation(this.from)) + "-" + indexToNotation (this.to);
	}

	public boolean equals( Object other ) {
		if ( other == null ) {
			return ( false );
		}
		else if ( other == this ) {
			return ( true );
		}
		else if ( other.getClass() != this.getClass() ) {
			return ( false );
		}
		else{
			// nothing to do here
		}
		Move otherMove = (Move) other;
		
		return ( this.to == otherMove.to() && this.from == otherMove.from() );
		
	} // End of method

	public String toString() {
		return ( "Move(" + indexToNotation( this.from ) + " " + indexToNotation( this.to ) + ")" );
	}


	public String indexToNotation( int index ) {
		char c = (char)( ( index % 16 ) + 'a' ); // discover what 16 means to replace magic number by a constant
		return ( c + "" + ( ( index / 16 ) + 1 ) );
	}

	public static int notationToIndex( String notation ) {
		int rank = Character.getNumericValue( notation.charAt( 1 ) ) - 1;
		int file = notation.charAt( 0 ) - 'a';
		return ( rank*16 + file );  // discover what 16 means to replace magic number by a constant
	}
}