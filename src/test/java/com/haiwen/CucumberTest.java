package com.haiwen;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/1 下午6:35</p>
 *
 * @author haiwen.li
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberTest {
}
