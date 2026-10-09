JAVA MODULE SYSTEM (JPMS) DEMO
================================

Requirements:
- JDK 9 or later
- Recommended: JDK 17 or 21

Project structure:
Java_Module_Demo/
  moduleA/
    src/
      module-info.java
      com/demo/service/Calculator.java
  moduleB/
    src/
      module-info.java
      com/demo/app/Main.java

Compile from the Java_Module_Demo directory:

Windows:
javac -d out/moduleA moduleA/src/module-info.java moduleA/src/com/demo/service/Calculator.java
javac --module-path out -d out/moduleB moduleB/src/module-info.java moduleB/src/com/demo/app/Main.java

Run:
java --module-path out -m moduleB/com.demo.app.Main

Expected output:
Addition = 30
Multiplication = 200

Concepts demonstrated:
- module declaration
- exports
- requires
- package encapsulation
- module path
- inter-module dependency
