package org.etux.maven.plugins.docker;


import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class DockerMojoTest {

    private DockerMojo original;
    private DockerMojo testing;
    private String containerId;
    @Mock
    private DockerClient mockDockerClient;
    @Mock
    private ContainerConfig mockContainerConfig;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        containerId = RandomStringUtils.random(16);
        original = new DockerMojo() {
            boolean executed = false;
            @Override
            public void execute() throws MojoExecutionException, MojoFailureException {
                executed = true;
            }
        };
        original.setDockerClient(mockDockerClient);
        original.setContainerConfig(mockContainerConfig);
        testing = spy(original);
    }

    @Test
    public void testCreateContainer() throws Exception {
        //Given
        final ContainerCreateResponse mockContainerCreateResponse = mock(ContainerCreateResponse.class);
        when(mockDockerClient.createContainer(mockContainerConfig)).thenReturn(mockContainerCreateResponse);
        when(mockContainerCreateResponse.getId()).thenReturn(containerId);
        //When
        testing.createContainer();
        //Then
        verify(mockDockerClient).createContainer(mockContainerConfig);
    }

    @Test
    public void testStartContainer() throws Exception {
        //Given
        when(testing.getContainerId()).thenReturn(containerId);
        //When
        testing.startContainer();
        //Then
        verify(mockDockerClient).startContainer(containerId);
    }

    @Test
    public void testStopContainer() throws Exception {
        //Given
        when(testing.getContainerId()).thenReturn(containerId);
        //When
        testing.stopContainer();
        //Then
        verify(mockDockerClient).stopContainer(containerId);
    }

    @Test
    public void testRemoveContainer() throws Exception {
        //Given
        when(testing.getContainerId()).thenReturn(containerId);
        //When
        testing.removeContainer();
        //Then
        verify(mockDockerClient).removeContainer(containerId);
    }
}
