# java-getting-started

A barebones Kotlin app, configured to give you a simple demo of how to get Kotlin running on heroku.

# Shortcut

Just click this: [![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/nafeger/heroku_kotlin_bootstrap/tree/java_bootstrap_kotlin)

# Source

This application was borrowed from heroku, and ported roughly as closely as possible to Kotlin.

Here's some basic docs oon the source project [Getting Started with Java on Heroku](https://devcenter.heroku.com/articles/getting-started-with-java).

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku Toolbelt](https://toolbelt.heroku.com/).

set a config value of the envegy calculation
```sh
heroku config:set KEY=ENERGY VALUE=37 GeV
```

Install the heroku config plugin to set up a local env
```sh
heroku plugins:install git://github.com/ddollar/heroku-config.git
```

[Pull that config](https://devcenter.heroku.com/articles/config-vars) from above into an env file
```sh
heroku config:pull --overwrite
```

```sh
$ git clone https://github.com/nafeger/kotlin-maven-getting-started.git
$ cd kotlin-maven-getting-started
$ mvn clean install
$ foreman start web
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

## Documentation

For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)

