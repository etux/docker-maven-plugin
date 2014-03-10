docker-maven-plugin
===================

Maven plugin to interact with Docker.

The idea of this plugin is to be able to operate Docker from Maven. All elements of the ContainerConfig are supported.

Initial implementation should covers:

- start container
- stop container
- create container
- remove container

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

* mvn docker:createContainer
* mvn docker:startContainer
* mvn docker:stopContainer
* mvn docker:removeContainer

At the moment you need to clone the repository and compile for yourself.
