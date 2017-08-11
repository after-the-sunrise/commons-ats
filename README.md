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


|Modules                    |Libraries                            |
|---------------------------|-------------------------------------|
|[commons-base][base]       |JDK 1.6                              |
|[commons-compress][comp]   |org.apache.commons : commons-compress|
|[commons-csv][csv_]        |net.sf.opencsv : opencsv             |
|[commons-guava][guav]      |com.google.guava : guava             |
|[commons-jcommander][jcom] |com.beust : jcommander               |
|[commons-log][log_]        |commons-logging : commons-logging    |
|[commons-spring][sprg]     |org.springframework : spring-context |
|[commons-thrift][thft]     |org.apache.thrift : libthrift        |
|[commons-ui][ui__]           |JDK 1.6 (AWT, sound, ...)            |

[base]:https://github.com/after-the-sunrise/commons-ats/tree/master/base
[comp]:https://github.com/after-the-sunrise/commons-ats/tree/master/compress
[csv_]:https://github.com/after-the-sunrise/commons-ats/tree/master/csv
[guav]:https://github.com/after-the-sunrise/commons-ats/tree/master/guava
[jcom]:https://github.com/after-the-sunrise/commons-ats/tree/master/jcommander
[log_]:https://github.com/after-the-sunrise/commons-ats/tree/master/log
[sprg]:https://github.com/after-the-sunrise/commons-ats/tree/master/spring
[thft]:https://github.com/after-the-sunrise/commons-ats/tree/master/thrift
[ui__]:https://github.com/after-the-sunrise/commons-ats/tree/master/ui

[travis-page]:https://travis-ci.org/after-the-sunrise/commons-ats
[travis-icon]:https://travis-ci.org/after-the-sunrise/commons-ats.svg?branch=master
[coverall-page]:https://coveralls.io/github/after-the-sunrise/commons-ats?branch=master
[coverall-icon]:https://coveralls.io/repos/github/after-the-sunrise/commons-ats/badge.svg?branch=master
[maven-page]:https://maven-badges.herokuapp.com/maven-central/com.after_sunrise.commons/commons-ats
[maven-icon]:https://maven-badges.herokuapp.com/maven-central/com.after_sunrise.commons/commons-ats/badge.svg

