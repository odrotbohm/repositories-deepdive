# Spring Data Repositories - A Deep Dive

This repository contains the sample code for the _"Spring Data Repositories - A Deep Dive"_ presentation that demonstrates the feature of the Spring Data repositories abstraction in detail.

The repository is not a continuous one in the classical sense. Every commit represents a step in the presentation and implements a new requirement as the code continues to grow.

__Attention:__ Expect the repository to be rebased at any time as changes to the codebases have to be applied to the commit the relevant code was introduced.

## The individual steps in detail

The domain model is a tiny CRM system in the first place but will be extended into an e-commerce system later on. Core abstractions are `Customer`s that have an `EmailAddress` as well as `Address`es, `Product`s and `Order`s carrying `LineItem`s placed by `Customers`.

### Step 0 - Basic project setup

__Objective:__ Learn how to quickly create a new Maven based project using Spring Boot for easy dependency management and Spring auto configuration.

> How to get a Spring Data JPA based project up and running quickly

- Create `pom.xml`
    - `spring-boot-starter-parent` - defaults dependency versions
    - `spring-boot-starter-data-jpa` - declares default dependencies (Spring Data JPA, Hibernate 4, Spring 4)
- Sample domain classes

### Step 1 - Basic JPA infrastructure setup

__Objective:__ Learn in how far Boot simplifies the setup and the configuration of an `ApplicationContext`. Learn about JPA extensions introduced with Spring 3.1, and possibilities to populate a `DataSource`.

> Persistence technology of choice is JPA. The application uses JavaConfig and sample data contained in data.sql

- Add JavaConfig with `@EnableAutoConfiguration` to let Boot automatically configure an HSQL `DataSource`, a `LocalContainerEntityManagerFactoryBean` for Hibernate as well as a JPA transaction manager.
- Add `application.properties` to tweak details of the auto-configuration
- Add integration test to bootstrap application
- Add integration base test to populate the database with `data.sql`
- Explain ResourceDatabasePopulator

### Step 2 - Quickstart

__Objective:__ Show basics of interface based programming model, query derivation and infrastructure setup.

> The implementation of the persistence layer will be based on the Spring Data repositories abstraction. Customers can be saved, looked up by their id, email address.

- Add `CustomerRepository extends Repository<Customer, Long>`
- Add methods `findOne(…)`, `save(…)`, `findByEmailAddress(String emailAddress)`
- `@EnableJpaRepositories` / `<jpa:repositories />`
- Add `CustomerRepositoryIntegrationTest` to execute repository methods
- Show IDE integration

### Step 3 - Extended CRUD methods

__Objective:__ Explain CRUD interface.

> Customers can be deleted and obtained all at once

- Move to `CustomerRepository extends CrudRepository<Customer, Long>`
- Discuss consequences
  - *all* CRUD method exposed, flexibility on the client side

### Step 4 - Pagination

__Objective:__  Explain PagingAndSortingRepository interface.

> Customers can be accessed page by page.

- Switch to `CustomerRepository extends PagingAndSortingRepository<Customer, Long>`
- Discuss consequences
  - *even more* methods exposed, but maximum flexibility for clients

### Step 5 - Re-declaring existing CRUD methods

__Objective:__ Show how users can tweak the configuration of CRUD methods or even alter return types if necessary.

> `CustomerRepository.findAll()` should rather return a `List`. The transaction timeout for `save(…)` should be customized to 10 seconds.

- Re-declare `findAll` and use `List` as return type
- Re-declare `save(…)` and annotate with `@Transactional`

### Step 6 - Introducing a read-only repository base interface

__Objective:__ Explain possibilities to define custom repository base interfaces.

> Orders shall be accessible in read-only mode only.

- Introduce `ReadOnlyRepository` base interface
  - Include reading methods of `CrudRepository` and `PagingAndSortingRepository`
  - Fix ID type to `Long`
- Introduce `ProductRepository extends ReadOnlyRepository<Product>`

### Step 7 - Using manually defined queries

__Objective:__ Learn how to manually define a query using the `@Query` annotation or named queries

> As a user, I want to look up products by their custom attributes

- Introduce the `@Query` annotation
- Mention `jpa-named-queries.properties`

### Step 8 - Flexible predicate execution

__Objective:__ Learn how to define atomic business predicates and execute them in flexible ways.

> As a user, I want to search for customers by first name, last name, email address and any combination of them

- Introduce Querydsl
  - Add Querydsl dependency and set up APT as well as IDE
  - Show generated query classes
  - Add `QuerydslPredicateExecutor`
  - Show usage in test case

### Step 9 - Custom implementation for  repositories

__Objective:__ Lear how to extend a repository with manually implemented code.

> As an admin user, I'd like to delete all products beyond a given price.

- Add `ProductRepositoryCustom` to declare custom method
- Add custom implementation extending `QueryDslRepositorySupport`
