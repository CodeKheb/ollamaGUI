# Ollama GUI
Local Hosted AI User Interface using JavaFX and Jackson

## Maven Dependencies
Jackson (JSON Parser)
``` xml
    <dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.21.1</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.21.1</version>
        </dependency>
```
JavaFX
``` xml
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>25.0.2</version>
        </dependency>
```
build
``` xml        
    <build>
        <plugins>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>ollamaGUI.Main</mainClass>
                    <options>
                        <option>--enable-native-access=javafx.graphics</option>
                    </options>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

To do list:
- Ollama AI picker
- Ollama Installer using terminal Curl (Process Builder)
- GUI Animations and Themes
