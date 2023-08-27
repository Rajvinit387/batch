package com.example.batch.config;

import javax.sql.DataSource;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.apache.catalina.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.retry.stats.DefaultStatisticsRepository;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.batch.model.user;

@Configuration
@EnableBatchProcessing
public class batchconfig {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager transactionManager;


	@Autowired
	private DataSource dataSource;

	
	
	
	
	
	@Bean
	public FlatFileItemReader<user> reader()
	{
		FlatFileItemReader reader=	new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("addresses1.csv"));
		reader.setLineMapper(getLineMapper());
		reader.setLinesToSkip(1);
		return reader;
		
	}



	
	@Bean
	public LineMapper<user> getLineMapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<user> defaultLineMapper=new DefaultLineMapper<>();
		
		DelimitedLineTokenizer d=new DelimitedLineTokenizer();
		
		d.setNames(new String[] {"ID","FIRSTNAME","SECONDNAME","STREET","CITY","STATE CODE","PINCODE"});
		d.setIncludedFields(new int[] {0,1,2,3,4,5,6});
		
		
		
		BeanWrapperFieldSetMapper fieldsetter=	new BeanWrapperFieldSetMapper<user>();
		
		fieldsetter.setTargetType(user.class);
		defaultLineMapper.setLineTokenizer(d);
		defaultLineMapper.setFieldSetMapper(fieldsetter);
		return defaultLineMapper;
		
		
		
	}
	
	
	@Bean
	public userItemprocessor processor()
	{
		return new userItemprocessor();
	}
	
	
	@Bean
	public JdbcBatchItemWriter<user> writer()
	{
	JdbcBatchItemWriter<user> jdbcBatchItemWriter=	new JdbcBatchItemWriter<>();
	jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<user>());
	jdbcBatchItemWriter.setSql("insert into user(userId, firstname, secondname,street,city,state_code,pincode) values (:userId, :firstname, :secondname,:street,:city,:state_code,:pincode)");
	jdbcBatchItemWriter.setDataSource(this.dataSource);
	return jdbcBatchItemWriter;
	}
	
	
	
	

	
	

	/*
	@Bean
	public Job importUserJob()
	{
		return this.jobBuilderFactory.get("USER-IMPORT-JOB");
		.incrementer(new RunIdIncrementer())
			.flow(step1()).end().build();
		
				
	}


	@Bean
	public Step step1() {
		this.stepBuilderFactory.get("step1").
				<User, User>chunk(10)
				.reader(reader()).processor(processor()).writer(writer())
				.build();
	}



	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<User, User>chunk(10, transactionManager)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	public Job runJob() {
		return new JobBuilder("USER-IMPORT-JOB", jobRepository)
				.start(step1())
				.build();
	}

	 */



	@Bean
	public Step step1(PlatformTransactionManager transactionManager) {
		// TODO Auto-generated method stub
		return new StepBuilder("mystep", this.jobRepository).<user,user>chunk(10, transactionManager).reader(reader())
				.processor(processor()).
				writer(writer()).build();
	}


	@Bean
	public Job importJob( Step step1)
	{

		return new JobBuilder("myjob",this.jobRepository).incrementer(new RunIdIncrementer())
				.flow(step1).end().build();


	}



}
