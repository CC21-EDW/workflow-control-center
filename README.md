# Workflow Control Center

## Run the App
```shell
gradle bootRun
```

## Docker
Run following tasks to run the application in a docker container:

```shell
gradle jar

docker build -t wcc .
docker run wcc
```

## Access the UI
[http://localhost:8080/](http://localhost:8080/)

## OpenAPI
- [http://localhost:8080/v3/api-docs/](http://localhost:8080/v3/api-docs/)
- [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Client API generation
The Spring Boot build will create an OpenApi specification file as part of
```groovy
implementation 'org.springdoc:springdoc-openapi-ui:1.5.12'
```

The generated [openapi.yml](generated/api/openapi.yaml) describing
the "Workflow Control Center" API is then used to generate the TypeScript API client. This is done
in the [npm prebuild step](src/main/webapp/package.json) utilizing the npm
dependency `@openapitools/openapi-generator-cli`:
```shell
openapi-generator-cli generate
```

This will read [openapitools.json](src/main/webapp/openapitools.json), specifying the generator and its configuration.
