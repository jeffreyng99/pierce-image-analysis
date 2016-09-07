# Finding the code
It's in the src folder; currently named uavWindowed.java

# To compile (build a .jar)
1. Install [maven](http://www.mkyong.com/maven/how-to-install-maven-in-windows/) (Yes it's somewhat of a pain since it doesn't set itself up in the PATH properly, so you'll have to do that yourself. The linked instructions should make this easy though.)
FYI: A copy of maven has already been downloaded in this repository (the maven3 folder). You may use that when setting it up per the linked instructions.
2. Go into the project folder (Pierce-Image-Analysis), hold shift and right-click, and click "Open Command Prompt Here"
3. Type `mvn package` and press enter.
4. After it prints "build successful," you will find the .jar you can double-click in the newly-created target folder.