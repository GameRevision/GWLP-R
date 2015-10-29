
---

# DEPRECATED
# please find the successor of this at:
# ['to entice so.'](https://github.com/entice)

---

![GWLPR Logo](http://img851.imageshack.us/img851/7279/logo4jb.png)

## Overview
    Project:       GWLP:R
    Info:          It's a set of game-apps for Reality:Shard that emulates a GW (1) server.
    Languages:     Java
    People:        _rusty, ACB, miracle444, JC123

**Current work**

This project is very slow in development, so dont expect a fully working game server in the near future.
As for the current development, we are basically designing the software like a sandbox that can be extended by
other developers. If you want to contribute to this project, you can either join the development team, or add
pull requests, according to this guide: [The diaspora git-flow guide](https://github.com/diaspora/diaspora/wiki/Git-Workflow)
Yes, this guide was made for the diaspora project, but it covers the basics quite well.


**How to talk to us**

 - Join the dev chat at irc://irc.rizon.net/gwlpr (if your using an IRC client, try joining #gwlpr on the server irc.rizon.net)
 - Leave a message in the forums on [GameRevision](http://www.gamerevision.com/forumdisplay.php?61-GWLP-R)


### Install notes for users (you still have to compile it :P):

---

*You may want to use a Virtual-Machine (running Linux) to run the server, especially if you dont have a machine that runs Linux (or a Unix system) natively. Trying to follow the instructions on Windows probably wont work - but there might be a Windows package on the GR forums somewhere.*

**On Windows (Or any OS that will run the GW client later on)**
 - Download, compile and config the Launcher from the [utils](https://github.com/GameRevision/GWLP-R-Utils/tree/master/Launcher)
 
**On Linux/Unix (Or any OS that will run the server later on)**
 - Make sure you have Git, Maven 3+, JDK 7+ and MySQL 5.5+ installed and configured.  
   _Make sure the MySQL server is up and working._
 - Install Java's unlimited-strength-for-security file according to [this guide](http://stackoverflow.com/a/6481658) to enable encryption for the server. **HOWEVER** If you're running Java 7, which should be the case, replace the file mentioned in the link with [this one](http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html). The path remains the same.
 - Run `git clone git://github.com/GameRevision/GWLP-R.git` anywhere you like on your system.  
   _But make sure not to use queer directory namings. Some tools got problems with whitespaces and symbols!_
 - Edit the install script from within the project you just cloned to match your MySQL settings (username, password and database).
 - Run the script with `./install.sh`.
 - The script recommends executing a certain command to fill the database - do that.
 - Edit DevelopmentEnvironment.java in the "host" project to match your MySQL settings and the IP of your Game Server (search for "MapShard").
 - Run `mvn clean package exec:java` from inside the "host" directory.
 
**Finally, run your GW client by using the Launcher and login with `email: "root@gwlp.ps"` / `password: "root"` / `character: "Test Char"`**


### Install notes for developers:


---

 - _Have a look at the existing guide here:_  [The Developer's Guide](https://github.com/GameRevision/GWLP-R/wiki/Dev-HowTo)


### Links:

---

 - [Reality:Shard](https://github.com/RealityShard/RealityShard)
