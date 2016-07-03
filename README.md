JRobot
======

Framework pour faire des actions IHM automatisée sous la forme d'un jar standalone paramètrable par environnement basé sur phantomjs.

### Installation
- Ajouter le jar sur votre Nexus
  - [téléchargement](https://drive.google.com/open?id=0B3RZ6sP4kUBANzB2MTh2QzFWSHc)
- Ajouter la dépendance dans votre pom.xml
```xml
<dependency>
    <groupId>com.github.ffremont</groupId>
    <artifactId>jrobot</artifactId>
    <version>X.Y.Z</version>
</dependency>
```
- Ajouter la déclaration du plugin shade
```xml
<build>
      <plugins>
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-shade-plugin</artifactId>
              <version>2.4.3</version>
              <executions>
                  <execution>
                      <phase>package</phase>
                      <goals>
                          <goal>shade</goal>
                      </goals>
                      <configuration>
                          <transformers>
                              <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                  <manifestEntries>
                                      <Main-Class>com.github.ffremont.jrobot.JRobotApp</Main-Class>
                                  </manifestEntries>
                              </transformer>
                          </transformers>
                      </configuration>
                  </execution>
              </executions>
          </plugin>
      </plugins>
  </build>
```

### Ecriture d'un cas
```java
public class ExempleTF extends JRobot{

    public ExempleTF(){
        super(UiConfig.create("web.test", "mon test"));
    }

    @Override
    public void useCase() {
        goTo("http://localhost:4567");
        
        assertEquals("Page de test", title());
        takeSnapshot();
        
    }
}
```

###### Configuration dans le répertoire courant
- Paramètrage de phantomJs phantomjs.properties
```properties
phantomjs_exec_path=
phantomjs_driver_path=
phantomjs_driver_loglevel=ERROR
phantomjs_driver_logFile=phantomjs.log
```
- Paramètrage des propriétés pour le cas
 - chercher dans l'ordre des fichiers 
   - commons.properties
   - commons_[env].properties
   - [idTest].properties
   - [idTest]_[env].properties
   
- Exemple : 
 - commons.properties 
```properties
ws=http://google.fr
```
 - test.properties
 ```properties
 ws.id=myTest
ws.url=${ws}/${ws.id}
 ```
 - test_dev.properties
 ```properties
 ws=www.google.fr/dev
 ```
 
 - Résultat :
  - ws=www.google.fr/dev
  - ws.id=myTest
  - ws.url=www.google.fr/dev/myTest

- Possibilité d'organiser les cas d'utilisation avec des namespaces
 * ```java
// va cherche dans ./web/test.properties la configuration du cas
UiConfig.create("web.test", "mon test"); 
```
###### Lancement
```bash
java [-Denv=???] -jar monapp_ui.jar
```

#### Licence MIT
