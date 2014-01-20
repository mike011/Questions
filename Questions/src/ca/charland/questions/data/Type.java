package ca.charland.questions.data;

/**
 * These are the types of questions.
 * 
 * @Note: When question types are added this will have to be changed.
 * @author Michael
 */
public enum Type {

	/**
	 * A check box question.
	 */
	CheckBox,

	/**
	 * A radio button question.
	 */
	RadioButton,

	/**
	 * A blank answer question.
	 */
	BlankAnswer,

	/**
	 * A fill in the blanks question.
	 */
	FillInTheBlanks,

	/**
	 * A short cut. Will mostly be used for eclipse shorts cuts to start.
	 */
	ShortCut;
}
