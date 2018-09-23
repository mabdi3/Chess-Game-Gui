import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Represents a PGN-format Chess Game
 * @author instructor
 * @version 1.0
 */
public class ChessGame {

    private StringProperty event = new SimpleStringProperty(this, "NA");
    private StringProperty site = new SimpleStringProperty(this, "NA");
    private StringProperty date = new SimpleStringProperty(this, "NA");
    private StringProperty white = new SimpleStringProperty(this, "NA");
    private StringProperty black = new SimpleStringProperty(this, "NA");
    private StringProperty result = new SimpleStringProperty(this, "NA");
    private List<String> moves;

    /**
     * Creates an instance of ChessGame with the following parameters:
     *
     * @param event the event
     * @param site the site
     * @param date the data
     * @param white the name of the white player
     * @param black the name of the black player
     * @param result the result of the match
     */
    public ChessGame(String event, String site, String date,
                     String white, String black, String result) {
        this.event.set(event);
        this.site.set(site);
        this.date.set(date);
        this.white.set(white);
        this.black.set(black);
        this.result.set(result);
        moves = new ArrayList<>();
    }

    /**
     * adds the specified move to the moves list
     * @param move the move to be added
     */
    public void addMove(String move) {
        moves.add(move);
    }
    /**
     * @return the nth move
     * @param n the move's number
     */
    public String getMove(int n) {
        return moves.get(n - 1);
    }
    /**
     * @return the event name
     */
    public String getEvent() {
        return event.get();
    }
    /**
     * @return the site name
     */
    public String getSite() {
        return site.get();
    }
    /**
     * @return the date name
     */
    public String getDate() {
        return date.get();
    }
    /**
     * @return the white player's name
     */
    public String getWhite() {
        return white.get();
    }
    /**
     * @return the black player's name
     */
    public String getBlack() {
        return black.get();
    }
    /**
     * @return the result of the game
     */
    public String getResult() {
        return result.get();
    }

    /**
     * @return the opening of the game
     */
    public String getOpening() {
        if (this.moves.size() >= 3) {
            if (this.getMove(1).contains("e4 e5")
                && this.getMove(2).contains("Nf3 Nc6")
                && this.getMove(3).contains("Bc4 Bc5")) {
                return "Giuoco Piano";
            } else if (this.getMove(1).contains("e4 e5")
                && this.getMove(2).contains("Nf3 Nc6")
                && this.getMove(3).contains("Bb5")) {
                return "Ruy Lopez";
            } else if (this.getMove(1).contains("e4 c5")) {
                return "Sicilian Defence";
            } else if (this.getMove(1).contains("d4 d5")
                    && this.getMove(2).contains("c4")) {
                return "Queen's Gambit";
            } else if (this.getMove(1).contains("d4 Nf6")) {
                return "Indian Defence";
            } else if (this.getMove(1).contains("e4 e5")
                    && this.getMove(2).contains("Nf3 d6")) {
                return "Philidor Defence";
            }
        } else if (this.moves.size() == 2) {
            if (this.getMove(1).contains("e4 c5")) {
                return "Sicilian Defence";
            } else if (this.getMove(1).contains("d4 d5")
                    && this.getMove(2).contains("c4")) {
                return "Queen's Gambit";
            } else if (this.getMove(1).contains("d4 Nf6")) {
                return "Indian Defence";
            } else if (this.getMove(1).contains("e4 e5")
                    && this.getMove(2).contains("Nf3 d6")) {
                return "Philidor Defence";
            }
        } else if (this.moves.size() == 1) {
            if (this.getMove(1).contains("e4 c5")) {
                return "Sicilian Defence";
            } else if (this.getMove(1).contains("d4 Nf6")) {
                return "Indian Defence";
            }
        } else {
            return "NA";
        }
        return "NA";
    }
}