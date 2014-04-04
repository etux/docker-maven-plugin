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
        <version>0.0.1</version>
        <configuration>
            <url>http://192.168.1.111:4342</url>
            <containerImage>ubuntu</containerImage>
        </configuration>
    </plugin>
</plugins>
```

to your POM.

Then you can execute any goals:

* mvn docker:pullImage -DcontainerImage=&lt;containerImage&gt;
* mvn docker:createContainer -DcontainerImage=&lt;containerImage&gt;
* mvn docker:startContainer -DcontainerId=&lt;containerId&gt;
* mvn docker:stopContainer -DcontainerId=&lt;containerId&gt;
* mvn docker:restartContainer -DcontainerId=&lt;containerId&gt;
* mvn docker:removeContainer -DcontainerId=&lt;containerId&gt;
* mvn docker:killContainer -DcontainerId=&lt;containerId&gt;

In case two different goals are executed as part of the same Maven session, then these two will share the containerId as long as they execute on the same thread.
