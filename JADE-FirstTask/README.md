# spsu-mm-multiagent-technologies
My homework for study course on multi-agent technologies.<br/>
My multi-agent system finds average of agents values.
<b>NOTE:</b>The graph for the algorithm is an acyclic graph.<br/>

<b>jade-myconfiguration:</b><br/>
Main class: jade.Boot
Program arguments:<br/>
-gui
-local-port
9999
Agent1:MyAgent(10,Agent2,Agent3,Agent4,Agent5);Agent2:MyAgent(5,Agent1,Agent6);Agent3:MyAgent(6,Agent1);Agent4:MyAgent(1,Agent1);Agent5:MyAgent(2,Agent1);Agent6:MyAgent(6,Agent2,Agent7,Agent8);Agent7:MyAgent(13,Agent6);Agent8:MyAgent(7,Agent6); <br/><br/>
AgentName:AgentClass(valueOfAgent,neighbour1,neighbour2,...)
<br/><br/>
<b>Algorithm's properties:</b><br/>
<table class="tg">
  <tr>
    <th class="tg-031e">memory<br></th>
    <th class="tg-031e">number of messages sent to the center</th>
    <th class="tg-031e">number of messages sent to each other</th>
    <th class="tg-031e">number of beats</th>
  </tr>
  <tr>
    <td class="tg-031e">O(N) or 2N<br></td>
    <td class="tg-031e">1 (in the worst case, 2)</td>
    <td class="tg-031e">2M</td>
    <td class="tg-031e">O(M)</td>
  </tr>
</table>
where 
	-M - number of links,
	-N - number of agents