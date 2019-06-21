package edu.byui.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Report handles the "so what?" portion of budgeting. After all the work that's
 * put into it, what does the user get out of it? These should be made obvious
 * in the Report module.
 *
 * In order to handling different views of the data, the Report class takes a
 * ReportInstruction object as a constructor parameter. The Report Instructor/Instruction
 * will inject the report instructions into a Report object. These instructions will need to be
 * validated first through validateInstructions().
 *
 */
@Entity
public class Report {

    //giving it an id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    // the name of the report.
    private String name;

    // reference to the reportInstruction. Does this need to be a WeakReference?
    // @ColumnInfo(name = "reportInstructions") // leaving this out until I can write a converter. MT.
    @Ignore
    private Instruction instructions;

    @Ignore
    Report(Instruction instruction) {
        this.instructions = instruction;
        this.name = instruction.getReportName();
        // something's up with this design to have the name pulled out like that, I'm thinking.
    }
    Report(String name){
        instructions = null;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Instruction getInstructions() {
        return instructions;
    }

    public void setInstructions(Instruction instructions) {
        this.instructions = instructions;
    }

}
