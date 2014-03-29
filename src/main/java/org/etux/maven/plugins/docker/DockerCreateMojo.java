package org.etux.maven.plugins.docker;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Enables maven to create a container in Docker.
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Mojo(name = DockerCreateMojo.MOJO_NAME)
public class DockerCreateMojo extends DockerMojo {

    static final String MOJO_NAME = "createContainer";

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            createContainer();
        } catch (DockerException e) {
            throw new MojoExecutionException(
                    String.format("Error while trying to remove container %s", getContainerId()), e);
        }
    }
}
