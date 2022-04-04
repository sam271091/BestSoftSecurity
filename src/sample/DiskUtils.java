package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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



    public static String getSerialNumber_Linux() {
//        String result = "";
//        try {
//            File file = File.createTempFile("realhowto", ".vbs");
//            file.deleteOnExit();
//            FileWriter fw = new java.io.FileWriter(file);
//
//            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
//                    + "Set colDrives = objFSO.Drives\n"
//                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
//                    + "Wscript.Echo objDrive.SerialNumber";  // see note
//            fw.write(vbs);
//            fw.close();
//            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
//            BufferedReader input =
//                    new BufferedReader
//                            (new InputStreamReader(p.getInputStream()));
//            String line;
//            while ((line = input.readLine()) != null) {
//                result += line;
//            }
//            input.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result.trim();


        // command to be executed on the terminal
        String command
//                = "sudo dmidecode -s baseboard-serial-number";
                = "udevadm info --query=all --name=/dev/sda | grep ID_SERIAL_SHORT";

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

                if(line.toLowerCase().contains("id_serial_short")){
                    String[] strings = line.split("=");
                    serialNumber = strings[strings.length-1];
                }

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
}

