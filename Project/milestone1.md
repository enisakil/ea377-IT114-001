<table><tr><td> <em>Assignment: </em> It114 Milestone1</td></tr>
<tr><td> <em>Student: </em> Enis Akil (ea377)</td></tr>
<tr><td> <em>Generated: </em> 10/25/2023 11:36:50 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-001-F23/it114-milestone1/grade/ea377" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <ol><li>Create a new branch called Milestone1</li><li>At the root of your repository create a folder called Project if one doesn't exist yet</li><ol><li>You will be updating this folder with new code as you do milestones</li><li>You won't be creating separate folders for milestones; milestones are just branches</li></ol><li>Create a milestone1.md file inside the Project folder</li><li>Git add/commit/push it to Github (yes it'll be blank for now)</li><li>Create a pull request from Milestone1 to main (don't complete/merge it yet, just have it in open status)</li><li>Copy in the latest Socket sample code from the most recent Socket Part example of the lessons</li><ol><li>Recommended Part 5 (clients should be having names at this point and not ids)</li><li><a href="https://github.com/MattToegel/IT114/tree/Module5/Module5">https://github.com/MattToegel/IT114/tree/Module5/Module5</a>&nbsp;<br></li></ol><li>Fix the package references at the top of each file (these are the only edits you should do at this point)</li><li>Git add/commit the baseline</li><li>Ensure the sample is working and fill in the below deliverables</li><ol><li>Note: The client commands likely are different in part 5 with the /name and /connect options instead of just connect</li></ol><li>Get the markdown content or the file and paste it into the milestone1.md file or replace the file with the downloaded version</li><li>Git add/commit/push all changes</li><li>Complete the pull request merge from step 5</li><li>Locally checkout main</li><li>git pull origin main</li></ol></td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Startup </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot showing your server being started and running</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-10-26T03.00.36Screenshot%202023-10-25%20at%2010.44.26%20PM.png.webp?alt=media&token=ea4afcd7-9403-4077-a6da-661b8fba6415"/></td></tr>
<tr><td> <em>Caption:</em> <p>Server running and listening<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot showing your client being started and running</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-10-26T03.03.00Screenshot%202023-10-25%20at%2011.02.21%20PM.png.webp?alt=media&token=4e786a5a-9e85-4a65-8580-cd81c5f71c23"/></td></tr>
<tr><td> <em>Caption:</em> <p>Shows client connected<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the connection process</td></tr>
<tr><td> <em>Response:</em> <p>On the server side, there is a server socket that is listening on<br>port 3000 that is waiting for clients to connect to that port. Once<br>a client connects it creates a server thread to handle communication between the<br>server and client. On the client side, there is also a socket that<br>attempts to connect to a server via a port. The server is continuously<br>listening for incoming clients and creates a new socket object once the client<br>joins<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Sending/Receiving </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing evidence related to the checklist</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-10-26T03.03.51Screenshot%202023-10-25%20at%2010.52.26%20PM.png.webp?alt=media&token=34ae98f6-1a20-44c4-bf21-29ec59fd0d78"/></td></tr>
<tr><td> <em>Caption:</em> <p>Two clients, sending messages, identifiers visible, all clients see message<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-10-26T03.04.30Screenshot%202023-10-25%20at%2010.53.20%20PM.png.webp?alt=media&token=5afdcacb-5759-483d-9f82-b02a8716f093"/></td></tr>
<tr><td> <em>Caption:</em> <p>One client in new room can see a message but the other client<br>who isn&#39;t in that room can&#39;t<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-10-26T03.13.54Screenshot%202023-10-25%20at%2010.53.38%20PM.png.webp?alt=media&token=17492aca-8e36-412a-bcc6-bbf72c93916d"/></td></tr>
<tr><td> <em>Caption:</em> <p>They can now both see the messages now that they&#39;re in the same<br>room<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how the messages are sent, broadcasted (sent to all connected clients), and received</td></tr>
<tr><td> <em>Response:</em> <p>The sending client uses the sendmessage method to send it&#39;s message to the<br>server. When the server receives the message from a client, the ServerThread checks<br>the message and broadcasts. The Room works in conjuction with the ServerThread by<br>tracking each clients thread and what room they are in to ensure the<br>right messages get broadcasted in the right rooms. The receiving client&#39;s ServerThread is<br>listening for any incoming messages and broadcasts them to the client.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Disconnecting / Terminating </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing evidence related to the checklist</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-10-26T03.28.35Screenshot%202023-10-25%20at%2011.22.27%20PM.png.webp?alt=media&token=9cd778d8-1991-4a6f-b180-d8ea2f320b21"/></td></tr>
<tr><td> <em>Caption:</em> <p>One client disconnected, server still running<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-10-26T03.28.43Screenshot%202023-10-25%20at%2011.27.58%20PM.png.webp?alt=media&token=c89ff2d9-1019-4521-adad-c3a1bb44dc8f"/></td></tr>
<tr><td> <em>Caption:</em> <p>Server terminated<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how the various disconnects/terminations are handled</td></tr>
<tr><td> <em>Response:</em> <p>When a client gets disconnected, the socket throws an exception and gets handled<br>in the try catch block, allowing the client to disconnect without the server<br>crashing. If the server disconnects the client simply terminates the ServerThread, but still<br>has its main thread active, allowing it to be able to connect to<br>another server<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add the pull request for this branch</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/enisakil/ea377-IT114-001/pull/6">https://github.com/enisakil/ea377-IT114-001/pull/6</a> </td></tr>
<tr><td> <em>Sub-Task 2: </em> Talk about any issues or learnings during this assignment</td></tr>
<tr><td> <em>Response:</em> <p>I was first struggling to wrap my head around what sockets where and<br>how they worked but after spending more time going over parts 1-3 I<br>think I am starting to have a better understanding. I felt like when<br>I was forced to explain it in this assignment it made me really<br>consider how the connections worked.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-001-F23/it114-milestone1/grade/ea377" target="_blank">Grading</a></td></tr></table>