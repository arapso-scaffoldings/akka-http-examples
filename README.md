# akka-http-examples
Akka Http Example Applications

1. Hello World Example

    To run project simply execute command below
    
        sbt run
        
    *Questions:*
    - What happend if we add additional file with object which implement main mathod as well?
    ```
    Multiple main classes detected, select one to run:
    
     [1] Hi
     [2] Hi2
    
    Enter number: 2
    ```
    - How to choose file to execute?
    ```
    ➜  akka-http-examples git:(master) ✗ sbt "run-main Hi2"     
    [info] Set current project to akka-http-examples (in build file:/Users/damian/arapso/akka-http-examples/)
    [info] Running Hi2
    Hello world
    [success] Total time: 1 s, completed 2017-07-05 10:04:24
    ```
