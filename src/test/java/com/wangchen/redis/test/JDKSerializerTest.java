package com.wangchen.redis.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangchen.common.utils.DateUtil;
import com.wangchen.common.utils.RandomUitl;
import com.wangchen.common.utils.StringUtil;
import com.wangchen.domain.User;
/**
 * 
    * @ClassName: JDKSerializerTest
    * @Description: JDKSerializerTest
    * @author 王晨
    * @date 2019年8月12日
    *
 */
@ContextConfiguration(locations = "classpath:spring-beans.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JDKSerializerTest {

	@Resource
	private RedisTemplate<String,Object> redis;
	
	/**
	 * 
	    * @Title: Stringtest
	    * @Description: 方法测试
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	@Test
	public void Stringtest()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(1967, 0, 0);
		List<User> list = new ArrayList<User>();
		for (int i = 0; i <100000; i++) {
			list.add(new User(i, StringUtil.generateChineseName(), i%2==0?"男":"女", RandomUitl.randomString(9), ""+RandomUitl.randomString(RandomUitl.random(3, 20))+"@qq.com", ""+ DateUtil.randomDate(calendar.getTime())));
		}	
		
		long starttime = System.currentTimeMillis();
		for (User user : list) {
			redis.opsForValue().set(""+user.getId(), user);
		}
		long stoptime = System.currentTimeMillis();
		System.out.println("JDKSerializer序列化十万个对象所用时间"+(stoptime-starttime));
		
	}
	
	
}
