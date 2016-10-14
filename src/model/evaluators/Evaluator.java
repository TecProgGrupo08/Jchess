package model.evaluators;

import model.Board;

// An interface used to create a static evaluator.
public interface Evaluator {

	public int evaluate( Board board );

}