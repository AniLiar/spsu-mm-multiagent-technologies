# spsu-mm-multiagent-technologies
My homework for study course on multi-agent technologies.<br/>
My multi-agent system finds average of agents values.</br>

<b>Conditions of the task:</b>
1) There is signal noise</br>
2) There are communication delays</br>
3) There is a possibility of a communication failure</br>
<b>jade-myconfiguration:</b><br/>
Main class: jade.Boot
Program arguments:<br/>
-gui
-local-port
9999
Agent1:MyAgent(10,Agent2,Agent3,Agent4);Agent2:MyAgent(4,Agent1,Agent3,Agent5);Agent3:MyAgent(6,Agent1,Agent2,Agent4);Agent4:MyAgent(1,Agent1,Agent3,Agent5);Agent5:MyAgent(9,Agent2,Agent4);
<br/><br/>
AgentName:AgentClass(valueOfAgent,neighbour1,neighbour2,...)
<br/><br/>
