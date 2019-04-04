//NONONONOW****3D3D3D******#************###################************####*#
//hjhebdhehjde
import java.util.*;

public class Player {

    //double bestv=0;
    /**
     * Performs a move
     *
     * @param pState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */


     long twoHundredMilliSeconds = 200000000L;
     long nineHundredMilliSeconds = 950000000L;
     int player;

     Deadline dead;

     double deadlineTime =Math.pow(10,8);//5*Math.pow(10,7);



    public GameState play(final GameState pState, final Deadline deadline) {

      Vector<GameState> nextStates = new Vector<GameState>();

      pState.findPossibleMoves(nextStates);
      ///. You will be provided a skeleton which generates a list of
      //valid moves for you to choose from

      if (nextStates.size() == 0) {
          // Must play "pass" move if there are no other moves possible.
          return new GameState(pState, new Move());
      }
      dead=deadline;

      int beta = Integer.MAX_VALUE;
      int alpha =Integer.MIN_VALUE;

      int maxd=20;//when debugg set high but in kattis you do not start at the beginning of the game so a smaller number is sufficient
      //int d =30;



      GameState topPerfState=null; //nextStates.elementAt(0);

        player = pState.getNextPlayer();
      //   System.err.println(Constants.CELL_RED);
      //   String board=pState.toMessage();
      //   System.err.println("START player "+player);
      //   //System.err.println(board);
      //   int it=-1;
      //
      //   for(int rr =0;rr<32;rr++){
      //     it++;
      //   System.err.print(board.charAt(rr));
      //   if(it==3){
      //     System.err.println();
      //     it=-1;
      //   }
      //
      //
      //
      // }

      //System.err.println("start player "+ player +"Start board: "+pState.toMessage());
      GameState state=null;

      int max_score = Integer.MIN_VALUE;
      int bestd=1;

      for (int d=0;d<maxd; d++){

            for (int i = 0; i < nextStates.size(); i++) {
              state = nextStates.elementAt(i);

              //int v=alphaBetaPruning(state,alpha,beta,d,false); // har kor vi ju for maximizing player df borde vara true
              int v=alphaBetaPruning2(state,alpha,beta,d,false); // har kor vi ju for maximizing player df borde vara true
              if(v > alpha) {
                  alpha = v;
                  topPerfState = state;
                  bestd =d;
              }

              if(beta <= alpha){
                 break;
               }


            }
      }
       //System.err.println(bestd);
    //   board=topPerfState.toMessage();
    //   System.err.println("END player "+pState.getNextPlayer());
    //   //System.err.println(board);
    //   it=-1;
    //
    //   for(int rr =0;rr<32;rr++){
    //     it++;
    //   System.err.print(board.charAt(rr));
    //   if(it==3){
    //     System.err.println();
    //     it=-1;
    //   }
    //
    //
    //
    // }

      //System.err.println(state);
//GameState currState,double alpha,double beta, int player,int d,int itters){
      return topPerfState;//nextStates.elementAt(bestindex);

      /**
       * Here you should write your algorithms to get the best next move, i.e.
       * the best next state. This skeleton returns a random move instead.
       **/


    }



    public boolean timeLeft1(){
      //System.err.println("timeuntil "+deadline.timeUntil());

      boolean keepSearching = true;
        if( dead.timeUntil() >= twoHundredMilliSeconds){
          return keepSearching;
        }
        return !keepSearching;
    }
    // public boolean timeLeft2(){
    //
    //   return (dead.getSeconds() < dead.now().getSeconds() + 100000000);
    // }


///return (pDue.getSeconds() < pDue.now().getSeconds() + TIME_EPSILON);



public int alphaBetaPruning2(GameState currState,int alpha,int beta, int d,boolean ismaxplayer){
  ///https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning

  //nineHundredMilliSeconds
    //System.err.println(dead.timeUntil());

    if((d==0) || (currState.isEOG()) || dead.timeUntil()<deadlineTime){///d==1){//if d=0 or node is a terminal node is has no next states     //currState.isEOG()){//d == 0 || nextStates.isEmpty()){ /// prova; currState.isEOG()
      //return heuristicmegaversion(currState);//heuristic4(currState);
      return heuristic4(currState);
    }else{


      GameState bestMove =new GameState();
      Vector<GameState> nextStates = new Vector<GameState>();
      currState.findPossibleMoves(nextStates);

      if(ismaxplayer){ //Maxes turn
        //int vbest=Integer.MIN_VALUE;

        for (GameState cNode: nextStates){
        int v =alphaBetaPruning2(cNode,alpha,beta,d-1,false);
        if(v >= alpha){
         alpha = v;
       }
        if(beta<=alpha){
          break;
        }
      }

      return alpha;
    }else{ // if player == Constants.CELL_X
      //int vbest=Integer.MAX_VALUE;
      for (GameState cNode: nextStates){
      int v =alphaBetaPruning2(cNode,alpha,beta,d-1,true);
      if(v <= beta){
        beta = v;
      }
      if(beta <= alpha){
         break;
       }
    }

    return beta;
    }
  }

}

public int alphaBetaPruning(GameState currState,int alpha,int beta, int d,boolean ismaxplayer){
  ///https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning

  //nineHundredMilliSeconds
    //System.err.println(dead.timeUntil());

    if((d==0) || (currState.isEOG()) || dead.timeUntil()<deadlineTime){///d==1){//if d=0 or node is a terminal node is has no next states     //currState.isEOG()){//d == 0 || nextStates.isEmpty()){ /// prova; currState.isEOG()
      return heuristicmegaversion(currState);//heuristic4(currState);
      //return heuristic4(currState);
    }//else{


      GameState bestMove =new GameState();
      Vector<GameState> nextStates = new Vector<GameState>();
      currState.findPossibleMoves(nextStates);

      if(ismaxplayer){ //Maxes turn
        int vbest=Integer.MIN_VALUE;

        for (GameState cNode: nextStates){
        int v =alphaBetaPruning(cNode,alpha,beta,d-1,false);
        vbest=getMaximum(v,vbest);
        alpha = getMaximum(alpha, vbest);
        if(beta<=alpha)break;

      }

      return vbest;
    }else{ // if player == Constants.CELL_X
      int vbest=Integer.MAX_VALUE;
      for (GameState cNode: nextStates){
      int v =alphaBetaPruning(cNode,alpha,beta,d-1,true);
      vbest =getMinimum(vbest,v);

      beta = getMinimum(beta, vbest);
      if(beta<=alpha)break;

    }

    return vbest;
    }


}
    // public double alphaBetaPruning2(GameState currState,double alpha,double beta, int d,boolean ismaxplayer){
    //   ///https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
    //
    //
    //   //getCpuTime
    //     if((d==0) || (currState.isEOG()) || dead.timeUntil()<nineHundredMilliSeconds ){//d==1){//if d=0 or node is a terminal node is has no next states     //currState.isEOG()){//d == 0 || nextStates.isEmpty()){ /// prova; currState.isEOG()
    //       return heuristic4(currState);
    //
    //     }else{
    //       GameState bestMove =new GameState();
    //       Vector<GameState> nextStates = new Vector<GameState>();
    //       currState.findPossibleMoves(nextStates);
    //
    //
    //       if(ismaxplayer){ //Maxes turn
    //         double v=Double.MIN_VALUE;
    //
    //         for (GameState cNode: nextStates){
    //         v =getMaximum(v,alphaBetaPruning(cNode,alpha,beta,d-1,false));
    //         alpha = getMaximum(alpha, v);
    //         if(alpha>=beta){
    //           break;}
    //
    //       }
    //
    //       return v;
    //     }else{ // if player == Constants.CELL_X
    //       double v=Double.MAX_VALUE;
    //       for (GameState cNode: nextStates){
    //       v =getMinimum(v,alphaBetaPruning(cNode,alpha,beta,d-1,true));
    //       beta = getMinimum(beta, v);
    //       if(alpha>=beta){
    //         break;
    //       }
    //
    //     }
    //
    //     return v;
    //     }
    //   }
    //
    // }


public int heuristic4(GameState currState){

  //   System.err.println();
  //   System.err.println("###################");
  //   String board=currState.toMessage();
  //   System.err.println(board);
  //   int it=-1;
  //
  //   for(int rr =0;rr<32;rr++){
  //     it++;
  //   System.err.print(board.charAt(rr));
  //   if(it==3){
  //     System.err.println();
  //     it=-1;
  //   }
  // }

    int totalScore=0;

    int no_white=0;
    int no_red=0;

    int no_red_kings=0;
    int no_white_kings=0;

    int board_side_red =0;
    int board_side_white =0;

      if (currState.isEOG()) {

        if((currState.isRedWin() && player==Constants.CELL_RED)||(currState.isWhiteWin() && player==Constants.CELL_WHITE)){
          return 1000000;

        }else if((currState.isRedWin() && player==Constants.CELL_WHITE)||(currState.isWhiteWin() && player==Constants.CELL_RED)){
          return -1000000;
        }else{//draw
          return 0;
        }

      }else{
        for (int i = 0; i < currState.NUMBER_OF_SQUARES; i++) {
          //COUNTING PIECES

          if (0!=(currState.get(i)&Constants.CELL_WHITE)) {
            no_white++;
            if (0 != (currState.get(i)&Constants.CELL_KING)){
               no_white_kings++;
             }


          }else if (0!=(currState.get(i)& Constants.CELL_RED)) {
                no_red++;
                if (0 != (currState.get(i)&Constants.CELL_KING)){
                   no_red_kings++;
                 }
              }


              //pieces that have advanced to the other side
              if(0!=(currState.get(i)&Constants.CELL_RED) && i>=20){
                board_side_red++;
              }else if(0!=(currState.get(i)&Constants.CELL_WHITE) && i<=11){
                board_side_white++;
              }



            }



          }

// System.err.println("no red "+no_red);
// System.err.println("no_red_kings "+no_red_kings);
//System.err.println("board_side_red "+board_side_red);
//
// System.err.println("no white "+no_white);
// System.err.println("no_white_kings "+no_white_kings);
//System.err.println("board_side_white "+board_side_white);


if(Constants.CELL_RED==player){
    totalScore += piecediff(no_red,no_white);
    totalScore+=piecediff(no_red_kings,no_white_kings);
    totalScore+=2*piecediff(board_side_red,board_side_white);

}else{
  totalScore += piecediff(no_white,no_red);
  totalScore+=piecediff(no_white_kings,no_red_kings);
  totalScore+=2*piecediff(board_side_white,board_side_red);
}

return totalScore;
}

int piecediff(int no_p1, int no_p2 ){
  return no_p1-no_p2;
}

public int heuristicmegaversion(GameState currState){
  //Add: implement heuristic for almost king
  //Add: in unprotected check also if there is a player infront of the piece = really bad. cuz if under attack + unprotected

  int totalScore=0;

  if (currState.isEOG()) {


    if((currState.isRedWin() && player==Constants.CELL_RED)||(currState.isWhiteWin() && player==Constants.CELL_WHITE)){
      return 1000000;

    }else if((currState.isRedWin() && player==Constants.CELL_WHITE)||(currState.isWhiteWin() && player==Constants.CELL_RED)){
      return -1000000;
    }else{//draw
      return 0;
    }



  }else{
          // if(currState.getMovesUntilDraw()<6){
          //   return -100;
          // }



          int no_white = 0;
          int no_red = 0;

          int no_red_kings = 0;
          int no_white_kings = 0;

          int no_red_protected =0;
          int no_white_protected =0;

          int itter_left=4;
          int itter_right=3;

          int board_side_red =0;
          int board_side_white =0;

          int unprotect_itter=5;
          int three_1=0;

          int unprotect_itter_2=8;
          int three_2=0;

          int no_red_attackable=0;
          int no_white_attackable=0;

          int no_red_under_attack=0;
          int no_white_under_attack=0;

          //System.err.println();
          //System.err.println("###################");

          //...R....rrrr.ww.....W....ww.....


        //   String board=currState.toMessage();
        //   System.err.println(board);
        //   int it=-1;
        //
        //   for(int rr =0;rr<32;rr++){
        //     it++;
        //   System.err.print(board.charAt(rr));
        //   if(it==3){
        //     System.err.println();
        //     it=-1;
        //   }
        //
        //
        //
        // }




          for (int i = 0; i < currState.NUMBER_OF_SQUARES; i++) {
            //COUNTING PIECES
            if (currState.get(i) ==Constants.CELL_WHITE) {
              no_white++;
                  if (currState.get(i) == Constants.CELL_KING){
                     no_white_kings++;
                   }
            }else if (currState.get(i) == Constants.CELL_RED) {
                  no_red++;
                  if (currState.get(i) == Constants.CELL_KING){
                     no_red_kings++;
                }
              }

            // Give extra points if a player has evolved to the other side of the board
            //These are players that are almost kings and also players that can attack more as they are in enemy territory
            //if we have a red player that is on the white players side
            if((currState.get(i)==Constants.CELL_RED) && i>=20){
              board_side_red+=1;

            }else if((currState.get(i)==Constants.CELL_WHITE) && i<=11){
              board_side_white+=1;

            }

            //Check if a piece is no_red_attackable
            //Add: check also if there is a player infront of the piece = really bad

            ///left 5,6,7,13,14,15,21,22,23

            //System.err.println("rrr "+(currState.get(i-4)&Constants.CELL_EMPTY));
            if(i==unprotect_itter && i<24){
              //System.err.println("get in here");



              if(currState.get(i)==Constants.CELL_RED){
                //System.err.println("get in here");

                //if(currState.get(i-4)==Constants.CELL_EMPTY){
                //  System.err.println("hallihalla ");
                //  System.err.println(currState.get(i-4)==Constants.CELL_EMPTY);
                  //break;
                //}

                //we wanna check if the space behind this piece is empty or contains another white piece
                //if (  ((0 != (currState.get(i-4)&Constants.CELL_WHITE))&& (0 != (currState.get(i-4)&Constants.CELL_EMPTY))  ) ||  ( (0 != (currState.get(i-5) &Constants.CELL_WHITE)) &&(0 != (currState.get(i-5)&Constants.CELL_EMPTY)) ) )  no_red_attackable+=1;
                if ( (currState.get(i-4)==Constants.CELL_EMPTY)  || (currState.get(i-5)==Constants.CELL_EMPTY)  ){
                    //System.err.println("get in here");
                    no_red_attackable+=1;
                    //System.err.println("index is attackable: "+i);

                    if ((currState.get(i+4)==Constants.CELL_WHITE)|| (currState.get(i+3)==Constants.CELL_WHITE)){
                        no_red_under_attack++;
                        //System.err.println("index is under attack: "+i);
                    }

                  }


              }else if((currState.get(i)==Constants.CELL_WHITE)){
                //we wanna check if the space behind this piece is empty or contains another white piece
                //if( ((0 != (currState.get(i+4) &Constants.CELL_WHITE))&& (0 !=(currState.get(i+4)&Constants.CELL_WHITE))  ) || ( (0 !=  (currState.get(i+3) &Constants.CELL_WHITE))&& (0 !=(currState.get(i+3) &Constants.CELL_EMPTY))) ){
                if(  (currState.get(i+4)==Constants.CELL_EMPTY)   || (currState.get(i+3) ==Constants.CELL_EMPTY) ){

                    no_white_attackable+=1;

                    if ((currState.get(i-4)==Constants.CELL_RED) || (currState.get(i-5)==Constants.CELL_RED)){
                        no_white_under_attack++;
                    }



                  }

              }



              //getting the indices for unprotected rows shifted to the left
              unprotect_itter++;
              three_1++;

              if(three_1%3==0){
                unprotect_itter+=5;
                three_1=0;
              }


          }


            //right 8,9,10,16,17,18,24,25,26
            if(i==unprotect_itter_2 && i<27){

              if(currState.get(i)==Constants.CELL_RED){
                //we wanna check if the space behind this piece is empty or contains another white piece
                //if (  ((0 != (currState.get(i-4)&Constants.CELL_WHITE))&&(0 != (currState.get(i-4)&Constants.CELL_EMPTY))  ) ||  ( (0 != (currState.get(i-3) &Constants.CELL_WHITE)) &&(0 != (currState.get(i-3)&Constants.CELL_EMPTY)) ) ){
                if ( (currState.get(i-4)==Constants.CELL_EMPTY)   ||  (currState.get(i-3)==Constants.CELL_EMPTY)  ){
                    no_red_attackable+=1;

                    //now we alos wanna check if it is actually under attack.
                    //i.e if there is a white player diagonally infront of the white player
                    if ( (currState.get(i+4)==Constants.CELL_WHITE) || (currState.get(i+5)==Constants.CELL_WHITE) ){
                        no_red_under_attack++;
                    }


                }
              }

              else if(currState.get(i)==Constants.CELL_WHITE){
                //we wanna check if the space behind this piece is empty or contains another white piece
                if( (currState.get(i+4) ==Constants.CELL_EMPTY) ||  (currState.get(i+5) ==Constants.CELL_EMPTY) ){
                      no_white_attackable+=1;

                      if ((currState.get(i-4)==Constants.CELL_RED) || (currState.get(i-3)==Constants.CELL_RED) ){
                          no_white_under_attack++;
                      }
                }

            }

              //getting the indices for unprotected rows shifted to the right
              unprotect_itter_2++;
              three_2++;

              if(three_2%3==0){
                unprotect_itter_2+=5;
                three_2=0;
              }

            }

            //protected pieces ie by sides
            //left side
            if(i==itter_left){
              //System.err.println("left "+i);
              itter_left+=8;
              if(currState.get(i)==Constants.CELL_RED) no_red_protected++;
              if(currState.get(i)==Constants.CELL_WHITE) no_white_protected++;

            }else if(i==itter_right){
              //System.err.println("right "+i);
              itter_right+=8;
              if(currState.get(i)==Constants.CELL_RED) no_red_protected++;
              if(currState.get(i)==Constants.CELL_WHITE) no_white_protected++;

            }


          }

          // You wanna keep track of who is the maximizing and who is the minimizing player
          // System.err.println("red no_red_attackable: "+(no_red_attackable));
          // System.err.println("white _attackable: "+(no_white_attackable));
          // System.err.println("red under a: "+(no_red_under_attack));
          //System.err.println("white under a: "+(no_white_under_attack));
          if(Constants.CELL_WHITE==player){
            //totalScore+=0;

            totalScore+=no_white-no_red;
            totalScore+=(no_white_kings-no_red_kings)*2;
            totalScore+=10*(no_red_under_attack-no_white_under_attack);
            //System.err.println("white: "+(no_red_under_attack-no_white_under_attack));
            //totalScore+=no_white_protected-no_red_protected;
            //totalScore+=20*(board_side_white -board_side_red);
            //totalScore+=(no_red_attackable-no_white_attackable)*10;

          }else if (Constants.CELL_RED==player){
            //totalScore+=0;
          //else
          totalScore+=no_red-no_white;
          totalScore+=(no_red_kings-no_white_kings)*2;
          totalScore+=10*(no_white_under_attack-no_red_under_attack);
          //System.err.println("red: "+(no_white_under_attack-no_red_under_attack));
          //totalScore+=no_red_protected-no_white_protected;
          //totalScore+=20*(board_side_red-board_side_white);
          //totalScore+=(no_white_attackable-no_red_attackable)*10;
        }

}

return totalScore;
}





    public int getMaximum(int vold, int vnew){
      if(vold>=vnew){
       return vold;
     }
      return vnew;
    }

    public int getMinimum(int vold, int vnew){
      if(vold<=vnew){
      return vold;
    }
      return vnew;
    }
  }
