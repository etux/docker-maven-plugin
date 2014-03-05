package org.etux.maven.plugins.docker;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Mojo(
        name = "stopContainer",
        defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST
)
public class DockerStopMojo extends DockerMojo{

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final DockerClient client = new DockerClient(url);
        try {
            client.removeContainer(containerId.get());
        } catch (DockerException e) {
            throw new MojoExecutionException("Error while trying to remove container "+containerId.get(), e);
        }
    }
}
