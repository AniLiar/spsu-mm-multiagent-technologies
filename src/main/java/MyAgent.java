import jade.core.Agent;

public class MyAgent extends Agent{

    protected void setup() {
        System.out.println("Hello, my dear friend! I'm " + getAID().getLocalName());
    }
}
