package mychessgame.Figures;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.*;

import mychessgame.utils.*;

public abstract class Figure {
    protected Board board;

    protected FigureType type;
    protected FigureColor color;

    protected Position pos;
    protected boolean isMoved;

    protected boolean isClicked, isSelected;

    public Figure(Board board, Position pos) {
        this.board = board;
        this.pos = pos;
    }

    public List<Position> getAvailablePositions() {
        List<Position> moves = new ArrayList<Position>();

        for(Position p : getUnfilteredMoves()) {
            if(isValidMove(p)) moves.add(p);
        }

        return moves;
    }

    public abstract List<Position> getUnfilteredMoves();

    public void render(Graphics2D g, int tileSize, int mouseX, int mouseY) {
        if(isClicked || isSelected) renderIfSelected(g, tileSize, mouseX, mouseY);
        else renderNormal(g, tileSize, mouseX, mouseY);
    }

    public void renderIfClicked(Graphics2D g, int tileSize, int mouseX, int mouseY) {
        g.drawImage(getImage(), mouseX - tileSize / 2, mouseY - tileSize / 2, tileSize, tileSize, null);
    }

    public void renderIfSelected(Graphics2D g, int tileSize, int mouseX, int mouseY) {
        g.setColor(new Color(20, 86, 30, 128));

        g.fillRect(tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize);

        if(type == FigureType.KING) ((King)this).renderCheck(g, tileSize);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, isClicked ? 0.3f : 1.f));
        g.drawImage(getImage(), tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        if(isClicked) renderIfClicked(g, tileSize, mouseX, mouseY);
    }

    public void renderNormal(Graphics2D g, int tileSize, int mouseX, int mouseY) {
        if(type == FigureType.KING) ((King)this).renderCheck(g, tileSize);

        g.drawImage(getImage(), tileSize * pos.getX(), tileSize * pos.getY(), tileSize, tileSize, null);
    }

    public void renderAvailablePositions(Graphics2D g, int tileSize, int mouseX, int mouseY) {
        List<Position> moves = getAvailablePositions();
        Figure[][] grid = board.getGrid();

        Graphics2D g2d = (Graphics2D)g;

        g.setColor(new Color(20, 86, 30, 128));

        for(Position p : moves) {
            if(p.isTileHovered(tileSize, mouseX, mouseY)) renderHoveredPosition(p, g2d, tileSize, mouseX, mouseY);
            else {
                if(grid[p.getY()][p.getX()] != null) renderOccupiedPosition(p, g2d, tileSize, mouseX, mouseY);
                else renderEmptyPosition(p, g2d, tileSize, mouseX, mouseY);
            }
        }
    }

    public void renderEmptyPosition(Position p, Graphics2D g, int tileSize, int mouseX, int mouseY) {
        int R = tileSize / 8;

        if(renderEnPassant(p, g, tileSize, mouseX, mouseY)) return;

        g.fillArc(
            tileSize * p.getX() + tileSize / 2 - R,
            tileSize * p.getY() + tileSize / 2 - R,
            2 * R, 2 * R, 0, 360
        );
    }

    public boolean renderEnPassant(Position p, Graphics2D g, int tileSize, int mouseX, int mouseY) {
        if(
            type == FigureType.PAWN && board.getPreviousMove() != null && p.getX() == board.getPreviousMove().from.getX() &&
            Math.abs(board.getPreviousMove().from.getY() - board.getPreviousMove().to.getY()) == 2 && pos.getY() == board.getPreviousMove().to.getY()
        ) {
            double coef = 0.6;
            Area fillArea = new Area(new Rectangle(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize));
            fillArea.subtract(new Area(
                new RoundRectangle2D.Double(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize, tileSize * coef, tileSize * coef)
            ));
            g.fill(fillArea);

            return true;
        }

        return false;
    }

    public void renderOccupiedPosition(Position p, Graphics2D g, int tileSize, int mouseX, int mouseY) {
        double coef = 0.6;
        Area fillArea = new Area(new Rectangle(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize));
        fillArea.subtract(new Area(
            new RoundRectangle2D.Double(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize, tileSize * coef, tileSize * coef)
        ));
        g.fill(fillArea);
    }

    public void renderHoveredPosition(Position p, Graphics2D g, int tileSize, int mouseX, int mouseY) {
        g.fillRect(tileSize * p.getX(), tileSize * p.getY(), tileSize, tileSize);
    }

    protected Image getImage() {
        return board.getFigureImages()[color.ordinal()][type.ordinal()];
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public FigureColor getColor() {
        return color;
    }

    public Position getPosition() {
        return pos;
    }

    protected boolean isValidMove(Position newPos) {
        Figure[][] grid = board.getGrid();
        Figure temp = grid[newPos.getY()][newPos.getX()];

        grid[newPos.getY()][newPos.getX()] = this;
        grid[pos.getY()][pos.getX()] = null;

        Position tempPos = pos;
        pos = newPos;

        boolean result = !board.isUnderCheck(color);

        pos = tempPos;

        grid[newPos.getY()][newPos.getX()] = temp;
        grid[pos.getY()][pos.getX()] = this;

        return result;
    }

    public boolean isAttacking(Position pos) {
        List<Position> moves = getUnfilteredMoves();

        return pos.isInList(moves);
    }

    public static boolean isPositionUnderAttack(Position pos, FigureColor color, Board board) {
        Figure[][] grid = board.getGrid();

        for(Figure[] gs : grid) {
            for(Figure g : gs) {
                if(g != null && g.color != color) {
                    if(g.isAttacking(pos)) return true;
                }
            }
        }

        return false;
    }

    public void move(Position newPos) {
        Figure[][] grid = board.getGrid();

        grid[newPos.getY()][newPos.getX()] = this;
        grid[pos.getY()][pos.getX()] = null;

        checkAndMoveKingsideCastle(newPos);
        checkAndMoveQueensideCastle(newPos);

        checkAndMoveEnPassant(newPos);

        board.setPreviousMove(pos, newPos, type);
        pos = newPos;
        isMoved = true;

        if(checkPromotion(newPos)) return;

        checkEndgame();
    }

    public boolean checkPromotion(Position newPos) {
        if(type == FigureType.PAWN && (newPos.getY() == 7 || newPos.getY() == 0)) {

            board.setPromotion(true);

            return true;
        }

        return false;
    }

    public void checkAndMoveKingsideCastle(Position newPos) {
        Figure[][] grid = board.getGrid();

        if(type == FigureType.KING && newPos.x - pos.x == 2) {
            grid[newPos.getY()][newPos.getX()-1] = grid[pos.getY()][pos.getX()+3];
            grid[pos.getY()][pos.getX()+3] = null;
            grid[newPos.getY()][newPos.getX()-1].isMoved = true;
            grid[newPos.getY()][newPos.getX()-1].pos.setPosition(newPos.getX()-1, newPos.getY());
        }
    }

    public void checkAndMoveQueensideCastle(Position newPos) {
        Figure[][] grid = board.getGrid();
        
        if(type == FigureType.KING && pos.x - newPos.x == 2) {
            grid[newPos.getY()][newPos.getX()+1] = grid[pos.getY()][pos.getX()-4];
            grid[pos.getY()][pos.getX()-4] = null;
            grid[newPos.getY()][newPos.getX()+1].isMoved = true;
            grid[newPos.getY()][newPos.getX()+1].pos.setPosition(newPos.getX()+1, newPos.getY());
        }
    }

    public void checkAndMoveEnPassant(Position newPos) {
        Figure[][] grid = board.getGrid();

        if(
            type == FigureType.PAWN && board.getPreviousMove() != null && newPos.getX() == board.getPreviousMove().from.getX() &&
            Math.abs(board.getPreviousMove().from.getY() - board.getPreviousMove().to.getY()) == 2 && pos.getY() == board.getPreviousMove().to.getY()
        ) {
            grid[board.getPreviousMove().to.getY()][board.getPreviousMove().to.getX()] = null;
        }
    }

    public void checkEndgame() {
        if(board.isCheckmated(color == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE)) {
            System.out.println("Game over! Winner is: " + (color == FigureColor.WHITE ? "WHITE" : "BLACK"));
        }
        else if(board.isStalemated(color == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE)) {
            System.out.println("Game over! Stalemate!");
        }
    }

    public static Figure factory(FigureType type, FigureColor color, Board board, Position pos) {
        switch(type) {
            case PAWN: return new Pawn(board, color, pos);
            case QUEEN: return new Queen(board, color, pos);
            case KING: return new King(board, color, pos);
            case KNIGHT: return new Knight(board, color, pos);
            case BISHOP: return new Bishop(board, color, pos);
            case ROOK: return new Rook(board, color, pos);
            default: return null;
        }
    }

    public enum FigureType {
        KING, QUEEN, ROOK, KNIGHT, BISHOP, PAWN
    }

    public enum FigureColor {
        BLACK, WHITE
    }

    public static Image[][] loadFigureImages() {
        Image[][] images = new Image[2][6];

        images[FigureColor.WHITE.ordinal()][FigureType.KING.ordinal()] = ResourseHelper.loadImage("assets/white_king.png");
        images[FigureColor.WHITE.ordinal()][FigureType.QUEEN.ordinal()] = ResourseHelper.loadImage("assets/white_queen.png");
        images[FigureColor.WHITE.ordinal()][FigureType.ROOK.ordinal()] = ResourseHelper.loadImage("assets/white_rook.png");
        images[FigureColor.WHITE.ordinal()][FigureType.KNIGHT.ordinal()] = ResourseHelper.loadImage("assets/white_knight.png");
        images[FigureColor.WHITE.ordinal()][FigureType.BISHOP.ordinal()] = ResourseHelper.loadImage("assets/white_bishop.png");
        images[FigureColor.WHITE.ordinal()][FigureType.PAWN.ordinal()] = ResourseHelper.loadImage("assets/white_pawn.png");

        images[FigureColor.BLACK.ordinal()][FigureType.KING.ordinal()] = ResourseHelper.loadImage("assets/black_king.png");
        images[FigureColor.BLACK.ordinal()][FigureType.QUEEN.ordinal()] = ResourseHelper.loadImage("assets/black_queen.png");
        images[FigureColor.BLACK.ordinal()][FigureType.ROOK.ordinal()] = ResourseHelper.loadImage("assets/black_rook.png");
        images[FigureColor.BLACK.ordinal()][FigureType.KNIGHT.ordinal()] = ResourseHelper.loadImage("assets/black_knight.png");
        images[FigureColor.BLACK.ordinal()][FigureType.BISHOP.ordinal()] = ResourseHelper.loadImage("assets/black_bishop.png");
        images[FigureColor.BLACK.ordinal()][FigureType.PAWN.ordinal()] = ResourseHelper.loadImage("assets/black_pawn.png");

        return images;
    }
}
