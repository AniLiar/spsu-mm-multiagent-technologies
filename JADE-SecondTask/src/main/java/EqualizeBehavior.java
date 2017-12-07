import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EqualizeBehavior extends OneShotBehaviour {
    private final double connectionQuality = (double)2 / 3;
    private final double alpha = 0.1;
    private final int maxIteration = 150;
    private final MyAgent agent;
    private Random random = new Random();

    public EqualizeBehavior(MyAgent agent) {
        this.agent = agent;
    }

    private double getNoise() {
        return random.nextDouble() - 0.5;
    }

    private double simulateSignalWithNoise() {
        return agent.getValueOfAgent() + getNoise();
    }

    private void synchronize(long value) {
        try {
            TimeUnit.MILLISECONDS.sleep(value);
        } catch (InterruptedException e) {
        }
    }

    private double calculateControlProtocol(double valueOfNeighbor) {
        return alpha * (valueOfNeighbor - agent.getValueOfAgent());
    }

    private void recalculateMyValue(double control) {
        agent.setValueOfAgent(agent.getValueOfAgent() + control);
    }

    private void initSend() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for (int i = 0; i < agent.neighbors.length; i++)
            msg.addReceiver(agent.neighbors[i]);
        msg.setContent(String.valueOf(simulateSignalWithNoise()));
        agent.send(msg);
    }

    private void mysend() {
        boolean isEmptyListOfReceiver = true;
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for (int i = 0; i < agent.neighbors.length; i++) {
            if (random.nextDouble() > connectionQuality) {
                msg.addReceiver(agent.neighbors[i]);
                isEmptyListOfReceiver = false;
            }
        }
        if (!isEmptyListOfReceiver) {
            msg.setContent(String.valueOf(simulateSignalWithNoise()));
            agent.send(msg);
        }
    }

    private void myreceive() {
        while (agent.getCurQueueSize() != 0) {
            MessageTemplate mtInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = agent.receive(mtInform);
            double valueOfNeighbor = Double.valueOf(msg.getContent());
            recalculateMyValue(calculateControlProtocol(valueOfNeighbor));
        }
    }

    public void action() {
        initSend();
        synchronize(2000);
        for (int i = 0; i < maxIteration; i++) {
            myreceive();
            mysend();
            synchronize(100);
        }
        System.out.println(agent.getAID().getLocalName() + ": My value is " + agent.getValueOfAgent());
    }
}
