# spsu-mm-multiagent-technologies
My homework for study course on multi-agent technologies.<br/>
My multi-agent system finds average of agents values.

<b>jade-myconfiguration:</b><br/>
Main class: jade.Boot
Program arguments:<br/>
-gui
-local-port
9999
Agent1:MyAgent(10,Agent2,Agent3,Agent4,Agent5);Agent2:MyAgent(5,Agent1,Agent6);Agent3:MyAgent(6,Agent1);Agent4:MyAgent(1,Agent1);Agent5:MyAgent(2,Agent1);Agent6:MyAgent(6,Agent2,Agent7,Agent8);Agent7:MyAgent(13,Agent6);Agent8:MyAgent(7,Agent6); <br/><br/>
AgentName:AgentClass(valueOfAgent,neighbour1,neighbour2,...)
<br/><br/>
| memory     | number of messages sent to the center | number of messages sent to each other | number of beats |
|------------|---------------------------------------|---------------------------------------|-----------------|
| O(N) or 2N | 1 (in the worst case, 2)              | 2M                                    | O(M)            |