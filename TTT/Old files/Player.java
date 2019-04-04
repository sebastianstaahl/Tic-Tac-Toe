import java.util.*;
import java.math.*;

public class Player {
    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState gameState, final Deadline deadline) {
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);
        ///. You will be provided a skeleton which generates a list of
        //valid moves for you to choose from
        int depth = 16;
        GameState bestNextState;
				System.err.println(gameState);

				//double alpha = 0;

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

				/**
				 * Here you should write your algorithms to get the best next move, i.e.
				 * the best next state. This skeleton returns a random move instead.
				 */

        double [] valsperfirstmove =new double [depth];

        double bestScore=-Double.MAX_VALUE;
        int bestindex=0;
				GameState nextState;
				double alpha = -Double.MAX_VALUE;
				double beta = 0;

        for(int j=0; j<nextStates.size();j++){
          // vi gar igenom alla mojliga forsta rotnoder. dvs alla forsta utlagg av x
          //System.err.println("kom hit 2");
					nextState = nextStates.elementAt(j);
          double stateVal = alphabeta(nextStates.elementAt(j), depth, alpha, beta, gameState.getNextPlayer());
          //System.err.println("kom hit 1");
          if(isNewValBigger(bestScore,stateVal)){
            bestindex=j;
        }

        }
        // ta ut indexet for max for denna: valsperfirstmove
        System.err.println("kom hit");
        return nextStates.elementAt(bestindex);



        //Random random = new Random();
        //System.err.println(nextStates.size());
        //Valj random vad som ar bast naxt state
        //return nextStates.elementAt(random.nextInt(nextStates.size()));
    }



    public double alphabeta(GameState currState, int d, double alpha, double beta, int player){
        Vector<GameState> nextStates = new Vector<>();
        currState.findPossibleMoves(nextStates);
				double alpha1 = alpha;

        double v;
        GameState bestMove =new GameState();
        if(d == 0 || nextStates.isEmpty()){ /// prova; currState.isEOG()
          v=utility(currState);
          //int g=7;
          return v;
        }else{
          if(player ==Constants.CELL_X){
            v=-Double.MAX_VALUE;
            for(int i=0;i<nextStates.size();i++){

	            GameState cNode = nextStates.elementAt(i);
							System.err.println(cNode);
							v = Math.max(v, alphabeta(cNode, d-1, alpha1, beta, Constants.CELL_O));
							alpha = Math.max(alpha, v);
            	if(beta <= alpha){
								break;
								}
          }
          return v;
        }else{ // if player == Constants.CELL_X
            v=Double.MAX_VALUE;
            for(int i=0;i<nextStates.size();i++){
            GameState cNode = nextStates.elementAt(i);
						v = Math.min(v, alphabeta(cNode, d-1, alpha1, beta, Constants.CELL_O));
						alpha = Math.min(alpha, v);
            if(beta <= alpha){
              break;
            }
        }
        return v;

        }
      }
    }

    public Boolean isNewValBigger(double vold, double vnew){
      if(vold<vnew){
        return true;
      }
      return false;
    }


    public double utility(GameState lastState){

      if(lastState.isXWin()){
        return 1;
      }
      else if(lastState.isOWin()){
        return -1;
      }
      return 0.5;



    }

		//public GameState alfaBetaPruning(GameState unpruned, int d){


		//}


}
