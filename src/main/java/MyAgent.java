import jade.core.AID;
import jade.core.Agent;

import java.util.Iterator;
import java.util.LinkedList;

public class MyAgent extends Agent {

    private int numOfMergedAgents;
    private double valueOfAgent;
    public LinkedList<AID> neighbors;

    public int getNumOfMergedAgents() {
        return numOfMergedAgents;
    }

    public void increaseNumOfMergedAgents(int value) {
        numOfMergedAgents += value;
    }

    public void deleteNeighborByAID(AID neighbor) {
        neighbors.remove(neighbor);
    }

    public double getValueOfAgent() {
        return valueOfAgent;
    }

    public void setValueOfAgent(double value) {
        valueOfAgent = value;
    }

    protected void setup() {
        initAgent();
        addBehaviour(new FindAverageBehaivor(this));
    }

    private void initNeighbors(Object[] args) {
        neighbors = new LinkedList<AID>();
        for (int i = 0; i < args.length - 1; i++)
            neighbors.add(new AID((String) args[i + 1], AID.ISLOCALNAME));
    }

    private void initAgent() {
        Object[] args = getArguments();
        initNeighbors(args);
        numOfMergedAgents = 1;
        valueOfAgent = Double.valueOf((String) args[0]);
        String listOfNeighbors = "My neighbor is ";
        Iterator<AID> iterator = neighbors.iterator();
        while(iterator.hasNext()){
            listOfNeighbors +=iterator.next().getLocalName() + ", ";
        }
        System.out.println("Hello, my dear friend! I'm " + getAID().getLocalName() + "." + listOfNeighbors +"my value is " + valueOfAgent);
    }

    protected void takeDown() {
        System.out.println(getAID().getLocalName() + " termimating.");
    }
}