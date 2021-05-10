package chess.game;

import chess.cli.Cli;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.Stack;


/**
 * Game class which defines current game.
 */

public class Game {
    public Player playerWhite;
    public Player playerBlack;
    public Board board;
    public ArrayList<Piece> beatenPieces;
    public Stack<Move> moveHistory;
    public Player currentPlayer;
    public Move move;

    /**
     * Constructor for a Game
     */
    public Game() {
        this.playerWhite = new Player(Colour.WHITE);
        this.playerBlack = new Player(Colour.BLACK);
        this.currentPlayer = playerWhite;   // White always begins
        this.board = new Board();
        this.moveHistory = new Stack<>();
        this.beatenPieces = new ArrayList<>();
    }

    /**
     * Checks if Console Input is a syntactical correct Move.
     *
     * @param consoleInput Input of active Player as a String.
     * @return a boolean if the syntax of the input is correct
     */
    public boolean isValidMove(String consoleInput){
        if(consoleInput.length() > 4 && consoleInput.length() < 7) {
            if (consoleInput.charAt(2) == '-') {
                return Label.contains(consoleInput.substring(0, 2)) && Label.contains(consoleInput.substring(3, 5));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Evaluates semantical correctness of input-move
     * @param selectedPiece Piece which Player wants to move
     * @param finalSquare Square which Player wants his Piece to move to
     * @return boolean Returns if input move is possible
     */
    public boolean isMoveAllowed(Piece selectedPiece, Square finalSquare) {
        if (selectedPiece == null) {
            return false;
        }
        if (selectedPiece.getColour() == currentPlayer.getColour()) {
            if (finalSquare.getOccupiedBy() != null) {
                Piece targetPiece = finalSquare.getOccupiedBy();
                // if command is castling, check if it is allowed
                if (selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK
                        && canDoCastling(selectedPiece, targetPiece)) {
                    return true;
                } else if (selectedPiece.getType() == Type.PAWN) {
                    return ((Pawn) selectedPiece).canCapture(finalSquare);
                } else {
                    return (selectedPiece.isPiecesMove(finalSquare)
                            && isPathEmpty(selectedPiece, finalSquare)
                            && !isInCheck());
                }
            } else {
                if (selectedPiece.getType() == Type.PAWN) {
                    return (selectedPiece.isPiecesMove(finalSquare) ||
                            ((Pawn) selectedPiece).isEnPassant(finalSquare, moveHistory));
                } else if (selectedPiece.getType() == Type.KING) {
                    return (selectedPiece.isPiecesMove(finalSquare) && isSafeSquare(finalSquare));
                }  else {
                    return (selectedPiece.isPiecesMove(finalSquare)
                            && isPathEmpty(selectedPiece, finalSquare)
                            && !isInCheck());
                }
            }
        }
        return false;
    }

    /**
     * Evaluates if current state of game is draw
     * @return boolean returns if game is draw or not
     */
    public boolean isADraw() {
        if (!isInCheck()) {
            // King is not in check
            if (!getAlliedPieces().isEmpty()) {
                // if ally exists that can move - not a draw
                return false;
            }
            // if King can Move it's not a draw
            return !canKingMove();
        }
        // King is in check, but that's not a draw
        return false;
    }

    /**
     * Evaluates if King is able to move safely
     * @return boolean Returns if King is able to make a safe move
     */
    private boolean canKingMove() {
        Square kingSquare = this.board.getSquareOfKing(currentPlayer.getColour());
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                // looping through board to check if Square is next to King and is not occupied by ally
                if (isSurroundingSquare(kingSquare, board.getBoard()[i][j])
                        && board.getBoard()[i][j].getOccupiedBy().getColour() != currentPlayer.getColour()) {
                    // if a Square is next to the King is it safe to move there
                    if(isSafeSquare(board.getBoard()[i][j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * implementation of movement
     * @param startSquare Square where movement starts
     * @param finalSquare Square where movement ends
     * @return returns opposite of "move is allowed"
     */
    public boolean processMove(Square startSquare, Square finalSquare) {
        Move currentMove = new Move(startSquare, finalSquare);
        Piece selectedPiece = startSquare.getOccupiedBy();
        Piece targetPiece = finalSquare.getOccupiedBy();

        if (targetPiece != null &&
                selectedPiece.getType() == Type.KING && targetPiece.getType() == Type.ROOK) {
            // if the move is castling and it is allowed to do the move
            currentMove.castlingMove(board);
            moveHistory.add(currentMove);
            selectedPiece.setHasMoved(true);
            targetPiece.setHasMoved(true);

            // change currentPlayer to next Colour
            currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

            if (isCheckMate()) {
                currentPlayer.setLoser(true);
            }
            return true;
        }

        if (selectedPiece.getType() == Type.PAWN && ((Pawn) selectedPiece).isEnPassant(finalSquare, moveHistory)) {
            // if the move is an en passant capture
            Piece enemyPiece = currentMove.enPassantMove(moveHistory, board);
            beatenPieces.add(enemyPiece);
            moveHistory.add(currentMove);
            // change currentPlayer to next Colour
            currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

            if (isCheckMate()) {
                currentPlayer.setLoser(true);
            }
            return true;
        }
        else if (selectedPiece.getType() == Type.PAWN &&
                finalSquare.getY() == ((currentPlayer == playerWhite) ? 0 : 7))
        {
            // Need to do a move as pawn and then change piece
            currentMove.doMove(this.board);
            selectedPiece.setHasMoved(true);
            moveHistory.add(currentMove);

            while(true)
            {
                System.out.println("Please select piece for change (b/n/r/q):");
                String val = Cli.getInput();

                if (val.equalsIgnoreCase("b"))      selectedPiece = new Bishop(finalSquare, currentPlayer.colour);
                else if (val.equalsIgnoreCase("n")) selectedPiece = new Knight(finalSquare, currentPlayer.colour);
                else if (val.equalsIgnoreCase("r")) selectedPiece = new Rook(finalSquare, currentPlayer.colour);
                else if (val.equalsIgnoreCase("q")) selectedPiece = new Queen(finalSquare, currentPlayer.colour);
                else continue;

                break;
            }

            return true;
        }

        if (targetPiece != null) {
            // add a beaten piece to the ArrayList
            beatenPieces.add(targetPiece);
        }
        // other moves
        currentMove.doMove(this.board);
        moveHistory.add(currentMove);
        if (isInCheck()) {
            // if after the move the King is in check, undo the move
            currentMove.undoMove(this.moveHistory, this.board);

            return false;   // current player can try again
        }
        selectedPiece.setHasMoved(true); // set only after checking if the King is in check

        // change currentPlayer to next Colour
        currentPlayer = currentPlayer == playerWhite ? playerBlack : playerWhite;

        if (isCheckMate()) {
            currentPlayer.setLoser(true);
        }
       return true; // the move was allowed
    }


    /**
     * evaluates if direct path from one square to another is empty
     * @param piece Piece which has to move
     * @param end Square where piece has to go to
     * @return returns if selected path is empty
     */
    private boolean isPathEmpty (Piece piece, Square finalSquare){
        if (isSurroundingSquare(piece.getSquare(), finalSquare) &&
                finalSquare.getOccupiedBy() != null && finalSquare.getOccupiedBy().getColour() != currentPlayer.getColour()) {
            return true;
        }
        Type type = piece.getType();
        ArrayList<Square> path = generatePath(type, piece.getSquare(), finalSquare);
        if (path.isEmpty() && type == Type.KNIGHT || type == Type.PAWN) {
            // Knight can leap, Pawns don't have a path
            return true;
        } else if (path.isEmpty()) {
            return false;
        }  else {
            for (Square visitedSquare : path) {
                if (visitedSquare.getOccupiedBy() != null) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * evaluates if current Players King is in check and sets Value
     * @return returns checkStatus
     */
    private boolean isInCheck (){
        Square squareKing = this.board.getSquareOfKing(currentPlayer.getColour());
        ArrayList<Piece> enemies = getEnemyPieces();
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.isPiecesMove(squareKing)
                    && isPathEmpty(enemyPiece, squareKing)) {
                currentPlayer.setInCheck(true);
                return true;
            }
        }
        currentPlayer.setInCheck(false);
        return false;
    }

    /**
     * evaluates if current Players King is in check and if he is able to avoid it and sets Value
     * @return boolean returns if Player is checkMate or not
     */
    public boolean isCheckMate() {
        if (currentPlayer.getInCheck()) {
            if (canKingMove()) {
                return false;
            }
            // can ally defend the King if the King can't move
            ArrayList<Piece> enemies = getEnemyPieces();
            for (Piece enemyPiece : enemies) {
                if (isMoveAllowed(enemyPiece, board.getSquareOfKing(currentPlayer.getColour()))) {
                    return !canDefendKing(enemyPiece);
                }
            }
            // cannot move and ally can't defend -> checkmate
            currentPlayer.setCheckMate(true);
            return true;
        } else {
            // King is not in check
            return false;
        }
    }

    /**
     * evaluates if one of Players Pieces is able to block an enemies attack
     * @param enemyPiece
     * @return boolean returns if Player is able to avoid check
     */
    private boolean canDefendKing(Piece enemyPiece) {
        ArrayList<Piece> allies = getAlliedPieces();
        for (Piece alliedPiece : allies) {
            if (isMoveAllowed(alliedPiece, enemyPiece.getSquare())) {
                return true;
            } else {
                ArrayList<Square> enemyPath = generatePath(enemyPiece.getType(),
                        enemyPiece.getSquare(), board.getSquareOfKing(currentPlayer.getColour()));
                for (Square end : enemyPath) {
                    return isMoveAllowed(alliedPiece, end);
                }
            }
        }
        return false;
    }

    /**
     * evaluates if King is able to safely move to selected square
     * @param finalSquare Square King should move to
     * @return boolean returns if King is able to move safely
     */
    private boolean isSafeSquare(Square finalSquare) {
        ArrayList<Piece> enemies = getEnemyPieces();
        System.out.println(enemies);
        for (Piece enemyPiece : enemies) {
            if (enemyPiece.getType() == Type.BISHOP
                    || enemyPiece.getType() == Type.ROOK
                    || enemyPiece.getType() == Type.QUEEN) {
                if (enemyPiece.isPiecesMove(finalSquare) &&
                        isPathEmpty(enemyPiece, board.getSquareOfKing(currentPlayer.getColour()))) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KNIGHT) {
                if (enemyPiece.isPiecesMove(finalSquare)) {
                    return false;
                }
            } else if (enemyPiece.getType() == Type.KING) {
                    if (enemyPiece.isPiecesMove(finalSquare)) {
                        return false;
                    }
            } else {
                if (enemyPiece instanceof Pawn) {
                    if (((Pawn)enemyPiece).canCapture(finalSquare)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Generates Path if Piece moves more than one Square
     * @param type Type of Piece
     * @param startSquare Start-Square of Movement
     * @param finalSquare End-Square of Movement
     * @return Path of moving Piece
     */
    private ArrayList<Square> generatePath(Type type, Square startSquare, Square finalSquare) {
        ArrayList<Square> path = new ArrayList<>();
        if (type == Type.QUEEN) {
            if (startSquare.getX() - finalSquare.getX() == 0) {                                 //vertical move
                if (Math.abs(startSquare.getY() - finalSquare.getY()) > 1) {                    //Queen moves more than one Square
                    int from = Math.min(startSquare.getY(), finalSquare.getY());
                    for (int i = 1; i < Math.abs(startSquare.getY() - finalSquare.getY()); i++) {
                        path.add(board.getBoard()[startSquare.getX()][from + i]);
                    }
                }
            } else if (startSquare.getY() - finalSquare.getY() == 0){                           //horizontal move
                if (Math.abs(startSquare.getX() - finalSquare.getX()) > 1) {                    //Queen moves more than one Square
                    int from = Math.min(startSquare.getX(), finalSquare.getX());
                    for (int i = 1; i < Math.abs(startSquare.getX() - finalSquare.getX()); i++) {
                        path.add(board.getBoard()[from + i][startSquare.getY()]);
                    }
                }
            }
            else {
                int diffX = finalSquare.getX() - startSquare.getX();                                // if positive: Queen moves from up to down
                int diffY = finalSquare.getY() - startSquare.getY();                                // if positive: Queen moves from left to right
                int dirX = diffX / Math.abs(diffX);                         // if positive: Queen moves from up to down
                int dirY = diffY / Math.abs(diffY);                         // if positive: Queen moves from left to right
                if (Math.abs(diffX) > 1){                                   // Queen moves more than one Square
                    for (int i = 1; i < Math.abs(diffX); i++){
                        path.add(board.getBoard()[startSquare.getX() + i * dirX][startSquare.getY() + i * dirY]);
                    }
                }
            }


        } else if (type == Type.BISHOP) {
            int diffX = finalSquare.getX() - startSquare.getX();                                // if positive: Bishop moves from up to down
            int diffY = finalSquare.getY() - startSquare.getY();                                // if positive: Bishop moves from left to right
            int dirX = diffX / Math.abs(diffX);                         // if positive: Bishop moves from up to down
            int dirY = diffY / Math.abs(diffY);                         // if positive: Bishop moves from left to right
            if (Math.abs(diffX) > 1){                                   // Bishop moves more than one Square
                for (int i = 1; i < Math.abs(diffX); i++){
                    path.add(board.getBoard()[startSquare.getX() + i * dirX][startSquare.getY() + i * dirY]);
                }
            }
        } else if (type == Type.ROOK) {
            if (startSquare.getX() - finalSquare.getX() == 0) {                                 //vertical move
                if (Math.abs(startSquare.getY() - finalSquare.getY()) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(startSquare.getY(), finalSquare.getY());
                    for (int i = 1; i < Math.abs(startSquare.getY() - finalSquare.getY()); i++) {
                        path.add(board.getBoard()[startSquare.getX()][from + i]);
                    }
                }
            } else {                                                    //horizontal move
                if (Math.abs(startSquare.getX() - finalSquare.getX()) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(startSquare.getX(), finalSquare.getX());
                    for (int i = 1; i < Math.abs(startSquare.getX() - finalSquare.getX()); i++) {
                        path.add(board.getBoard()[from + i][startSquare.getY()]);
                    }
                }
            }
        }
        return path;
    }


    /**
     * evaluates if selected castling-alternative is possible
     * @param selectedPiece
     * @param targetPiece
     * @return boolean returns is castling is possible
     */
    private boolean canDoCastling(Piece selectedPiece, Piece targetPiece) {
        // selectedPiece is King, targetPiece is Rook
        Colour kingColour = selectedPiece.getColour();

        if (!selectedPiece.getHasMoved() && !targetPiece.getHasMoved() // King and Rook didn't move yet
                && isPathEmpty(selectedPiece, targetPiece.getSquare())) {   // no pieces between King and Rook

            ArrayList<Piece> enemies = getEnemyPieces();
            int diff = Math.abs(targetPiece.getSquare().getX() - selectedPiece.getSquare().getX());
            int king_x;
            int king_y = selectedPiece.getSquare().getY();

            // check if the Kings current Square and/or any Squares the King visits are in check/under attack
            for (Piece enemyPiece : enemies) {
                for (int i = 0; i < diff; i++) {
                    king_x = selectedPiece.getSquare().getX() + i;
                    Square tempSquare = new Square(Label.values()[king_x + king_y], king_x, king_y);
                    if(enemyPiece.isPiecesMove(tempSquare) && isPathEmpty(enemyPiece, tempSquare)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * evaluates if direct path from one square to another is empty
     * @param piecesSquare Piece which has to move
     * @param squareOfInterest Square where piece has to go to
     * @return returns if selected path is empty
     */
    private boolean isSurroundingSquare(Square piecesSquare, Square squareOfInterest){
        int diffX = piecesSquare.getX() - squareOfInterest.getX();
        int diffY = piecesSquare.getY() - squareOfInterest.getY();
        return diffX < 2 && diffY < 2;
    }

    /**
     * A function determining all the allied Pieces on the current board of a certain Piece
     *
     * @return an ArrayList containing all allied Pieces
     */
    public ArrayList<Piece> getAlliedPieces() {
        ArrayList<Piece> allies;
        if(currentPlayer.getColour() == Colour.WHITE) {
            allies = board.whitePieces;
        } else {
            allies = board.blackPieces;
        }
        for (int i = 0; i < allies.size(); i++) {
            for (Piece beaten : beatenPieces) {
                if (allies.get(i).getColour() == beaten.getColour() && allies.get(i).getType() == beaten.getType()
                    && beaten.equals(allies.get(i))) {
                    allies.remove(allies.get(i));
                }
            }
        }
        return new ArrayList<>(allies);
    }

    /**
     * A function determining all the enemy Pieces on the current board of a certain Piece
     *
     * @return an ArrayList containing all active enemy Pieces
     */
    public ArrayList<Piece> getEnemyPieces() {
        ArrayList<Piece> enemies;
        if(currentPlayer.getColour() == Colour.WHITE) {
            enemies = board.blackPieces;
        } else {
            enemies = board.whitePieces;
        }
        for (int i = 0; i < enemies.size(); i++) {
            for (Piece beaten : beatenPieces) {
                if (enemies.get(i).getColour() == beaten.getColour() && enemies.get(i).getType() == beaten.getType()
                    && beaten.equals(enemies.get(i))) {
                    enemies.remove(enemies.get(i));
                }
            }
        }
        return new ArrayList<>(enemies);
    }
}

