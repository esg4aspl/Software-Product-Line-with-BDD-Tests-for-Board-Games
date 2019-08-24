# Setting Up Cucumber for Eclipse 

This tutorial assumes that you have Maven and Eclipse IDE for Java already installed on your system.

> Follow this tutorial to install Maven if you don't have it: [https://www.onlinetutorialspoint.com/maven/how-to-install-maven-on-windows10.html](https://www.onlinetutorialspoint.com/maven/how-to-install-maven-on-windows10.html)

> The source for this tutorial can be found here: [https://cucumber.io/docs/guides/10-minute-tutorial/](https://cucumber.io/docs/guides/10-minute-tutorial/)

If your Eclipse project is not a Maven project, right click on the project name -> Configure -> Convert to Maven project.
Add the following dependencies to your pom.xml:

```
<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-java</artifactId>
	<version>4.3.1</version>
</dependency>

<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-junit</artifactId>
	<version>4.3.1</version>
	<scope>test</scope>
</dependency>
```

If you still receive errors regarding Cucumber.class, add the jar manually to your library from mvn repository: [https://mvnrepository.com/artifact/io.cucumber/cucumber-junit/4.3.1](https://mvnrepository.com/artifact/io.cucumber/cucumber-junit/4.3.1)

Now your Cucumber installation should be complete.

When you run your TestRunner class as a JUnit test, it matches the scenarios in .feature files with methods in StepDefinitions class by annotations.

# Integrating Hiptest
> The source for this tutorial: 
> [https://github.com/hiptest/hiptest-publisher#using-local-installation](https://github.com/hiptest/hiptest-publisher#using-local-installation)

We need Hiptest Publisher on our machine. And for that we need to have Ruby.

Follow this link for Ruby installation: [https://www.tutorialspoint.com/ruby-on-rails/rails-installation.htm](https://www.tutorialspoint.com/ruby-on-rails/rails-installation.htm)

Check your Ruby installation with the following command: 
```
ruby -v
```
After it's installed properly, install the Hiptest Publisher:
```
gem install hiptest-publisher
```
<br/>

## Getting tests from Hiptest to workspace

The following command will push your tests from Hiptest to your development environment based on the specifications of hiptest-publisher.conf file.
```
hiptest-publisher -c hiptest-publisher.config
```
An example .conf file looks like this:
```
token = <YOUR_PROJECTS_SECRET_TOKEN>
not_recursive = false
split_scenarios = false
with_folders = true
keep_filenames = false
keep_foldernames = false
empty_folders = false
leafless_export = false
no_uids = false
no_parent_folder_tags = false
with_dataset_names = false
language = cucumber
output_directory = './resources/features'
framework = java
site = https://app.hiptest.com
step_definitions_output_directory = './src/testing/stepdefinitions'
actionwords_output_directory = './src/testing/stepdefinitions'
package = 'testing.stepdefinitions'
```
After these tests are first published to our workspace, we don't need to re-publish the Actionwords class everytime because it will be modified in our local system with methods.
<br/>

## Uploading test results to Hiptest
From now on we'll run the following command before uploading our test results to Hiptest:
```
hiptest-publisher --config-file hiptest-publisher.conf --test-run-id <TEST_RUN_ID> --without=actionwords
```

Your test-run-id is in the link. For example, here the test-run id is 999999:
```
https://app.hiptest.com/projects/000000/test-runs/999999/
```
<br/>

After you run your tests, the results json file will be generated, in this project we generate them in target/cuke-results.json file as indicated by the following line in TestRunner.class:
```
@CucumberOptions(features="./resources/features", glue="testing/stepdefinitions", plugin = {"pretty", "json:target/cuke-results.json"})
```
<br/>

And with this final line, the latest test results will be uploaded to Hiptest:
```
hiptest-publisher --config-file hiptest-publisher.conf --push target/cuke-results.json --test-run-id <TEST_RUN_ID> --push-format junit
```
