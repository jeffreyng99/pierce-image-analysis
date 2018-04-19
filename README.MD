# Cloning this repo with Github Desktop

Here's a clickable link to easily clone this repo with Github Desktop: [github-windows://openRepo/https://gitlab.com/ESL-UAV/Pierce-Image-Analysis](github-windows://openRepo/https://gitlab.com/ESL-UAV/Pierce-Image-Analysis)

# Finding the code
It's in the src folder; The main class is UserInterface.java ~~(yea what a wonderfully-named class right)~~ which we really should rename. (The project itself could use a bit of a refactor, mostly in giving better names to the methods, even though there's not many lines of code.)

# To compile (build a .jar)
1. Install [maven](http://www.mkyong.com/maven/how-to-install-maven-in-windows/) (Yes it's somewhat of a pain since it doesn't set itself up in the PATH properly, so you'll have to do that yourself. The linked instructions should make this easy though.)
FYI: A copy of maven has already been downloaded in this repository (the maven3 folder). You may use that when setting it up per the linked instructions.
2. Go into the project folder (Pierce-Image-Analysis), hold shift and right-click, and click "Open command window here" (or Powershell, either will do.)
3. Type `mvn clean package` and press enter.
4. After it prints "build successful," you will find a single, built `isnap.jar` you can double-click in the newly-created target folder. (This is the program you should distribute.)

The `IgnoreThis.jar` may be deleted. Do not use or distribute this; this jar is the unshaded (i.e. doesn't include opencv) version of the compiled jar file.

# Running the jar

To run the jar without a console window for debugging, simply double-click the jar. Otherwise, to run jars like a real ~~man~~ developer:

1. cd to the target folder. (Go to the "target" folder, hold shift and right-click, and click "Open command window here.")
2. Type `java -jar isnap.jar`

Note: you can tab-complete file and folder names.

# Other documentation

A PowerPoint exists somewhere in the box, but given the small amount of code, there really shouldn't be a need for more than in-code comments. If you're having trouble understanding the methods of Java-included classes, please refer to the Javadocs (which you can easily find via doing a web search for "classname Javadoc", e.g. `File Javadoc`).