package com.connor.taotie.container;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.connor.taotie.dto.SupperMan;

/**
 * 用于测试Spring的各种容器
 */
// @SLF4J 这个怎么弄呢?
public class SpringTest {


  @Test
  public void testXMLFactory() {

    // 直接加载SpringBean容器
    BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring-bean.xml"));

    // get hello world bean
    SupperMan helloSupperMan = (SupperMan) bf.getBean("helloSupperMan");
    helloSupperMan.userWenpon();


  }
}
