# Chat System
A menu driven program that supports instant messaging and file transfer using a binary method.The chat system first asks the users to enter their name. It will then proceed to display a menu option in which the user can choose between 3 options. The user can either type "m" if they wish to send a message, "f" if they wish to request a file from a particular user, or "x" if they wish to leave the chat system.

<h2>How It Works</h2>
<p>The server side is first setup in order to accept incomming connections from multiple clients on a abritary port number the user assign.</p>


![screen shot 2018-11-23 at 1 21 31 pm](https://user-images.githubusercontent.com/37357578/48974586-d05b5180-f022-11e8-8ea6-a2b3bd717233.png)

<p>The first client proceeds to connect to the chat system by providing two different port numbers, one for file transferring and one for messaging respectively.</p>

<img align="right" src="https://user-images.githubusercontent.com/37357578/48974629-5a0b1f00-f023-11e8-8245-c9bd97f054f6.png">

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><p>The second client connects.</p>
  
<img align="left" src="https://user-images.githubusercontent.com/37357578/48974638-bbcb8900-f023-11e8-88e1-4c0471ca0763.png">

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><p>The third client connects.</p>

<img align="right" src="https://user-images.githubusercontent.com/37357578/48974650-21b81080-f024-11e8-88cb-bcfd39157227.png">

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><p>Users can communicate with each other with simple messages.</p>

<img align="center" src="https://user-images.githubusercontent.com/37357578/48974653-5deb7100-f024-11e8-8afe-6704b933ff53.png">

<p>Users can request file from other users in the chat system.</p>

<img align="center" src="https://user-images.githubusercontent.com/37357578/48974671-a73bc080-f024-11e8-8ea7-0d111a52adc2.png">

<h2>Usage</h2>

<p>First, create a folder and name it "ChatServer"(Recommended), download the source code into the folder and run the following command from the folder path.</p>

<div><pre> java ChatServer 6001</pre></div>

<p>Second, create separte folders for each client and run the following command from each folder directory.</p>

<p>Client 1</p>

<div><pre> java ChatClient -p 6002 -l 6001</pre></div>

<p>Client 2</p>

<div><pre> java ChatClient -p 6003 -l 6001</pre></div>

<p>Client 3</p>

<div><pre> java ChatClient -p 6004 -l 6001</pre></div>
