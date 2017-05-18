package com.jgreenlight.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgreenlight.core.base.TestCase;


@RestController
@RequestMapping("/testcases")
public class TestCaseController {

	@Autowired
	public TestCaseController() {
	}

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<TestCase> listAllTestCases() throws Exception {
		return Arrays.asList(new TestCase(1, "FooTest"),new TestCase(2, "BarTest"));
	}

}
