package com.et;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

/**
 * LoadBalancerClient是负载均衡的主对象
 * @author Administrator
 *
 */
@Controller
public class FignController {
	@Autowired
	private FignSendMail fsm;
	@Autowired
	private LoadBalancerClient lba;
	/**
	 * 测试是否负载均衡
     * 启动多个发布者 端口不一致 程序名相同   
     * 使用  
     * @LoadBalanced必须添加  
     * @return  
     */  
    @RequestMapping("choosePub") 
    @ResponseBody
    public String choosePub() {  
        StringBuffer sb=new StringBuffer();  
        for(int i=0;i<=10;i++) {  
            ServiceInstance ss=lba.choose("SENDMAIL");//从两个idserver中选择一个 这里涉及到选择算法  
            sb.append(ss.getUri().toString()+"<br/>");  
        }  
        return sb.toString();  
    }  
    /**
     * 请求调用sendmail的/user/这个请求
     * @return
     */
    @ResponseBody
    @GetMapping("/invokeUser")
    public String invokeUser(String id){
    	Map result=fsm.getUser(id);
    	return result.get("name").toString();
    }
    /**
     * get请求特点通过id获取资源 例如/user/1
     * post  /user 请求体中带上参数{}
     */
	@GetMapping("/sendClient")
	public String send(String email_to,String email_subject,String email_content){
		//调用SENDMAIL服务
		String controller="/send";
		//通过注册中心客户端负载均衡 获取一台主机来调用
		try {
			
			controller+="?email_to="+email_to+"&email_subject="+email_subject+"&&email_content="+email_content;
			//String result=restTemplate.getForObject("http://SENDMAIL"+controller, String.class);
			
			//第二种方法
			/*ServiceInstance ss=lba.choose("SENDMAIL");
			String uri=ss.getUri().toString();
			String result=restTemplate.getForObject(uri+controller, String.class,email_to,email_subject,email_content);*/
		} catch (RestClientException e) {
			
			e.printStackTrace();
			return "redirect:/error.html";
		}
		return "redirect:/suc.html";
	}
	@GetMapping("/postSendClient")
	public String Psend(String email_to,String email_subject,String email_content){
		//调用SENDMAIL服务
		String controller="/send";
		//通过注册中心客户端负载均衡 获取一台主机来调用
		try {
			//调用的代码
	        Map<String, Object> map=new HashMap<String, Object>();  
			map.put("email_to", email_to);
			map.put("email_subject", email_subject);
			map.put("email_content", email_content);
			fsm.send(map);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/error.html";
		}
		return "redirect:/suc.html";
	}
}
