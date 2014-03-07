package org.etux.maven.plugins.docker;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Enables maven to create a container in Docker.
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Mojo(name="restartContainer")
public class DockerRestartMojo extends DockerMojo{


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            restartContainer();
        } catch (DockerException e) {
            throw new MojoExecutionException("Error restarting the container", e);
        }
    }
}