import com.kpelykh.docker.client.model.Container
import com.kpelykh.docker.client.DockerClient
import es.devera.maven.plugins.docker.DockerMojo

String containerId = DockerMojo.getThreadLocalContainerId()

if (containerId != null) {
    DockerClient client = new DockerClient(url)
    for (Container container : containers) {
        System.out.println(container.getId())
    }
} else {
    throw new RuntimeException()
}

