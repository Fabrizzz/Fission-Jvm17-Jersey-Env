# Fission: Java and JVM-Jersey Environment

This is the JVM (Jersey based) environment for Fission.

It's a Docker image containing a OpenJDK17 runtime, along with a
dynamic loader. A few dependencies are included in the
pom.xml file.

Unlike the other [JVM environment](../jvm) which is based on the Spring framework, this environment uses Jersey.

Looking for ready-to-run examples? See the [JVM examples directory](../../examples/jvm-jersey).

## Customizing this image

To add package dependencies, edit pom.xml to add what you
need, and rebuild this image (instructions below).

## Rebuilding and pushing the image

You'll need access to a Docker registry to push the image: you can
sign up for Docker hub at hub.docker.com, or use registries from
gcr.io, quay.io, etc. Let's assume you're using a docker hub account
called USER. Build and push the image to the the registry:

```
   docker build -t USER/jvm-jersey-env . && docker push USER/jvm-jersey-env
```


## Using the image in fission

You can add this customized image to fission with "fission env
create":

```
   fission env create --name jvm --image USER/jvm-jersey-env
```

Or, if you already have an environment, you can update its image:

```
   fission env update --name jvm --image USER/jvm-jersey-env 
```

After this, fission functions that have the env parameter set to the
same environment name as this command will use this environment.

## Web Server Framework

JVM Jersey environment uses an embedded Jetty 11 server by default, as can be seen in the pom.xml file.

```
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-webapp</artifactId>
        </dependency>
```

## Java and JVM builder

The default build command
runs `mvn clean package` and uses the target/*with-dependencies.jar file for function. The default build command can be
overridden as long as the uber jar file is copied to ${DEPLOY_PKG}.
