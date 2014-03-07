package org.etux.maven.plugins.docker;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Enables maven to kill the current Docker container.
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Mojo(name="killContainer")
public class DockerKillMojo extends DockerMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            killContainer();
        } catch (DockerException e) {
            throw new MojoExecutionException(
                    String.format("Error while trying to kill container"), e);
        }
    }
}
