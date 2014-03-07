package org.etux.maven.plugins.docker;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * Only works when docker daemon is running on a tcp port (no unix socket support at the moment).
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public abstract class DockerMojo extends AbstractMojo {

    //TODO find out the default port for docker when listening to network ports.
    private static final String DEFAULT_URL = "http://localhost:4243";

    //package protected so that only classes inside the package can access it.
    private static final ThreadLocal<String> tlContainerId = new ThreadLocal<String>();

    private ContainerConfig containerConfig;
    private DockerClient dockerClient;

    @Parameter(defaultValue = DEFAULT_URL)
    private String url;
    @Parameter(required = true)
    private String containerImage;
    @Parameter
    private boolean stderr;
    @Parameter
    private boolean stdin;
    @Parameter
    private boolean stdout;
    @Parameter
    private int cpuShares;
    @Parameter
    private String[] dnss;
    @Parameter
    private String[] entryPoints;
    @Parameter
    private String[] envs;
    @Parameter
    private String hostname;
    @Parameter
    private long memoryLimit;
    @Parameter
    private long memorySwap;
    @Parameter
    private boolean networkDisabled;
    @Parameter
    private String[] onBuild;
    @Parameter
    private String[] portSpecs;
    @Parameter
    private boolean privileged;
    @Parameter
    private boolean stdInOnce;
    @Parameter
    private boolean stdInOpen;
    @Parameter
    private boolean tty;
    @Parameter
    private String user;
    @Parameter
    private Object volumes;
    @Parameter
    private String volumesFrom;
    @Parameter
    private String workingDir;
    @Parameter
    private String[] cmds;
    @Parameter
    private int timeout;


    /**
     * Creates a container in Docker and stores the container id in a ThreadLocal variable
     * so that it can be accessed by other goals of the plugin.
     *
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
        validateContainerId();
        getDockerClient().stopContainer(getContainerId());
    }

    void removeContainer() throws DockerException {
        getLog().debug(String.format("Trying to remove container %s", getContainerId()));
        validateContainerId();
        final String containerId = this.getContainerId();
        getDockerClient().removeContainer(containerId);
        DockerMojo.tlContainerId.set(null);
        getLog().info(String.format("Container %s has been removed", containerId));
    }

    void killContainer() throws DockerException {
        getLog().debug(String.format("Trying to kill container %s", getContainerId()));
        validateContainerId();
        final String containerId = this.getContainerId();
        getDockerClient().kill(containerId);
        getLog().info(String.format("Container %s has been killed", getContainerId()));
    }

    void restartContainer() throws DockerException {
        getLog().debug(String.format("Trying to restart container %s", getContainerId()));
        validateContainerId();
        final String containerId = this.getContainerId();
        getDockerClient().restart(containerId, timeout);
        getLog().info(String.format("Container %s has been restarted", getContainerId()));
    }

    private void validateContainerId() {
        if (getContainerId() == null) throw new InvalidStateException("There isn't any container id set.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                           Getter and Setters                                                   //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ContainerConfig getContainerConfig() {
        if (containerConfig == null) {
            containerConfig = new ContainerConfig();
            containerConfig.setHostName(getUrl());
            containerConfig.setCmd(getCmds());
            containerConfig.setAttachStderr(isStderr());
            containerConfig.setAttachStdin(isStdin());
            containerConfig.setAttachStdout(isStdout());
            containerConfig.setCpuShares(getCpuShares());
            containerConfig.setDns(getDnss());
            containerConfig.setEntrypoint(getEntryPoints());
            containerConfig.setEnv(getEnvs());
            containerConfig.setHostName(getHostname());
            containerConfig.setImage(getContainerImage());
            containerConfig.setMemoryLimit(getMemoryLimit());
            containerConfig.setMemorySwap(getMemorySwap());
            containerConfig.setNetworkDisabled(isNetworkDisabled());
            containerConfig.setOnBuild(getOnBuild());
            containerConfig.setPortSpecs(getPortSpecs());
            containerConfig.setPrivileged(isPrivileged());
            containerConfig.setStdInOnce(isStdInOnce());
            containerConfig.setStdinOpen(isStdInOpen());
            containerConfig.setTty(isTty());
            containerConfig.setUser(getUser());
            containerConfig.setVolumes(getVolumes());
            containerConfig.setVolumesFrom(getVolumesFrom());
            containerConfig.setWorkingDir(getWorkingDir());
        }
        return containerConfig;
    }

    String getContainerId() {
        return DockerMojo.tlContainerId.get();
    }

    public String getContainerImage() {
        return containerImage;
    }

    private String getUrl() {
        if (url == null) url = DEFAULT_URL;
        return url;
    }

    DockerClient getDockerClient() {
        if (dockerClient == null) dockerClient = new DockerClient(url);
        return dockerClient;
    }
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setContainerImage(String containerImage) {
        this.containerImage = containerImage;
    }

    public boolean isStderr() {
        return stderr;
    }

    public void setStderr(boolean stderr) {
        this.stderr = stderr;
    }

    public boolean isStdin() {
        return stdin;
    }

    public void setStdin(boolean stdin) {
        this.stdin = stdin;
    }

    public boolean isStdout() {
        return stdout;
    }

    public void setStdout(boolean stdout) {
        this.stdout = stdout;
    }

    public int getCpuShares() {
        return cpuShares;
    }

    public void setCpuShares(int cpuShares) {
        this.cpuShares = cpuShares;
    }

    public String[] getDnss() {
        return dnss;
    }

    public void setDnss(String[] dnss) {
        this.dnss = dnss;
    }

    public String[] getEntryPoints() {
        return entryPoints;
    }

    public void setEntryPoints(String[] entryPoints) {
        this.entryPoints = entryPoints;
    }

    public String[] getEnvs() {
        return envs;
    }

    public void setEnvs(String[] envs) {
        this.envs = envs;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public long getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public long getMemorySwap() {
        return memorySwap;
    }

    public void setMemorySwap(long memorySwap) {
        this.memorySwap = memorySwap;
    }

    public boolean isNetworkDisabled() {
        return networkDisabled;
    }

    public void setNetworkDisabled(boolean networkDisabled) {
        this.networkDisabled = networkDisabled;
    }

    public String[] getOnBuild() {
        return onBuild;
    }

    public void setOnBuild(String[] onBuild) {
        this.onBuild = onBuild;
    }

    public String[] getPortSpecs() {
        return portSpecs;
    }

    public void setPortSpecs(String[] portSpecs) {
        this.portSpecs = portSpecs;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    public boolean isStdInOnce() {
        return stdInOnce;
    }

    public void setStdInOnce(boolean stdInOnce) {
        this.stdInOnce = stdInOnce;
    }

    public boolean isStdInOpen() {
        return stdInOpen;
    }

    public void setStdInOpen(boolean stdInOpen) {
        this.stdInOpen = stdInOpen;
    }

    public boolean isTty() {
        return tty;
    }

    public void setTty(boolean tty) {
        this.tty = tty;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Object getVolumes() {
        return volumes;
    }

    public void setVolumes(Object volumes) {
        this.volumes = volumes;
    }

    public String getVolumesFrom() {
        return volumesFrom;
    }

    public void setVolumesFrom(String volumesFrom) {
        this.volumesFrom = volumesFrom;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    public String[] getCmds() {
        return cmds;
    }

    public void setCmds(String[] cmds) {
        this.cmds = cmds;
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
