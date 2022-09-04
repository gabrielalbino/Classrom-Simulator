import javax.swing.JFrame;
import javax.swing.JLabel;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Word From Main");
		jade.core.Runtime rt = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		
		profile.setParameter(Profile.PLATFORM_ID, "Simulador Sala de Aula");
		profile.setParameter(Profile.CONTAINER_NAME, "Sala de aula");
		
		AgentContainer mainContainer = rt.createMainContainer(profile);
		try {
			 AgentController ac = mainContainer.createNewAgent("aluno",
					                      "AlunoAgent",null);
			 AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
			 ac.start();
			 rma.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
