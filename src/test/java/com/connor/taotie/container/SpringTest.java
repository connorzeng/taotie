package com.connor.taotie.container;

import com.connor.taotie.dto.SupperMan;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * 用于测试Spring的各种容器
 */
@Slf4j
public class SpringTest {


    @Test
    public void testXMLFactory(){

        //直接加载SpringBean容器,xmlBeanFactory已经被deprecated
        //BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring-bean.xml"));
        //使用xmlBeanDefinitionReader来加载
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(new ClassPathResource("spring-bean.xml"));
        SupperMan helloSupperMan = (SupperMan) bf.getBean("helloSupperMan");
        System.out.println("SpringTest.testXMLFactory");
    }

    @Test
    public void testClassPathXmlApplicationContext(){
        
        log.info("testClassPathXmlApplicationContext-begin");
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring-bean.xml");
        GenericApplicationContext context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("spring-bean.xml");
        // 这里一定要进行一次refresh
        context.refresh();
        SupperMan helloSupperMan = (SupperMan) context.getBean("helloSupperMan");
    }


}
