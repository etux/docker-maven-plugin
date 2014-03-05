package org.etux.maven.plugins.docker;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Mojo that enables Maven to start Docker containers.
 *
 */
@Mojo(
        name = "runContainer",
        defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST
)
public class DockerRunMojo extends DockerMojo {


    private DockerClient dockerClient;

    public void execute() throws MojoExecutionException {
        dockerClient = new DockerClient(url);
        try {
            final ContainerCreateResponse container = dockerClient.createContainer(getContainerConfig());
            containerId.set(container.getId());
            addContainer(container.getId());
            dockerClient.startContainer(container.getId());
        } catch (DockerException e) {
            throw new MojoExecutionException("Error starting container image "+containerImage+" on host on URL "+url, e);
        }
    }
}
