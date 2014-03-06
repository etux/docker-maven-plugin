package org.etux.maven.plugins.docker;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Only works when docker daemon is running on a tcp port (on unix socket support at the moment).
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public abstract class DockerMojo extends AbstractMojo {

    //TODO find out the default port for docker when listening to network ports.
    private static final String DEFAULT_URL = "http://localhost:4243";

    //package protected so that only classes inside the package can access it.
    private static final ThreadLocal<String> tlContainerId = new ThreadLocal<String>();

    @Parameter(defaultValue = DEFAULT_URL)
    private String url;

    @Parameter(required = true)
    private String containerImage;

    private ContainerConfig containerConfig;

    private DockerClient dockerClient;

    public String getContainerImage() {
        return containerImage;
    }

    public ContainerConfig getContainerConfig() {
        if (containerConfig == null) {
            containerConfig = new ContainerConfig();
            containerConfig.setHostName(getUrl());
            containerConfig.setImage(getContainerImage());
            containerConfig.setCmd(new String[] {"true"});
        }
        return containerConfig;
    }

    private String getUrl() {
        if (url == null) url = DEFAULT_URL;
        return url;
    }

    DockerClient getDockerClient() {
        if (dockerClient == null) dockerClient = new DockerClient(url);
        return dockerClient;
    }

    /**
     * Creates a container in Docker and stores the container id in a ThreadLocal variable
     * so that it can be accessed by other goals of the plugin.
     * @throws DockerException
     */
    void createContainer() throws DockerException {
        getLog().debug(String.format("Creating new container"));
        final ContainerCreateResponse response = getDockerClient().createContainer(getContainerConfig());
        final String containerId = response.getId();
        getLog().info(String.format("Created container with id %s", containerId));
        DockerMojo.tlContainerId.set(containerId);
    }

    void startContainer() throws DockerException {
        getLog().debug(String.format("Trying to start container %s", getContainerId()));
        if (getContainerId() == null) throw new RuntimeException("There isn't any container id set.");
        getDockerClient().startContainer(getContainerId());
    }

    void stopContainer() throws DockerException {
        getLog().debug(String.format("Trying to stop container %s", getContainerId()));
        if (getContainerId() == null) throw new RuntimeException("There isn't any container id set.");
        getDockerClient().stopContainer(getContainerId());
    }

    void removeContainer() throws DockerException {
        getLog().debug(String.format("Trying to remove container %s", getContainerId()));
        if (getContainerId() == null) throw new RuntimeException("There isn't any container id set.");
        final String containerId = this.getContainerId();
        getDockerClient().removeContainer(containerId);
        DockerMojo.tlContainerId.set(null);
        getLog().info(String.format("Container %s has been removed", containerId));
    }

    String getContainerId() {
        return DockerMojo.tlContainerId.get();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //                          For testing purposes                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    void setDockerClient(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    void setContainerConfig(ContainerConfig containerConfig) {
        this.containerConfig = containerConfig;
    }
}
