import jade.core.AID;
import jade.core.Agent;

public class MyAgent extends Agent {
    private int numOfMergedAgents;
    private double valueOfAgent;
    public AID[] neighbors;

    public double getValueOfAgent() {
        return valueOfAgent;
    }

    public void setValueOfAgent(double value) {
        valueOfAgent = value;
    }


    protected void setup() {
        initAgent();
        addBehaviour(new EqualizeBehavior(this));
    }

    private void initNeighbors(Object[] args) {
        neighbors = new AID[args.length - 1];
        for (int i = 0; i < neighbors.length; i++)
            neighbors[i] = new AID((String)args[i + 1], AID.ISLOCALNAME);
    }

    private void initAgent() {
        Object[] args = getArguments();
        initNeighbors(args);
        numOfMergedAgents = 1;
        valueOfAgent = Double.valueOf((String)args[0]);
        String listOfNeighbors = "My neighbor is ";
       for (int i = 0; i < neighbors.length; i++){
            listOfNeighbors += neighbors[i].getLocalName() + ", ";
        }
        System.out.println("Hello, my dear friend! I'm " + getAID().getLocalName() + "." + listOfNeighbors +"my value is " + valueOfAgent);
    }

    protected void takeDown() {
        System.out.println(getAID().getLocalName() + " termimating.");
    }
}
