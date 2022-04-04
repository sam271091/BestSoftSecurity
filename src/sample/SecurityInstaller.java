package sample;

import com.sun.jna.platform.win32.Advapi32Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;



import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

//import com.sun.jna.platform.win32.WinReg;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

//import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Map;

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
    private RadioButton RadioButtonConf6;

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

        //ERP 2.4
        dataMap.put(RadioButtonConf6,new Configuration("ERP_2_4","Configuration, \"Best Soft: ERP 2.4\"","6256223652"));
        //



        if (OSValidator.isIsWindows())
           sn = DiskUtils.getSerialNumber_Windows("C");
        else if (OSValidator.isIsUnix()){
            sn = DiskUtils.getSerialNumber_Linux();
        }
        SetConfText(RadioButtonConf1);

        SetMainTabVisible(0,false);

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
                SetMainTabVisible(tabNumber-1,true);
            }
        });

        ButtonNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetMainTabVisible(tabNumber+1,false);
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


        TextAreaConfDescription.setText(sn);
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


    void SetMainTabVisible(int currtabNumber,boolean backPressed){
        MainTabPane.getTabs().removeAll(TabSetUp1,TabSetUp2,TabResult);
        tabNumber = currtabNumber;

        if (tabNumber > 2) {
            tabNumber = 2;
        } else if (tabNumber < 0){
            tabNumber = 0;
        }

          if (tabNumber == 2 && RadioFile.isSelected()){
             if (TextFieldPath.getText().equals("")){
                 String toastMsg = "Fill in the path to the database!";
                 MakeText(toastMsg);

                 tabNumber = 1;
             } else {
                 createKeyOnMachine();
             }

        } else if (RadioButtonConf6.isSelected()){
            if (!backPressed) {
                createKeyOnMachine();
                tabNumber = 2;
            } else {
                tabNumber = 0;
            }
        }

          else if (tabNumber ==2 && RadioServer.isSelected()){
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
//               ButtonNext.setVisible(true);
//               ButtonBack.setVisible(false);
               ButtonNext.setDisable(false);
               ButtonBack.setDisable(true);
               break;
           case 1 : MainTabPane.getTabs().add(0,TabSetUp2);
               ButtonNext.setDisable(false);
               ButtonBack.setDisable(false);
               break;
           case 2 : MainTabPane.getTabs().add(0,TabResult);
               ButtonNext.setDisable(true);
               ButtonBack.setDisable(false);
               break;
       }



    }

    void createKeyOnMachine(){
        if (OSValidator.isIsWindows()) {
            createRegFile();
        } else if (OSValidator.isIsUnix()){
            createTempFile();
        }
    }


    void createTempFile(){

        long confKey = Long.parseLong(currentConf.getConfKey());

        StringBuilder sb = new StringBuilder();
//        sb.append(decToHex(Integer.parseInt(sn)));
        sb.append(sn.toString());
        sb.append("-");
        sb.append(decToHex(confKey));

        StringBuilder sbKeyName = new StringBuilder();
        sbKeyName.append("Addin");
        sbKeyName.append("_");
        sbKeyName.append(currentConf.getConfName() + 1);






        try {

            String tempDir = System.getProperty("java.io.tmpdir");



            String keyPath = String.format("%s/Addin",tempDir);

            File newFile = new File(keyPath);

            BufferedWriter output = new BufferedWriter(new FileWriter(newFile));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(sbKeyName.toString(),sb.toString());

            output.write(jsonObject.toString());
            output.close();




            LabelResult.setText(currentConf.getConfName() + " configuration key has been successfully installed!");


        } catch (IOException e) {
            e.printStackTrace();
            LabelResult.setText(e.toString());
        }



        LabelResult.setWrapText(true);
//        ButtonNext.setVisible(false);
        ButtonNext.setDisable(true);
    }







    void createRegFile(){

        Advapi32Util.registryCreateKey(HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Policies\\Control");

        try {
//            WinRegistry.createKey(WinRegistry.HKEY_LOCAL_MACHINE,"SYSTEM\\CurrentControlSet\\Policies\\Control");

//            WinReg reg = new WinReg();
//            reg.addKey(WinReg.WRKey.HKLM,"SYSTEM\\CurrentControlSet\\Policies\\Control");






            long confKey = Long.parseLong(currentConf.getConfKey());

            StringBuilder sb = new StringBuilder();
            sb.append(decToHex(Integer.parseInt(sn)));
            sb.append("-");
            sb.append(decToHex(confKey));

            StringBuilder sbKeyName = new StringBuilder();
            sbKeyName.append("Addin");
            sbKeyName.append("_");
            sbKeyName.append(currentConf.getConfName() + 1);



            Advapi32Util.registrySetStringValue
                    (HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Policies\\Control", sbKeyName.toString(), sb.toString());


//            WinRegistry.writeStringValue(WinRegistry.HKEY_LOCAL_MACHINE,"SYSTEM\\CurrentControlSet\\Policies\\Control",sbKeyName.toString(),sb.toString());

//            reg.addValue(WinReg.WRKey.HKLM,"SYSTEM\\CurrentControlSet\\Policies\\Control",sbKeyName.toString(),sb.toString().getBytes(), WinReg.WRType.REG_SZ);

//            reg.createRegString(WinReg.WRKey.HKLM,"SYSTEM\\CurrentControlSet\\Policies\\Control",sbKeyName.toString(),sb.toString().getBytes(), WinReg.WRType.REG_SZ,false);

            LabelResult.setText(currentConf.getConfName() + " configuration key has been successfully installed!");


//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            LabelResult.setText(e.toString());
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//            LabelResult.setText(e.toString());
        } catch (Exception e) {
            LabelResult.setText(e.toString());
        }



        LabelResult.setWrapText(true);
//        ButtonNext.setVisible(false);
        ButtonNext.setDisable(true);

    }


    String decToHex(long inputDigit){

        inputDigit = Long.max(inputDigit,-inputDigit);

        int base = 16;

        String result = "";

        String hexSymbols = "0123456789ABCDEF";

        while (inputDigit != 0) {
            Long pos = (inputDigit % base);

//            int pos = Long.

            result = hexSymbols.substring(pos.intValue(),pos.intValue()+1) + result;

            inputDigit = inputDigit/base;
        }

        return result;
    }




}
