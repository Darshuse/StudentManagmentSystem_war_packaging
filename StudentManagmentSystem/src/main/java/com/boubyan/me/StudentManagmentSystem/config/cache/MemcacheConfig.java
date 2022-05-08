package com.boubyan.me.StudentManagmentSystem.config.cache;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.spy.memcached.MemcachedClient;

@EnableCaching
@Configuration
public class MemcacheConfig extends CachingConfigurerSupport {

	@Value("${memcached.port}")
	private Integer port;
	@Value("${memcached.ip}")
	private String ip;

	@Bean
	public MemcachedClient memcachedClient() {
		try {
			return new MemcachedClient(new InetSocketAddress(ip, port));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
