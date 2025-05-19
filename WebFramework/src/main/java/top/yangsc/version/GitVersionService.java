package top.yangsc.version;

import org.springframework.stereotype.Service;

import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.io.IOException;
import java.util.Properties;

@Service
public class GitVersionService {

    private final ResourceLoader resourceLoader;
    private final Properties gitProperties = new Properties();

    public GitVersionService(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        // 手动加载git.properties
        Resource resource = resourceLoader.getResource("classpath:git.properties");
        gitProperties.load(resource.getInputStream());
    }

    public Map<String,String> getVersion() {
        return Map.of(
            "git.commit.id", gitProperties.getProperty("git.commit.id"),
            "git.commit.time", gitProperties.getProperty("git.commit.time")
        );
    }
}
