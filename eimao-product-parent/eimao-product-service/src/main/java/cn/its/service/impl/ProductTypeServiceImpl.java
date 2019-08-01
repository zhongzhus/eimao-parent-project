package cn.its.service.impl;

import cn.its.domain.ProductType;
import cn.its.mapper.ProductTypeMapper;
import cn.its.service.IProductTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        return loop();
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
