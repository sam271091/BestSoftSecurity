package sample;


import java.util.HashMap;
import java.util.Scanner;

public class SecurityInstallerNoGUI {
    private HashMap<Integer,Configuration> dataMap = new HashMap<Integer, Configuration>();
    private String sn;

    private Integer selectedConf;

    private Configuration currentConfig;

    public void initialize(){
        dataMap.put(0,new Configuration("NASC_ENTERPRISE","Configuration, \"Best Soft: Manufacturing enterprise management for Azerbaijan\" NASC","825847393"));
        dataMap.put(1,new Configuration("NASC_ACCOUNTING","Configuration, \"Best Soft: Accounting for Azerbaijan\" NASC","825847856"));
        dataMap.put(2,new Configuration("NASC_ARAUTOMATION","Configuration, \"Best Soft: Complex automation for Azerbaijan\" NASC\n","825847417"));
        dataMap.put(3,new Configuration("TRADE_AZ","Configuration, \"Best Soft: Trade management for Azerbaijan\"","1953653114"));
        dataMap.put(4,new Configuration("ENTERPRISEAZ","Configuration, \"Best Soft: Manufacturing enterprise management for Azerbaijan\"","1701724538"));

        //ERP 2.4
        dataMap.put(5,new Configuration("ERP_2_4","Configuration, \"Best Soft: ERP 2.4\"","6256223652"));
        //

        if (OSValidator.isIsWindows())
            sn = DiskUtils.getSerialNumber_Windows("C");
        else if (OSValidator.isIsUnix()){
            sn = DiskUtils.getSerialNumber_Linux();
        }

        System.out.println("Select configuration:");

        for (int i = 0;i < dataMap.size();i++) {
             Configuration config = dataMap.get(i);
            System.out.println("Type: " + i + " for " + config.getConfName() + " : " + config.getConfDescription());
        }

    }

    String getResponseFromUser(Scanner in){

        return in.nextLine();

    }

    public void startInstallation(){

        Scanner in = new Scanner(System.in);

        String s = getResponseFromUser(in);

        currentConfig = dataMap.get(Integer.parseInt(s));


        System.out.println("Your selection: " + currentConfig.getConfName());

        System.out.println("Proceed installation? Y/N");

        String proceed = getResponseFromUser(in);

        if (proceed.toUpperCase().equals("Y")){

            if (OSValidator.isIsUnix()){
                System.out.println(DiskUtils.createTempFileLinux(sn,currentConfig));
            }

        } else {
            startInstallation();
        }


    }

}
