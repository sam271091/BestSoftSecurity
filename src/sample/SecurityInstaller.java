package sample;

import com.sun.jna.platform.win32.Advapi32Util;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
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
    private TextField TextFieldInfobase;



    @FXML
    private Label LabelResult;

    @FXML
    private Button ButtonBack;

    @FXML
    private Button ButtonNext;


    private int tabNumber;

    private HashMap<RadioButton,Configuration> dataMap = new HashMap<>();

    private Configuration currentConf;

    private Stage stage;

    private String sn;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void initialize(){

        dataMap.put(RadioButtonConf1,new Configuration("NASC_ENTERPRISE","Configuration, \"Best Soft: Manufacturing enterprise management for Azerbaijan\" NASC","12345"));
        dataMap.put(RadioButtonConf2,new Configuration("NASC_ACCOUNTING","Configuration, \"Best Soft: Accounting for Azerbaijan\" NASC","321"));
        dataMap.put(RadioButtonConf3,new Configuration("NASC_ARAUTOMATION","Configuration, \"Best Soft: Complex automation for Azerbaijan\" NASC\n","654"));
        dataMap.put(RadioButtonConf4,new Configuration("TRADE_AZ","Configuration, \"Best Soft: Trade management for Azerbaijan\"","951"));
        dataMap.put(RadioButtonConf5,new Configuration("ENTERPRISEAZ","Configuration, \"Best Soft: Manufacturing enterprise management for Azerbaijan\"","753"));



        sn = DiskUtils.getSerialNumber("C");

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


        ButtonOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);

                TextFieldPath.setText(f.getSelectedFile().toString());

            }
        });


    }


    void MakeText(String msg){
        int toastMsgTime = 3500; //1.5 seconds
        int fadeInTime = 500; //0.5 seconds
        int fadeOutTime= 500; //0.5 seconds
        Toast.makeText(stage, msg, toastMsgTime, fadeInTime, fadeOutTime);
    }

    void SetConfText(RadioButton radioButton){
        currentConf = dataMap.get(radioButton);
        TextAreaConfDescription.setText(currentConf.getConfDescription().toString());
        TextAreaConfDescription.setWrapText(true);


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
        } if (tabNumber == 2 && RadioFile.isSelected()){
             if (TextFieldPath.getText().equals("")){
                 String toastMsg = "Fill in the path to the database!";
                 MakeText(toastMsg);

                 tabNumber = 1;
             } else {
                 createKeyOnMachine();
             }

        } else if (tabNumber ==2 && RadioServer.isSelected()){
            if (TextFieldSrvCluster.getText().equals("") || TextFieldInfobase.getText().equals("")){
                String toastMsg = "Fill in the server data!";
                MakeText(toastMsg);

                tabNumber = 1;
            } else {
                createKeyOnMachine();
            }
        }

       switch (tabNumber){
           case 0 : MainTabPane.getTabs().add(0,TabSetUp1);
               ButtonNext.setVisible(true);
               ButtonBack.setVisible(false);
               break;
           case 1 : MainTabPane.getTabs().add(0,TabSetUp2);
               ButtonNext.setVisible(true);
               ButtonBack.setVisible(true);
               break;
           case 2 : MainTabPane.getTabs().add(0,TabResult);
               ButtonBack.setVisible(true);
               break;
       }



    }

    void createKeyOnMachine(){
        if (OSValidator.isIsWindows()) {
            createRegFile();
        }
    }


    void createRegFile(){

        Advapi32Util.registryCreateKey(HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Policies\\Control");

        StringBuilder sb = new StringBuilder();
        sb.append(sn);
        sb.append("-");
        sb.append(currentConf.getConfKey());

        StringBuilder sbKeyName = new StringBuilder();
        sbKeyName.append("Addin");
        sbKeyName.append("_");
        sbKeyName.append(currentConf.getConfName() + 1);



        Advapi32Util.registrySetStringValue
                (HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Policies\\Control", sbKeyName.toString(), sb.toString());

        LabelResult.setText(currentConf.getConfName() + " configuration key has been successfully installed!");
        LabelResult.setWrapText(true);
        ButtonNext.setVisible(false);

    }






}
