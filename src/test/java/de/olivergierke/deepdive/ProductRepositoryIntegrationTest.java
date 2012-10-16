/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.olivergierke.deepdive;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Integration tests for {@link ProductRepository}.
 * 
 * @author Oliver Gierke
 * @since Step 6
 */
public class ProductRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	ProductRepository repository;

	/**
	 * @since Step 6.1
	 */
	@Test
	public void findsAllProducts() {

		List<Product> findAll = repository.findAll();
		assertThat(findAll, hasSize(3));
	}

	/**
	 * @since Step 6.2
	 */
	@Test
	public void findsAllAppleProductPaged() {

		Page<Product> products = repository.findByDescriptionContaining("Apple", new PageRequest(0, 1));

		assertThat(products.isFirstPage(), is(true));
		assertThat(products.hasNextPage(), is(true));
		assertThat(products, Matchers.<Product> hasItem(hasProperty("name", is("iPad"))));
		assertThat(products, not(Matchers.<Product> hasItem(hasProperty("name", is("Dock")))));
	}

	/**
	 * @since Step 7
	 */
	@Test
	public void executesManuallyDeclaredQuery() {

		List<Product> products = repository.findByAttributeAndValue("connector", "plug");

		assertThat(products, Matchers.<Product> hasItem(hasProperty("name", is("Dock"))));
	}

	/**
	 * @since Step 9
	 */
	@Test
	public void executesCustomlyImplementedMethod() {

		repository.removeProductsMoreExpensiveThan(new BigDecimal(500));

		List<Product> result = repository.findAll();

		assertThat(result, hasSize(2));
		assertThat(result, not(Matchers.<Product> hasItem(hasProperty("name", is("Mac Book Pro")))));
	}
}
