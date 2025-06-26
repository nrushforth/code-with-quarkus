# code-with-quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


# Setup for Spanner Emulator

```shell script
docker run -d --rm -p 9010:9010 -p 9020:9020 --name spanner-emulator gcr.io/cloud-spanner-emulator/emulator
```

```shell script
docker exec -it spanner-emulator /bin/bash
```

```shell script
gcloud config configurations activate emulator
```
Activated [emulator].
```shell script
gcloud config set auth/disable_credentials true
```
Updated property [auth/disable_credentials].
```shell script
gcloud config set project banking-project
```
WARNING: UNAUTHENTICATED: Request is missing required authentication credential. Expected OAuth 2 access token, login cookie or other valid authentication credential. See https://developers.google.com/identity/sign-in/web/devconsole-project. This command is unauthenticated because the [auth/disable_credentials] property is True.
- '@type': type.googleapis.com/google.rpc.ErrorInfo
  domain: googleapis.com
  metadata:
  method: google.cloudresourcemanager.v1.Projects.GetProject
  service: cloudresourcemanager.googleapis.com
  reason: CREDENTIALS_MISSING
  Are you sure you wish to set property [core/project] to banking-project?

Do you want to continue (Y/n)?  y

Updated property [core/project].
```shell script
gcloud spanner instances create banking-instance --config=emulator-config --description="Banking instance" --nodes=1                                                          
```
Creating instance...done.                                                                                                                                                                                                                                                                                                           
```shell script
gcloud spanner databases create banking-database --instance banking-instance                                                                                                  
```
Creating database...done.                                                                                                                                                                                                                                                                                                           
```shell script
gcloud spanner databases ddl update banking-database --instance=banking-instance --ddl='CREATE TABLE Customers (CustomerId INT64 NOT NULL, Name STRING(1024)) PRIMARY KEY (CustomerId)'
```
Schema updating...done.                                                                                                                                                                                                                                                                                                             
 
