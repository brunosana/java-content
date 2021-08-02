## Java Backend Learn Files

I'm use for this course an [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/).

For me, that use VSCode, its perfect install the [VSCode Keymap](https://plugins.jetbrains.com/plugin/12062-vscode-keymap):

1. File -> Settings -> Keymaps
2. Click on `Get more keymaps in setting`
3. Search `VSCode` and install
4. Choose VSCode on `Keymap` panel
5. Save

### Maven Project

> It's a Project Dependencies Manager.

To install some dependencies, you need to search the code part that makes maven add library.

A code part like this needs to be past on `pom.xml`:

````xml
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.5.5.Final</version>
</dependency>
````

Saving the file (`Ctrl + S`), maven will att your project.

### Configure JPA with MySQL

To configure JPA, you need:

1. Create a `META-INF`folder on `src/main/resources`
2. Inside META-INF, create a `persistence.xml` file

OBS: The file/folder names needs to be EXACTLY equals!

On `persistence.xml`, put:

````xml
<?xml version="1.0" encoding="UTF-8"?>
    <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
     http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
    version="2.1">
    <persistence-unit name="projectName" transaction-type="RESOURCE_LOCAL">
        <properties>
             <property name="javax.persistence.jdbc.url"
             value="jdbc:mysql://localhost/yourDatabase?useSSL=false&amp;serverTimezone=UTC" />
             <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
             <property name="javax.persistence.jdbc.user" value="root" />
             <property name="javax.persistence.jdbc.password" value="" />
             <property name="hibernate.hbm2ddl.auto" value="update" />
             <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect" />
            </properties>
    </persistence-unit>
</persistence>
````

Maybe you need create a container: `docker run --name=java_mysql -d mysql/mysql-server`

### Mapping Entities

For Map Entities, you need to put on Entities Class a decorator called `@Entity`.
Should be imported by `javax.persistence`:

````java

@Entity
public class User {
    private Integer id;
    private String username;
    //[...]
}
````

For Primary Keys, we use this decorators over declaration:

````java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
//[...]
````

Other columns naturally are created by Hibernate.

### Operations on Database

To make `create` operations on Database, we need use a `EntityManagerFactory` and `EntityManager` from `javax.persistence`:

````java
public static void main(String[] args) {
    EntityManagerFactory userEntityFactory = Persistence.createEntityManagerFactory("Course");

    EntityManager userEntity = userEntityFactory.createEntityManager();

    User user = new User(null, "Bruno Santana", "bruno@bruno.com", "123456789");

    //Quando o JPA faz uma operação no banco diferente de consulta, ele precisa de uma transação
    userEntity.getTransaction().begin();
    userEntity.persist(user);
    userEntity.getTransaction().commit();
    System.out.println("Data writed on Database");
    userEntity.close();
    userEntityFactory.close();

}
````

The `projectName` needs to be the exact name that contains in the `name` property at `persistence-unit` on `persistence.xml`.
Finnaly we can close both the `userEntity` and `userEntityFactory`.

To make `read` operations, we can use `find` method:

````java
User userFromDatabase = userEntity.find(User.class, 8);
````

To make `remove` operations you need to parse non-monitored Entities (Object that you inserted and not close entities):

````java
userEntity.getTransaction().begin();
userEntity.remove(userFromDatabase);
userEntity.getTransaction().commit();
````


## Spring

> Framework that provides all configs and basic packages that should able to create an basic API. Management transactions and create a repository.

### Create a Spring Project

1. Go to [Spring Initializr Site](https://start.spring.io/)
2. In Project, choose _Maven_
3. In Spring Boot, don't change anything
4. Fill Project metadata with the correct data
5. Choose a LTS java version
6. Click in _GENERATE_
7. Unzip files and open project

To running application, just type `CTRL + Shift + F10` to Build and Run.

To change default port and other configs, you can change the `application.properties` in `src/main/resources`:

````
server.port = ${port:3333}
````

### Creating a Management Repository

