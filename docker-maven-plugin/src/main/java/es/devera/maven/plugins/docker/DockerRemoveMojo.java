package es.devera.maven.plugins.docker;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Allows maven to remove the current Docker container.
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Mojo(name = DockerRemoveMojo.MOJO_NAME)
public class DockerRemoveMojo extends DockerMojo {

    protected static final String MOJO_NAME = "removeContainer";

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            removeContainer();
        } catch (DockerException e) {
            throw new MojoExecutionException(
                    String.format("Error while trying to remove container %s", getContainerId()), e);
        }
    }
}
