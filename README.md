# asfa

This application was generated using JHipster 8.7.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v8.7.1](https://www.jhipster.tech/documentation-archive/v8.7.1).

![Static Badge](https://img.shields.io/badge/Jhipster-v8.7.1-blue)
![Static Badge](https://img.shields.io/docker/pulls/spacecowboy72/asfa)
![Docker Image Version](https://img.shields.io/docker/v/spacecowboy72/asfa)
![Docker Image Size](https://img.shields.io/docker/image-size/spacecowboy72/asfa)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=bugs)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=coverage)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=noobmaster122_asfa&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=noobmaster122_asfa)

## Branch Naming Conventions

Follow these naming conventions for consistent branch management:

- **Features**: `feature/short-description`

  - Example: `feature/add-user-auth`

- **Bug Fixes**: `bugfix/short-description`

  - Example: `bugfix/fix-login-error`

- **Hotfixes**: `hotfix/short-description`
  - Example: `hotfix/critical-security-fix`

**Best Practices**:

- Use lowercase letters with words separated by dashes (`-`).
- Keep names descriptive yet concise.
- Include issue/ticket ID if applicable (e.g., `feature/1234-add-search`).

### Commit Message Conventions

Follow these conventions for clear and consistent commit messages:

- **Structure**: `<type>: <short description>`

  - Example: `feat: add user authentication`

- **Types**:

  - `feat`: New features (e.g., `feat: implement search functionality`)
  - `fix`: Bug fixes (e.g., `fix: resolve login error`)
  - `docs`: Documentation updates (e.g., `docs: update README`)
  - `style`: Code formatting (e.g., `style: apply consistent indentation`)
  - `refactor`: Code refactoring (e.g., `refactor: simplify API response`)
  - `test`: Adding/updating tests (e.g., `test: add unit tests for service`)
  - `chore`: Maintenance tasks (e.g., `chore: update dependencies`)

- **Best Practices**:
  - Keep messages concise and descriptive.
  - Use present tense (e.g., `add`, not `added`).
  - Limit the short description to 50 characters.
  - Add a detailed body if necessary, separated by a blank line.

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husky, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if omitted) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files

- `npmw` - wrapper to use locally installed npm.
  JHipster installs Node and npm locally using the build tool by default. This wrapper makes sure npm is installed locally and uses it avoiding some differences different versions can cause. By using `./npmw` instead of the traditional `npm` you can configure a Node-less environment to develop or test your application.
- `/src/main/docker` - Docker configurations for the application and services that the application depends on

## Development

### Doing API-First development using openapi-generator-cli

[OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:

```bash
./mvnw generate-sources
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.
The build system will install automatically the recommended version of Node and npm.

We provide a wrapper to launch npm.
You will only need to run this command when dependencies change in [package.json](package.json).

```
./npmw install
```

We use npm scripts and [Angular CLI][] with [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
./npmw start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `./npmw update` and `./npmw install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `./npmw help update`.

The `./npmw run` command will list all the scripts available to run for this project.

### PWA Support

JHipster ships with PWA (Progressive Web App) support, and it's turned off by default. One of the main components of a PWA is a service worker.

The service worker initialization code is disabled by default. To enable it, uncomment the following code in `src/main/webapp/app/app.config.ts`:

```typescript
ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
```

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

```
./npmw install --save --save-exact leaflet
```

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

```
./npmw install --save-dev --save-exact @types/leaflet
```

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/app.config.ts](src/main/webapp/app/app.config.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import 'leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

```
ng generate component my-component
```

will generate few files:

```
create src/main/webapp/app/my-component/my-component.component.html
create src/main/webapp/app/my-component/my-component.component.ts
update src/main/webapp/app/app.config.ts
```

## Building for production

### Packaging as jar

To build the final jar and optimize the asfa application for production, run:

```
./mvnw -Pprod clean verify
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

### JHipster Control Center

JHipster Control Center can help you manage and control your application(s). You can start a local control center server (accessible on http://localhost:7419) with:

```
docker compose -f src/main/docker/jhipster-control-center.yml up
```

## Testing

### Spring Boot tests

To launch your application's tests, run:

```
./mvnw verify
```

### Client tests

Unit tests are run by [Jest][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```
./npmw test
```

UI end-to-end tests are powered by [Cypress][]. They're located in [src/test/javascript/cypress](src/test/javascript/cypress)
and can be run by starting Spring Boot in one terminal (`./mvnw spring-boot:run`) and running the tests (`./npmw run e2e`) in a second one.

#### Lighthouse audits

You can execute automated [Lighthouse audits](https://developers.google.com/web/tools/lighthouse/) with [cypress-audit](https://github.com/mfrachet/cypress-audit) by running `./npmw run e2e:cypress:audits`.
You should only run the audits when your application is packaged with the production profile.
The lighthouse report is created in `target/cypress/lhreport.html`

## Others

### Code quality using Sonar

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off forced authentication redirect for UI in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

Additionally, Instead of passing `sonar.password` and `sonar.login` as CLI arguments, these parameters can be configured from [sonar-project.properties](sonar-project.properties) as shown below:

```
sonar.login=admin
sonar.password=admin
```

For more information, refer to the [Code quality page][].

### Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
npm run java:docker
```

Or build a arm64 docker image when using an arm64 processor os like MacOS with M1 processor family running:

```
npm run java:docker:arm64
```

Then run:

```
docker compose -f src/main/docker/app.yml up -d
```

When running Docker Desktop on MacOS Big Sur or later, consider enabling experimental `Use the new Virtualization framework` for better processing performance ([disk access performance is worse](https://github.com/docker/roadmap/issues/7)).

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[JHipster Homepage and latest documentation]: https://www.jhipster.tech
[JHipster 8.7.1 archive]: https://www.jhipster.tech/documentation-archive/v8.7.1
[Using JHipster in development]: https://www.jhipster.tech/documentation-archive/v8.7.1/development/
[Using Docker and Docker-Compose]: https://www.jhipster.tech/documentation-archive/v8.7.1/docker-compose
[Using JHipster in production]: https://www.jhipster.tech/documentation-archive/v8.7.1/production/
[Running tests page]: https://www.jhipster.tech/documentation-archive/v8.7.1/running-tests/
[Code quality page]: https://www.jhipster.tech/documentation-archive/v8.7.1/code-quality/
[Setting up Continuous Integration]: https://www.jhipster.tech/documentation-archive/v8.7.1/setting-up-ci/
[Node.js]: https://nodejs.org/
[NPM]: https://www.npmjs.com/
[OpenAPI-Generator]: https://openapi-generator.tech
[Swagger-Editor]: https://editor.swagger.io
[Doing API-First development]: https://www.jhipster.tech/documentation-archive/v8.7.1/doing-api-first-development/
[Webpack]: https://webpack.github.io/
[BrowserSync]: https://www.browsersync.io/
[Jest]: https://facebook.github.io/jest/
[Cypress]: https://www.cypress.io/
[Leaflet]: https://leafletjs.com/
[DefinitelyTyped]: https://definitelytyped.org/
[Angular CLI]: https://cli.angular.io/
