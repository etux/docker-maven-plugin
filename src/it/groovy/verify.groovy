import com.kpelykh.docker.client.DockerClient
import com.kpelykh.docker.client.model.Container

package es.devera.maven.plugins.docker

String containerId = DockerMojo.getThreadLocalContainerId();

if (containerId != null) {
    DockerClient client = new DockerClient(url);
    containers = client.
    for (Container container : containers) {

    }
} else {
    throw new RuntimeException()
}

