# commons-ats
[![Build Status][travis-icon]][travis-page] [![Coverage Status][coverall-icon]][coverall-page] [![Maven Central][maven-icon]][maven-page]

Miscellaneous code snippets for lazy developers.


## Overview

Commons ATS is a collection of generic code snippets, providing 
boilerplate codes out-of-the-box to prevent reinventing the wheels.

Although the standard Java library and 3rd party libraries are getting better and better, 
there are still some codes which are repeatedly reimplemented across various projects. 
This project is intended to externalize those boilerplate codes 
and keep things [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself).

*'Don't repeat yourself. Life is too short to reinvent the wheels. Again, don't repeat yourself.*
 

## Modules

The libraries are split into multiple submodules, so that the transitive dependencies are kept minimal.
Each modules are aimed to provide support for specific libraries. 
Refer to each module's `README.md` documentation for the details.


|Module                                      |Libraries                            |
|--------------------------------------------|-------------------------------------|
|[commons-base](tree/master/base)            |JDK 1.6                              |
|[commons-compress](tree/master/compress)    |org.apache.commons : commons-compress|
|[commons-csv](tree/master/csv)              |net.sf.opencsv : opencsv             |
|[commons-guava](tree/master/guava)          |com.google.guava : guava             |
|[commons-jcommander](tree/master/jcommander)|com.beust : jcommander               |
|[commons-log](tree/master/log)              |commons-logging : commons-logging    |
|[commons-spring](tree/master/spring)        |org.springframework : spring-context |
|[commons-thrift](tree/master/thrift)        |org.apache.thrift : libthrift        |
|[commons-ui](tree/master/ui)                |JDK 1.6 (AWT, sound, ...)            |


[travis-page]:https://travis-ci.org/after-the-sunrise/commons-ats
[travis-icon]:https://travis-ci.org/after-the-sunrise/commons-ats.svg?branch=master
[coverall-page]:https://coveralls.io/github/after-the-sunrise/commons-ats?branch=master
[coverall-icon]:https://coveralls.io/repos/github/after-the-sunrise/commons-ats/badge.svg?branch=master
[maven-page]:https://maven-badges.herokuapp.com/maven-central/com.after_sunrise.commons/commons-ats
[maven-icon]:https://maven-badges.herokuapp.com/maven-central/com.after_sunrise.commons/commons-ats/badge.svg
