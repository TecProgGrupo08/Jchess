/**
    * File: SANParser.java
    * Purpose: Parser from SAN notation into jMove notation (jMove is our move type).
    */

package model;
import static lookup.Pieces.*;

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class SANParser {

	private Board board = null;
	private int to = 0;
	private int NUMBER_OF_PIECES = 16;
	static Logger logging = Logger.getLogger(SANParser.class);

	public SANParser() {
	}

	/**
	 * 
	 * @param board
	 * @param san
	 */
	protected Move sanToJ( Board board, final byte COLOUR, String san ) {
		/**
		 * e3 Ne3 Nfe3 N3e3 Nf3e3
		 * 
		 * 
		 */
		assert( !san.isEmpty() ):"Invalid san";
		assert( san.length() > 0 ):"Invalid string length";
		
		this.board = board;
		san = san.trim().replace( "x", "" ).replace( "+", "" ).replace( "#", "" );
		System.out.println( san );
		to = Move.notationToIndex( san.substring( san.length() - 2 ) );

		
		if ( san.length() == 2 ){

			return new Move( getPawnOrigin( san, COLOUR ), to );
		}
		else {
			
			return new Move( decide( san, COLOUR ), to );
		}
	}

	private int decide( final String SAN, final byte COLOUR ) {
		
		assert( !SAN.isEmpty() ):"Invalid san";
		assert( SAN.length() > 0 ):"Invalid string length";
		
		switch ( SAN.charAt( 0 ) ) {
			case 'K': {
				logging.info("King position requested");
				return getKingOrigin( SAN.substring( 1 ), COLOUR );
			}
			case 'Q': {
				logging.info("Queen position requested");
				return getQueenOrigin( SAN.substring( 1 ), COLOUR );
			}
			case 'B': {
				logging.info("Bishop position requested");
				return getBishopOrigin( SAN.substring( 1 ), COLOUR );
			}
			case 'N': {
				logging.info("Knight position requested");
				return getKnightOrigin( SAN.substring( 1 ), COLOUR );
			}
			case 'R': {
				logging.info("Rook position requested");
				return getRookOrigin( SAN.substring( 1 ), COLOUR );
			}
			default: {
				logging.info("Pawn position requested");
				return getPawnOrigin( SAN, COLOUR );
			}
		}
	}

	/**
	 * generateRookDestinations(to) for each destination if (
	 * board.pieceTypeAt(destination) == ROOK && board.pieceColourAt(colour){ if
	 * (from.length ==2) return destination; if (from.length ==3){ else if (
	 * from.charAt(0).isAlpha && from.charAt(0) == destination.file ) return
	 * destination; else if ( from charAt(0).isNum && from.charAt(0) ==
	 * destination.rank ) return destination; else if (from.charAt(0) ==
	 * destination.file && from.charAt(1) == destination.rank return destination;
	 * }
	 */

	private int getRookOrigin( final String SAN, final byte COLOUR ) {
		
		assert( SAN != null):"Invalid san";
		assert( !SAN.isEmpty() ):"Invalid san";
		assert( SAN.length() > 0 ):"Invalid string length";//is this assert condition right ???
		
		ArrayList<Integer> destinations = board.generateRookDestinations( to );

		logging.info("Begin search for a destination to a Rook");
		for ( int origin : destinations ) {
			
			
			if ( board.pieceTypeAt( origin ) == ROOK && 
				board.pieceColourAt( origin ) == COLOUR ) {
				
				if ( SAN.length() == 2 ) {
					
					return origin;
				
				}
				else if ( SAN.length() == 3 ) {
					
					if ( SAN.substring( 0, 1 ).matches( "[a-h]" ) && SAN.charAt( 0 ) == getFile( origin ) ){
					
						return origin;
					
					}
					else if ( SAN.substring( 0, 1 ).matches( "[0-7]" ) && SAN.charAt( 0 ) == getRank( origin ) ){
					
						return origin;
					
					}	
					else{
					
						//do nothing
					}
				
				} else if ( SAN.charAt( 0 ) == getFile( origin ) && SAN.charAt( 0 ) == getRank( origin ) ) {
					
					return origin;
				
				}
				else{
				
					//do nothing
				
				}
			}else{
			
				// Nothing to do
			
			}
		} // End of for

		return - 1; //Java and return code ? use exceptions 
	}

	/**
	 * 
	 * @param san
	 * @param colour
	 * @return origin of knight
	 */

	private int getKnightOrigin( final String SAN, final byte COLOUR ) {

		assert( !SAN.isEmpty() ):"Invalid san";
		assert( SAN.length() > 0 ):"Invalid string length";

		ArrayList<Integer> destinations = board.generateKnightDestinations( to );
		
		logging.info("Begin search for a destination to a Knight");
		for ( int origin : destinations ) {
			
			if ( board.pieceTypeAt( origin ) == KNIGHT && 
				 board.pieceColourAt( origin ) == COLOUR ) {
				
				if ( SAN.length() == 2 ) {

					return origin;
				
				}
				else if ( SAN.length() == 3 ) {
					
					if ( SAN.substring( 0, 1 ).matches( "[a-h]" ) && SAN.charAt( 0 ) == getFile( origin ) ) {
						
						return origin;
					
					}
					else if ( SAN.substring( 0, 1 ).matches( "[0-7]" ) && SAN.charAt( 0 ) == getRank( origin ) ){
						
						return origin;
					
					}
					else{
						
						//do nothing
					}

				} else if ( SAN.charAt( 0 ) == getFile( origin ) && SAN.charAt( 0 ) == getRank( origin ) ) {
					
					return origin;
				
				}
				else{
					
					//do nothing
				}
			}
			else {

				// Nothing to do
			}

		}// End of for

		return - 1;
	}// End of method

	/**
	 * 
	 * @param san
	 * @param colour
	 * @return origin of bishop
	 */
	private int getBishopOrigin( final String SAN, final byte COLOUR ) {

		assert( !SAN.isEmpty() ):"Invalid san";
		assert( SAN.length() > 0 ):"Invalid string length";
		
		ArrayList<Integer> destinations = board.generateBishopDestinations( to );
		
		logging.info("Begin search for a destination to a Bishop");
		for ( int origin : destinations ) {
			
			if ( board.pieceTypeAt( origin ) == BISHOP
					&& board.pieceColourAt( origin ) == COLOUR ) {

				if ( SAN.length() == 2 ){

					return origin;

				}
				
				else if ( SAN.length() == 3 ) {
					
					if ( SAN.substring( 0, 1 ).matches( "[a-h]" )
							&& SAN.charAt( 0 ) == getFile( origin ) ){

						return origin;
					
					}
					else if ( SAN.substring( 0, 1 ).matches( "[0-7]" )
							&& SAN.charAt( 0 ) == getRank( origin ) ){

						return origin;
					
					} 
					else{

						//do nothing
					
					}			
				} else if ( SAN.charAt( 0 ) == getFile( origin )
						&& SAN.charAt( 0 ) == getRank( origin ) ){

					return origin;

				}
				else{

					//do nothing
				
				} 

				
			}
		}

		return - 1;
	}

	/**
	 * 
	 * @param san
	 * @param colour
	 * @return origin of queen
	 */

	private int getQueenOrigin( final String SAN, final byte COLOUR ) {

		assert( !SAN.isEmpty() ):"Invalid san";
		assert( SAN.length() > 0 ):"Invalid string length";
		
		ArrayList<Integer> destinations = board.generateQueenDestinations( to );
		
		logging.info("Begin search for a destination to the Queen");
		for ( int origin : destinations ) {
			
			if ( board.pieceTypeAt( origin ) == QUEEN && 
				board.pieceColourAt( origin ) == COLOUR ) {
				
				if ( SAN.length() == 2 ) {
					
					return origin;
				
				}
				else if ( SAN.length() == 3 ) {
					
					if ( SAN.substring( 0, 1 ).matches( "[a-h]" ) && 
						SAN.charAt( 0 ) == getFile( origin ) ) {
						
						return origin;
					
					}
					else if ( SAN.substring( 0, 1 ).matches( "[0-7]" ) && 
						SAN.charAt( 0 ) == getRank( origin ) ) {
						
						return origin;
					
					}
					else{

						//do nothing

					}
				
				} else if ( SAN.charAt( 0 ) == getFile( origin ) &&
					 SAN.charAt( 0 ) == getRank( origin ) ) {
					
					return origin;
				
				}
				else{

					//do nothing

				}
			}// End of if
		}// End of for

		return - 1;
	}

	/**
	 * 
	 * @param san
	 * @param colour
	 * @return origin of king
	 */

	private int getKingOrigin( final String SAN, final byte COLOUR ) {

		assert( !SAN.isEmpty() ):"Invalid san";
		assert( SAN.length() > 0 ):"Invalid string length";
		
		ArrayList<Integer> destinations = board.generateKingDestinations( to );
		
		logging.info("Begin search for a destination to the King");
		for ( int origin : destinations ) {
			
			if ( board.pieceTypeAt( origin ) == KING
					&& board.pieceColourAt( origin ) == COLOUR ) {
				
				if ( SAN.length() == 2 ){

					return origin;

				}
				else if ( SAN.length() == 3 ) {

					if ( SAN.substring( 0, 1 ).matches( "[a-h]" )
							&& SAN.charAt( 0 ) == getFile( origin ) ){

						return origin;

					}						
					else if ( SAN.substring( 0, 1 ).matches( "[0-7]" )
							&& SAN.charAt( 0 ) == getRank( origin ) ){

						return origin;
					
					}
					else{

						//do nothing

					} 
				} else if ( SAN.charAt( 0 ) == getFile( origin )
						&& SAN.charAt( 0 ) == getRank( origin ) ){

					return origin;

				}
				else{

					//do nothing
				
				} 
			}
		}

		return - 1;
	}

	/**
	 * 
	 * @param san
	 * @param colour
	 * @return origin of pawn
	 */
	private int getPawnOrigin( final String SAN, final byte COLOUR ) {

		assert( !SAN.isEmpty() ):"Invalid san";
		assert( SAN.length() > 0 ):"Invalid string length";
		
		ArrayList<Integer> destinations = null;
		
		if ( COLOUR == WHITE ) {

			destinations = board.generateBlackPawnDestinations( to );// Uses opposites colour generator as pawns can't move backwards
		
		}
		else {
		
			destinations = board.generateWhitePawnDestinations( to );
		
		}

		logging.info("Begin search for a destination to a Pawn");
		for ( int origin : destinations ) {

			if ( board.pieceTypeAt( origin ) == PAWN && board.pieceColourAt( origin ) == COLOUR ) {
				
				if ( SAN.length() == 2 ) {

					return origin;
				
				}
				else if ( SAN.length() == 3 ) {
					
					if ( SAN.substring( 0, 1 ).matches( "[a-h]" ) && SAN.charAt( 0 ) == getFile( origin ) ) {
						
						return origin;
					
					}
					else if ( SAN.substring( 0, 1 ).matches( "[0-7]" ) && SAN.charAt( 0 ) == getRank( origin ) ) {
						
						return origin;
					
					}
					else{
						
						//do nothing

					}
				} else if ( SAN.charAt( 0 ) == getFile( origin ) && SAN.charAt( 0 ) == getRank( origin ) ) {
					
					return origin;
				
				}
				else{

					//do nothing

				}
			} //End of if
		} // End of for

		return - 1;
	}

	private char getFile( final int POSITION ) {
		return ( intToLetter( POSITION % NUMBER_OF_PIECES ) );
	}

	private int getRank( final int POSITION ) {
		return POSITION / NUMBER_OF_PIECES;
	}

	private int letterToInt( char c ) {
		return ( c - 'a' );
	}

	private char intToLetter( int i ) {
		return ( ( char ) ( i + 'a' ) );
	}
}
