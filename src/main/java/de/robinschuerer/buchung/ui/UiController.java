package de.robinschuerer.buchung.ui;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.felixroske.jfxsupport.FXMLController;
import de.robinschuerer.buchung.ImportService;
import de.robinschuerer.buchung.LoginService;
import de.robinschuerer.buchung.UploadService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

@FXMLController
public class UiController {

    public TextField textField;
    public PasswordField passwordField;

    public TextArea logOutput;
    public TableView<AccountMovementDto> tableView;
    public TableColumn tagColumn;
    public TableColumn<AccountMovementDto, Boolean> disableColumn;
    public ComboBox<String> importTypeField;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ImportService importService;

    @Autowired
    private UploadService uploadService;
    private String type;

    public void connect(ActionEvent actionEvent) {

        boolean loggedIn = loginService.login(textField.getText(), passwordField.getText());

    }

    @FXML
    public void initialize() {
        OutputStream os = new TextAreaOutputStream(logOutput);

        MyStaticOutputStreamAppender.setStaticOutputStream(os);

        Callback<TableColumn, TableCell> cellFactory = p -> new EditingCell();

        tableView.setOnKeyPressed(event -> {
            TablePosition<AccountMovementDto, ?> pos = tableView.getFocusModel().getFocusedCell() ;
            if (pos != null && event.getCode().isLetterKey()) {
                tableView.edit(pos.getRow(), pos.getTableColumn());
            }
        });

        tagColumn.setCellFactory(cellFactory);
        tagColumn.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<AccountMovementDto, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<AccountMovementDto, String> editEvent) {
                    tableView.getItems().get(
                        editEvent.getTablePosition().getRow()).setTag(editEvent.getNewValue());
                }
            }
        );

        disableColumn.setCellValueFactory(new PropertyValueFactory<>("disabled"));
        disableColumn.setCellFactory(p -> new CheckBoxTableCell<>());

        disableColumn.setCellFactory(col -> {
            CheckBoxTableCell<AccountMovementDto, Boolean> cell = new CheckBoxTableCell<>(index -> {
                BooleanProperty active = new SimpleBooleanProperty(tableView.getItems().get(index)
                    .isDisabled());
                active.addListener((obs, wasActive, isNowActive) -> {
                    AccountMovementDto item = tableView.getItems().get(index);
                    item.setDisabled(isNowActive);
                });
                return active;
            });
            return cell;
        });
        disableColumn.setEditable(true);

        importTypeField.valueProperty().addListener((ov, oldValue, newValue) -> type = newValue);

    }

    public void openFileDialog(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
            "CSV Export-Dateien", "csv"));
        final File file = fileChooser.showOpenDialog(tableView.getScene().getWindow());

        List<AccountMovementDto> data = importService.importData(file, type);
        tableView.getItems().addAll(data);

    }

    public void upload(ActionEvent actionEvent) {
        final ObservableList items = tableView.getItems();
        items.forEach(o -> uploadService.upload((AccountMovementDto) o));
    }

    class EditingCell extends TableCell<AccountMovementDto, String> {

        private TextField textField = new TextField();

        public EditingCell() {
            textField.setText(getItem());

            itemProperty().addListener((obx, oldItem, newItem) -> {
                if (newItem == null) {
                    setText(null);
                } else {
                    setText(newItem);
                }
            });

            setGraphic(textField);
            setContentDisplay(ContentDisplay.TEXT_ONLY);

            textField.setOnAction(evt -> commitEdit(textField.getText()));
            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {

                    commitEdit(textField.getText());
                }
            });
            textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                final KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.ESCAPE) {
                    textField.setText(getItem());
                    cancelEdit();
                    event.consume();
                } else if (keyCode == KeyCode.RIGHT) {
                    getTableView().getSelectionModel().selectRightCell();
                    if(isEditing()){
                        commitEdit(getItem());
                    }
                    event.consume();
                } else if (keyCode == KeyCode.LEFT) {
                    getTableView().getSelectionModel().selectLeftCell();
                    if(isEditing()){
                        commitEdit(getItem());
                    }
                    event.consume();
                } else if (keyCode == KeyCode.UP) {
                    getTableView().getSelectionModel().selectAboveCell();
                    if(isEditing()){
                        commitEdit(getItem());
                    }
                    event.consume();
                } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.TAB || keyCode == KeyCode.ENTER) {
                    if(isEditing()){
                        commitEdit(getItem());
                    }
                    getTableView().getSelectionModel().selectBelowCell();
                    getTableView().edit(getTableView().getSelectionModel().getSelectedIndex(),tagColumn);
                    event.consume();
                }
            });

        }

        @Override
        public void startEdit() {
            super.startEdit();
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
            textField.requestFocus();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void commitEdit(String item) {
            if (!isEditing() && item != null && !item.equals(getItem())) {
                TableView<AccountMovementDto> table = getTableView();
                if (table != null) {
                    TableColumn<AccountMovementDto, String> column = getTableColumn();
                    TableColumn.CellEditEvent<AccountMovementDto, String> event =
                        new TableColumn.CellEditEvent<>(
                            table,
                            new TablePosition<>(table, getIndex(), column),
                            TableColumn.editCommitEvent(), item);
                    Event.fireEvent(column, event);
                }

                updateItem(item, false);
            }

            setContentDisplay(ContentDisplay.TEXT_ONLY);

            super.commitEdit(item);
        }
    }
}
