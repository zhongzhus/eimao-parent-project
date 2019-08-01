package cn.its.service.impl;

import cn.its.domain.Product;
import cn.its.mapper.ProductMapper;
import cn.its.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2019-07-31
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
