package sample;

import org.json.JSONObject;

import java.io.*;

public class DiskUtils {
    private DiskUtils() {
    }

    public static String getSerialNumber_Windows(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }


    public static void showAvailableDevs(){
        String command
                = "ls -lha /dev/disk/by-uuid";

        try {

            // declaring the process to run the command
            Process DevsProcess
                    = Runtime.getRuntime().exec(command);

            // getting the input stream using
            InputStreamReader ISR = new InputStreamReader(
                    DevsProcess.getInputStream());

            // declaring the Buffered Reader
            BufferedReader br = new BufferedReader(ISR);



            String line;
            while ((line = br.readLine()) != null) {

//                if(line.toLowerCase().contains("id_serial_short")){
//                    String[] strings = line.split("=");
//                    serialNumber = strings[strings.length-1];
//                }

                System.out.println(line);

            }


            // waiting for the system to return
            // the serial number
            DevsProcess.waitFor();

            // closing the Buffered Reader
            br.close();
        }

        // catch block
        catch (Exception e) {

            // printing the exception
            e.printStackTrace();
        }
    }



    public static String getSerialNumber_Linux(String selectedDisk) {


        // command to be executed on the terminal
        String command
                = String.format("blkid -s UUID -o value /dev/%s",selectedDisk);
//        "udevadm info --query=all --name=/dev/sda | grep ID_SERIAL_SHORT";

        // variable to store the Serial Number
        String serialNumber = null;

        // try block
        try {

            // declaring the process to run the command
            Process SerialNumberProcess
                    = Runtime.getRuntime().exec(command);

            // getting the input stream using
            // InputStreamReader using Serial Number Process
            InputStreamReader ISR = new InputStreamReader(
                    SerialNumberProcess.getInputStream());

            // declaring the Buffered Reader
            BufferedReader br = new BufferedReader(ISR);

            // reading the serial number using
            // Buffered Reader
//            serialNumber = br.readLine().trim();



            String line;
            while ((line = br.readLine()) != null) {
//                serialNumber += line;

//                if(line.toLowerCase().contains("id_serial_short")){
//                    String[] strings = line.split("=");
//                    serialNumber = strings[strings.length-1];
//                }

                serialNumber = line;

            }


            // waiting for the system to return
            // the serial number
            SerialNumberProcess.waitFor();

            // closing the Buffered Reader
            br.close();
        }

        // catch block
        catch (Exception e) {

            // printing the exception
            e.printStackTrace();

            // giving the serial number the value null
            serialNumber = null;
        }

        // returning the serial number
        return serialNumber;



    }




    public static String createTempFileLinux(String sn,Configuration currentConf,String selectedDisk){
        long confKey = Long.parseLong(currentConf.getConfKey());

        StringBuilder sb = new StringBuilder();
//        sb.append(decToHex(Integer.parseInt(sn)));
        sb.append(sn.toString().replace("-",""));
        sb.append("-");
        sb.append(decToHex(confKey));

        StringBuilder sbKeyName = new StringBuilder();
        sbKeyName.append("Addin");
        sbKeyName.append("_");
        sbKeyName.append(currentConf.getConfName());

        try {

            String tempDir = System.getProperty("java.io.tmpdir");



            String keyPath = String.format("%s/Addin_ERP_2_4",tempDir);

            File newFile = new File(keyPath);

            BufferedWriter output = new BufferedWriter(new FileWriter(newFile));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(sbKeyName.toString(),sb.toString());
            jsonObject.put("diskName",selectedDisk);


            output.write(jsonObject.toString());
            output.close();

            return currentConf.getConfName() + " configuration key has been successfully installed!";





        } catch (IOException e) {
            e.printStackTrace();

            return e.toString();
        }

    }


    public static String decToHex(long inputDigit){

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




