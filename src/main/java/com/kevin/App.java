package com.kevin;

import com.kevin.dao.CustomerRepository;
import com.kevin.domain.Customer;
import com.kevin.domain.SysDict;
import com.kevin.mapper.SysDictMapper;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.System.exit;

/**
 * Hello world!
 * @SpringBootApplication 相当于开启了 @Configuration @EnableWebMvc
 * http://tengj.top/2017/03/09/springboot3/
 *
 * https://www.mkyong.com/spring-boot/spring-boot-spring-data-jpa-oracle-example/
 * http://tengj.top/2017/04/24/springboot0/
 *
 * https://www.mkyong.com/spring-boot/spring-boot-hello-world-example-jsp/
 * https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples
 */
//@tk.mybatis.spring.annotation.MapperScan(basePackages = "org.mybatis.spring.sample.mapper")
@SpringBootApplication
public class App implements CommandLineRunner
{
    public static void main( String[] args )
    {
            System.out.println(System.getProperty("file.encoding"));
//        System.out.println("false");
//        System.setProperty("spring.devtools.restart.enabled", "false");
//        System.setProperty("spring.devtools.restart.log-condition-evaluation-delta", "false");
        SpringApplication.run(App.class,args);
//
    }


    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerRepository customerRepository;

    static void readFile(){
        System.out.println(Charset.availableCharsets());
        String filePath = "D:\\shikaiwen\\fenghe\\在SC-在庫参照(品目別)(csv).csv";
        List<String> strings = FileUtil.readLines(new File(filePath), "Shift_JIS");

        List<Map<String, String>> linesMapList = new ArrayList<>();

        List<List<String>> listdataAll = new ArrayList<>();

        if (strings != null && strings.size() > 0) {

            String nameStart = "A";

            for (String str : strings) {
                Map<String,String> nameMap = new TreeMap();
                String sn = nameStart;
                String[] split = str.split(",");
                for (String s : split) {
                    nameMap.put(sn, s);
                    sn =  ((char)(sn.charAt(0)+1)) + "";

                }

                if("1000".equals(nameMap.get("A"))){
                    nameMap.put("A", "u");
                }
                nameMap.put("D", "");
                nameMap.put("E", "");
                linesMapList.add(nameMap);


                List<String> data = new ArrayList<>();
                data.add(nameMap.get("A"));
                data.add(nameMap.get("C"));
                data.add(nameMap.get("D"));
                data.add(nameMap.get("E"));
                data.add(nameMap.get("O"));
                listdataAll.add(data);

            }

            StringBuilder sb = new StringBuilder();

            List<String> headers = Arrays.asList("区分", "商品コード", "属性１名", "属性２名", "在庫数量");
            sb.append(CollectionUtil.join(headers, ",")).append("\n");

            for (List<String> stringList : listdataAll) {
                String join = CollectionUtil.join(stringList, ",");
                sb.append(join).append("\n");
            }

            System.out.println(sb.toString());

        }
    }


    public void japTest(){
        System.out.println("DATASOURCE = " + dataSource);

        System.out.println("\n1.findAll()...");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }

        System.out.println("\n2.findByEmail(String email)...");
        for (Customer customer : customerRepository.findByEmail("222@yahoo.com")) {
            System.out.println(customer);
        }

        System.out.println("\n3.findByDate(Date date)...");
        for (Customer customer : customerRepository.findByDate(sdf.parse("2017-02-12"))) {
            System.out.println(customer);
        }

        // For Stream, need @Transactional
        System.out.println("\n4.findByEmailReturnStream(@Param(\"email\") String email)...");
        try (Stream<Customer> stream = customerRepository.findByEmailReturnStream("333@yahoo.com")) {
            stream.forEach(x -> System.out.println(x));
        }

        System.out.println("Done!");

        exit(0);
    }

    @Autowired
    SysDictMapper dictMapper;

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) throws Exception {

        SysDict sysDict = dictMapper.selectByPrimaryKey(1);

        SysDict sysDict1 = dictMapper.myfindById(1);


    }
}
