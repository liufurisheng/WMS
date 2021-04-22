package com.wms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.wms.repository")
@ServletComponentScan
public class WmsLiufuApplication {

	public static void main(String[] args) {
		SpringApplication.run(WmsLiufuApplication.class, args);
	}

}
