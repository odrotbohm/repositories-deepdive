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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.olivergierke.deepdive.CustomerRepository;

/**
 * Integration tests for {@link CustomerRepository}.
 * 
 * @author Oliver Gierke
 * @since Step 2
 */
public class CustomerRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	CustomerRepository repository;

	/**
	 * @since Step 2.1
	 */
	@Test
	public void findsCustomerById() {

		Customer customer = repository.findOne(1L);

		assertThat(customer, is(notNullValue()));
		assertThat(customer.getFirstname(), is("Dave"));
		assertThat(customer.getLastname(), is("Matthews"));
	}

	/**
	 * @since Step 2.2
	 */
	@Test
	public void savesNewCustomer() {

		Customer stefan = new Customer("Stefan", "Lassard");
		Customer result = repository.save(stefan);

		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getFirstname(), is("Stefan"));
		assertThat(result.getLastname(), is("Lassard"));
	}

	/**
	 * @since Step 2.2
	 */
	@Test
	public void savesExistingCustomer() {

		Customer dave = repository.findOne(1L);
		dave.setEmailAddress(new EmailAddress("davematthews@dmband.com"));
		repository.save(dave);

		Customer result = repository.findOne(1L);

		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getFirstname(), is("Dave"));
		assertThat(result.getEmailAddress(), is(new EmailAddress("davematthews@dmband.com")));
	}
}
