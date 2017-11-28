import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class FindAverageBehaivor extends Behaviour {
    private boolean done = false;
    private final MyAgent agent;
    private boolean hadRequested = false;

    public FindAverageBehaivor(MyAgent agent) {
        this.agent = agent;
    }

    public void action() {
        if (agent.neighbors.size() > 1)
            mergeWithMe();
        else if (agent.neighbors.size() == 1) {
            if (!hadRequested)
                requestMerger();
            end();
        }
    }

    private String packData(int num, double value) {
        return String.valueOf(num) + ":" + String.valueOf(value);
    }

    private String[] unpackData(String data) {
        return data.split(":");
    }

    private void recount(double addValue, int addNumOfFusionAgent) {
        agent.increaseNumOfMergedAgents(addNumOfFusionAgent);
        agent.setValueOfAgent(agent.getValueOfAgent() + addValue);
    }

    private void confirmMerger(ACLMessage msg) {
        ACLMessage reply = msg.createReply();
        reply.setPerformative(ACLMessage.CONFIRM);
        agent.send(reply);
    }

    private void merge(ACLMessage msg) {
        String[] components = unpackData(msg.getContent());
        int addNumOfMergedAgent = Integer.valueOf(components[0]);
        double addValue = Double.valueOf(components[1]);
        recount(addValue, addNumOfMergedAgent);
        agent.deleteNeighborByAID(msg.getSender());
        confirmMerger(msg);
    }

    private void mergeWithMe() {
        MessageTemplate mtRequest = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = agent.receive(mtRequest);
        if (msg != null)
            merge(msg);
        else
            block();
    }

    private boolean isMainAgent(ACLMessage msg) {
        String[] components = unpackData(msg.getContent());
        return agent.getValueOfAgent() > Double.valueOf(components[1]);
    }

    private void sendToCenter() {
        double avrValue = agent.getValueOfAgent() / agent.getNumOfMergedAgents();
        System.out.println(agent.getAID().getLocalName() + ": The average value of agents = " + avrValue);
    }

    private  void requestMerger() {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(agent.neighbors.element());
        request.setContent(packData(agent.getNumOfMergedAgents(), agent.getValueOfAgent()));
        agent.send(request);
        hadRequested = true;
    }

    private  void end() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.CONFIRM) {
                agent.deleteNeighborByAID(msg.getSender());
                done = true;
            } else if (msg.getPerformative() == ACLMessage.REQUEST) {
                if (isMainAgent(msg)) {
                    merge(msg);
                    sendToCenter();
                    done = true;
                }
            }
        } else
            block();
    }

    public boolean done() {
        return done;
    }
}
