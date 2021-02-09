package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;

public class SecurityInstaller {

    @FXML
    private TabPane MainTabPane;

    @FXML
    private Tab TabSetUp1;

    @FXML
    private Tab TabSetUp2;

    @FXML
    private Tab TabResult;


    @FXML
    private RadioButton RadioButtonConf1;

    @FXML
    private ToggleGroup ConfList;

    @FXML
    private RadioButton RadioButtonConf2;

    @FXML
    private RadioButton RadioButtonConf3;

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
    private Label LabelResult;

    @FXML
    private Button ButtonBack;

    @FXML
    private Button ButtonNext;


    private int tabNumber;

    private HashMap<RadioButton,Configuration> dataMap = new HashMap<>();

    private Configuration currentConf;

    @FXML
    void initialize(){

        dataMap.put(RadioButtonConf1,new Configuration("NASC_ENTERPRISE","Configuration, \"Best Soft: Manufacturing enterprise management for Azerbaijan\" NASC","12345"));
        dataMap.put(RadioButtonConf2,new Configuration("NASC_ACCOUNTING","Configuration, \"Best Soft: Accounting for Azerbaijan\" NASC","321"));
        dataMap.put(RadioButtonConf3,new Configuration("NASC_ARAUTOMATION","Configuration, \"Best Soft: Complex automation for Azerbaijan\" NASC\n","654"));
        dataMap.put(RadioButtonConf4,new Configuration("TRADE_AZ","Configuration, \"Best Soft: Trade management for Azerbaijan\"","951"));
        dataMap.put(RadioButtonConf5,new Configuration("ENTERPRISEAZ","Configuration, \"Best Soft: Manufacturing enterprise management for Azerbaijan\"","753"));



        SetConfText(RadioButtonConf1);

        SetMainTabVisible(0);

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


        ButtonBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetMainTabVisible(tabNumber-1);
            }
        });

        ButtonNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetMainTabVisible(tabNumber+1);
            }
        });


        ConfList.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (ConfList.getSelectedToggle() != null) {
                    SetConfText((RadioButton) newValue);
                }
            }
        });


    }


    void SetConfText(RadioButton radioButton){
        Configuration currentConf = dataMap.get(radioButton);
        TextAreaConfDescription.setText(currentConf.getConfDescription().toString());

    }

    void SetTabVisible(){
        TypeTables.getTabs().removeAll(TabFile,TabServer);
        if (RadioFile.isSelected()){
            TypeTables.getTabs().add(0,TabFile);
        } else {
            TypeTables.getTabs().add(0,TabServer);
        }

    }


    void SetMainTabVisible(int currtabNumber){
        MainTabPane.getTabs().removeAll(TabSetUp1,TabSetUp2,TabResult);
        tabNumber = currtabNumber;

        if (tabNumber > 2) {
            tabNumber = 2;
        } else if (tabNumber < 0){
            tabNumber = 0;
        }

       switch (tabNumber){
           case 0 : MainTabPane.getTabs().add(0,TabSetUp1);
               break;
           case 1 : MainTabPane.getTabs().add(0,TabSetUp2);
               break;
           case 2 : MainTabPane.getTabs().add(0,TabResult);
               break;
       }



    }






}
