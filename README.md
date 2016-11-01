# Commune [![Build Status](https://travis-ci.org/paulrevere4/commune.svg?branch=master)](https://travis-ci.org/paulrevere4/commune)
An issue tracker for real life. This a project for RPI's [SD&D](https://sites.google.com/site/rpisdd/home) class.

## Contributing
The project is built on Travis CI with each push to this repository and with each pull request. To test the android build, run the following from the project's top directory, you must have docker installed.
```
$ docker build -t android_app android/
```
To test the ios build, run the following from the project's top directory, you must have docker installed.
```
$ docker build -t ios_app ios/
```
