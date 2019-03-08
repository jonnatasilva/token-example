package com.jonnatas.token.example.resourceserver;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jonnatas.token.example.resourceserver.controller.FooController;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceServerApplicationTest.class)
public abstract class BaseClass {
	
	@Autowired 
	FooController fooController;

	@Before 
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(fooController);
	}
}
