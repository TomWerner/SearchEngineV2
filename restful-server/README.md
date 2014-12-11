This is the server half of the sample RESTful interaction between
client and server using Java and the Jersey (REST) API with JSON 
representation of the encapsulated data exchange in both directions.

The two programs here are Main.java and Handler.java. The simple 
description of the purpose is this:

> Launch a Grizzly HTTP server and let Jersey invoke Handler to 
> respond to client requests.

Notes:

1. The port of the server is a constant in Main.java;  change this if you prefer.  If Main.java abnormally terminates then the port typically is blocked for a couple of minutes before it can be used again. Normal termination (press Enter) doesn't have this drawback. 
2. Running the program will get an error if port is blocked. 
3. How to run using command-line Maven:
> $ mvn clean compile
> $ mvn exec:java
4. How to run in Eclipse:
 1. File -> import -> Maven -> Existing Maven Projects (then select restful-server to import)
 2. Open the restful-server project, open src/main to edit Main.java
 3.  Click the run icon
5. Observe that Main.java has no references to Handler. The connection between Main and Handler is done automatically by the Jersey framework, mainly through the Java annotations that Jersey uses. Much of the code is sensible only if one has read through the Jersey documentation, found at [Jersey Website](https://jersey.java.net/) (start with "Getting Started" and carry on reading as needed).
