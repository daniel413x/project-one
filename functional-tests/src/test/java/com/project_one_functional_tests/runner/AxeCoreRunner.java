package com.project_one_functional_tests.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/features/axe-core")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/extent-report/axe-core-report.html, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.project_one_functional_tests.steps.base_steps")
public class AxeCoreRunner {
}
