import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import java.util.function.Predicate;
import javafx.collections.transformation.SortedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
/**
 * Represents metadate of ChessGames one perline, of each game in
 * ChessDB.
 * @author mabdi3
 * @version 1.0
 */
public class ChessGui extends Application {

    private ChessDb hm = new ChessDb();
    private TableView<ChessGame> tableView;
    private ArrayList<String> fileList = new ArrayList<String>();
    private List<ChessGame> chessGames = new ArrayList<>();
    private File[] files;


    private void loadPgns() {
        File dir = new File(".");
        files = dir.listFiles((d, name) -> name.endsWith(".pgn"));
        for (File file: files) {
            chessGames.add(new ChessGame(
                    tagValue("Event", fileContent(file.getPath())),
                    tagValue("Site", fileContent(file.getPath())),
                    tagValue("Date", fileContent(file.getPath())),
                    tagValue("White", fileContent(file.getPath())),
                    tagValue("Black", fileContent(file.getPath())),
                    tagValue("Result", fileContent(file.getPath()))));
            fileList.add(fileContent(file.getPath()));
        }

//        File realFega = new File("fegatello.pgn");
//        File realFool = new File("fools-mate.pgn");
//        File realFrench = new File("french-with-ep.pgn");
//        File realMorph = new File("panandh-amirpb007-2011.pgn");
//        File realGiuoco = new File("giuoco-piano.pgn");
//        fileList.add(fileContent(realFega.getAbsolutePath()));
//        fileList.add(fileContent(realFool.getAbsolutePath()));
//        fileList.add(fileContent(realFrench.getAbsolutePath()));
//        fileList.add(fileContent(realMorph.getAbsolutePath()));
//        fileList.add(fileContent(realGiuoco.getAbsolutePath()));
//        ChessGame fega = new ChessGame(
//            tagValue("Event", fileList.get(0)),
//            tagValue("Site", fileList.get(0)),
//            tagValue("Date", fileList.get(0)),
//            tagValue("White", fileList.get(0)),
//            tagValue("Black", fileList.get(0)),
//            tagValue("Result", fileList.get(0)));
//        ChessGame foolsMate = new ChessGame(
//            tagValue("Event", fileList.get(1)),
//            tagValue("Site", fileList.get(1)),
//            tagValue("Date", fileList.get(1)),
//            tagValue("White", fileList.get(1)),
//            tagValue("Black", fileList.get(1)),
//            tagValue("Result", fileList.get(1)));
//        ChessGame french = new ChessGame(
//            tagValue("Event", fileList.get(2)),
//            tagValue("Site", fileList.get(2)),
//            tagValue("Date", fileList.get(2)),
//            tagValue("White", fileList.get(2)),
//            tagValue("Black", fileList.get(2)),
//            tagValue("Result", fileList.get(2)));
//        ChessGame morph = new ChessGame(
//            tagValue("Event", fileList.get(3)),
//            tagValue("Site", fileList.get(3)),
//            tagValue("Date", fileList.get(3)),
//            tagValue("White", fileList.get(3)),
//            tagValue("Black", fileList.get(3)),
//            tagValue("Result", fileList.get(3)));
//        ChessGame giuoco = new ChessGame(
//            tagValue("Event", fileList.get(4)),
//            tagValue("Site", fileList.get(4)),
//            tagValue("Date", fileList.get(4)),
//            tagValue("White", fileList.get(4)),
//            tagValue("Black", fileList.get(4)),
//            tagValue("Result", fileList.get(4)));
//        chessGames.add(fega);
//        chessGames.add(foolsMate);
//        chessGames.add(french);
//        chessGames.add(morph);
//        chessGames.add(giuoco);
        loadMoves();
    }


    private void loadMoves() {
        String[] woop = new String[8];
        ChessGame found = new ChessGame(
                "Night at the Opera",
                "Paris Opera House",
                "1958.01.01",
                "Morphy, Paul",
                "Comte Isouard de Vauvenargues and Karl II, Duke of Brunswick",
                "1-0"
        );
        int count = 0;
        for (String game: fileList) {
            if (count < chessGames.size()) {
                found = chessGames.get(count);


                woop = splitMoves(game);
                for (int a = 0; a < woop.length; a++) {
                    if (a == woop.length - 1
                            && Character.isDigit(woop[a - 1].charAt(0))) {
                        found.addMove(woop[a]);
                    }

                    if ((!(Character.isDigit(woop[a].charAt(0)))
                            && a != woop.length - 1)
                        || (woop[a].contains("-"))) {
                        found.addMove(woop[a] + " "
                                + woop[a + 1]);
                        a++;
                    }
                }
            }
            count++;
        }

//
//        String[] fega = splitMoves(fileList.get(0));
//        String[] fools = splitMoves(fileList.get(1));
//        String[] french = splitMoves(fileList.get(2));
//        String[] morph = splitMoves(fileList.get(3));
//        String[] giouco = splitMoves(fileList.get(4));
//        // fegatello
//        for (int a = 0; a < fega.length; a++) {
//            if (a == fega.length - 1
//                && Character.isDigit(fega[a - 1].charAt(0))) {
//                chessGames.get(0).addMove(fega[a]);
//            }
//
//
//            if (!(Character.isDigit(fega[a].charAt(0)))
//                && a != fega.length - 1) {
//                chessGames.get(0).addMove(fega[a] + " "
//                    + fega[a + 1]);
//                a++;
//            }
//        }
//        // fools
//        for (int i = 0; i < fools.length; i++) {
//            if (i == fools.length - 1
//                && Character.isDigit(fools[i - 1].charAt(0))) {
//                chessGames.get(1).addMove(fools[i]);
//            }
//
//
//            if (!(Character.isDigit(fools[i].charAt(0)))
//                && i != fools.length - 1) {
//                chessGames.get(1).addMove(fools[i] + " "
//                    + fools[i + 1]);
//                i++;
//            }
//        }
//        // french
//        for (int b = 0; b < french.length; b++) {
//            if (b == french.length - 1
//                && Character.isDigit(french[b - 1].charAt(0))) {
//                chessGames.get(2).addMove(french[b]);
//            }
//
//
//            if (!(Character.isDigit(french[b].charAt(0)))
//                && b != french.length - 1) {
//                chessGames.get(2).addMove(french[b] + " "
//                    + french[b + 1]);
//                b++;
//            }
//        }
//        // morph
//        for (int c = 0; c < morph.length; c++) {
//            if (c == morph.length - 1
//                && Character.isDigit(morph[c - 1].charAt(0))) {
//                chessGames.get(3).addMove(morph[c]);
//            }
//
//
//            if (!(Character.isDigit(morph[c].charAt(0)))
//                && c != morph.length - 1) {
//                chessGames.get(3).addMove(morph[c] + " "
//                    + morph[c + 1]);
//                c++;
//            }
//        }
//        // giouco
//        for (int d = 0; d < giouco.length; d++) {
//            if (d == giouco.length - 1
//                && Character.isDigit(giouco[d - 1].charAt(0))
//                && !giouco[d].contains(".")) {
//                chessGames.get(4).addMove(giouco[d]);
//            }
//
//
//            if (!(Character.isDigit(giouco[d].charAt(0)))
//                && d != giouco.length - 1
//                && !giouco[d].contains(".")) {
//                chessGames.get(4).addMove(giouco[d] + " "
//                    + giouco[d + 1]);
//                d++;
//            }
//        }
    }


    private static String[] splitMoves(String gamee) {
        int begin = gamee.indexOf("\n1.") + 1;
        String begin2 = gamee.substring(begin);
        String finalString = begin2.replace("\n" , " ");
        String finalString2 = finalString.replaceAll(
                "\\d+\\. |\\d+\\.|\\+|x|=|#|!|\\?|e.p.|\\d-\\d" , "");
        String[] splitString = finalString.split(" ");
        return splitString;
    }


    private Stage window;
    /**
     * Called by the init() method upon running a Java Application
     *
     * @param stage  The primary stage is provided to you by the init() method
     */
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        window = stage;
        loadPgns();


        Button dismiss = new Button();
        dismiss.setText("Dismiss");
        dismiss.setOnAction(e -> closeProgram());
        dismiss.setStyle("-fx-text-fill: #181818");

        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setMaxWidth(200);

        Button view = new Button();
        view.setText("View Game");
        view.setStyle("-fx-text-fill: #181818");



        HBox hbox = new HBox();
        hbox.getChildren().addAll(dismiss, view);


        tableView = createTable();

        view.disableProperty().bind(Bindings.isEmpty(tableView
            .getSelectionModel().getSelectedItems()));
        view.setOnAction(e -> {
                display(tableView
                    .getSelectionModel().getSelectedItem().getEvent());
            });



        FilteredList<ChessGame> filtered =
            new FilteredList<>(getGames(), e -> true);
        searchField.setOnKeyReleased(e -> {
                searchField.textProperty()
                    .addListener((observableValue, oldValue, newValue) -> {
                            filtered
                                .setPredicate((
                                Predicate<? super ChessGame>) game -> {
                                        if (newValue == null
                                            || newValue.isEmpty()) {
                                            return true;
                                        }
                                        String lower = newValue.toLowerCase();
                                        if (game.getEvent()
                                            .contains(newValue)) {
                                            return true;
                                        } else if (game.getEvent().toLowerCase()
                                            .contains(lower)) {
                                            return true;
                                        } else if (game.getSite()
                                                .contains(newValue)
                                                || game.getSite().toLowerCase()
                                                .contains(lower)) {
                                            return true;
                                        } else if (game.getDate()
                                                .contains(newValue)
                                                || game.getDate().toLowerCase()
                                                .contains(lower)) {
                                            return true;
                                        } else if (game.getWhite()
                                                .contains(newValue)
                                                || game.getWhite().toLowerCase()
                                                .contains(lower)) {
                                            return true;
                                        } else if (game.getBlack()
                                                .contains(newValue)
                                                || game.getBlack()
                                                .toLowerCase()
                                                .contains(lower)) {
                                            return true;
                                        } else if (game.getResult()
                                                .contains(newValue)
                                                || game.getResult()
                                                .toLowerCase()
                                                .contains(lower)) {
                                            return true;
                                        } else if (game.getOpening()
                                                .contains(newValue)
                                                || game.getOpening()
                                                .toLowerCase()
                                                .contains(lower)) {
                                            return true;
                                        }
                                        return false;
                                    });
                        });
                SortedList<ChessGame> sortedData = new SortedList<>(filtered);
                sortedData.comparatorProperty()
                    .bind(tableView.comparatorProperty());
                tableView.setItems(sortedData);
            });
        VBox vbox = new VBox();
        vbox.getChildren().addAll(searchField, tableView, hbox);
        Scene scene = new Scene(vbox);
        stage.getIcons().add(new Image(ChessGui.class
            .getResourceAsStream("chess.png")));
        stage.setScene(scene);
        stage.setTitle("ChessDB Gui");
        stage.show();
    }

    private void closeProgram() {
        window.close();
    }
    @SuppressWarnings("unchecked")
    private TableView<ChessGame> createTable() {
        TableColumn<ChessGame, StringProperty> eventColumn =
                new TableColumn<>("Event");
        eventColumn.setMinWidth(200);
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));


        // site
        TableColumn<ChessGame, StringProperty> siteColumn =
                new TableColumn<>("Site");
        siteColumn.setMinWidth(200);
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));

        // white
        TableColumn<ChessGame, StringProperty> whiteColumn =
                new TableColumn<>("White");
        whiteColumn.setMinWidth(200);
        whiteColumn.setCellValueFactory(new PropertyValueFactory<>("white"));

        // black
        TableColumn<ChessGame, StringProperty> blackColumn =
                new TableColumn<>("Black");
        blackColumn.setMinWidth(200);
        blackColumn.setCellValueFactory(new PropertyValueFactory<>("black"));

        // result
        TableColumn<ChessGame, StringProperty> resultColumn =
                new TableColumn<>("Result");
        resultColumn.setMinWidth(200);
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));


        // date
        TableColumn<ChessGame, StringProperty> dateColumn =
                new TableColumn<>("Date");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // opening
        TableColumn<ChessGame, String> openingColumn =
                new TableColumn<>("Opening");
        openingColumn.setMinWidth(200);
        openingColumn.setCellValueFactory(
                new Callback<CellDataFeatures<ChessGame, String>,
                        ObservableValue<String>>() {
                    public ObservableValue<String> call(
                            CellDataFeatures<ChessGame, String> p) {
                        return new ReadOnlyObjectWrapper<String>(
                                p.getValue().getOpening());
                    }
                });



        TableView<ChessGame> table = new TableView<>();
        table.setItems(getGames());
        table.getColumns().addAll(dateColumn,
                siteColumn, eventColumn, resultColumn,
                blackColumn, whiteColumn, openingColumn);
        return table;
    }


    private ObservableList<ChessGame> getGames() {
        ObservableList<ChessGame> game = FXCollections.observableArrayList();
        for (ChessGame a: hm.getGames()) {
            game.add(a);
        }
        for (ChessGame b: chessGames) {
            game.add(b);
        }
        return game;
    }


     /**
      * Displays a new window the the specified title
      * @param title title of the new window
      */
    public void display(String title) {
        String result = "MetaData and Moves for: " + title;
        Stage windowTwo = new Stage();
        windowTwo.initModality(Modality.APPLICATION_MODAL);
        windowTwo.setTitle(result);
        windowTwo.setMinWidth(250);
        ChessGame game = new ChessGame(
            "Night at the Opera",
            "Paris Opera House",
            "1958.01.01",
            "Morphy, Paul",
            "Comte Isouard de Vauvenargues and Karl II, Duke of Brunswick",
            "1-0"
        );

        for (ChessGame wow: getGames()) {
            if (wow.getEvent().equals(title)) {
                game = wow;
            }
        }

        ObservableList<String> moves = FXCollections.observableArrayList();
        ObservableList<Integer> numMoves = FXCollections.observableArrayList();
        int count = 0;
        try {
            for (int i = 0;; i++) {
                game.getMove(count + 1);
                count++;
            }
        } catch (Exception e) {
            int cc = 0;
        }
        int x = 1;
        while (x <= count) {
            moves.add("" + x + ". " + game.getMove(x));
            numMoves.add(x);
            x++;
        }

        ListView<String> listView = new ListView<>(moves);
        Button closeButton = new Button("Dismiss");
        closeButton.setOnAction(e -> windowTwo.close());

        ListView<Integer> intView = new ListView<>(numMoves);


        Label label = new Label();
        String woah = "";
        woah = woah + "Event: " + game.getEvent() + "\n"
            + "Site: " + game.getSite()
            + "\n" + "Date: " + game.getDate() + "\n" + "White: "
            + game.getWhite()
            + "\n" + "Black: " + game.getBlack() + "\n"
            + "Result: " + game.getResult() + "\n"
            + "Opening: " + game.getOpening();
        label.setText(woah);
        HBox gottem = new HBox(10);
        gottem.getChildren().addAll(label);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(listView, gottem, closeButton);
        layout.setAlignment(Pos.CENTER);



        Scene scenes = new Scene(layout);
        windowTwo.getIcons().add(new Image(ChessGui.class
            .getResourceAsStream("chess.png")));
        windowTwo.setScene(scenes);
        windowTwo.showAndWait();

    }

     /**
     * Find the tagName tag pair in a PGN game and return its value.
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
     *
     * @param tagName the name of the tag whose value you want
     * @param game a `String` containing the PGN text of a chess game
     * @return the value in the named tag pair
     */
    public static String tagValue(String tagName, String game) {
        String[] str = game.split("\n");
        /*
        for(int i =0; i < Strgame.length; i++) {
            System.out.printf("%d , %s\n", i, Strgame[i]);
        }
        */



        for (int x = 0; x < str.length; x++) {
            String tagResult = str[x];
            tagResult = tagResult.replace("[", "");
            tagResult = tagResult.replace("]", "");
            tagResult = tagResult.replace("\"", "");

            //String [] name =  tagResult.split("\"+.*\"+");
            String[] name = tagResult.split(" ");
            if (name[0].equals(tagName)) {
                String result = "";
                for (int i = 1; i < name.length; i++) {
                    result += " " + name[i];
                }
                if (result.substring(1, result.length()).isEmpty()) {
                    return "NA";
                }
                return result.substring(1 , result.length());
            }
        }
        return "NA";
    }

    /**
     * Reads the file named by path and returns its content as a String.
     *
     * @param path the relative or abolute path of the file to read
     * @return a String containing the content of the file
     */
    public static String fileContent(String path) {
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Add the \n that's removed by readline()
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }
        return sb.toString();
    }






}