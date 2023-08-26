package com.example.batch.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.batch.model.user;

public class userItemprocessor implements ItemProcessor<user, user> {

	@Override
	public user process(user user) throws Exception {
		// TODO Auto-generated method stub
		return user;
	}

}
