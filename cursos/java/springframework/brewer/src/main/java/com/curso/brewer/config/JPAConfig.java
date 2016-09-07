package com.curso.brewer.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.Cervejas;

@Configuration
@EnableJpaRepositories(basePackageClasses = Cervejas.class) // Habilitar a aplicação para repositorios. Busca através da classe(criando um vinculo) o pacote basico de repositorios 
public class JPAConfig {
	
	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true); // Configura a buscar dentro do conteiner de servlet(TOMCAT) a configuração de JNDI
		// O mesmo "name" do resource configura no arquivo context.xml
		return dataSourceLookup.getDataSource("jdbc/brewerDB");
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();		
		adapter.setDatabase(Database.MYSQL); // Qual o banco de dados que eu estou utilizando
		adapter.setShowSql(false); //mostrar os SQL. Neste caso, o SLF4j Vai ficar responsavel pelos logs do banco de dados.		
		adapter.setGenerateDdl(false); // não é para ficar criando ou atualizando o banco de dados, pq ja temos o Flyway para gerenciar o schema do DB
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect"); //para converter o dialeto do MySql para a linguagem ddl do hibernate
		return adapter;		
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		// gerenciador de entidades
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		// Qual o loca onde estão essas entidades
		// Informar o pacote onde se encontrao suas entidades
		// getPackage().getName() <-  sempre busca o nome do pacote onde se encotra suas entidades
		factory.setPackagesToScan(Cerveja.class.getPackage().getName());
		factory.afterPropertiesSet(); // aplicar
		return factory.getObject();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		// Repossavel pela gerenciamento de transação com o banco de dados
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
