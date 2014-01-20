package ca.charland.questions.ui.mainWindow;

import javax.swing.JFrame;

/**
 *  This is the main entry point for the Questions program.
 *  
 * @author Michael
 *
 */
public final class MainWindowFrameBase {

	/**
	 * No constructor needed for this class.
	 */
	private MainWindowFrameBase() {

	}

	/**
	 * This is the main entry point for the Questions program.
	 * @param args No arguments are expected.
	 */
	public static void main(final String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			/**
			 * Runs the frame.
			 */
			public void run() {
				@SuppressWarnings("unused")
				final JFrame jf = new MainWindowFrame();
			}
		});
	}
}
