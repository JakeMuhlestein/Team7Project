package edu.byui.myapplication.model;

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
public class Report {
    // the name of the report.
    String name;
    // reference to the reportInstruction. Does this need to be a WeakReference?
    Instruction instructions;

    Report(Instruction instruction) {
        this.instructions = instruction;
        this.name = instruction.getReportName();
        // something's up with this design to have the name pulled out like that, I'm thinking.
    }
}
