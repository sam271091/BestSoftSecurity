import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SecurityInstaller {

    @FXML
    private Tab TabSetUp2;

    @FXML
    private RadioButton RadioButtonConf1;

    @FXML
    private ToggleGroup ConfList;

    @FXML
    private RadioButton RadioButtonConf2;

    @FXML
    private RadioButton RadioButtonConf4;

    @FXML
    private RadioButton RadioButtonConf5;

    @FXML
    private TextArea TextAreaConfDescription;

    @FXML
    private RadioButton RadioFile;

    @FXML
    private ToggleGroup LocationType;

    @FXML
    private RadioButton RadioServer;

    @FXML
    private TabPane TypeTables;

    @FXML
    private Tab TabFile;

    @FXML
    private TextField TextFieldPath;

    @FXML
    private Button ButtonOpen;

    @FXML
    private Tab TabServer;

    @FXML
    private TextField TextFieldSrvCluster;

    @FXML
    private TextField TextFileInfobase;

    @FXML
    private Tab TabResult;

    @FXML
    private Label LabelResult;

    @FXML
    private Button ButtonBack;

    @FXML
    private Button ButtonNext;

    @FXML
    void initialize(){


        SetTabVisible();

        RadioFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetTabVisible();
            }
        });

        RadioServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetTabVisible();
            }
        });



    }


    void SetTabVisible(){
        TypeTables.getTabs().removeAll(TabFile,TabServer);
        if (RadioFile.isSelected()){
            TypeTables.getTabs().add(0,TabFile);
        } else {
            TypeTables.getTabs().add(0,TabServer);
        }

    }


}
