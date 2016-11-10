/**
    * File: Board.java
    * Purpose: A Chess Board implementation using the 0x88 board representation.
    */

package model;

import static lookup.Pieces.*;
import static lookup.Masks.*;
import static lookup.Coordinates.*;
import static lookup.PieceTables.*;

import java.util.ArrayList;

public class Board {

	private byte[] squares = null;
    private byte turnColour = 0;
    private Move previousMove = null;
    private int whiteKingPosition = 0;
    private int blackKingPosition = 0;
    private ArrayList<Move> validMoves = new ArrayList<Move>();
    private ArrayList<Byte> whitePiecesCaptured = new ArrayList<Byte>();
    private ArrayList<Byte> blackPiecesCaptured = new ArrayList<Byte>();
    private int score = 0;
    private int amountOfMoves = 0;

    /**
     * Initialise and create the board to contain chess pieces arranged in an order such that the resulting positions represent
     * a valid chess starting position.
     */
    public Board() {
    	this.squares = new byte[]{ ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
                               PAWN,  PAWN,  PAWN,  PAWN,  PAWN,  PAWN,  PAWN,  PAWN,   EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
                               EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,  EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
                               EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,  EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
                               EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,  EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
                               EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,  EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
                               BPAWN, BPAWN, BPAWN, BPAWN, BPAWN, BPAWN, BPAWN, BPAWN,  EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
                               BROOK, BKNIGHT, BBISHOP, BQUEEN, BKING, BBISHOP, BKNIGHT, BROOK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY };

    	this.turnColour = WHITE;
    	this.previousMove = null;
    	this.whiteKingPosition = E1;
    	this.blackKingPosition = E8;
    	this.validMoves = generateValidMoves();
    	this.whitePiecesCaptured = new ArrayList<Byte>();
    	this.blackPiecesCaptured = new ArrayList<Byte>();
    	this.score = 0;
    	this.amountOfMoves = 0;
    }

    /**
     * Construct a new chess board which is a copy of a supplied board.
     *
     * @param board - The chess board to copy.
     */
    public Board( Board board ) {
    	this.squares = board.getSquares();
    	this.turnColour = board.getTurnColour();
    	this.previousMove = board.getPreviousMove();
    	this.whiteKingPosition = board.getWhiteKingPosition();
    	this.blackKingPosition = board.getBlackKingPosition();
    	this.validMoves = board.getValidMoves();
    	this.whitePiecesCaptured = board.getWhitePiecesCaptured();
    	this.blackPiecesCaptured = board.getBlackPiecesCaptured();
    	this.score = board.getScore();
    	this.amountOfMoves = board.getAmountOfMoves();
    }

       /**
     * Is the square mapped to by the given index empty?
     *
     * @param position    The square index.
     *
     * @return True if there are no pieces on the square, false otherwise.
     */
    private boolean squareEmpty( final int POSITION ) {	
    	
    	if ( pieceAt( POSITION ) == EMPTY ){
    		return true;
    		
    	}else{
    		return false;
    	}
    }

    /**
     * Get the piece located on the square mapped to by the given index.
     *
     * @param position    The square index.
     *
     * @return A byte representation of the piece located at 'position'.
     */
	private byte pieceAt(final int POSITION ) {
		
		 if ( ( POSITION & 0x88 ) == VALID ) {
			 return ( this.squares[ POSITION ] );
		 }
	
		 return ( EMPTY );
    }

	/**
	 * What colour is the piece on the square mapped to by the given index?
	 *
	 * @param position    The square index.
	 *
	 * @return The colour, as a byte, of the piece.
	 */
    public byte pieceColourAt( final int POSITION ) {
    	
		return ( (byte)( pieceAt( POSITION ) & COLOUR_MASK ) );
    }

    /**
     * What is the type of the given piece?
     *
     * @param piece    The piece to check.
     *
     * @return A byte representing the piece type.
     */
    public static byte pieceType( final byte PIECE ) {
    	return ( (byte)( PIECE & PIECE_MASK ) );
    }

     /**
     * What is the colour of the given piece?
     *
     * @param piece    The piece to check.
     *
     * @return A byte representing the piece colour.
     */
    public static byte pieceColour( final byte PIECE ) {
        return ( (byte)( PIECE & COLOUR_MASK ) );
    }
  
    /**
     * What is the type of the piece located at the given square index?
     *
     * @param position    The square index.
   	 *
   	 * @return A byte representing the piece type.
   	 */
    public byte pieceTypeAt( final int POSITION ) {
    	return ( (byte)( pieceAt( POSITION ) & PIECE_MASK ) );
    }
  
  /**
   * Is the given square index within the playable game area?
   *
   * @param destination    The square index to check.
   *
   * @return True if the destination square index is on the playable chess board, false otherwise.
   */ 
    private boolean isValidDestination(final int DESTINATION ) {
    	
    	if  ( ( ( DESTINATION & 0x88 ) == VALID ) &&
                ( squareEmpty( DESTINATION ) || ( pieceColourAt( DESTINATION ) != this.turnColour ) ) )  {
    		return true;
    		
    	}else {
    		return false;
    	}
    }

    /**
     * Is the piece located at the given square index white?
     *
     * @param position    The square index.
     *
     * @return True if the piece is white, false otherwise.
     */
    private boolean isWhitePiece(final int POSITION ) {
    	
    	if ( pieceColourAt( POSITION ) == WHITE ){
    		return true;
    		
    	}else{
    		return false;
    	}
    }

    /**
     * Can the given move be played on the chess board?
     *
     * @param move    The move to check the validity of.
     *
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove( Move move ) {
		assert( move != null ):"Move is null";
    	
    	if  ( this.validMoves.contains( move ) == true ){
    		return true;
    	
    	}else{
    		return false;
    	}
    }
  
    /**
     * Set the bit that represents whether or not a piece has moved.
     *
     * @param position    The square index at which to set the bit.
     */
    private void setMovementBit(final int POSITION ) {
    	this.squares[ POSITION ] |= MOVED;
    }

    /**
     * Has a given piece made at least one move?
     *
     * @param piece    The piece to check.
     *
     * @return True if the piece has moved, false otherwise.
     */
    private static boolean hasPieceMoved( final byte PIECE ) {
    	
    	if ( ( PIECE & MOVED_MASK ) == MOVED ){
    		return true;
    		
    	}else{
    		return false;
    	}
    }

    /**
     * Does the current board state represent a checkmate situation?
     *
     * @return True if checkmate, false otherwise.
     */
    public boolean isCheckmate() {
    	if ( this.validMoves.size() == 0 && kingInCheck() ){
    		return true;
    	
    	}else{
    		return false;
    	}
    }
  
    /**
     * Does the current board state represent a stalemate situation?
     *
     * @return True if stalemate, false otherwise.
     */
    public boolean isStalemate() {
    	if  ( this.validMoves.size() == 0 && !kingInCheck() ){
    		return true;
    	
    	}else{
    		return false;
    	}
    }

    /**
     * Is is the white players turn to move?
     *
     * @return True if it is the white players turn to move, false otherwise.
     */
    private boolean isWhiteTurn() {
    	if ( this.turnColour == WHITE ){
    		return true;
    	
    	}else{
    		return false;
    	}
    }

    /**
     * Set the position of the current players king to a new square.
     *
     * @param position    The new square index.
     */
    private void setKingPosition( final int POSITION ) {
		assert( POSITION >= 0 ):"Position invalid";
    	if ( isWhiteTurn() ) {
    		this.whiteKingPosition = POSITION;
    	} 
    	else {
    		this.blackKingPosition = POSITION;
        }
    }

    /**
     * Return the colour of the current players opponent.
     *
     * @return A byte representation of the opponents colour.
     */
    private byte opponentColour() {
    	if ( isWhiteTurn() ){
    		return BLACK;
    	
    	}else{
    		return WHITE;
    	}
    }

    /**
     * Does the given move perform a king or queenside castle?
     *
     * @param move    The move to check.
     *
     * @return True if the move performs a castling, false otherwise.
     */
    private boolean isCastle( Move move ) {
    	return ( isKingMove( move ) && Math.abs( move.to() - move.from() ) == 2 );
    }

    /**
     * Perform the castling move contained in 'move'.
     *
     * @param move - The castling move to perform.
     */
    private void performCastle( Move move ) {
		assert( move != null ):"Move is null";
		
    	if ( move.to() > move.from() ) {
    		performCastleKingSide( getKingPosition() );
    	}
    	else {
    		performCastleQueenSide( getKingPosition() );
    	}

    	if ( isWhiteTurn() ) {
    		score += 30;
    	} 
    	else {
    		score -= 30;
    	}
    	
    	setKingPosition( move.to() );
    }

    /**
     * Perform a kingside castling for the king located at 'kingPosition'.
     *
     * @param kingPosition - The position of the king to castle.
     */
    private void performCastleKingSide( final int KING_POSITION ) {
    	this.squares[ KING_POSITION + 1 ] = this.squares[ KING_POSITION + 3 ];
    	this.squares[ KING_POSITION + 3 ] = EMPTY;
    	this.squares[ KING_POSITION + 2 ] = this.squares[ KING_POSITION ];
    	this.squares[ KING_POSITION ] = EMPTY;
    }

    /**
     * Perform a queenside castling for the king located at 'kingPosition'.
     *
     * @param kingPosition - The position of the king to castle.
     */
    private void performCastleQueenSide(  final int KING_POSITION ) {
    	this.squares[ KING_POSITION - 1 ] = this.squares[ KING_POSITION - 4 ];
    	this.squares[ KING_POSITION - 4 ] = EMPTY;
    	this.squares[ KING_POSITION - 2 ] = this.squares[ KING_POSITION ];
    	this.squares[ KING_POSITION ] = EMPTY;
    }

    /**
     * Is the given move an en passent move?
     *
     * @param move - The move to check.
     *
     * @return True if the move performs en passent, false otherwise.
     */
    private boolean isEnPassant( Move move ) {
    	return ( ( Math.abs( move.to() - move.from() ) == 17 || Math.abs( move.to() - move.from() ) == 15 ) && squareEmpty( move.to() ) && isPawnMove( move ) );
    }

    /**
  	* Is a pawn being moved?
  	*
  	* @param move - The move to check.
  	*
  	* @return True if a pawn is being moved.
  	*/
    private boolean isPawnMove( Move move ) {
    	assert( move != null ):"null move";
    	if ( pieceTypeAt( move.from() ) == PAWN ) {
    		return true;
    	} 
    	else {
    		return false;
        }
    }
  
    /**
     * Is a rook being moved?
     *
     * @param move - The move to check.
   	*
   	* @return True if a rook is being moved.
   	*/
    private boolean isRookMove( Move move ) {
    	if ( pieceTypeAt( move.from() ) == ROOK ) {
    		return true;
    	} 
    	else {
    		return false;
        }
    }

    /**
     * Is a king being moved?
     *
     * @param move - The move to check.
     *
   	* @return True if a king is being moved.
   	*/
    private boolean isKingMove( Move move ) {
    	assert( move != null ):"null move";
    	if ( pieceTypeAt( move.from() ) == KING ) {
    		return true;
    	} 
    	else {
    		return false;
        }
    }

    /**
     * Does the move result in a pawn promotion?
     *
     * @param move - The move to check.
     */ 
    private boolean isPawnPromotion( Move move ) {
    	assert( move != null ):"null move";
    	if ( isPawnMove( move ) && ( ( move.to() >= A1 && move.to() <= H1 ) || ( move.to() >= A8 && move.to() <= H8 ) ) ) {
    		return true;
    	} 
    	else {
    		return false;
        }
    }

    /**
     * Promote the pawn located on the given square.
     *
     * @param pawnPosition - The square index of the pawn to promote.
     */
    private void promotePawn( final int PAWN_POSITION ) {
    	this.squares[ PAWN_POSITION ] = isWhiteTurn() ? QUEEN : BQUEEN;
    }

    /**
     * Perform the supplied move on the board.
     *
     * @param move - The move to make.
     */
    public void makeMove( Move move ) {
    	assert( move != null ):"null move";
    	setMovementBit( move.from() );

    	if ( isPawnMove( move ) ) {
    		movePawn ( move );
    	}
    	else if ( isKingMove( move ) ) {
    		// separate this block of code (isKingMove) from this function. Warning: It may generate an error on the 'roque' moviment.
    		assert( move != null ):"null move";
    		setMovementBit( move.from() );

    		if ( isCastle( move ) ) {
    			performCastle( move );
    			setKingPosition( move.to() );
    			this.turnColour = opponentColour();
    			this.previousMove = move;
    			this.validMoves = generateValidMoves();
    			return;
    		}
    		setKingPosition( move.to() );
    		
    	} 
    	else if ( isRookMove( move ) ) {
    		setMovementBit( move.from() );
    	}

    	updateScore( move.to() );

    	if ( !squareEmpty( move.to() ) ) {
    		if ( isWhiteTurn() ) {
    			this.blackPiecesCaptured.add( this.squares[ move.to() ] );
    		} 
    		else {
    			this.whitePiecesCaptured.add( this.squares[ move.to() ] );
    		}
    	}
    	else {
    		//do nothing
    	}

    	// updates the squares status after the movement
    	this.squares[ move.to() ] = this.squares[ move.from() ];
    	this.squares[ move.from() ] = EMPTY;

    	this.turnColour = opponentColour();
    	this.previousMove = move;
    	this.validMoves = generateValidMoves();
    	this.amountOfMoves++;
    }
   
    /**
     * Perform the moves for a Pawn piece.
     *
     * @param move - The move to make.
     */ 
   private void movePawn ( Move move ){
		
	   setMovementBit( move.from() );

		if ( isEnPassant( move ) ) {
			if ( isWhiteTurn() ) {
				updateScore( move.to() - 16 );
				this.squares[ move.to() - 16 ] = EMPTY;
				this.squares[ move.to() ] = this.squares[ move.from() ];
				this.squares[ move.from() ] = EMPTY;
				this.blackPiecesCaptured.add( PAWN );
			}
			else {
				updateScore( move.to() + 16 );
				this.squares[ move.to() + 16 ] = EMPTY;
				this.squares[ move.to() ] = this.squares[ move.from() ];
				this.squares[ move.from() ] = EMPTY;
				this.whitePiecesCaptured.add( PAWN );
			}

			this.turnColour = opponentColour();
			this.previousMove = move;
			this.validMoves = generateValidMoves();

			return;
		} 
		else if ( isPawnPromotion( move ) ) {
			promotePawn( move.from() );
			if ( isWhiteTurn() ) {
				score += 700;
			} 
			else {
				score -= 700;
			}
		}
   }
   
    /**
     * What is the value of the piece located on the given square index?
     *
     * @param position    The index of the square the piece is on.
     *
     * @return A integer value of the piece worth.
     */
    private int pieceValueAt(final int POSITION ) {
    	
    	// returns the value of the piece according to the requested position
    	switch ( pieceTypeAt( POSITION ) ) {
          	case PAWN: {
          		return ( 100 );
          	}
          	case KNIGHT: {
          		return ( 325 );
          	}
          	case BISHOP: {
          		return ( 330 );
          	}
          	case ROOK: {
          		return ( 500 );
          	}
          	case QUEEN: {
        	 	return ( 900 );
          	}
          	case KING: {
          		return ( 20000 );
          	}
          	default: {
          		//do nothing
          	}
    	}

    	return 0;
    }

    /**
     * Update the zero sum material balance score.
     *
     * @param position    The index of the square at which a piece was captured.
     */
    private void updateScore( final int POSITION ) {
    	if ( this.turnColour == WHITE ) {
         	score = score + pieceValueAt( POSITION );
         	
    	} else {
    		score = score - pieceValueAt( POSITION );
    	}
    }

    /**
     * Can the piece located at 'position' legally move to 'destination'?
     *
     * @param position    The index of the square the piece is currently on.
     * @param destination The index of the square the piece would like to move to.
     *
     * @return True if the piece can move to the destination, false otherwise.
     */ 
    private boolean canMoveTo( final int POSITION, int destination ) {
    	byte p = this.squares[ POSITION ];
    	byte t = this.squares[ destination ];
      
    	if ( pieceType( p ) == KING ) {
    		setKingPosition( destination );
    	}
    	else {
    		//do nothing
    	}
      
    	this.squares[ POSITION ] = EMPTY;
    	this.squares[ destination ] = p;
	
    	boolean canMove = !kingInCheck();
	
    	if ( pieceType( p ) == KING ) {
    		setKingPosition( POSITION );
    	}
    	else {
		  	//do nothing
    	}
	  
    	this.squares[ POSITION ] = p;
    	this.squares[ destination ] = t;
	
    	return ( canMove );
    }
  
    /** 
     * Is the current players king in check?
     *
     * @return True if the king is in check, false otherwise.
     */
    public boolean kingInCheck() {
    	return ( squareAttacked( getKingPosition() ) );
    }

    /**
     * Is the square indexed by 'position' under attack from any opponent pieces?
     *
     * @param position    The index of the square to check.
     *
     * @return True if at least one piece is attacking the square, false otherwise.
     */ 
    private boolean squareAttacked( final int POSITION ) {
    	int[] directions = new int[]{ 15, 17, -15, -17 };
    
    	// searches on diagonal positions for enemy pieces that may attack
    	for ( int direction : directions ) {
    		for ( int i = 1; isValidDestination( POSITION + i*direction ); i++ ) {
    			if ( enemyPieceAt( POSITION + i*direction ) ) {
    				if ( pieceTypeAt( POSITION + i*direction ) == QUEEN || pieceTypeAt( POSITION + i*direction ) == BISHOP ) {
    					return ( true );
    				} 
    				else if ( pieceTypeAt( POSITION + i*direction ) == PAWN && i == 1 ) {
    					if ( this.turnColour == WHITE ) {
    						return ( direction == 15 || direction == 17 );
    					}
    					else {
    						//do nothing
    					}
    					return ( direction == -15 || direction == -17 );
    				}
    				else {
    					//do nothing
    				}
    				break;
	        		}
		    		else {
		    			//do nothing
		    		}
    		}
    	}

    	// searches on horizontal and vertical lines for possible enemy attacks
    	for ( int direction : new int[]{ 1, -1, 16, -16 } ) {
    		for ( int i = 1; isValidDestination( POSITION + i*direction ); i++ ) {
    			if ( enemyPieceAt( POSITION + i*direction ) ) {
    				if ( pieceTypeAt( POSITION + i*direction ) == ROOK || pieceTypeAt( POSITION + i*direction ) == QUEEN ) {
    					return ( true );
    				}
    				break;
    			}
    			else {
    				//do nothing
    			}
    		}
    	}

    	// searches on 'L' for a possible Knight attack
    	directions = new int[]{ 18, 33, 31, 14, -18, -33, -31, -14 };
    	for ( int direction : directions ) {
    		if ( isValidDestination( POSITION + direction ) && enemyPieceAt( POSITION + direction ) && pieceTypeAt( POSITION + direction ) == KNIGHT ) {
    			return ( true );
    		}
    		else {
    			//do nothing
    		}
    	}

    	return ( false );
    }

    /**
     * Obtain a list of all valid moves that can be played on the current board position.
     *
     * @return An ArrayList of all valid moves that can be played.
     */
    public ArrayList<Move> getValidMoves() {
    	ArrayList<Move> validMoves = new ArrayList<Move>();
    	validMoves = this.validMoves;
    	
    	assert( validMoves != null ): "Move Valid is null";
        return ( validMoves );
    }

    private ArrayList<Move> generateValidMoves() {
    	ArrayList<Move> validMoves = new ArrayList<Move>();

    	for ( int rank = 0; rank < 8; rank++ ) {
    		for ( int file = rank * 16 + 7; file >= rank * 16; file-- ) {
    			if ( !squareEmpty( file ) && pieceColourAt( file ) == this.turnColour ) {
    				validMoves.addAll( generateValidMoves( pieceTypeAt( file ), file ) );
    			}
    			else {
    				//do nothing
    			}
    		}
    	}

    	return ( validMoves );
    }

    /**
     * Generate all valid moves for the piece of type 'pieceType' located on square 'position'.
     *
     * @param pieceType    The type of the piece to generate moves for.
     * @param position     The index of the current square position of the piece.
     *
     * @return An ArrayList of all valid moves that the piece can make.
     */
    public ArrayList<Move> generateValidMoves( final byte PIECE_TYPE, final int POSITION ) {
    	
    	ArrayList<Move> validMoves = new ArrayList<Move>();
    	ArrayList<Integer> destinations = generateDestinations( PIECE_TYPE, POSITION );
    	
    	//This for put all valid moves in an ArrayList that the piece can make.
    	for ( int destination : destinations ) {
    		if ( isValidDestination( destination ) && canMoveTo( POSITION, destination ) ) {
    			validMoves.add( new Move( POSITION, destination ) );
    		}
    		else {
    			//do nothing
    		}
    	}

    	assert( validMoves != null ): "Move Valid is null";
    	return ( validMoves );
 	}
  

    /**
     * Generate all destinations for the piece of type 'pieceType' located on square 'position'.
     *
     * @param pieceType    The type of the piece to generate moves for.
     * @param position     The index of the current square position of the piece.
     *
     * @return An ArrayList of all destinations that the piece can move to.
     */
    private ArrayList<Integer> generateDestinations( final byte PIECE_TYPE, final int POSITION ) {
    	
    	//This switch return an arrayList of all destinations that the piece can move to.
    	switch ( PIECE_TYPE ) {
          	case PAWN: {
          		return ( generatePawnDestinations( POSITION ) );
          	}
          	case KNIGHT: {
          		return ( generateKnightDestinations( POSITION ) );
          	}
          	case BISHOP: {
        	  	return ( generateBishopDestinations( POSITION ) );
          	}
          	case ROOK: {
          		return ( generateRookDestinations( POSITION ) );
          	}
          	case QUEEN: {
          		return ( generateQueenDestinations( POSITION ) );
          	}
          	case KING: {
          		return ( generateKingDestinations( POSITION ) );
          	}
          	default: {
          		//do nothing
          	}
    	}
	  
    	return ( new ArrayList<Integer>() );
    }

    /**
     * Generate the destinations for a pawn located at 'position'.
     *
     * @param position    The index of the square the pawn is located.
     *
     * @return An ArrayList of all destinations that the pawn can move to.
     */
    private ArrayList<Integer> generatePawnDestinations( final int POSITION ) {
    	
    	if ( isWhitePiece( POSITION ) ) {
    		return ( generateWhitePawnDestinations( POSITION ) );
    	}
    	else {
    		//do nothing
    	}

    	return ( generateBlackPawnDestinations( POSITION ) );
    }

    /**
     * Generate the destinations for a white pawn located at 'position'.
  	*
  	* @param position    The index of the square the white pawn is located.
  	*
  	* @return An ArrayList of all destinations that the white pawn can move to.
  	*/
    public ArrayList<Integer> generateWhitePawnDestinations( final int POSITION ) {
    	
    	ArrayList<Integer> destinations = new ArrayList<Integer>();

    	if ( !hasPieceMoved( pieceAt( POSITION ) ) && squareEmpty( POSITION + 16 ) && squareEmpty( POSITION + 32 ) ) {
    		destinations.add( POSITION + 32 );
    	}
    	if ( !squareEmpty( POSITION + 15 ) || whiteCanEnPassantLeft( POSITION ) ) {
    		destinations.add( POSITION + 15 );
    	}
    	if ( !squareEmpty( POSITION + 17 ) || whiteCanEnPassantRight( POSITION ) ) {
    		destinations.add( POSITION + 17 );
    	}
    	if ( squareEmpty( POSITION + 16 ) ) {
    		destinations.add( POSITION + 16 );
    	}

    	assert( destinations != null ): "Destinations of white pawn is null";
    	return ( destinations );
    }

    /**
     * Can the white pawn located at 'position' perform en passent to the left?
     *
     * @param position    The index of the square the white pawn is located.
     *
     * @return True if the pawn can en passent left, false otherwise.
     */
    private boolean whiteCanEnPassantLeft( final int POSITION ) {
    	assert( POSITION > 0 ):"invalid position";
        if ( this.previousMove != null && pieceTypeAt( POSITION - 1 ) == PAWN
        	&& this.previousMove.from() == ( POSITION + 31 ) && this.previousMove.to() == ( POSITION - 1 )){
    	    return true;
        } 
        else {
    	    return false;
        }
    }

    /**
     * Can the white pawn located at 'position' perform en passent to the right?
     *
     * @param position    The index of the square the white pawn is located.
     *
     * @return True if the pawn can en passent right, false otherwise.
     */
    private boolean whiteCanEnPassantRight( final int POSITION ) {
    	
        if  ( this.previousMove != null && pieceTypeAt( POSITION + 1 ) == PAWN
        		&& this.previousMove.from() == ( POSITION + 33 ) && this.previousMove.to() == ( POSITION + 1 ) ){
        	    return true;
            } 
            else {
        	    return false;
            }
    }

    /**
     * Generate the destinations for a black pawn located at 'position'.
     *
     * @param position    The index of the square the black pawn is located.
     *
     * @return An ArrayList of all destinations that the black pawn can move to.
     */
    public ArrayList<Integer> generateBlackPawnDestinations( final int POSITION ) {
    	
    	ArrayList<Integer> destinations = new ArrayList<Integer>();

    	if ( !hasPieceMoved( pieceAt( POSITION ) ) && squareEmpty( POSITION - 16 ) && squareEmpty( POSITION - 32 ) ) {
    		destinations.add( POSITION - 32 );
    	}
    	else {
    		//do nothing
    	}
    
    	if ( !squareEmpty( POSITION - 15 ) || blackCanEnPassantLeft( POSITION ) ) {
    		destinations.add( POSITION - 15 );
    	}
    	else {
    		//do nothing
    	}
    
    	if ( !squareEmpty( POSITION - 17 ) || blackCanEnPassantRight( POSITION ) ) {
    		destinations.add( POSITION - 17 );
    	}
    	else {
    		//do nothing
    	}

    	if ( squareEmpty( POSITION - 16 ) ) {
    		destinations.add( POSITION - 16 );
    	}
    	else {
    		//do nothing
    	}

    	return ( destinations );
    }

    /**
     * Can the black pawn located at 'position' perform en passent to the left?
     *
     * @param position    The index of the square the black pawn is located.
     *
     * @return True if the pawn can en passent left, false otherwise.
     */
    private boolean blackCanEnPassantLeft( final int POSITION ) {
    	
        if  ( this.previousMove != null && pieceTypeAt( POSITION + 1 ) == PAWN 
        		&& this.previousMove.from() == ( POSITION - 31 ) && this.previousMove.to() == ( POSITION + 1 ) ){
        	    return true;
            } 
            else {
        	    return false;
            }
    }	

    /**
     * Can the black pawn located at 'position' perform en passent to the right?
     *
     * @param position    The index of the square the black pawn is located.
     *
     * @return True if the pawn can en passent right, false otherwise.
     */
    private boolean blackCanEnPassantRight( final int POSITION ) {
    	
        if  ( this.previousMove != null && pieceTypeAt( POSITION - 1 ) == PAWN
        		&& this.previousMove.from() == ( POSITION - 33 ) && this.previousMove.to() == ( POSITION -1 ) ){
        	    return true;
            } 
            else {
        	    return false;
            }
    }

    /**
     * Generate the destinations for a king located at 'position'.
     *
     * @param position    The index of the square the king is located on.
     *
     * @return An ArrayList of all destinations that the king can move to.
     */
	public ArrayList<Integer> generateKingDestinations( final int POSITION ) {
	    	
	    	ArrayList<Integer> destinations = new ArrayList<Integer>();
	    	
	    	int[] kingPositions = { 15, 16, 17, 1, -1, -17, -16, -15 };
	
	    	if ( canCastleKingSide( POSITION ) ) {
	    		destinations.add( POSITION + 2 );
	    	}
	    	else {
	    		//do nothing
	    	}	
	    
	    	if ( canCastleQueenSide( POSITION ) ) {
	    		destinations.add( POSITION - 2 );
	    	}
	    	else {
	    		//do nothing
	    	}
	
	    	for ( int i : kingPositions) {
	    		if ( !nextToOpponentKing( POSITION + i ) ) {
	    			destinations.add( POSITION + i );
	    		}
	    		else {
	    			//do nothing
	    		}
	    	}
	
	    	return ( destinations );
	    }

    /**
     * Can the king located at 'position' perform a king side castling move?
     *
     * @return True if the king can castle, false otherwise.
     */
    private boolean canCastleKingSide( final int POSITION ) {
    	
    	return ( !hasPieceMoved( pieceAt( POSITION ) ) && !kingInCheck() && pieceTypeAt( POSITION + 3 ) == ROOK && !hasPieceMoved( pieceAt( POSITION + 3 ) )
             && squareEmpty( POSITION + 1 ) && !squareAttacked( POSITION + 1 )
             && squareEmpty( POSITION + 2 ) && !squareAttacked( POSITION + 2 ) );
    }

    /**
     * Can the king located at 'position' perform a queen side castling move?
     *
     * @return True if the king can castle, false otherwise.
     */
    private boolean canCastleQueenSide( final int POSITION ) {
    	
    	return ( !hasPieceMoved( pieceAt( POSITION ) ) && !kingInCheck() && pieceTypeAt( POSITION + 3 ) == ROOK && !hasPieceMoved( pieceAt( POSITION - 4 ) )
             && squareEmpty( POSITION - 1 ) && !squareAttacked( POSITION - 1 )
             && squareEmpty( POSITION - 2 ) && !squareAttacked( POSITION - 2 )
             && squareEmpty( POSITION - 3 ) && !squareAttacked( POSITION - 3 ) );
    }

    /**
     * Is the king located at 'position' adjacent to an opponents king?
     *
     * @param position    The location of the king to check.
     */
    private boolean nextToOpponentKing( final int POSITION ) {
    	
    	int[] offsets = new int[]{ 15, 16, 17, -1, 1, -15, -16, -17 };

    	for ( int i : offsets ) {
    		if ( ( POSITION + i ) == getOpposingKingPosition() ) {
    			return ( true );
    		}
    	}

    	return ( false );
    }

    /**
   	* Generate the destinations for a knight located at 'position'.
   	*
   	* @param position    The index of the square the knight is located on.
   	*
   	* @return An ArrayList of all destinations that the knight can move to.
   	*/
    public ArrayList<Integer> generateKnightDestinations( final int POSITION ) {
    	
    	ArrayList<Integer> destinations = new ArrayList<Integer>();
    	
    	int[] knightDestinations = { 18, 33, 31, 14, -18, -33, -31, -14 };
    	
    	//This for put all destinations that the knight can move to.
    	for ( int d : knightDestinations) {
    		destinations.add( POSITION + d );
    	}
    	
    	finalizeObject(knightDestinations);
    	
    	return ( destinations );
    }	
    
    
    private void finalizeObject(Object object) {
    	object = null;
    }

    /**
     * Generate the destinations for a bishop located at 'position'.
     *
     * @param position    The index of the square the bishop is located on.
     *
     * @return An ArrayList of all destinations that the bishop can move to.
     */
    public ArrayList<Integer> generateBishopDestinations( int position ) {
    	
    	return ( generateUpDownDestinations( position, new int[]{ 15, 17, -15, -17 } ) );
    }

    /**
     * Generate the destinations for a rook located at 'position'.
     *
     * @param position    The index of the square the rook is located on.
     *
     * @return An ArrayList of all destinations that the rook can move to.
     */
    public ArrayList<Integer> generateRookDestinations( final int POSITION ) {
    	
    	return ( generateUpDownDestinations( POSITION, new int[]{ 1, -1, 16, -16 } ) );
    }

    /**
     * Generate the destinations for a queen located at 'position'.
     *
     * @param position    The index of the square the queen is located on.
     *
     * @return An ArrayList of all destinations that the queen can move to.
     */
    public ArrayList<Integer> generateQueenDestinations( final int POSITION ) {
    	
    	return ( generateUpDownDestinations( POSITION, new int[]{ 1, -1, 16, -16, 15, 17, -15, -17 } ) );
    }

    /**
     * Is there an enemy piece located at the given square index?
     *
     * @param position    The square index to check.
     *
     * @return True if an opponents piece is located at that position, false otherwise.
     */
    public boolean enemyPieceAt( final int POSITION ) {
    	
    	if ( !squareEmpty( POSITION ) && pieceColourAt( POSITION ) != this.turnColour ) {
    		return true;
    	} 
    	else {
    		return false;
        }
    }

    /**
     * Multipurpose method that can generate destinations for sliding pieces.
     *
     * @param position    The square index of the locations of the piece.
     * @param directions  The directions in which the piece should be moving.
     *
     * @return An ArrayList of all destinations that the piece can move to.
     */
    private ArrayList<Integer> generateUpDownDestinations( final int POSITION, int[] directions ) {
    	assert( directions.length > 0): "no directions found";
    	
    	ArrayList<Integer> destinations = new ArrayList<Integer>();

    	for ( int direction : directions ) {
    		for ( int i = 1; isValidDestination( POSITION + i*direction ); i++ ) {
    			if ( enemyPieceAt( POSITION + i*direction ) ) {
    				destinations.add( POSITION + i*direction );
    				break;
    			}
    			else {
    				//do nothing
    			}

    			destinations.add( POSITION + i*direction );
      		}
    	}

    	return ( destinations );
    }

    /*-----------------------------------------------*/
    /*--------------Evaluation Methods---------------*/
    /*-----------------------------------------------*/

    /**
     * Evaluate the material balance on the board.
     *
     * @return The zero sum material score.
     */
    public int evaluateMaterial() {
    	if ( isWhiteTurn() == true ) {
    		return this.score;
    	} 
    	else {
    		return  -this.score;
        }
    }

    /**
     * Evaluate the positional score of each piece on the board.
     *
     * @return The material score for the current player.
     */
    public int evaluatePiecePositions() {
    	int score = 0;

    	for ( int rank = 0; rank < 8; rank++ ) {
    		for ( int file = rank * 16 + 7; file >= rank * 16; file-- ) {
    			if ( !squareEmpty( file ) && pieceColourAt( file ) == this.turnColour ) {
    				if ( isWhiteTurn() ) {
    					score += piecePositionScore( pieceTypeAt( file ), file );
    				} 
    				else {
    					score -= piecePositionScore( pieceTypeAt( file ), file );
            		}
    			}
    			else {
    				//do nothing
    			}
    		}
    	}
    
    	if ( isWhiteTurn() == true ) {
    		return score;
    	} else {
    		return  -score;
        }
    }

    /**
     * Give a score to a piece based on its position on the board.
     *
     * @return A score representing how good/bad the position of the piece is.
     */
    public int piecePositionScore( final byte PIECE_TYPE, final int POS ) {
    	
    	switch ( PIECE_TYPE ) {
      		case PAWN: {
      			return pawnPositionScore(POS);
      		}
      		case KNIGHT: {
      			return KNIGHT_POSITION_TABLE[ POS ];
      		}
      		case BISHOP: {
      			return bishopPositionScore(POS);
      		}
      		case ROOK: {
      			return rookPositionScore(POS);
      		}
      		case QUEEN: {
      			return queenPositionScore(POS);
      		}
      		case KING: {
      			return kingPositionScore(POS);
      		}
      		default: {
      			// do nothing
      		}
    	}

    	return 0;
    }
    
    private int pawnPositionScore (final int POS){
    	
    	if (isWhiteTurn() == true){
    		return WPAWN_POSITION_TABLE[ POS ] ;
    		
    	}else{
    		return BPAWN_POSITION_TABLE[ POS ];
    	}
    }
    
    private int bishopPositionScore (final int POS){
    	
    	if (isWhiteTurn() == true){	
    		return WBISHOP_POSITION_TABLE[ POS ] ;
    
    	}else{
    		return BBISHOP_POSITION_TABLE[ POS ] ;
    	}
    }
    
    private int rookPositionScore (final int POS){
    	
    	if (isWhiteTurn() == true){	
    		return WROOK_POSITION_TABLE[ POS ] ;
    
    	}else{
    		return BROOK_POSITION_TABLE[ POS ] ;
    	}
    }
    
    private int queenPositionScore (final int POS){
    	
    	if (this.amountOfMoves < 15){	
    		return OPENING_QUEEN_POSITION_TABLE[ POS ] ;
    
    	}else{
    		return QUEEN_POSITION_TABLE[ POS ] ;
    	}
    }
    
    private int kingPositionScore (final int POS){
    	
    	if (isWhiteTurn() == true){	
    		return WKING_POSITION_TABLE[ POS ] ;
    
    	}else{
    		return BKING_POSITION_TABLE[ POS ] ;
    	}
    }


    /**
     * Evaluate the development of knights and bishops.
     *
     * @return A score representing how good the piece development of the current player is.
     */
    public int evaluatePieceDevelopment() {
    	int score = 0;

    	int[] minorPiecePositions = new int[]{ 1, 2, 5, 6 };
    	int turn = isWhiteTurn() ? 0 : 112;

    	for ( int pos : minorPiecePositions ) {
    		if ( !squareEmpty( pos + turn ) && !hasPieceMoved( pieceAt( pos + turn ) ) ) {
    			if ( isWhiteTurn() ) {
    				score -= 50;
    			} 
    			else {
    				score += 50;
    			}
    		}
    	}
    	if ( isWhiteTurn() == true ) {
    		return score;
    	} else {
    		return  -score;
    	}
    }
    
    /**
     * Returns a list of all white pieces captured by the black player.
     *
     * @return A list of all white pieces captured.
     */
    private ArrayList<Byte> getWhitePiecesCaptured() {
    	return ( new ArrayList<Byte>( this.whitePiecesCaptured ) );
	}

    /**
     * Returns a list of all black pieces captured by the white player.
     *
     * @return A list of all black pieces captured.
     */
    private ArrayList<Byte> getBlackPiecesCaptured() {
    	return ( new ArrayList<Byte>( this.blackPiecesCaptured ) );
    }

    /**
     * Return the zero sum material score of the board.
     *
     * @return The zero sum material score.
     */
    private int getScore() {
    	return ( this.score );
  	}

    /**
     * Return the byte array of chess board squares.
     *
     * @return The array of chess board squares as bytes.
     */
    public byte[] getSquares() {
    	return ( this.squares.clone() );
    }

    /**
     * What is the colour of the player to move?
     *
     * @return A byte representing the colour of the player whos turn it is currently.
     */
    public byte getTurnColour() {
    	return ( this.turnColour );
    }

    /**
     * How many moves have been made since board creation?
     *
     * @return The number of moves made since the board was created.
     */
    public int getAmountOfMoves() {
    	return this.amountOfMoves;
    }

    /**
     * Get the last move made on the board.
     *
     * @return The last move that was made on the board.
     */
    private Move getPreviousMove() {
    	return ( this.previousMove );
    }

    /**
     * Which square is the white king located on?
     *
     * @return The square index of the white king position.
     */
    public int getWhiteKingPosition() {
    	return ( this.whiteKingPosition );
    }

    /**
     * Which square is the black king located on?
     *
     * @return The square index of the black king position.
     */
    public int getBlackKingPosition() {
    	return ( this.blackKingPosition );
    }

    /**
     * Which square is the current players king on?
     *
     * @return The square index of the current players king.
     */
    public int getKingPosition() {
    	
    	if ( isWhiteTurn() == true){
    		return this.whiteKingPosition;
    	}else{
    		return this.blackKingPosition;
    	}
    }

    /**
     * Which square is the opponent of the current players king on?
     *
     * @return The square index of opponent of the current players king.
     */
    private int getOpposingKingPosition() {
    	
    	if (isWhiteTurn() == true){
    		return this.blackKingPosition;
    		
    	}else{
    		return this.whiteKingPosition;	
    	}
    }


}