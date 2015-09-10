[![Maven central][maven img]][maven]
[![][travis img]][travis]

Watchrabbit - Scanner
=====================

Vulnerability scanner with HTML5 / JavaScript support.

## Powered by [watchrabbit.com]

## Current release
24/05/2015 rabbit-scanner **0.8.1** released! Should appear in maven central shortly.

## Running scanner
In order to run scanner add depenencies to all modules of this application, and annotate your configuration class with `@EnableSupervisiorService` and `@EnableAttackerService`. Then, if you want to implement custom result processing strategy implement `ResultProcessingStrategy`. 
As a final step add site to testing using `OrderService`.

 ## Building project
 
 In order to build project run `mvn clean install`.
 
[watchrabbit.com]:http://watchrabbit.com
[travis]:https://travis-ci.org/watchrabbit/rabbit-scanner
[travis img]:https://travis-ci.org/watchrabbit/rabbit-scanner.svg?branch=master
[maven]:https://maven-badges.herokuapp.com/maven-central/com.watchrabbit/rabbit-scanner
[maven img]:https://maven-badges.herokuapp.com/maven-central/com.watchrabbit/rabbit-scanner/badge.svg
