/**
    * File: AI.java
    * Purpose: The artificial intelligence player.
    */

package model;

import model.evaluators.EasyEvaluator;
import model.evaluators.MediumEvaluator;
import model.evaluators.Evaluator;
import org.apache.log4j.Logger;


public class AI extends Player {

    private int depth = 0;
    private Evaluator evaluator = null;
    static Logger logging = Logger.getLogger(AI.class);

    /**
     * Creates an AI with an extra parameter to select 
     * the evaluator and changed difficulty level to be the depth
     * @param depth
     * @param evaluatorToSelect
     * @param colour
     * @param name
     * @param timeRemaining
     */
    public AI(int depth,int evaluatorToSelect, byte colour, String name, long timeRemaining) {
    	super( name, colour, timeRemaining );
    	this.depth = depth;
    
    	if (evaluatorToSelect == 1) {
    		this.evaluator = new EasyEvaluator();
    		logging.info("Easy difficulty selected");
    	}
    	else {
    		this.evaluator = new MediumEvaluator();
    		logging.info("Medium difficulty selected");
    	}
    }
  
    /**
     *
     * Given a board, getMove will return the best scoring move for the AI.
     *
     * <p>It uses the alphaBetaNegamax algorithm to calculate the best move.</p>
     *
     * @param board - Object of board
     * @return Best move for AI
     */
    public Move getMove( Board board ) {
    	
		assert( board != null ):"Board is null";
    	Move bestMove = null;
    	int bestScore = Integer.MIN_VALUE;
    	
    	//Creates a board copy to simulate moves and find the best one
    	for ( Move move : board.getValidMoves() ) {
    		Board copy = new Board (board);
    		copy.makeMove ( move );
    		int score = -alphaBetaNegamax( copy, this.depth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1 );
    	
    		if ( score > bestScore ) {
    			bestScore = score;
    			bestMove = move;
    			logging.info("Identifying the best move");
    		}
    		else {
    			// do nothing
    		}
    	}
    	
    	assert( bestMove != null ): "Best Move is null";
    	return bestMove;
 	}

    /**
     * <b>Alpha Beta Negamax </b><p>
     *
     * Utilises the Negamax algorithm with Alpha Beta pruning to calculate the best move</p><p>
     *
     * This is normally called with  alphaBetaNegamax(board, depth, +ve infinity, -ve infinity)
     * The calling method handles the first move made by the AI.</p>
     *
     * @param board - board to evaluate and make next move on
     * @param depth - how much further to traverse the tree before returning
     * @param alpha
     * @param beta
     * @return Best move for a given board
     */
    public int alphaBetaNegamax( Board board, final int DEPTH,  int alpha, final int BETA ) {
		assert( board != null ):"Board is null";
	    int score = Integer.MIN_VALUE + 1;
	    
	    if ( board.isCheckmate() ) {
	        return ( Integer.MIN_VALUE + 1 + this.depth - DEPTH );
	    } 
	    else if ( board.isStalemate() ) {
	    	return 0; 
	    } 
	    else if ( DEPTH <= 0 ) {
	    	return ( evaluator.evaluate( board ) );
	    }
	    else {
	    	//do nothing
	    }
    
	    for ( Move move : board.getValidMoves() ) {
	    	Board child = new Board( board );
	    	child.makeMove( move );
	    	score = -alphaBetaNegamax( child, DEPTH - 1, - BETA, -alpha );
	      
	    	if ( score >= BETA ) {
	    		return score;
	    	}
	    	else {
	    		// do nothing
	    	}
	    	
	    	if ( score > alpha ) {
	    		alpha = score;
	    	}
	    	else {
	    		// do nothing
	    	}
	    }
    
	    return alpha;
    }

    public boolean isHuman() {
    	return false;
    }
}
