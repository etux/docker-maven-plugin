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

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Enables maven to start the current Docker container.
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Mojo(name = "startContainer")
public class DockerStartContainer extends DockerMojo {

    public void execute() throws MojoExecutionException {
        try {
            createContainer();
            startContainer();
        } catch (DockerException e) {
            throw new MojoExecutionException(
                    String.format("Error starting container image %s", getContainerImage()), e);
        }
    }
}
