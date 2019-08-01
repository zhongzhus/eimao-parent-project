package cn.its.service;

import cn.its.basic.util.PageList;
import cn.its.domain.Brand;
import cn.its.query.BrandQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author zzh
 * @since 2019-07-31
 */
public interface IBrandService extends IService<Brand> {
    public PageList<Brand> queryPage(BrandQuery query);
}
