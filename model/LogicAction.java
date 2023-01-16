/**
 * Interface:
 * 		LogicAction
 * Description:
 * 		Represents anything that can execute an action based on logical inputs
 * Areas of Concern:
 * 		None
 */

package model;

public interface LogicAction {

    // executes an operation based on inputted logical inputs
    public boolean execute(boolean[] in);
    
}