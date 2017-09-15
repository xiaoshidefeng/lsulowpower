package com.example.demo;

import com.example.demo.domain.Power;
import com.example.demo.domain.User;
import com.example.demo.service.Impl.UserServiceImpl;
import com.example.demo.service.PowerService;
import com.example.demo.utils.CodeUtil;
import com.example.demo.utils.IsNull;
import com.example.demo.utils.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LsulowpowerApplicationTests {

//	@Autowired
//	private MailService mailService;
//
//	@Autowired
//	private MailUtil mailUtil;
//
//	@Autowired
//	private TemplateEngine templateEngine;
//
//	@Autowired
//	private UserServiceImpl userService;

//	@Autowired
//	private PowerService powerService;

	@Test
	public void totest() {
//		List<Power> list1 = powerService.findAll();
//		System.out.println("第一次查询：" + list1.get(0));
//
//		List<Power> list2 = powerService.findAll();
//		System.out.println("第二次查询：" + list2.get(0));


	}
//
//	@Test
//	public void testSimpleMail() throws Exception {
//		mailService.sendSimpleMail("1330661071@qq.com","test simple mail"," hello this is simple mail");
//	}
//
//	@Test
//	public void sendTemplateMail() {
//		//创建邮件正文
//		Context context = new Context();
//		context.setVariable("id", "006");
//		String emailContent = templateEngine.process("emailTemplate", context);
//
//		mailService.sendHtmlMail("1330661071@qq.com","主题：这是模板邮件",emailContent);
//	}


//	@Test
//	public void sendRegister() {
////		BindingResult bindingResult;
//		User user = new User();
//		user.setUserEmail("1330661071@qq.com");
//		user.setPassword("123123123");
//		String code = CodeUtil.generateCode(user.getUserEmail());
//		user.setUserState(0);
//		user.setUserCode(code);
//
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//		user.setRegisterTime(df.format(new Date()));
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				mailUtil.sendRegisterMail(user.getUserEmail(), code);
//			}
//		}).start();
//
////		mailUtil.sendRegisterMail(user.getUserEmail(), code);
//	}



}
