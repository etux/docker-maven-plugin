package org.etux.maven.plugins.docker;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 09/03/14
 *         Time: 21:29
 */
@Mojo(name= DockerPullMojo.MOJO_NAME)
public class DockerPullMojo extends DockerMojo {

    static final String MOJO_NAME = "pullImage";

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            pullImage();
        } catch (DockerException dockerException) {
            throw new MojoExecutionException(
                    String.format("Exception while pulling image"),
                    dockerException);
        }
    }
}
