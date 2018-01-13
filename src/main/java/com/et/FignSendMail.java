package com.et;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * 使用FeignClient 告知发布方的应用名称 默认使用ribbon进行负载均衡  
 * @author Administrator
 *
 */
@FeignClient("SENDMAIL")
public interface FignSendMail {
	@GetMapping("/user/{userId}")
	public Map getUser(@PathVariable("userId") String userId);//因为是调用必须明确告诉是GET方式传入的参数是userId 
	@PostMapping("/send")
	public String send(@RequestBody Map<String,Object> map);//post请求必须添加@RequestBody  
}
