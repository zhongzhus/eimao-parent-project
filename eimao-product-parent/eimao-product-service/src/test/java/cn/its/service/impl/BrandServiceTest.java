package cn.its.service.impl;

import cn.its.basic.util.PageList;
import cn.its.domain.Brand;
import cn.its.query.BrandQuery;
import cn.its.service.IBrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandServiceTest {

    //测试
    @Autowired
    private IBrandService iBrandService;
    @Test
    public void queryPage() {
        PageList<Brand> page = iBrandService.queryPage(new BrandQuery());
        System.out.println(page.getTotal());
        page.getRows().forEach(e-> System.out.println(e));
    }
}