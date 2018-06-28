package com.artgeektech.househub.service;

import com.artgeektech.househub.common.Constant;
import com.artgeektech.househub.common.HouseUserType;
import com.artgeektech.househub.common.PageData;
import com.artgeektech.househub.common.PageParams;
import com.artgeektech.househub.domain.House;
import com.artgeektech.househub.domain.HouseUser;
import com.artgeektech.househub.domain.User;
import com.artgeektech.househub.repository.HouseRepository;
import com.artgeektech.househub.repository.HouseUserRepository;
import com.artgeektech.househub.utils.BeanHelper;
import com.google.common.base.Joiner;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by guang on 2:53 PM 4/30/18.
 */
@Service
public class HouseService {

    @Value("${file.prefix}")
    private String imgPrefix;

    @Autowired
    private FileService fileService;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseUserRepository houseUserRepository;

    private Logger logger = LoggerFactory.getLogger(HouseService.class);

//    @Autowired
//    private MailService mailService;

    /**
     * 1.查询小区
     * 2.添加图片服务器地址前缀
     * 3.构建分页结果
     * @param query
     * @param pageParams
     */
    public PageData<House> queryPageHouse(House query, PageParams pageParams) {
        logger.info("sorting:" + query.getSort());
        List<House> houses;
        if (query.getSort().equals("price_asc")) {
            houses = houseRepository.queryPageHousePriceAsc(
                query.getName(), query.getType(), pageParams.getOffset(), pageParams.getLimit()
            );
        } else if (query.getSort().equals("price_desc")) {
            houses = houseRepository.queryPageHousePriceDesc(
                query.getName(), query.getType(), pageParams.getOffset(), pageParams.getLimit()
            );
        } else {
            houses = houseRepository.queryPageHouseTimeDesc(
                query.getName(), query.getType(), pageParams.getOffset(), pageParams.getLimit()
            );
        }
        if (houses.size() > 0) {
            logger.info("inside method queryPageHouse before");
            logger.info(houses.get(0).getName());
            logger.info(houses.get(0).getImages());
            logger.info(houses.get(0).getFirstImg());
            logger.info(houses.get(0).getImageList().get(0));
            logger.info("inside method queryPageHouse after");
            setFullPaths(houses);
            logger.info(houses.get(0).getFirstImg());
            logger.info(houses.get(0).getImageList().get(0));
        }
        Long totalCount = houseRepository.queryPageCount(query.getName(), query.getType());
        return PageData.buildPage(houses, totalCount, pageParams.getPageSize(), pageParams.getPageNum());
    }

    private void setFullPaths(List<House> houses) {
        houses.forEach(this::setFullPaths);
    }

    private void setFullPaths(House house) {
        house.setImages(house.getImages());
        house.setFloorPlan(house.getFloorPlan());
        house.setProperties(house.getProperties());
        house.setFirstImg(imgPrefix + house.getFirstImg());
        house.setImageList(house.getImageList().stream()
            .map(img -> imgPrefix + img).collect(Collectors.toList()));
        house.setFloorPlanList(house.getFloorPlanList().stream()
            .map(img -> imgPrefix + img).collect(Collectors.toList()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void addHouse(House house, User user) {
        if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
            String paths = Joiner.on(",").join(fileService.getImgPaths(house.getHouseFiles()));
            house.setImages(paths);
        }
        if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
            String paths = Joiner.on(",").join(fileService.getImgPaths(house.getFloorPlanFiles()));
            house.setFloorPlan(paths);
        }
        house.setStatus(Constant.ON_THE_MARKET);
        BeanHelper.setDefaultProp(house, House.class);
        BeanHelper.onInsert(house);
        house = houseRepository.save(house);
        bindUserToHouse(house.getId(), user.getId(), HouseUserType.SALE.value);
    }

    public void bindUserToHouse(Long houseId, Long userId, Integer type) {
        HouseUser exist = findByHouseIdAndUserIdAndType(houseId, userId, type);
        if (exist == null) {
            HouseUser houseUser = new HouseUser(houseId, userId, type);
            BeanHelper.setDefaultProp(houseUser, HouseUser.class);
            BeanHelper.onInsert(houseUser);
            houseUserRepository.save(houseUser);
        }
    }

    public void unbindUserToHouse(Long houseId, Long userId, Integer type) {
        houseUserRepository.deleteByHouseIdAndUserIdAndType(houseId, userId, type);
    }

    public HouseUser findByHouseIdAndUserIdAndType(Long houseId, Long userId, Integer type) {
        return houseUserRepository.findByHouseIdAndUserIdAndType(houseId, userId, type);
    }

    public PageData<House> queryPageHouseUser(Long userId, Integer houseUserType, PageParams pageParams) {
        List<House> houses = houseRepository.queryPageHouseUser(
            userId, houseUserType, pageParams.getOffset(), pageParams.getLimit()
        );
        setFullPaths(houses);
        Long totalCount = houseRepository.queryPageCountHouseUser(userId, houseUserType);
        return PageData.buildPage(houses, totalCount, pageParams.getPageSize(), pageParams.getPageNum());
    }

    public House find(Long id) {
        House house = houseRepository.findOne(id);
        if (house == null) {
            return null;
        }
//        house.setImages(house.getImages());
//        house.setFloorPlan(house.getFloorPlan());
//        house.setProperties(house.getProperties());
        setFullPaths(house);
        return house;
    }
}
