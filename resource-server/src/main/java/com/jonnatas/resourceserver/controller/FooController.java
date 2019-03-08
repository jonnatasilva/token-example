package com.jonnatas.resourceserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jonnatas.resourceserver.domain.Foo;

@RestController
public class FooController {
	
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping(value="/food/{id}")
	public Foo findById(@PathVariable long id) {
		return new Foo(1l, "Teste");
	}
	
}
