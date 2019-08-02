package cn.its.service.impl;

import cn.its.basic.util.AjaxResult;
import cn.its.common.RedisClient;
import cn.its.common.StaticPageClient;
import cn.its.domain.ProductType;
import cn.its.mapper.ProductTypeMapper;
import cn.its.service.IProductTypeService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2019-07-31
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private StaticPageClient staticPageClient;

    /**
     * 生成主页面
     *
     * 先根据product.type.vm生成一个product.type.vm.html
     *
     * 再根据home.vm生成主页面
     *
     */
    @Override
    public void getHomPage() {
        //第一步 ： 生成product.type.vm.html
        Map<String, Object> map = new HashMap<>();
        String templatePath = "F:\\ideaWorlspace1\\eimao-parent\\eimao-product-parent\\eimao-product-service\\src\\main\\resources\\template\\product.type.vm";
        String targetPath = "F:\\ideaWorlspace1\\eimao-parent\\eimao-product-parent\\eimao-product-service\\src\\main\\resources\\template\\product.type.vm.html";
        //model 就是List 存放所有的商品类型
        List<ProductType> productTypes = loadTypeTree();
        map.put("model", productTypes);
        map.put("templatePath", templatePath);
        map.put("targetPath", targetPath);
        staticPageClient.getStaticPage(map);

       //第二步 ： 生成home.html
       templatePath = "F:\\ideaWorlspace1\\eimao-parent\\eimao-product-parent\\eimao-product-service\\src\\main\\resources\\template\\home.vm";
        targetPath = "F:\\ideaWorlspace1\\eimao-web-parent\\eimao-web-home\\home.html";
        Map<String, Object> model = new HashMap<String, Object>();
        //model 中要有一个数据是staticRoot
        model.put("staticRoot","F:\\ideaWorlspace1\\eimao-parent\\eimao-product-parent\\eimao-product-service\\src\\main\\resources\\");
        map.put("model",model);
        map.put("templatePath",templatePath);
        map.put("targetPath",targetPath);

        staticPageClient.getStaticPage(map);
    }



    /**
     * 递归方式实现加载类型树
     * 缺点：
     * （1）性能很低，要发送多次sql
     * （2）递归的深度可能会导致栈溢出
     *
     * @return
     */
    @Override
    public List<ProductType> loadTypeTree() {
        //递归方式实现
//        return recursive(0L);
        String productTypesStr = (String) redisClient.get("productTypes").getObject();
        List<ProductType> productTypes = JSON.parseArray(productTypesStr, ProductType.class);
        if (productTypes == null || productTypes.size()<=0) {
            productTypes = loop();
            String jsonString = JSON.toJSONString(productTypes);
            redisClient.set("productTypes", jsonString);
        }
        return productTypes;
    }

    /**
     * 递归方式
     * @return
     */
    private List<ProductType> recursive(long id) {
        //查询所有一级菜单
        List<ProductType> parents = baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid", id));
        for (ProductType parent : parents) {
            //这里开始递归获取所有的子集菜单
            List<ProductType> children = recursive(parent.getId());
            if (!children.isEmpty()) {
                parent.setChildren(children);
            }
        }
        return parents;
    }

    /**
     * 循环方式
     * @return
     */
    private List<ProductType> loop() {
        List<ProductType> productTypes = baseMapper.selectList(null);
        List<ProductType> list = new ArrayList<>();
        Map<Long, ProductType> map = new HashMap<>();
        //先全部存入map中
        for (ProductType productType : productTypes) {
            map.put(productType.getId(), productType);
        }
        //循环判断pid是不是0，是不是一级菜单，不是就存入对应pid对象（父菜单）的子集菜单中，
        for (ProductType productType : productTypes) {
            if (productType.getPid()==0){
                list.add(productType);
            }else{
                ProductType parent = map.get(productType.getPid());
                parent.getChildren().add(productType);
            }
        }
        return list;

    }
}
