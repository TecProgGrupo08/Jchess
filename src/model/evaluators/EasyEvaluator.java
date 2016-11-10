package model.evaluators;

import model.Board;

// An evaluator that is suitable for casual players and beginners.
public class EasyEvaluator implements Evaluator {

	  private static final int MATERIAL_WEIGHT = 90;
	  private static final int POSITION_WEIGHT = 10;

	  public int evaluate( Board board ) {
		
		assert( board != null):"Value of board empty!";
			    
	    return ( ( board.evaluateMaterial() * MATERIAL_WEIGHT )
		     + ( board.evaluatePiecePositions() * POSITION_WEIGHT ) );
	  }
}