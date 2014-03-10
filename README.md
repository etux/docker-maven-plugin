docker-maven-plugin
===================

Maven plugin to interact with Docker.

The idea of this plugin is to be able to operate Docker from Maven. All elements of the ContainerConfig are supported.

Initial implementation should covers:

- pull image
- start container
- stop container
- restart container
- create container
- remove container
- kill container

In order to use, add 

```xml
<plugins>
    <plugin>
        <groupId>org.etux.maven.plugins</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <configuration>
            <url>http://192.168.1.111:4342</url>
            <containerImage>ubuntu</containerImage>
        </configuration>
    </plugin>
</plugins>
```

to your POM.

Then you can execute any goals:

* mvn docker:pullImage -DcontainerImage=<containerImage>
* mvn docker:createContainer -DcontainerImage=<containerImage>
* mvn docker:startContainer -DcontainerId=<containerId>
* mvn docker:stopContainer -DcontainerId=<containerId>
* mvn docker:restartContainer -DcontainerId=<containerId>
* mvn docker:removeContainer -DcontainerId=<containerId>
* mvn docker:killContainer -DcontainerId=<containerId>

In case two different goals are executed as part of the same Maven session, then these two will share the containerId. In fact createContainer will set the containerId in a ThreadLocal variable so that other goals of the plugin that are executed afterwards can pick it up.

At the moment you need to clone the repository and compile for yourself.
