package com.example.idtk.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@ConfigurationProperties("classpath:log4j.properties")
public class LogFactory {
}
