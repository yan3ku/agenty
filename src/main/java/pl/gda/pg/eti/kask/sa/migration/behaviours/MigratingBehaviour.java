package pl.gda.pg.eti.kask.sa.migration.behaviours;

import javax.swing.JOptionPane;

import jade.core.Location;
import jade.core.behaviours.Behaviour;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;
import pl.gda.pg.eti.kask.sa.migration.behaviours.RequestContainersListBehaviour;

/**
 *
 * @author psysiu
 */
public class MigratingBehaviour extends Behaviour {

    protected final MigratingAgent myAgent;

    private boolean done = false;
    
    public MigratingBehaviour(MigratingAgent agent) {
        super(agent);
        myAgent = agent;
    }

    @Override
    public void action() {
        if (!myAgent.here().getID().equals(myAgent.getNextLocation().getID())) {
            JOptionPane.showMessageDialog(null, "Coś jest nie tak!");
            myAgent.setNextLocation(myAgent.here());
            myAgent.getLocations().clear();
        }

        if (myAgent.getLocations().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nowa lista!");
            myAgent.addBehaviour(new RequestContainersListBehaviour(myAgent));
            done = false;
            return;
        }

        Location location = myAgent.getLocations().get(0);
        myAgent.getLocations().remove(location);

        JOptionPane.showMessageDialog(null, "Wybralem " + location.getName());

        myAgent.setNextLocation(location);
        myAgent.doMove(location);
    }

    @Override
    public boolean done() {
        return done;
    }

}
