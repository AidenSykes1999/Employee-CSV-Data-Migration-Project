package model;

import java.util.ArrayList;
import java.util.HashSet;


public class EmployeeCsvDataValidator extends AbstractDataValidator implements CSVTool{
    private HashSet<String> existingIds;

    public EmployeeCsvDataValidator(String[][] data) {
        super(data);
    }

    public EmployeeCsvDataValidator(){
        super();
    }

    /**
     * This seperates the Corrupted Data from the valid data*/
    @Override
    public void splitData() {
        super.splitData();
        this.existingIds = new HashSet<>();
        String[][] data = this.getData();
        for(int i = 0; i < getData().length; i++){
            String[] row = data[i];
            if(isEmployeeRowCorrupt(row, this.existingIds)){
                this.corruptedData.add(data[i]);
            }else {
                this.existingIds.add(row[0]);
                this.validData.add(row);
            }
        }
    }

    /**
     * This is the method that determines if a row (The data for a single employee) in the String[][] 2D array is corrupted or valid*/
    private boolean isEmployeeRowCorrupt(String[] row, HashSet<String> existingIds){

        if(row.length != 10) return true;


        String id = row[0];
        String middleInitial = row[3];
        String gender = row[5];
        String salary = row[9];

        // If ID isn't numeric, add to Corrupt Array file.
        if(id.matches("[a-zA-Z]+")){
            return true;
        }
        if(existingIds.contains(id)){
            return true;
        }

        //   If Middle initial not 1 character long
        if(!(middleInitial.length() == 1)){
            return true;
        }
        //   If Gender not in M, F
        if(!gender.matches(("^M$|^F$"))){
            return true;
        }
        //   If Salary includes anything but numbers
        if(salary.matches("[a-zA-Z]+")){
            return true;
        }

        // if name prefix not in enum?

        // If date of birth/date of join don't adhere to date format

        return false;
    }

    /**
     * Determines if a record is duplicated and separates it from unique records
     */
    public void setUniqueAndDuplicate(){
        this.uniqueData = new ArrayList<>();
        this.duplicatedData = new ArrayList<>();
        String[][] data = this.getData();
        HashSet<String> uniqueData = new HashSet<>();
        for(int i = 0; i < data.length; i++){
            String[] row = data[i];
            if(uniqueData.contains(row[0])){
                this.duplicatedData.add(row);
            }
            else {
                this.uniqueData.add(row);
                uniqueData.add(row[0]);
            }
        }
    }

    /**
     * Checks records for missing values and adds them to an array list
     */
    public void setMissingValuesData(){
        this.missingValuesData = new ArrayList<>();
        String[][] data = this.getData();
        for(int i = 0; i < data.length; i++){
            String[] row = data[i];
            for(int j = 0; j < row.length; j++){
                if (row[j] == ""){
                    this.missingValuesData.add(row);
                }
            }
        }
    }

    @Override
    public String validate() {
        return "";
    }
}
