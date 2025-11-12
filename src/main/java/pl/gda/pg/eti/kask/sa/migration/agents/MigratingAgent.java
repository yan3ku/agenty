package pl.gda.pg.eti.kask.sa.migration.agents;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.mobility.MobilityOntology;
import java.util.List;
import javax.swing.JOptionPane;
import pl.gda.pg.eti.kask.sa.migration.behaviours.RequestContainersListBehaviour;

/**
 * MigratingAgent class responsible for handling the agent's behavior during migration.
 *
 * @author psysiu
 */
public class MigratingAgent extends Agent {

    private List<Location> locations;
    private Location nextLocation;

    public MigratingAgent() {
    }

    // Getter for locations
    public List<Location> getLocations() {
        return locations;
    }

    // Setter for locations
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    // Getter for nextLocation
    public Location getNextLocation() {
        return nextLocation;
    }

    // Setter for nextLocation
    public void setNextLocation(Location nextLocation) {
        this.nextLocation = nextLocation;
    }

    @Override
    protected void setup() {
        super.setup();
        nextLocation = here();
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());
        this.addBehaviour(new RequestContainersListBehaviour(this));
    }

    @Override
    protected void afterMove() {
        super.afterMove();
        //restore state
        //resume threads
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());

        JOptionPane.showMessageDialog(null, "Przybywam!");
    }

    @Override
    protected void beforeMove() {
        JOptionPane.showMessageDialog(null, "Odchodzę do " + nextLocation.getName());
        //stop threads
        //save state
        super.beforeMove();
    }

}
