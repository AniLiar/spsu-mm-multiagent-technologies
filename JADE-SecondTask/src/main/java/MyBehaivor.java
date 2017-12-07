import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyBehaivor extends Behaviour {
    private final Random random = new Random();
    private final double connectionQuality = (double)2 / 3;
    private final double alpha = 0.1;
    private final int maxIteration = 100; //потом изменить на 100 или сделать глобальным?
    private int iterator = 0;
    private int step = 0;
    private int msgByNeighbors = 0;
    private final MyAgent agent;

    public MyBehaivor(MyAgent agent) {
        this.agent = agent;
    }

    private double simulateNoise() {
        double noise = random.nextDouble() - 0.5;
        return agent.getValueOfAgent() + noise;
    }

    private String packData(double value) {
        return String.valueOf(value);
    }

    private void synchronize(long value) {
        try {
            TimeUnit.MILLISECONDS.sleep(value);
        } catch (InterruptedException e) {
        }
    }

    private double calculateControlProtocol(double value)
    {
        double control = agent.getValueOfAgent() - value;
        control *= alpha;
        return control;
    }

    private void initSend()
    {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for (int i = 0; i < agent.neighbors.length; i++) {
            msg.addReceiver(agent.neighbors[i]);
        }
        msg.setContent(packData(simulateNoise()));
        agent.send(msg);
    }

    private void recalculateMyValue(double control)
    {
        double newValue = agent.getValueOfAgent() + control;
        agent.setValueOfAgent(newValue);
    }

    private void mysend() {
        boolean isEmptyListOfReceiver = true;
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        for (int i = 0; i < agent.neighbors.length; i++) {
            if (random.nextDouble() > connectionQuality) { //перебои связи //если нет ни одного отправителя?
                msg.addReceiver(agent.neighbors[i]);
                isEmptyListOfReceiver = false;
            }
        }
        if(!isEmptyListOfReceiver) {
            msg.setContent(packData(simulateNoise()));
            agent.send(msg);
        }
    }

    private void myreceive() {
        while (agent.getCurQueueSize() != 0) { //пока очередь не пустая!
            MessageTemplate mtInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = agent.receive(mtInform);
            double valueOfNeighbor = Double.valueOf(msg.getContent());
            recalculateMyValue(calculateControlProtocol(valueOfNeighbor));
        }
    }

    public void action() {
       /* switch (step) {
            case 0:
                initSend();
                synchronize(2000);
                step = 2;
                break;
            case 1:
                mysend();
                step = 2;
                break;
            case 2:
                myreceive();
                step = 3;
                break;
             case 3:
                synchronize(250);
                print();
                iterator++;
        }*/
    }

    public boolean done() {
        System.out.println(agent.getAID().getLocalName() + ":" + iterator + "time");
        return iterator > maxIteration;
    }
}
