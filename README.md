# Html-validate
programming tools HTML VALIDATOR 



## DEMO

Download the Jar file and double click to run

Or run `java -jar SimpleJavaTextEditor.jar` from the command line

Or run ant on the extracted folder a dist folder will be created run the file(note - make the jar file executable) 


### Important

**Icons directory and its files must be present on the path when running the application (so you will have to move "*icons/*" into "*src/*" directory)**
### tools info
1. Ant- type ant in the teminal to build the project.
2. javadoc - in terminal type javadoc filename.java
3. github - UI based
4. Jar-  in src/ directory type:  jar cmvf ../manifest.mf ../SimpleJavaTextEditor.jar simplejavatexteditor/*.class
5.  Eclipse or Netbeans or terminal+Editor whichever comfortable to you.
6. error-prone - gives possible java errors in form of errors.
type in home ie default terminal location:
java -Xbootclasspath/p:error_prone_ant-2.1.1.jar com.google.errorprone.ErrorProneCompiler /home/ayush/Documents/textedit/src/simplejavatexteditor/UI.java


extra------
7. maven(instead of ant) instead of javac only for compiling.
in src directory type ---------- mvn compile.

Profling tool:
javac -J-agentlib:hprof=heap=sites Hello.java
