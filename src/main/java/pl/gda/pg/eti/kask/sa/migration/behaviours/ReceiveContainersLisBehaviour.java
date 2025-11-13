package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Result;
import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

import javax.swing.*;

/**
 *
 * @author psysiu
 */
public class ReceiveContainersLisBehaviour extends Behaviour {

    private boolean done = false;

    protected final MigratingAgent myAgent;

    protected final String conversationId;

    protected MessageTemplate mt;

    public ReceiveContainersLisBehaviour(MigratingAgent agent, String conversationId) {
        super(agent);
        myAgent = agent;
        this.conversationId = conversationId;
    }

    @Override
    public void onStart() {
        super.onStart();
        mt = MessageTemplate.MatchConversationId(conversationId);
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            done = true;
            try {
                JOptionPane.showMessageDialog(null, "merging ");
                ContentElement ce = myAgent.getContentManager().extractContent(msg);
                jade.util.leap.List items = ((Result) ce).getItems();
                List<Location> newLocations = new ArrayList<>();
                items.iterator().forEachRemaining(i -> {
                    newLocations.add((Location) i);
                });
                newLocations.remove(myAgent.here());

                List<Location> currentLocations = myAgent.getLocations();
                if (currentLocations == null || currentLocations.isEmpty()) {
                    myAgent.setLocations(newLocations);
                    myAgent.addBehaviour(new MigratingBehaviour(myAgent));
                    return;
                }
                currentLocations.retainAll(newLocations);
                myAgent.setLocations(currentLocations);
                myAgent.addBehaviour(new MigratingBehaviour(myAgent));
            } catch (Codec.CodecException | OntologyException ex) {
                JOptionPane.showMessageDialog(null, "Error while executing action: " + ex.getMessage());
            }
        }
    }

    @Override
    public boolean done() {
        return done;
    }

}
