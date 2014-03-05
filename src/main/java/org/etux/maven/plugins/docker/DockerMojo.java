package org.etux.maven.plugins.docker;

import java.util.LinkedList;
import java.util.List;

import com.kpelykh.docker.client.model.ContainerConfig;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Only works when docker daemon is running on a tcp port (on unix socket support at the moment).
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public abstract class DockerMojo extends AbstractMojo {

    static final ThreadLocal<String> containerId = new ThreadLocal<String>();

    private List<String> dockerContainerIds;

    @Parameter(defaultValue="http://localhost:4243")
    String url;
    @Parameter(required = true)
    String containerImage;

    ContainerConfig containerConfig;

    public DockerMojo() {
        dockerContainerIds = new LinkedList<String>();
    }

    public void addContainer(String container) {
        dockerContainerIds.add(container);
    }

    public void removeContainer(String container) {
        dockerContainerIds.remove(container);
    }

    public ContainerConfig getContainerConfig() {
        if (containerConfig == null) {
            containerConfig = new ContainerConfig();
            containerConfig.setHostName(url);
            containerConfig.setImage(containerImage);
            containerConfig.setCmd(new String[] {"true"});
        }
        return containerConfig;
    }
}
