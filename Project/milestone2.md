<table><tr><td> <em>Assignment: </em> IT114 Trivia Milestone 2</td></tr>
<tr><td> <em>Student: </em> Enis Akil (ea377)</td></tr>
<tr><td> <em>Generated: </em> 11/21/2023 10:34:37 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-001-F23/it114-trivia-milestone-2/grade/ea377" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone2 from the proposal document:&nbsp;<a href="https://docs.google.com/document/d/1h2aEWUoZ-etpz1CRl-StaWbZTjkd9BDMq0b6TXK4utI/view">https://docs.google.com/document/d/1h2aEWUoZ-etpz1CRl-StaWbZTjkd9BDMq0b6TXK4utI/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Payload </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Payload Screenshots</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T00.29.01Screenshot%202023-11-21%20at%207.21.52%20PM.png.webp?alt=media&token=976ab2bf-9e7b-4569-a884-01df03bdf7c2"/></td></tr>
<tr><td> <em>Caption:</em> <p>Payload propertys<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T00.29.18Screenshot%202023-11-21%20at%207.27.55%20PM.png.webp?alt=media&token=32201cbe-d6ad-4246-a4a4-0194d26b65c6"/></td></tr>
<tr><td> <em>Caption:</em> <p>You can see the properties in the toString messages (playerId&#39;s, chosenAnswer, Score)<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Game Play Code </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show the code related to the question/category choice</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-21T21.13.47Screenshot%202023-11-21%20at%201.59.23%20PM.png.webp?alt=media&token=adc2dba7-b459-414c-a068-88807ab46bfc"/></td></tr>
<tr><td> <em>Caption:</em> <p>QuestionDatabase to store the questions, and contains logic for selecting a random category<br>and question <br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-21T21.13.57Screenshot%202023-11-21%20at%201.59.59%20PM.png.webp?alt=media&token=6ae18ce6-c9da-4eb6-a512-cc47d1493bc8"/></td></tr>
<tr><td> <em>Caption:</em> <p>StartGameSession method that pretty much has all of the logic for the game<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-21T21.14.07Screenshot%202023-11-21%20at%202.02.56%20PM.png.webp?alt=media&token=f5a99b6d-f4af-45e7-855a-83226dcc1a21"/></td></tr>
<tr><td> <em>Caption:</em> <p>Some of the methods used in the StartGameSession method. These methods handle things<br>such as starting round timer and processing player answers <br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-21T21.14.36Screenshot%202023-11-21%20at%202.03.17%20PM.png.webp?alt=media&token=6e37ac06-eb0d-4238-87ac-03deb4de2eac"/></td></tr>
<tr><td> <em>Caption:</em> <p>Methods to check whether the answer is correct and to calculate points, get<br>used in StartGameSession<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show the code related to players picking answers</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-21T21.32.29Screenshot%202023-11-21%20at%204.27.35%20PM.png.webp?alt=media&token=b27aa3fd-95b1-4263-941a-054f4763b2c2"/></td></tr>
<tr><td> <em>Caption:</em> <p>The processClientCommand on the client side processes a pick by looking at any<br>message that starts with /pick, with the answer following the command<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T03.25.40Screenshot%202023-11-21%20at%2010.25.21%20PM.png.webp?alt=media&token=1279c27a-c4ec-4763-a4d0-f47a083fe5ab"/></td></tr>
<tr><td> <em>Caption:</em> <p>roundTimerExpired checks if the roundTimer hits 0. The endRound method executes only if<br>all players have picked or if time has ran out.<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-21T22.31.56Screenshot%202023-11-21%20at%205.31.47%20PM.png.webp?alt=media&token=99527875-61f3-4ff2-974c-18f27ed7d940"/></td></tr>
<tr><td> <em>Caption:</em> <p>IsAnswerCorrect and calculatePoints are used to determine if the answer is correct and<br>how many points the user should get<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T03.25.53Screenshot%202023-11-21%20at%2010.22.55%20PM.png.webp?alt=media&token=89b6f3ff-8ed8-4228-b1e5-213692396fe7"/></td></tr>
<tr><td> <em>Caption:</em> <p>Spoke to Professor Toegel he said it&#39;s ok if players change answers during<br>the round as long as it penalizes them(which it should)<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Show the code related to the timer functionality</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-21T22.22.37Screenshot%202023-11-21%20at%205.22.08%20PM.png.webp?alt=media&token=b24ef102-efa6-45be-a680-2d97908088bc"/></td></tr>
<tr><td> <em>Caption:</em> <p>StartRoundTimer method creates a new RoundTimer, gets called In handleGameLogic<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T03.27.26Screenshot%202023-11-21%20at%2010.25.21%20PM.png.webp?alt=media&token=61d7341a-64fd-4d5f-900b-6c6bacd6f3ea"/></td></tr>
<tr><td> <em>Caption:</em> <p>roundTimerExpired method helps check if the roundTimer expires and then that method gets<br>used in the if statement of the EndRound method to decide if the<br>round is ending.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 4: </em> Explain the Game flow code at a high level</td></tr>
<tr><td> <em>Response:</em> <p>First, The GameRoom becomes initialized. The players then join the GameRoom and connect<br>to the server through ServerThread. The game session is started by players reading<br>up through a readycheck and through a startgame command. Once the game session<br>has started, a random category and random question from that category gets selected<br>and sent to all the players. After the question and potential answers have<br>been broadcasted to the players, the game logic gets initiated, and players have<br>a certain amount of time submit their answers. After answers have been submitted,<br>the logic to process the answers and calculate the scores based on how<br>fast the player answered gets implemented. After all players have answered, the correct<br>answer and the score each player got for the round is broadcasted to<br>the players. The round then gets restarted with a another question, and all<br>the game logic repeats itself<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Game Evidence </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show screenshots of the terminal output of a working game flow</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T01.06.29Screenshot%202023-11-21%20at%207.59.40%20PM.png.webp?alt=media&token=d89396af-e919-4464-95f7-86cd5d4b5c7f"/></td></tr>
<tr><td> <em>Caption:</em> <p>You can see the player being informed of the category, question, and potential<br>answers<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T03.30.24Screenshot%202023-11-21%20at%2010.28.45%20PM.png.webp?alt=media&token=fc2d5f36-5c01-4e32-ae00-54288eedae8b"/></td></tr>
<tr><td> <em>Caption:</em> <p>You can see the pick being recorded and the player receiving points based<br>on if it was correct or not<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fea377%2F2023-11-22T03.31.10Screenshot%202023-11-21%20at%2010.29.50%20PM.png.webp?alt=media&token=83b27407-ce11-450e-9e81-d0c78b64baa6"/></td></tr>
<tr><td> <em>Caption:</em> <p>If the roundTimer expires and everyone hasn&#39;t picked it just shows what the<br>correct answer was and let&#39;s the user know if they picked or not<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Include the pull request for Milestone2 to main</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/enisakil/ea377-IT114-001/pull/7">https://github.com/enisakil/ea377-IT114-001/pull/7</a> </td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-001-F23/it114-trivia-milestone-2/grade/ea377" target="_blank">Grading</a></td></tr></table>